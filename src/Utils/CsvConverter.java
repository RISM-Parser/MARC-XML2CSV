package Utils;

public class CsvConverter {
    /**
     * Convert string to CSV format - twice double quotes
     * @param string String to be formatted
     * @return Formatted string, or empty string
     */
    public static String convertToCsv(String string) {

        //return null when empty string
        if(string == null) return "";

        //output buffer
        char[] buffer = new char[2*string.length()];
        //index in buffer
        int buffer_index = 0;

        //traverse through string and double a double quote
        for(int string_index = 0; string_index < string.length(); string_index++) {
            char c = string.charAt(string_index);
            buffer[buffer_index++] = c;
            if(c == '"') {
                buffer[buffer_index++] = '"';
            }
        }

        //return formatted string
        return(new String(buffer).trim());
    }

    /**
     * Create a string with double quotes for CSV
     * @param string string to receive double quotes
     * @return string with double quotes
     */
    public static String asCsvString(String string) {
        StringBuilder outputString = new StringBuilder();
        outputString
                .append("\"")
                .append(convertToCsv(string))
                .append("\"");
        return outputString.toString();
    }
}
