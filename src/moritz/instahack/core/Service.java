package moritz.instahack.core;

import moritz.instahack.selenium.CookieManager;
import moritz.instahack.selenium.Selenium;
import moritz.instahack.utils.StaticFinalValues;
import moritz.instahack.utils.ToolBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.net.URI;

public class Service {

    public static Selenium selenium = null;

    public static void main(String[] args) {
        new Thread(() -> selenium = new Selenium(true)).start();
        while (selenium == null) Thread.yield();

        String clipboardContent, previousClipboardContent = ToolBox.valueOfSystemClipboardAsString();

        System.out.println("Ready.");

        while (true) {
            try {
                Thread.sleep(100);
                clipboardContent = ToolBox.valueOfSystemClipboardAsString();

                if(clipboardContent.equals(previousClipboardContent)) continue;
                previousClipboardContent = clipboardContent;

                if(!clipboardContent.startsWith("https://www.instagram.com/")) continue;
                String finalClipboardContent = clipboardContent;
                if(clipboardContent.startsWith("https://www.instagram.com/accounts/login/")) {
                    getLoginCookies();
                    continue;
                }

                new Thread(() -> getInstagramSourceURL(finalClipboardContent)).start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void getInstagramSourceURL(String url) {
        try {
            System.out.println("Loading Website..");
            selenium.webDriver.get(url);
            selenium.waitForElement(By.xpath(StaticFinalValues.XPATH_POST), 30L);

            WebElement media = null;
            for(By identifier : StaticFinalValues.MEDIA_IDENTIFIERS) {
                media = selenium.waitForElement(identifier, 0L);
                if(media != null) break;
            }
            if(media == null) return;

            //if(media == null) media = selenium.waitForElement(By.xpath(StaticFinalValues.XPATH_MEDIA_VIDEO), 0L);
            //if(media == null) return;

            String mediaSourceURL = media.getAttribute("src");
            Desktop.getDesktop().browse(new URI(mediaSourceURL));
            System.out.println(mediaSourceURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getLoginCookies() {
        try {
            selenium.quit();
            selenium = new Selenium(false);
            waitForSeleniumDriver();
            selenium.webDriver.get("https://www.instagram.com/accounts/login/");

            while(!selenium.webDriver.getCurrentUrl().equals("https://www.instagram.com/")) {
                try { Thread.sleep(100); } catch (Exception e) {}
            }

            try { Thread.sleep(5000); } catch (Exception e) {}

            selenium.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        selenium = new Selenium(true);
        waitForSeleniumDriver();
    }

    public static void waitForSeleniumDriver() {
        while (selenium == null) Thread.yield();
    }

}
