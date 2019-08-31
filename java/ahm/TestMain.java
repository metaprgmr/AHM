package ahm;

import java.io.File;

import github.metaprgmr.ahm.usage.AHMGenImage;
import github.metaprgmr.ahm.usage.AHMGetInfo;

public class TestMain {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Required parameter: some-ahm.zip");
            System.exit(0);
        }

        String fileName = args[0];

        // Read the AHM data
        AHM ahm = AHMReader.readAHMArchive(new File(fileName));

        // Print out its stats
        AHMGetInfo.showInfo(ahm);

        // Generate its image
        File imageFile = new File("output.png");
        System.err.println("Generating image file " + imageFile.getAbsolutePath());
        AHMGenImage.generateImage(ahm.getData(), imageFile);
    }

} // end.
