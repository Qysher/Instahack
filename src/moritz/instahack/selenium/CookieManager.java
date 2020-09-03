package moritz.instahack.selenium;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

public class CookieManager {

    public static String getCookiePath() {
        return System.getenv("appdata") + "/Selenium/Cookies/";
    }

    public static ArrayList<Cookie> loadCookies() {
        ArrayList<Cookie> cookies = new ArrayList<>();

        File cookieFolder = new File(getCookiePath());
        boolean mkdirs = cookieFolder.mkdirs();

        File[] files = cookieFolder.listFiles();

        if(files != null) {
            try {
                for(File f : files) {
                    if(f != null) {
                        FileInputStream fileInputStream = new FileInputStream(f);
                        byte[] cookieData = new byte[fileInputStream.available()];
                        fileInputStream.read(cookieData);
                        fileInputStream.close();

                        Cookie cookie = deserialize(cookieData);

                        if(cookie != null) {
                            cookies.add(cookie);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(cookies.size());

        return cookies;
    }

    public static void saveCookies(WebDriver webDriver) {
        if(webDriver == null || webDriver.manage() == null || webDriver.manage().getCookies() == null) return;

        System.out.println("Saving cookies..");

        try {
            Set<Cookie> cookies = webDriver.manage().getCookies();

            File cookieFolder = new File(getCookiePath());
            boolean mkdirs = cookieFolder.mkdirs();

            for(Cookie cookie : cookies) {
                String name = cookie.getName();
                /*String value = cookie.getValue();
                String domain = cookie.getDomain();
                String path = cookie.getPath();
                Date expiry = cookie.getExpiry();
                boolean isSecure = cookie.isSecure();
                boolean isHttpOnly = cookie.isHttpOnly();*/
                byte[] cookieData = serialize(cookie);
                File cookiePath = new File(cookieFolder, name + ".cookie");

                if(cookieData != null) {
                    FileOutputStream fileOutputStream = new FileOutputStream(cookiePath);
                    fileOutputStream.write(cookieData);
                    fileOutputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCookies(String profile) {
        FileFilter fileFilter = new CookieFileFilter();

        File cookieFolder = new File(getCookiePath());
        boolean mkdirs = cookieFolder.mkdirs();

        File[] files = cookieFolder.listFiles();

        if(files != null) {
            try {
                for(File f : files) {
                    if(f != null) {
                        if(fileFilter.accept(f)) {
                            if(!f.delete()) {
                                System.out.println("Couldn't delete File! " + f.getName());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] serialize(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T deserialize(byte[] objData) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objData);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class CookieFileFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".cookie");
        }
    }

}
