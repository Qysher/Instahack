package moritz.instahack.core;

import moritz.instahack.selenium.Selenium;
import moritz.instahack.utils.StaticFinalValues;
import moritz.instahack.utils.ToolBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.logging.LogManager;

public class Start {

    public static Selenium selenium = null;

    public static void main(String[] args) {
        try {
            LogManager.getLogManager().reset();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new Thread(() -> selenium = new Selenium(true)).start();

            String clipboard = ToolBox.valueOfSystemClipboardAsString();
            String instaUrl = JOptionPane.showInputDialog("Insta URL",
                    clipboard.startsWith("https://www.instagram.com/") ? clipboard : "");

            while (selenium == null) Thread.yield();

            selenium.webDriver.get(instaUrl);
            System.out.println("Post..");
            selenium.waitForElement(By.xpath(StaticFinalValues.XPATH_POST), 30L);
            System.out.println("Image..");
            WebElement media = selenium.waitForElement(By.xpath(StaticFinalValues.XPATH_MEDIA_IMAGE), 0L);
            System.out.println("Video..");
            if(media == null) media = selenium.waitForElement(By.xpath(StaticFinalValues.XPATH_MEDIA_VIDEO), 0L);
            if(media == null) return;

            String mediaSourceURL = media.getAttribute("src");
            Desktop.getDesktop().browse(new URI(mediaSourceURL));
            System.out.println(mediaSourceURL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
