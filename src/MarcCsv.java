import MARC.*;
import Utils.*;
import XML.XMLParser;
import XML.XmlNode;


import java.io.*;
import java.text.ParseException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static java.lang.System.exit;

public class MarcCsv {

    private final static String REJECTIONS_FILE = "rejections.txt";
    private final static int MAX_LINES_IN_FILE = 10000;

    public static void main(String[] args) {
        //check for number of arguments
        if (args.length < 1) {
            ProgressDisplayer.displayError("Invalid number of arguments. Expected argument: source_file");
            exit(1);
        }

        //file names
        String source_file_name = args[0];

        //source file
        File source_file = Utils.getFile(source_file_name);
        if(source_file == null) {
            exit(1);
        }

        //print start
        ProgressDisplayer.displayStart();

        try {
            //print progress
            ProgressDisplayer.displayProgress("Getting number of lines in source file");

            //count number of lines to be processed in source file
            int number_of_lines = Utils.countLines(source_file_name);

            //print progress
            ProgressDisplayer.displayProgress("Split source file");

            //number of output files
            int number_of_output_files = Utils.numberOfOutputFiles(number_of_lines, MAX_LINES_IN_FILE);

            //create output file names
            HashMap<String,String> source_file_names = SourceFileBuilder.createFilenames(source_file_name, number_of_output_files);
            //distribute source over the output files
            SourceFileBuilder.splitSourceFile(source_file_name,source_file_names,MAX_LINES_IN_FILE);
//exit(0);
            //start duration measurement
            Instant start_overall = Instant.now();

            //delete existing output files
            deleteOutputFiles();

            //sorted keys
            List<String> keys = SourceFileBuilder.sortedKeys(source_file_names);
            //last key
            String last_key = keys.get(keys.size() - 1);
            //process all source files
            for (String key : keys) {
                //get file name
                String file_name = source_file_names.get(key);
                //file processing: parse every line in the source file
                processInputFile(key, file_name, last_key);
            }

            //display total run time
            ProgressDisplayer.displayTotalDuration(number_of_lines, start_overall);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Delete existing output files.
     */
    private static void deleteOutputFiles() {
        MarcRecord.deleteOutputFile();
        MarcLeader.deleteOutputFile();
        MarcControlfield.deleteOutputFiles(".");
        MarcDatafield.deleteOutputFiles(".");
        MarcSubfield.deleteOutputFiles(".");
    }

    private static void processInputFile(String current_file_number, String file_name, String max_file_number) throws IOException {

        //source file
        File source_file = Utils.getFile(file_name);
        if(source_file == null) {
            exit(1);
        }

        //count number of errors in file
        int error_counter = 0;

        //start duration measurement
        Instant start = Instant.now();

        //initialize progress values
        ProgressDisplayer.initializeProgressValues();

        //progress indicator number
        long progress_indicator_number = 0;

        //read source file using a buffer
        BufferedReader reader = new BufferedReader(new FileReader(source_file));

        //get line to be processed
        String line = reader.readLine();

        //process until end-of-file
        while (line != null) {

            //increase progress indicator
            progress_indicator_number++;

            //root of XML tree
            XmlNode root = null;

            try {
                //parse the XML
                root = XMLParser.parse(line);
            } catch(ParseException parseException) {
                error_counter++;
                ExceptionLogger.logException(parseException, current_file_number, line, progress_indicator_number, REJECTIONS_FILE);
            }


            if(root != null) {

                try {
                    //create marc record
                    MarcRecord record = MarcRecord.create(root);
                    if(record == null) {
                        throw new ParseException("Invalid marc record", -1);
                    } else {
                        //write marc record to file
                        record.writeToFile();
                    }
                } catch(Exception e) {
                    error_counter++;
                    ExceptionLogger.logException(e, current_file_number, line, progress_indicator_number, REJECTIONS_FILE);
                }

                //display the progress of the run
                ProgressDisplayer.displayProgress(current_file_number, max_file_number, progress_indicator_number, start, MAX_LINES_IN_FILE);

            }

            //read next line
            line = reader.readLine();
        }

        //close the buffer
        reader.close();

        //remove file when no errors occurred
        if(error_counter == 0) {
            FileDeleter.deleteFile(file_name);
        }
    }



}