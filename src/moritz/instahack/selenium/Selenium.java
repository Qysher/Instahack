package moritz.instahack.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Comparator;

public class Selenium {

    public FirefoxDriver webDriver;

    public Selenium(boolean headless) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if(headless)
            firefoxOptions.addArguments("--headless");
        webDriver = new FirefoxDriver(firefoxOptions);
        webDriver.get("https://www.instagram.com/");
        loadCookies();
    }

    public String getRawHTMLFromURL(String url) {
        webDriver.get(url);
        return webDriver.getPageSource();
    }

    public String getRawHTMLFromURLWaitForElement(String url, By elementIdentifier) {
        webDriver.get(url);
        waitForElement(elementIdentifier, 30);
        return webDriver.getPageSource();
    }

    public WebElement waitForElement(By identifier, long timeout) {
        try {
            if(timeout == 0) return webDriver.findElement(identifier);
            return new WebDriverWait(webDriver, timeout).until(
                    ExpectedConditions.elementToBeClickable(identifier));
        } catch (TimeoutException | NoSuchElementException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadCookies() {
        ArrayList<Cookie> cookies = CookieManager.loadCookies();
        cookies.sort(Comparator.comparing(Cookie::getDomain));
        String lastCookieDomain = "";
        for(Cookie cookie : cookies) {
            try {
                String cookieDomain = cookie.getDomain();
                if(cookieDomain.startsWith(".")) cookieDomain = "https://www" + cookieDomain;
                if(!cookieDomain.startsWith("https://")) cookieDomain = "https://" + cookieDomain;
                System.out.println(cookieDomain);

                if(!cookieDomain.equals(lastCookieDomain))
                    webDriver.get(cookieDomain);

                webDriver.manage().addCookie(cookie);
                lastCookieDomain = cookieDomain;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveCookies() {
        CookieManager.saveCookies(webDriver);
    }

    public void quit() {
        saveCookies();
        webDriver.quit();
    }

}
