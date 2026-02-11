package dev.mattiassoderberg.addressbook.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public final class ImageUtil {

    private ImageUtil() {}

    public static BufferedImage cropImageSquare(byte[] image, int squareSize, int x, int y) throws IOException {

        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        if (originalImage.getHeight() == squareSize && originalImage.getWidth() == squareSize) {
            return originalImage;
        }

        return originalImage.getSubimage(
                x,
                y,
                squareSize,
                squareSize
        );
    }

    public static String getExtension(String fileName) {
        if (fileName == null) {
            return null;
        }

        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex >= 0) {
            return fileName.substring(dotIndex + 1);
        }

        return "";
    }
}
