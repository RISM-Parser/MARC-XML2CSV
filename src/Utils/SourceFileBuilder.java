package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SourceFileBuilder {
    /**
     * Create list of temporary file names based on given file name
     * @param base_file_name Base file name
     * @param tallySize number of temporary files
     * @return LIst of temporary file names
     */
    public static HashMap<String,String> createFilenames(String base_file_name, int tallySize) {
        //check parameter
        if(base_file_name == null) return null;

        //split string on '.'
        String[] parts = base_file_name.split("\\.");

        //list with file names
        HashMap<String,String> filenames = new HashMap<>();

        //only one file name exists?
        if(parts.length <= 1 || tallySize <= 1) {
            filenames.put(createKey(0), base_file_name);
            return(filenames);
        }

        //create first part of file name
        StringBuilder firstPart = new StringBuilder();
        for(int i = 0; i < parts.length - 1; i++) {
            firstPart.append(parts[i]);
            if(parts.length > 1){
                firstPart.append('.');
            }
        }
        //last part of file name
        String lastPart = parts[parts.length - 1];

        //create temporary file name
        for(int tally = 0; tally < tallySize; tally++) {
            StringBuilder file_name = new StringBuilder();
            String index = createKey(tally);
            file_name
                    .append(firstPart)
                    .append(index)
                    .append('.')
                    .append(lastPart);
            filenames.put(index,file_name.toString());
        }

        //return list
        return(filenames);

    }

    /**
     * Split source file into smaller files. First delete previous output files.
     * @param source_file_name Source file name
     * @param output_file_names List with output file names
     * @param number_of_lines Number of lines written into an output file
     * @throws IOException File exception
     */
     public static void splitSourceFile(String source_file_name, HashMap<String,String> output_file_names, int number_of_lines) throws IOException {
        //check parameter values
        if(source_file_name == null) throw new RuntimeException("source_file_name is null");
        if(output_file_names == null) throw new RuntimeException("source_file_part_names is null");
        if(output_file_names.isEmpty()) throw new RuntimeException("source_file_part_names is empty");
        if(number_of_lines <= 0) throw new RuntimeException("number_of_lines is invalid");

         //key list
         List<String> keys = sortedKeys(output_file_names);

         //delete previous files
         for (String key : keys) {
            String file_name = output_file_names.get(key);
            FileDeleter.deleteFile(file_name);
        }

         //number of keys
         int number_of_files = keys.size();
         //index in key set
         int key_index = 1;
         //value of last key
         String last_key = keys.get(keys.size()-1);

        //string containing read line
        String line;
        //all lines added into one string
        StringBuilder str = new StringBuilder();

        //line number in output file
        int line_number = 0;
        //index in list of output files
        int output_file_index = 0;

        //reader of source file
        BufferedReader reader = new BufferedReader(new FileReader(source_file_name));

        //while source file has lines
        while((line = reader.readLine()) != null) {

            //append line and end-of-line
            str.append(line).append('\n');
            //increase line number
            line_number++;

            //are number of lines written into output file?
            if(line_number == number_of_lines) {
                //create key
                String key = createKey(output_file_index++);
                //get file name
                String file_name = output_file_names.get(key);

                //calculate progress percentage
                double progress_percentage = (double) key_index++ / (double) number_of_files;
                ProgressDisplayer.displayProgress("Split source file. Processing file " + key + "/" + last_key + " (" + String.format("%.1f",100*progress_percentage) + "%)");

                //write to a new file
                FileAppender.appendFile(file_name, str);
                //re-initialize the counter
                line_number = 0;
                //re-initialize the String content
                str = new StringBuilder();
            }
        }

        //remaining lines?
        if (str.length() > 0) {
            //create key
            String key = createKey(output_file_index);
            //get file name
            String file_name = output_file_names.get(key);

            //calculate progress percentage
            double progress_percentage = (double) key_index / (double) number_of_files;
            ProgressDisplayer.displayProgress("Split source file \"" + key + "/" + last_key + "\" (" + String.format("%.0f",100*progress_percentage) + "%)");

            //write to a new file
            FileAppender.appendFile(file_name, str);
        }
    }

    /**
     * Creates a key from number.
     * @param number input for key
     * @return String containing key
     */
    private static String createKey(int number) {
        return(String.format("%04d", number));
    }

    /**
     * Sort keys from key-value-pairs
     * @param file_names key-value-pairs
     * @return List with sorted keys, or null
     */
    public static List<String> sortedKeys(HashMap<String,String> file_names){
        //check parameter
        if(file_names == null) return(null);

        //list of keys
        List<String> keys = new ArrayList<>();
        //traverse through keys and add to list
        for (Map.Entry<String,String> mapElement : file_names.entrySet()) {
            String key = mapElement.getKey();
            keys.add(key);
        }
        //sort list
        Collections.sort(keys);
        //return list
        return(keys);
    }
}
