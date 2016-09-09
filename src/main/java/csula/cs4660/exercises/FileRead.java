package csula.cs4660.exercises;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
=======
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
>>>>>>> 6c705035dd2e0fc0493533e929b85392ac775ab2

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
<<<<<<< HEAD
    
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
    
=======
        List<List<Integer>> listOfNumbers = Lists.newArrayList();
        try (Stream<String> stream = Files.lines(file.toPath())) {
            stream.forEach(line -> {
                List<Integer> lineNumbers = Lists.newArrayList();
                for (String token: line.split(" ")) {
                    lineNumbers.add(Integer.parseInt(token));
                }
                System.out.println(line);
                listOfNumbers.add(lineNumbers);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        numbers = converList(listOfNumbers);
>>>>>>> 6c705035dd2e0fc0493533e929b85392ac775ab2
    }

    /**
     * Read the file assuming following by the format of split by space and next
     * line. Display the sum for each line and tell me
     * which line has the highest mean.
     *
     * lineNumber starts with 0 (programming friendly!)
     */
    public int mean(int lineNumber) {
<<<<<<< HEAD
    	
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
=======
        return sum(lineNumber) / numbers[lineNumber].length;
    }

    public int max(int lineNumber) {
        int max = Integer.MIN_VALUE;
        for (int i : numbers[lineNumber]) {
            max = Integer.max(max, i);
        }
        return max;
    }

    public int min(int lineNumber) {
        int min = Integer.MAX_VALUE;
        for (int i : numbers[lineNumber]) {
            min = Integer.min(min, i);
        }
        return min;
    }

    public int sum(int lineNumber) {
        int sum = 0;
        for (int i : numbers[lineNumber]) {
            sum += i;
        }
        return 0;
>>>>>>> 6c705035dd2e0fc0493533e929b85392ac775ab2
    }

    private int[][] converList(List<List<Integer>> arrayList) {
        int[][] array = new int[arrayList.size()][];
        for (int i = 0; i < arrayList.size(); i++) {
            List<Integer> row = arrayList.get(i);
            array[i] = new int[row.size()];
            for (int j = 0; j < row.size(); j ++) {
                array[i][j] = row.get(j);
            }
        }
        return array;
    }
}

