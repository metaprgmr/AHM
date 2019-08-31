package ahm.usage;

import static java.lang.System.out;
import ahm.AHM;

/**
 * A demonstration to collect the statistics of an AHM data set.
 *
 * @author James Huang
 */
public class AHMGetInfo {

    public static void showInfo(AHM ahm) {
        int width = ahm.width();
        int height = ahm.height();

        int cntNoData = 0, cntLand = 0, cntLake = 0, cntRiver = 0, cntOcean = 0;
        int minAltitude = 10000, maxAltitude = -10000;

        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                int pixel = ahm.get(x,  y);

                int altitude = (short) pixel;
                if (altitude > maxAltitude)
                    maxAltitude = altitude;
                if (altitude < minAltitude)
                    minAltitude = altitude;

                switch (AHM.getType(pixel)) {
                case NO_DATA: cntNoData++; break;
                case LAND:    cntLand++; break;
                case LAKE:    cntLake++; break;
                case RIVER:   cntRiver++; break;
                case OCEAN:   cntOcean++; break;
                }
            }
        }
        
        out.printf("     Data dimension: %d x %d\n", width, height);
        out.printf("     Altitude range: %d - %d meters\n", minAltitude, maxAltitude);
        out.println();
        out.printf("No-data point count: %6d\n", cntNoData);
        out.printf("   Land point count: %6d\n", cntLand);
        out.printf("   Lake point count: %6d\n", cntLake);
        out.printf("  River point count: %6d\n", cntRiver);
        out.printf("  Ocean point count: %6d\n", cntOcean);
        out.println();
    }

} // end.
