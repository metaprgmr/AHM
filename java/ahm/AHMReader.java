package ahm;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Reads a AHM tile data in zip.
 * The data size is assumed 1201x1201.
 *
 * @author James Huang
 */
public class AHMReader {

	static int dim = 1200;

	public static AHM readAHMArchive(File zipFile) throws Exception {
        final int dim_1 = dim + 1;
        int[][] data = null;

        try (ZipInputStream zis = openZip(zipFile)) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.getName().endsWith(".ahm")) {
                    DataInputStream dis = new DataInputStream(zis);
                    data = new int[dim_1][dim_1];
                    for (int row=0; row<dim_1; row++)
                        for (int col=0; col<dim_1; col++)
                           data[row][col] = dis.readInt();
                    return new AHM(data);
                }
            }
            return null;
        }
    }

    static ZipInputStream openZip(File zf) throws Exception {
        return new ZipInputStream(new BufferedInputStream(new FileInputStream(zf)));
    }

} // end.
