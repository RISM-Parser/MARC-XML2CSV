package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileAppender {
    /**
     * Append string to file
     * @param filename Name of file
     * @param string String to be added to file
     * @throws IOException Error while adding string to file
     */
    public static void appendFile(String filename, String string) throws IOException {
        //open file for append - UTF-8
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, StandardCharsets.UTF_8, true));
        //write string
        writer.append(string);
        //flush buffer
        writer.flush();
        //close file
        writer.close();
    }

    /**
     * Append string to file
     * @param file File
     * @param string String to be added to file
     * @throws IOException Error while adding string to file
     */
    public static void appendFile(File file, String string) throws IOException {
        //open file for append - UTF-8
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8, true));
        //write string
        writer.append(string);
        //flush buffer
        writer.flush();
        //close file
        writer.close();
    }

    /**
     * Append string to file
     * @param filename Name of file
     * @param builder String to be added to file
     * @throws IOException Error while adding string to file
     */
    public static void appendFile(String filename, StringBuilder builder) throws IOException {
        appendFile(filename, builder.toString());
    }

    /**
     * Append string to file
     * @param file File
     * @param builder String to be added to file
     * @throws IOException Error while adding string to file
     */
    public static void appendFile(File file, StringBuilder builder) throws IOException {
        appendFile(file, builder.toString());
    }
}
