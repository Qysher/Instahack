package moritz.instahack.utils;

import org.openqa.selenium.By;

public class StaticFinalValues {

    public static final String XPATH_POST = "/html[1]/body[1]/div[1]/section[1]/main[1]/div[1]/div[1]/article[1]";
    public static final String XPATH_MEDIA_IMAGE = "/html[1]/body[1]/div[1]/section[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[1]/div[1]/div[1]/img[1]";
    public static final String XPATH_MEDIA_IMAGE_WITH_TAGS = "html[1]/body[1]/div[1]/section[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[1]/div[1]/div[1]/div[1]/img[1]";
    public static final String XPATH_MEDIA_VIDEO = "/html[1]/body[1]/div[1]/section[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/video[1]";
    public static final String XPATH_MEDIA_MULTI = "/html[1]/body[1]/div[1]/section[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[2]/div[1]/div[1]/div[1]/div[1]/img[1]";
    public static final String XPATH_MEDIA_MULTI_WITH_TAGS = "/html[1]/body[1]/div[1]/section[1]/main[1]/div[1]/div[1]/article[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[2]/div[1]/div[1]/div[1]/div[1]/img[1]";
    public static final By[] MEDIA_IDENTIFIERS = {
            By.xpath(StaticFinalValues.XPATH_MEDIA_IMAGE),
            By.xpath(StaticFinalValues.XPATH_MEDIA_IMAGE_WITH_TAGS),
            By.xpath(StaticFinalValues.XPATH_MEDIA_MULTI),
            By.xpath(StaticFinalValues.XPATH_MEDIA_MULTI_WITH_TAGS),
            By.xpath(StaticFinalValues.XPATH_MEDIA_VIDEO)
    };

}
