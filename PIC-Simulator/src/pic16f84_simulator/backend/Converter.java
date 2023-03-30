// Linus
package pic16f84_simulator.backend;

public class Converter {
    public static int[] hexToBinary(char hex){
        int[] binarycode = new int[] {0,0,0,0};
        switch (hex) {
        case '0' -> {}
        case '1' -> {binarycode[3] = 1;}
        case '2' -> {binarycode[2] = 1;}
        case '3' -> {binarycode[3] = 1;
                     binarycode[2] = 1;}
        case '4' -> {binarycode[1] = 1;}
        case '5' -> {binarycode[3] = 1;
                     binarycode[1] = 1;}
        case '6' -> {binarycode[2] = 1;
                     binarycode[1] = 1;}
        case '7' -> {binarycode[3] = 1;
                     binarycode[2] = 1;
                     binarycode[1] = 1;}
        case '8' -> {binarycode[0] = 1;}
        case '9' -> {binarycode[3] = 1;
                     binarycode[0] = 1;}
        case 'A' -> {binarycode[2] = 1;
                     binarycode[0] = 1;}
        case 'B' -> {binarycode[3] = 1;
                     binarycode[2] = 1;
                     binarycode[0] = 1;}
        case 'C' -> {binarycode[1] = 1;
                     binarycode[0] = 1;}
        case 'D' -> {binarycode[3] = 1;
                     binarycode[1] = 1;
                     binarycode[0] = 1;}
        case 'E' -> {binarycode[2] = 1;
                     binarycode[1] = 1;
                     binarycode[0] = 1;}
        case 'F' -> {binarycode[3] = 1;
                     binarycode[2] = 1;
                     binarycode[1] = 1;
                     binarycode[0] = 1;}
        }
        return binarycode;
    }
    
    public static String binaryToHex(int[] binary) {
        return null;
    }
    
    public static int hexToDec(char hex) {
        int value = 0;
        switch (hex) {
        case '0' -> {value = 0;}
        case '1' -> {value = 1;}
        case '2' -> {value = 2;}
        case '3' -> {value = 3;}
        case '4' -> {value = 4;}
        case '5' -> {value = 5;}
        case '6' -> {value = 6;}
        case '7' -> {value = 7;}
        case '8' -> {value = 8;}
        case '9' -> {value = 9;}
        case 'A' -> {value = 10;}
        case 'B' -> {value = 11;}
        case 'C' -> {value = 12;}
        case 'D' -> {value = 13;}
        case 'E' -> {value = 14;}
        case 'F' -> {value = 15;}
        }
        return value;
    }
}
