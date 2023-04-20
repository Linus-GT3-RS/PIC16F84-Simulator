package pic16f84_simulator.backend.memory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pic16f84_simulator.backend.tools.Utils;

public class Program_Memory extends Template_Memory {// Eduard

    private static boolean creationAllowed = true; // secures the creation of ONLY ONE instance of this class
    public Program_Memory(){
        super(1024,14);
        if(Program_Memory.creationAllowed == false) {
            throw new IllegalArgumentException("Theres already an instance of this class: ProgramMemory !"); 
        }
        Program_Memory.creationAllowed = false; 
    }

    public void readTestProgram(String path) {
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
                    store(result);//<- Store
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
    }


    public void store(String data) {
        int counter = 0;
        int index = 0;
        int[] memoryIndex = new int[4]; // always size 4
        int[] element = new int[16];
        int[] binaryCode = new int[14];
        // Decode String
        for(int j = 0; j <data.length();j++) {
            char hex = data.charAt(j); //Cast char to String 
            if(j<4) { //Case: Memory-Index
                int number = Utils.hexToDec(hex); //Convert to dec
                memoryIndex[j]=number;
            } else if(j>= 4){ // Case: Data and Opcode
                int[] binary = Utils.hexToBinary(hex);
                for(int k = 0; k<binary.length;k++) {
                    element[counter]=binary[k];
                    counter++;
                }
            }
        }
        for(int l = 0; l<memoryIndex.length;l++) {
            index = index + memoryIndex[memoryIndex.length-1-l]*((int)Math.pow(10,l)); 
        }
        System.arraycopy(element, 2, binaryCode,0,14);
        super.writeDataCell(index,binaryCode);
    }

}


