package pic16f84_simulator.backend.memory;
import pic16f84_simulator.backend.Converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramMemory extends Memory {
    //private int[][] memory;
    
    ProgramMemory(){
        super(1024,16);
    }
    //int[] getMemory(int i) {
    //    return this.memory[i];
    //}
    
    public List<String> readTestProgram(String path) {
        // Initialize global variable
        List<String> data = new ArrayList<String>();
        // Read File
        File file = new File(path);
        
        //If it has problems this code will be executed
        if (!file.canRead() || !file.isFile())
            System.exit(0);
            BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                // Logic Part
                boolean contain = false;
                String substring = zeile.substring(0, 9);
                String result = "";
                for(int i = 0;i <substring.length();i++) {
                    if(substring.charAt(i) != ' ') {
                        result=result+substring.charAt(i);
                        contain = true;
                    }
                }
                if(contain) {
                    Store(result);//<- Store
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return data;
    }
    public void Store(String data) {
        // Decode String-List
        int counter = 0;
        int index = 0;
        int[] memoryIndex = new int[4]; // always size 4
        int[] binaryCode = new int[16]; // we use only 14 bit, but hexa is 4,8,12,16 possible -> first two bits unnecessary
        // Decode String
        for(int j = 0; j <data.length();j++) {
            char hex = data.charAt(j); //Cast char to String 
            if(j<4) { //Case: Memory-Index
                int number = Converter.hexToDec(hex); //Convert to dec
                memoryIndex[j]=number;
            } else { // Case: Memory content
                int[] binary = Converter.hexToBinary(hex);
                for(int k = 0; k<binary.length;k++) {
                    binaryCode[counter]=binary[k];
                    counter++;
                }
            }
        }
        for(int l = 0; l<memoryIndex.length;l++) {
            index = index + memoryIndex[memoryIndex.length-1-l]*((int)Math.pow(10,l)); 
        }
        super.setMemory(index,binaryCode);
    }

}
    
    
