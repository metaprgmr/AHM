package ahm.usage;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ahm.AHM;

/**
 * A utility to generate a PNG image of an AHM data set.
 *
 * @author James Huang
 */
public class AHMGenImage {

    public static void generateImage(int[][] data, File imageFile) throws Exception {
        int w = data[0].length, h = data.length;
        ABGRWriter iw = new ABGRWriter(w, h);

        for (int i=0; i<w; i++)
            for (int j=0; j<h; j++)
                setPixel(iw, i, h-j-1, data[j][i]);

        iw.write(imageFile);
    }

    static void setPixel(ABGRWriter iw, int imgX, int imgY, int ahmData) {
    	int altitude = (short) ahmData;

    	int[] rgb;
        switch (AHM.getType(ahmData)) {
        case LAND:  rgb = landColor(altitude); break;
        case RIVER:
        case LAKE:  rgb = LAND_WATER_COLOR; break;
        case OCEAN_FLOOR:
        case OCEAN: rgb = OCEAN_COLOR; break;
        default:    rgb = NO_DATA_COLOR; break;
        }
        iw.setPixel(imgX, imgY, rgb);
    }

    static int[] landColor(int height) {
    	// you can implement color coding of altitude here
        return LAND_COLOR;
    }

    static final int[] NO_DATA_COLOR    = ABGRWriter.toColor("ffffff");
    static final int[] LAND_COLOR       = ABGRWriter.toColor("00994d");
    static final int[] LAND_WATER_COLOR = ABGRWriter.toColor("66ffff");
    static final int[] OCEAN_COLOR      = ABGRWriter.toColor("0000cc");

} // end of AHMGenImage.



class ABGRWriter {

    private BufferedImage image;
    private WritableRaster raster;

    public ABGRWriter(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        this.raster = image.getRaster();
    }

    public void write(File file) throws IOException {
        String fileName = file.getName();
        int idx = fileName.lastIndexOf(".");
        String ext = (idx > 0) ? fileName.substring(idx+1).toLowerCase() : "png";
        ImageIO.write(image, ext, file);
    }

    public void setPixel(int x, int y, int[] rgba) {
           raster.setPixel(x, y, rgba);
    }

    public static int[] toColor(String color) {
        return new int[] {
                Integer.parseInt(color.substring(0,2), 16),
                Integer.parseInt(color.substring(2,4), 16),
                Integer.parseInt(color.substring(4,6), 16),
                255
        };
    }

} // end of ABGRWriter.
