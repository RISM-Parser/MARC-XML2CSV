package Utils;

import java.io.*;

public class Utils {

    //fixed values
    private static final int FILE_SIZE_COUNT_LINES_BUFFER_SIZE = 8*1024;


    /**
     * https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
     * @param filename File to be inspected
     * @return Number of lines in file
     * @throws IOException Exception when reading characters from file
     */
    public static int countLines(String filename) throws IOException {
        try(InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[FILE_SIZE_COUNT_LINES_BUFFER_SIZE];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == FILE_SIZE_COUNT_LINES_BUFFER_SIZE) {
                for (int i=0; i<FILE_SIZE_COUNT_LINES_BUFFER_SIZE;) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        }
    }

    /**
     * Calculate number of output files
     * @param number_of_lines number of lines to be processed
     * @param max_lines_in_file maximum lines in one output file
     * @return number of output files
     */
    public static int numberOfOutputFiles(int number_of_lines, int max_lines_in_file){
        //number of output files
        int number_of_output_files = number_of_lines / max_lines_in_file;
        //leftovers in last file: increase number
        if (number_of_lines % max_lines_in_file != 0)
            number_of_output_files++;
        //return number of output files
        return(number_of_output_files);
    }


    /**
     * Get the file with file name
     * @param file_name Name of the file
     * @return File with file name, or null when error
     */
    public static File getFile(String file_name) {

        //source file
        File file = new File(file_name);

        //check if file exists
        if (!file.exists()) {
            ProgressDisplayer.displayError(file_name + " does not exist");
            return(null);
        }
        //check if file is directory
        if (file.isDirectory()) {
            ProgressDisplayer.displayError(file_name + " is a directory");
            return(null);
        }

        //return file
        return(file);

    }

}
