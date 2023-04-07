// Linus
package pic16f84_simulator.backend.tools;

import java.util.Arrays;

public class Converter {

    // 'F' zu --> 15
    public static int hexToDec(char hex) {        
        return Integer.parseInt(Character.toString(hex), 16);
    }

    
    // 'F' zu --> [1,1,1,1]
    public static int[] hexToBinary(char hex){
        int[] result = new int[4];
        
        // hex in Binärform umwandeln und in charArray speichern
        String hexAsBinary = Integer.toBinaryString(Integer.parseInt(Character.toString(hex), 16));
        char[] binaryAsCharArray = hexAsBinary.toCharArray();
        
        // binaryCharArray in Result übertragen
        int lastBinaryIndex = binaryAsCharArray.length - 1;
        for(int i = (result.length-1); i >=0 ; i--) {
            
            if(lastBinaryIndex < 0) {
                break;
            }
            result[i] = Character.getNumericValue(binaryAsCharArray[lastBinaryIndex]); 
            lastBinaryIndex--;            
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
    
    
    
    // changes binaryWord [x,y,y] of length ... to one of size ...
    public static int[] enlargeArray (int[] old, int newSize) {
        if(newSize < old.length) {
            throw new NegativeArraySizeException("New size is smaller than original size");
        }        
        int[] result = new int[newSize];
        
        if(newSize == old.length) {
            result = old;
        }        
        
        int lastIndexResult = result.length - 1;        
        for(int i = old.length - 1; i >= 0; i--) {
            result[lastIndexResult] = old[i];
            lastIndexResult--;
        }
        return result;        
    }
    
    
    
    public static String cutArray(int[] arr, int inclStart, int inclEnd) {        
        String result = "";
        
        for(int i=inclStart; i <= inclEnd; i++) {
            result += arr[i];
        }
        return result.strip();
    }
    
    
}
