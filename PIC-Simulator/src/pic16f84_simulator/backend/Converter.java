// Linus
package pic16f84_simulator.backend;

import java.util.Arrays;

public class Converter {

    // 'F' zu --> 15
    public static int hexToDec(char hex) {        
        return Integer.parseInt(Character.toString(hex), 16);
    }

    
    // 'F' zu --> [1,1,1,1]
    public static int[] hexToBinary(char hex, int numberOfBinaryDigits){
        int[] result = new int[numberOfBinaryDigits];

        String hexAsBinary = Integer.toBinaryString(Integer.parseInt(Character.toString(hex), 16));
        char[] binaryAsCharArray = hexAsBinary.toCharArray();
        int lastIndexOfBinary = binaryAsCharArray.length - 1;

        if(binaryAsCharArray.length > result.length) {
            throw new BinaryArrayIsToSmall("The *requested* BinaryArray-size does not match the *actual* BinaryArray-size");
        }

        for(int i = (numberOfBinaryDigits - 1); i >=0; i--) {

            if(lastIndexOfBinary >= 0) {
                result[i] = Character.getNumericValue(binaryAsCharArray[lastIndexOfBinary]);
                lastIndexOfBinary--;
            }
            else break;
        }
        return result;        
    }


    // [1,1,1,1] zu --> (0x)"F"
    public static String binaryToHex(int[] binaryWord) {        
        String binaryString = "";
        for(int x : binaryWord) {
            binaryString += x;
        }        
        int binaryToDec = Integer.parseInt(binaryString, 2);
        
        return Integer.toString(binaryToDec, 16);
    }
    
    

}
