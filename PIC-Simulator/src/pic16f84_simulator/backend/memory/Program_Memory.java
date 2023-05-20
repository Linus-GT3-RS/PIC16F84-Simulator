package pic16f84_simulator.backend.memory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import pic16f84_simulator.backend.tools.Utils;

public class Program_Memory extends Template_Memory {// Eduard

    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
    public Program_Memory(){
        super(1024, 14);
        allow = Utils.allow(allow, this);
    }
    
    public int[] readDataCell(int indexCell) {
        return super.readCell(indexCell);
    }
    
    public int readSpecificBit(int indexCell, int indexBit) {
        return super.readBit(indexCell, indexBit);
    }
    
    @Override
    public void writeDataCell(int indexCell, int[] data) {
        super.writeDataCell(indexCell, data);
    }
    
    @Override
    public void writeSpecificBit(int indexCell, int indexBit, int bit) {
        super.writeSpecificBit(indexCell, indexBit, bit);
    }

    public void loadTestProgram(String path) {    
        File file = new File(path); // Read File

        //If it has problems this code will be executed
        if (!file.canRead() || !file.isFile())
        {
            System.exit(0);
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(path));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                
                // Logic Part
                boolean contain = false;
                String substring = zeile.substring(0, 9);
                String result = "";

                for(int i = 0; i <substring.length(); i++) {
                    if(substring.charAt(i) != ' ') {
                        result=result+substring.charAt(i);
                        contain = true;
                    }
                }
                if(contain) {
                    store(result);
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {}
        }
    }


    /*
     * @author 
     * @param data get hexdecimal number
     */
    public void store(String data) {
        // Get index of Datacell
        int pmDataCell = Integer.parseInt(data.substring(0,4),16);
        
        // result declaration
        int[] result = new int[14];
        int indexResult = 0;
        
        // go through data for extracting them
        for(int i = 4; i<data.length();i++) {
            int[] binaryCode = Utils.hexToBinary(data.charAt(i));
            if(i==4) {
                for(int j=2;j<binaryCode.length;j++) {
                    result[indexResult] =binaryCode[j];
                    indexResult++;
                }
            }else {
                for(int binary : binaryCode) {
                    result[indexResult] = binary;
                    indexResult++;
                }
            }
        }
        super.writeDataCell(pmDataCell, result);
    }

}



