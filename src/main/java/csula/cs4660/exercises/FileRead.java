package csula.cs4660.exercises;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Introduction Java exercise to read file
 */
public class FileRead {
    private int[][] numbers;
    /**
     * Read the file and store the content to 2d array of int
     * @param file read file
     */
    public FileRead(File file) {
        // TODO: read the file content and store content into numbers
    
    	BufferedReader br = null;

        try {
    	
        	BufferedReader buffer = new BufferedReader(new FileReader(file));

            String line;
            int row = 0;
            int size = 0;

            while ((line = buffer.readLine()) != null) {
            	//System.out.println(line);
                String[] vals = line.trim().split("\\s+");

                // Lazy instantiation.
                if (numbers == null) {
                    size = vals.length;
                    numbers = new int[size][size];
                }

                for (int col = 0; col < size; col++) {
                	numbers[row][col] = Integer.parseInt(vals[col]);
                }

                row++;
            }


        	    

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    
    }

    /**
     * Read the file assuming following by the format of split by space and next
     * line. Display the sum for each line and tell me
     * which line has the highest mean.
     *
     * lineNumber starts with 0 (programming friendly!)
     */
    public int mean(int lineNumber) {
    	
    	int sum= 0;
    	for(int i:numbers[lineNumber]){
    		sum = sum+i;	
    	}
		return sum/numbers[lineNumber].length;
    }

    public int max(int lineNumber) {
    	int max= 0;
    	for(int i:numbers[lineNumber]){
    		if(max<i){
    			max = i;
    		}
    	}
		return max;
    }

    public int min(int lineNumber) {
    	int min= 0;
    	for(int i:numbers[lineNumber]){
    		if(min>i){
    			min = i;
    		}
    	}
		return min;
    }

    public int sum(int lineNumber) {
    	int sum= 0;
    	for(int i:numbers[lineNumber]){
    		sum = sum+i;	
    	}
		return sum;
    }
}
