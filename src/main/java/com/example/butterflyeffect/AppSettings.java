package com.example.butterflyeffect;
import java.io.*;
import java.util.Properties;

public class AppSettings {
    private static final String PHOTO_TWO_IMAGE_PATH_KEY = "photoTwoImagePath";
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = new FileInputStream("app-settings.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPhotoTwoImagePath() {
        return properties.getProperty(PHOTO_TWO_IMAGE_PATH_KEY, "");
    }

    public static void setPhotoTwoImagePath(String imagePath) {
        properties.setProperty(PHOTO_TWO_IMAGE_PATH_KEY, imagePath);
        try (OutputStream outputStream = new FileOutputStream("app-settings.properties")) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final String PHOTO_ONE_IMAGE_PATH_KEY = "photoOneImagePath";
    private static final Properties property = new Properties();

    static {
        try (InputStream inputStream = new FileInputStream("app-settings.properties")) {
            property.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPhotoOneImagePath() {
        return property.getProperty(PHOTO_ONE_IMAGE_PATH_KEY, "");
    }

    public static void setPhotoOneImagePath(String imagePath) {
        property.setProperty(PHOTO_ONE_IMAGE_PATH_KEY, imagePath);
        try (OutputStream outputStream = new FileOutputStream("app-settings.properties")) {
            property.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
