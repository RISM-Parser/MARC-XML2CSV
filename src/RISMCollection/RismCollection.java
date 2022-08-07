package RISMCollection;

import Utils.FileAppender;
import Utils.ProgressDisplayer;

import java.io.*;
import java.time.*;


import static java.lang.System.exit;

public class RismCollection {

    //fixed values
    private static final int INPUT_BUFFER_SIZE = 150000;

    //XML tokens
    private static final String TOKEN_MARC_RECORD = "<marc:record>";
    private static final String TOKEN_MARC_RECORD_END = "</marc:record>";
    private static final String TOKEN_MARC_COLLECTION_END = "</marc:collection>";
    private static final String TOKEN_XML_HEADER = "<?xml";

    public static void main(String[] args) {
        //check for number of arguments
        if(args.length<2){
            System.out.println("Invalid number of arguments. Expected two arguments: source_file target_file");
            exit(1);
        }

        //file names
        String source_file_name = args[0];
        String target_file_name = args[1];

        //source file
        File source_file = new File(source_file_name);

        //check if file exists
        if(!source_file.exists()) {
            System.out.println("source_file does not exist");
            exit(1);
        }
        //check if file is directory
        if(source_file.isDirectory()) {
            System.out.println("source_file is a directory");
            exit(1);
        }

        //empty existing target_file
        try {
            PrintWriter writer = new PrintWriter(target_file_name);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //file processing: from source to target
        try {

            //start duration measurement
            Instant start = Instant.now();

            //progress indicator number
            long progress_indicator_number = 0;

            //string that still needs to be processed
            String to_be_processed = "";

            //read source file in parts
            BufferedReader br = new BufferedReader(new FileReader(source_file), INPUT_BUFFER_SIZE);

            //flag to indicated if outer loop needs to stop
            boolean stop_outer_loop = false;

            //keep reading data from file until flag indicates: stop
            while (!stop_outer_loop) {

                //memory space to store the buffer read from file
                char[] buffer_array = new char[INPUT_BUFFER_SIZE];
                //read next buffer from file
                int size = br.read(buffer_array);
                //if buffer is empty: stop reading from file
                if (size <= 0) {
                    stop_outer_loop = true;
                    continue;
                }

                //transform buffer into String
                String buffer = new String(buffer_array);
                //remove end-of-line characters from buffer, causes problems further on while saving into target file
                buffer = buffer.replace("\r", " ");
                buffer = buffer.replace("\n", " ");

                //add buffer to string that needs to be processed
                to_be_processed = to_be_processed.concat(buffer);

                //start value for flag indicating that there is still a part of the string to be processed before the next buffer needs to be read from source file
                boolean stop_inner_loop = false;

                //process string until the flag indicates: stop
                while (!stop_inner_loop) {
                    //split string on token marc:record; first part is full record, second part will be processed in next iteration of the inner loop
                    String[] buffer_part = to_be_processed.split(TOKEN_MARC_RECORD, 2);

                    //store first part
                    String first_part = buffer_part[0];
                    //second part can be empty: last record
                    String second_part = null;
                    if (buffer_part.length == 2) {
                        //store remaining part after the split
                        second_part = buffer_part[1];
                    } else {
                        //last record is processed: stop the inner loop
                        stop_inner_loop = true;
                    }

                    //do not process xml header
                    if (first_part.contains(TOKEN_XML_HEADER)) {
                        //store the remainder after the split into string to be processed for next iteration of the inner loop
                        to_be_processed = second_part;
                        continue;
                    }

                    //create full marc record: add token upfront
                    String marc_record = TOKEN_MARC_RECORD.concat(first_part);

                    //when marc record is complete: write to target file
                    if (marc_record.contains(TOKEN_MARC_RECORD_END)) {
                        //last record contains collection end, that needs to be stripped
                        if(marc_record.contains((TOKEN_MARC_COLLECTION_END))) {
                            String[] marc_record_part = marc_record.split(TOKEN_MARC_COLLECTION_END, 2);
                            //first part is complete mark record; second part is collection end token
                            marc_record = marc_record_part[0];
                        }

                        //create line
                        StringBuilder line = new StringBuilder().append(marc_record).append('\n');

                        //write to target file
                        FileAppender.appendFile(target_file_name, line);

                        //periodically print progress in number of processed records
                        ProgressDisplayer.displayProgress(source_file_name, ++progress_indicator_number, start);

                    }

                    //last record is processed, no further records to be processed: break out of inner loop
                    if(stop_inner_loop) {
                        continue;
                    }

                    //still records to be processed
                    to_be_processed = second_part;

                    //when string to be processed doesn't contain marc:record or collection end (in case of last record), next buffer needs to read from source file
                    if (!to_be_processed.contains(TOKEN_MARC_RECORD)&&!to_be_processed.contains(TOKEN_MARC_COLLECTION_END)){
                        stop_inner_loop = true;
                    }
                }
            }

            //close the buffer
            br.close();

            //duration measurement
            ProgressDisplayer.displayTotalDuration(progress_indicator_number, start);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}