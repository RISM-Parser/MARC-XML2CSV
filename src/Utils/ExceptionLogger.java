package Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionLogger {
    /**
     * Log exception to file
     * @param exception Occurred exception
     * @param file_number Number of file in progress
     * @param str string which cause the exception
     * @param row_number row number which caused the exception
     * @param error_file_name file name of error log
     * @throws IOException in case of file io error
     */
    public static void logException(Exception exception, String file_number, String str, long row_number, String error_file_name) throws IOException {
        //timestamp
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //build error string
        StringBuilder errorString = new StringBuilder();
        errorString.append(timeStamp)
                .append(" - ")
                .append(file_number)
                .append(" - ")
                .append(row_number)
                .append(" - ")
                .append(exception.getMessage())
                .append(": ")
                .append(str)
                .append('\n');
        //append string to logfile
        FileAppender.appendFile(error_file_name, errorString);
    }
}
