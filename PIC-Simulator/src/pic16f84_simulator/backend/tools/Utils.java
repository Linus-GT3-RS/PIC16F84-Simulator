package pic16f84_simulator.backend.tools;

public class Utils {

    // 'F' zu --> 15
    public static int hexToDec(char hex) {
        return Integer.parseInt(Character.toString(hex), 16);
    }

    // 'F' zu --> [1,1,1,1]
    public static int[] hexToBinary(char hex) {
        int[] result = new int[4];

        // hex in Binärform umwandeln und in charArray speichern
        String hexAsBinary = Integer.toBinaryString(Integer.parseInt(Character.toString(hex), 16));
        char[] binaryAsCharArray = hexAsBinary.toCharArray();

        // binaryCharArray in Result übertragen
        int lastBinaryIndex = binaryAsCharArray.length - 1;
        for (int i = (result.length - 1); i >= 0; i--) {

            if (lastBinaryIndex < 0) {
                break;
            }
            result[i] = Character.getNumericValue(binaryAsCharArray[lastBinaryIndex]);
            lastBinaryIndex--;
        }
        return result;
    }

    // [1,1,1,1] zu --> (0x)"F"
    public static String binaryToHex(int[] binaryWord) {
        return Integer.toString(binaryToDec(binaryWord), 16);
    }

    // [1,1,1,1] zu --> 15
    public static int binaryToDec(int[] binaryWord) {
        String binaryString = "";
        for (int x : binaryWord) {
            binaryString += x;
        }
        return Integer.parseInt(binaryString, 2);

    }

    // 15 zu --> [1,1,1,1]
    /*
     * @param size -> if you want store 15 in 8 bit Word, then size is 8 and
     * undefined indexes will be filled with 0 -> [0,0,0,0,1,1,1,1]
     */
    public static int[] decToBinary(int dec, int size) {
        String binary = Integer.toBinaryString(dec);
        if (binary.length() > size) {
            throw new IllegalArgumentException("Number is to big for bit-word");
        }
        
        int[] result = new int[size];
        for (int i = size - 1; i >= size - binary.length(); i--) {
            int charPosition = i - (size - binary.length());
            if (binary.charAt(charPosition) == '0') {
                result[i] = 0;
            } else {
                result[i] = 1;
            }
        }
        return result;
    }

    // changes binaryWord [x,y,y] of length ... to one of size ...
    public static int[] enlargeArray(int[] old, int newSize) {
        if (newSize < old.length) {
            throw new NegativeArraySizeException("New size is smaller than original size");
        }
        int[] result = new int[newSize];

        if (newSize == old.length) {
            result = old;
        }

        int lastIndexResult = result.length - 1;
        for (int i = old.length - 1; i >= 0; i--) {
            result[lastIndexResult] = old[i];
            lastIndexResult--;
        }
        return result;
    }

    public static String cutArray(int[] arr, int inclStart, int inclEnd) {
        String result = "";

        for (int i = inclStart; i <= inclEnd; i++) {
            result += arr[i];
        }
        return result.strip();
    }
    
    public static boolean allow (boolean condt, Object c) {
        if(condt == false) {
            throw new IllegalArgumentException("Es gibt schon eine Instanz von " + c.getClass());
        }
        return false; 
    }

}
