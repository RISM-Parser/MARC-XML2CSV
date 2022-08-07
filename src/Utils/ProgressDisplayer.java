package Utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ProgressDisplayer {

    //fixed values
    private static final int OUTPUT_INDICATOR_INTERVAL = 100;
    //store values of previous duration display
    private static long previous_duration_seconds = 0;
    private static long previous_progress_indicator_number = 0;

    /**
     * Periodically print progress in number of processed records.
     * @param file_number File number of file in progress
     * @param progress_indicator_number Number of records processed
     * @param start timestamp when run started
     */
    public static void displayProgress(String file_number, long progress_indicator_number, Instant start) {
        //duration measurement
        Instant now = Instant.now();

        //duration in seconds
        long duration_seconds = Duration.between(start,now).toSeconds();

        //only display rounded progress amounts
        if (progress_indicator_number % OUTPUT_INDICATOR_INTERVAL == 0) {
            previous_progress_indicator_number = progress_indicator_number;
        }

        //periodically print progress in number of processed records
        if(duration_seconds >= previous_duration_seconds) {
            long elapsed_time = Duration.between(start, now).toMillis();
            //printable time conversion
            long elapsed_hours = TimeUnit.MILLISECONDS.toHours(elapsed_time);
            long elapsed_minutes = (TimeUnit.MILLISECONDS.toMinutes(elapsed_time) % 60);
            long elapsed_seconds = (TimeUnit.MILLISECONDS.toSeconds(elapsed_time) % 60);

            //Carriage return: overwrite previous text
            System.out.print('\r');
            //Print information line
            System.out.print("File: ");
            System.out.print(file_number);
            System.out.print(". Processing record: ");
            System.out.print(previous_progress_indicator_number);
            System.out.print(". Elapsed time: ");
            System.out.print(elapsed_hours);
            System.out.print(":");
            if (elapsed_minutes <= 9) System.out.print("0");
            System.out.print(elapsed_minutes);
            System.out.print(":");
            if (elapsed_seconds <= 9) System.out.print("0");
            System.out.print(elapsed_seconds);
            System.out.print(".");
        }

        //store for next display
        previous_duration_seconds = duration_seconds;

    }

    /**
     * Initialize values used by display progress
     */
    public static void initializeProgressValues() {
        previous_duration_seconds = 0;
        previous_progress_indicator_number = 0;
    }

    /**
     * Periodically print progress in number of processed records.
     * @param file_number File number of file in progress
     * @param max_file_number Maximal number of files to be processed
     * @param progress_indicator_number Number of records processed
     * @param start timestamp when run started
     * @param max_lines maximum of lines to be processed
     */
    public static void displayProgress(String file_number, String max_file_number, long progress_indicator_number, Instant start, long max_lines) {
        //duration measurement
        Instant now = Instant.now();

        //elapsed time in milliseconds
        long elapsed_time = Duration.between(start, now).toMillis();

        //periodically print progress in number of processed records
        if(elapsed_time >= previous_duration_seconds * 1000) {

            //printable time conversion
            long elapsed_hours = TimeUnit.MILLISECONDS.toHours(elapsed_time);
            long elapsed_minutes = (TimeUnit.MILLISECONDS.toMinutes(elapsed_time) % 60);
            long elapsed_seconds = (TimeUnit.MILLISECONDS.toSeconds(elapsed_time) % 60);

            //calculate progress percentage
            double progress_percentage = (double) progress_indicator_number / (double) max_lines;
            double files_percentage = (Double.parseDouble(file_number) + 1) / (Double.parseDouble(max_file_number) + 1);

            //calculate remaining time
            long expected_remaining_time = ((long) ((double) elapsed_time / progress_percentage)) - elapsed_time;

            //printable remaining time
            long remaining_hours = TimeUnit.MILLISECONDS.toHours(expected_remaining_time);
            long remaining_minutes = (TimeUnit.MILLISECONDS.toMinutes(expected_remaining_time) % 60);
            long remaining_seconds = (TimeUnit.MILLISECONDS.toSeconds(expected_remaining_time) % 60);

            //store progress for next display progress
            previous_progress_indicator_number = progress_indicator_number;

            //Carriage return: overwrite previous text
            System.out.print('\r');
            //Print information line
            System.out.print("File: ");
            System.out.print(file_number);
            System.out.print("/");
            System.out.print(max_file_number);
            System.out.print(" (");
            System.out.printf("%.1f",100*files_percentage);
            System.out.print("%)");
            System.out.print(". Processing record: ");
            System.out.print(previous_progress_indicator_number);
            System.out.print(" (");
            System.out.printf("%.1f",100*progress_percentage);
            System.out.print("%)");
            System.out.print(". Elapsed time: ");
            System.out.print(elapsed_hours);
            System.out.print(":");
            if (elapsed_minutes <= 9) System.out.print("0");
            System.out.print(elapsed_minutes);
            System.out.print(":");
            if (elapsed_seconds <= 9) System.out.print("0");
            System.out.print(elapsed_seconds);
            System.out.print(". Remaining time: ");
            System.out.print(remaining_hours);
            System.out.print(":");
            if (remaining_minutes <= 9) System.out.print("0");
            System.out.print(remaining_minutes);
            System.out.print(":");
            if (remaining_seconds <= 9) System.out.print("0");
            System.out.print(remaining_seconds);
            System.out.print(".");
        }

        //store for next display
        previous_duration_seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed_time);

    }

    /**
     * Print tot duration of run
     * @param number Number of records processed
     * @param start timestamp when run started
     */
    public static void displayTotalDuration(long number, Instant start) {
        //duration measurement
        Instant finish = Instant.now();
        long elapsed_time = Duration.between(start, finish).toMillis();
        //printable time conversion
        long elapsed_days = TimeUnit.MILLISECONDS.toDays(elapsed_time);
        long elapsed_hours = TimeUnit.MILLISECONDS.toHours(elapsed_time) % 24;
        long elapsed_minutes = (TimeUnit.MILLISECONDS.toMinutes(elapsed_time) % 60);
        long elapsed_seconds = (TimeUnit.MILLISECONDS.toSeconds(elapsed_time) % 60);
        //Carriage return: overwrite previous text
        System.out.print('\r');
        //Print information line
        System.out.print("Processed ");
        System.out.print(number);
        System.out.print(" records in ");
        System.out.print(elapsed_days);
        System.out.print(" days ");
        if (elapsed_hours <= 9) System.out.print("0");
        System.out.print(elapsed_hours);
        System.out.print(":");
        if (elapsed_minutes <= 9) System.out.print("0");
        System.out.print(elapsed_minutes);
        System.out.print(":");
        if (elapsed_seconds <= 9) System.out.print("0");
        System.out.print(elapsed_seconds);
        System.out.print(".");
    }

    /**
     * Display progres text.
     * @param text Text to be displayed
     */
    public static void displayProgress(String text){
        //Carriage return: overwrite previous text
        System.out.print('\r');
        //print progress text
        System.out.print(text);
    }

    /**
     * Display start of processing
     */
    public static void displayStart(){
        //timestamp
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //
        System.out.print(timeStamp);
        System.out.println(" Start processing");

    }

    /**
     * Display error text.
     * @param text Text to be displayed
     */
    public static void displayError(String text) {
        System.err.println("Error:" + text);
    }
}
