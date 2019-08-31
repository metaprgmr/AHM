package ahm;

/**
 * The <b>Annotated Height Map</b> data representation.
 * 
 * <ol>
 * <li>Byte[0:1] &mdash; annotations</li>
 * <li>Byte[2:3] &mdash; the HGT value</li>
 *
 * <pre>
 *                Byte-0            Byte-1
 *      ----------------- ----------------
 *       7 6 5 4 3 2 1 0   7 6 5 4 3 2 1 0
 *      ----------------- ----------------
 *                       |           0 0 0    no-data
 *                       |           0 0 1    land
 *                       |           0 1 0    ocean
 *                       |           0 1 1    lake
 *                       |           1 0 0    river
 *                       |           1 0 1    ocean-floor
 *                       |           1 1 0      (reserved)
 *                       |           1 1 1      (reserved)
 *       ? ? ? ? ? ? ? ? | ? ? ? ? ?            (free use)
 *     ------------------ -----------------
 * </pre>
 * 
 * @author James Huang
 */
public class AHM {

    public enum SurfaceType {
        NO_DATA     (0x00000000),
        LAND        (0x00010000),
        OCEAN       (0x00020000),
        RIVER       (0x00030000),
        LAKE        (0x00040000),
        OCEAN_FLOOR (0x00050000),
        RESERVED_1  (0x00060000),
        RESERVED_2  (0x00070000);

        public final int flag;
        private SurfaceType(int flag) { this.flag = flag; }
        
        public static SurfaceType get(int flag) {
            switch(flag) {
            case 0x000000: return NO_DATA;
            case 0x010000: return LAND;
            case 0x020000: return OCEAN;
            case 0x030000: return RIVER;
            case 0x040000: return LAKE;
            case 0x050000: return OCEAN_FLOOR;
            case 0x060000: return RESERVED_1;
            case 0x070000: return RESERVED_2;
            default:       throw new RuntimeException("Unknown surface type value: " + ((flag>>16) & 0x07));
            }
        }
    }

    protected int[][] data; // [0,0] is the lower-left corner.

    public AHM(int[][] data) {
        this.data = data;
    }

    public int[][] getData() {
        return data;
    }

    public int width() {
        return data == null ? 0 : data[0].length;
    }

    public int height() {
        return data == null ? 0 : data.length;
    }

    public int get(int x, int y) {
        return (data != null) ? data[data.length-y-1][x] : SurfaceType.NO_DATA.flag;
    }

    public static SurfaceType getType(int ahm) {
        return SurfaceType.get(ahm & 0x00070000);
    }

    public static int setType(int value, SurfaceType type) {
        return (value & 0xfff8ffff) | type.flag;
    }

    public static int setMark(int value, int mark) {
        return (value & 0x0007ffff) | ((mark << 19) & 0xfff80000);
    }

    public static int getMark(int height) {
        return (height >> 19) & 0xff1f;
    }

    public static boolean hasMark(int value) {
        return (value & 0xfff80000) != 0;
    }

} // end.
