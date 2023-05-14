package renderer;

import primitives.Color;
import renderer.ImageWriter;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Efrat Roth and Hadassah Stulman
 */
class ImageWriterTests {

    /**
     This method verifies the functionality of creating and coloring a basic image.
     */
    @Test
    void WriteImageTest() {

        ImageWriter image = new ImageWriter("initialImage", 800, 500);

        // Define the colors
        Color gridColor = new Color(256, 0, 0);
        Color imageColor = new Color(200, 180, 20);
        int modI = 0;

        // Iterate over the width of the image (columns)
        for (int j = 0; j< 800; j++) {

            modI = j % 50;
            // Iterate over the height of the image (rows)
            for (int i = 0; i < 500; i++) {

                // If the current pixel is on the grid, write it using the grid color.
                if (modI == 0 || i % 50 == 0)
                    image.writePixel(j,i,gridColor);

                // Otherwise, write the pixel using the image color.
                else
                    image.writePixel(j,i,imageColor);
            }
        }

        // Write the image to the disk
        image.writeToImage();
    }

}