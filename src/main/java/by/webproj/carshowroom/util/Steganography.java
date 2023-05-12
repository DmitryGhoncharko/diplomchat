package by.webproj.carshowroom.util;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Steganography {

    public static String unhideText(BufferedImage image, String message) {
        if(image==null){
            return null;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if (pixelIndex < message.length() * 3) {
                    char c = message.charAt(pixelIndex / 3);
                    int ascii = (int) c;

                    if (pixelIndex % 3 == 0) {
                        red = (red & 0xFE) | ((ascii >> 6) & 0x01);
                        green = (green & 0xFE) | ((ascii >> 5) & 0x01);
                        blue = (blue & 0xFE) | ((ascii >> 4) & 0x01);
                    }

                    if (pixelIndex % 3 == 1) {
                        red = (red & 0xFE) | ((ascii >> 2) & 0x01);
                        green = (green & 0xFE) | ((ascii >> 1) & 0x01);
                        blue = (blue & 0xFE) | (ascii & 0x01);
                    }

                    pixelIndex++;
                }

                image.setRGB(x, y, new Color(red, green, blue).getRGB());

                if (pixelIndex >= message.length() * 3) {
                    break;
                }
            }

            if (pixelIndex >= message.length() * 3) {
                break;
            }
        }

        return "image";
    }

    public static BufferedImage hideText(BufferedImage image, String message) {
        if(image==null){
            return null;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if (pixelIndex < message.length() * 3) {
                    char c = message.charAt(pixelIndex / 3);
                    int ascii = (int) c;

                    if (pixelIndex % 3 == 0) {
                        red = (red & 0xFE) | ((ascii >> 6) & 0x01);
                        green = (green & 0xFE) | ((ascii >> 5) & 0x01);
                        blue = (blue & 0xFE) | ((ascii >> 4) & 0x01);
                    }

                    if (pixelIndex % 3 == 1) {
                        red = (red & 0xFE) | ((ascii >> 2) & 0x01);
                        green = (green & 0xFE) | ((ascii >> 1) & 0x01);
                        blue = (blue & 0xFE) | (ascii & 0x01);
                    }

                    pixelIndex++;
                }

                image.setRGB(x, y, new Color(red, green, blue).getRGB());

                if (pixelIndex >= message.length() * 3) {
                    break;
                }
            }

            if (pixelIndex >= message.length() * 3) {
                break;
            }
        }

        return image;
    }
}
