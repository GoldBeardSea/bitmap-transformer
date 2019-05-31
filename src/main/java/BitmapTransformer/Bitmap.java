package BitmapTransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Bitmap {

    public String inputPath;
    public String outputPath;
    public String transformName;
    public BufferedImage image;


    public Bitmap(String inputPath, String outputPath, String transformName) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.transformName = transformName;
    }

    public void converterToBitmap () throws IOException {
        try {
            image = ImageIO.read(new File(inputPath));

        } catch(FileNotFoundException e) {
            System.out.println("Input File not found, cannot convert to Bitmap");

        }
    }

    public void createThumbnail() {
        //http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Create_thumbnail_views_of_images.htm
        BufferedImage thumbNail = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) thumbNail.getGraphics();
        g2d.drawImage(image, 0, 0, thumbNail.getWidth() - 1, thumbNail.getHeight() - 1, 0, 0,
                image.getWidth() - 1, image.getHeight() - 1, null);
        g2d.dispose();
        download(thumbNail);

    }

    public void makeGreyScale() {
        BufferedImage greyScaleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.getWidth(); ++x) {
            for (int y = 0; y < image.getHeight(); ++y) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24)&0xFF;
                int red = (rgb >> 16)&0xFF;
                int green = (rgb >> 8)&0xFF;
                int blue = (rgb)&0xFF;
                int average = (red+green+blue)/3;
                rgb = (alpha << 24) | (average << 16)| (average << 8) | average;
                greyScaleImage.setRGB(x,y, rgb);
            }
        }
        download((greyScaleImage));
    }

    public void download(BufferedImage img) {
        try {
            RenderedImage transformedImage = img;
            ImageIO.write(transformedImage, "bmp", new File(outputPath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
