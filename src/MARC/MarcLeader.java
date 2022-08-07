package MARC;

import XML.XmlNode;
import Utils.*;

import java.io.IOException;

    /*
    Character Positions
00-04 - Record length
05 - Record status
06 - Type of record
07 - Bibliographic level
08 - Type of control
09 - Character coding scheme
10 - Indicator count
11 - Subfield code count
12-16 - Base address of data
17 - Encoding level
18 - Descriptive cataloging form
19 - Multipart resource record level
20 - Length of the length-of-field portion
21 - Length of the starting-character-position portion
22 - Length of the implementation-defined portion
23 - Undefined

https://www.loc.gov/marc/bibliographic/bdleader.html
     */


public class MarcLeader extends Marc {

    //identifying XML string
    public static final String XML_TOKEN = "marc:leader";

    //output filename
    private static final String OUTPUT_FILE = "marc_leader.csv";

    //attributes
    private String value;
    private MarcRecord parent;

    //details
    private String record_length;
    private String record_status;
    private String record_type;
    private String bibliographic_level;
    private String control_type;
    private String character_coding_scheme;
    private String indicator_count;
    private String subfield_code_count;
    private String data_base_address;
    private String encoding_level;
    private String descriptive_cataloging_form;
    private String multipart_resource_record_level;
    private String length_of_field_portion_length;
    private String starting_character_position_portion_length;
    private String implementation_defined_portion_length;
    private String undefined;

    //Getters & Setters
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MarcRecord getParent() {
        return parent;
    }

    public void setParent(MarcRecord parent) {
        this.parent = parent;
    }

    //Constructor
    public MarcLeader() {
        this.value = null;
        this.parent = null;
    }

    //Create string
    @Override
    public String toString() {
        return "MarcLeader{" +
                "value='" + value + '\'' +
                '}';
    }

    /**
     * Create marc leader from XML node
     * @param node XML node to be processed
     * @param parent connection to parent
     * @return new marc leader
     */
    public static MarcLeader create(XmlNode node, MarcRecord parent) {
        MarcLeader leader = new MarcLeader();
        leader.setValue(node.getInnerText());
        leader.setParent(parent);
        return(leader);
    }

    /**
     * Delete output file.
     */
    public static boolean deleteOutputFile() {
        return(FileDeleter.deleteFile(OUTPUT_FILE));
    }

    /**
     * Actions to be taken before write to output file
     */
     void preWrite() {
        //Split value into separate parts
        split();
    }

    /**
     * Actions to be taken after write to output file
     */
     void postWrite() throws IOException {

    }

    /**
     * Construct output filename
     * @return filename
     */
     String outputFilename() {
        return(OUTPUT_FILE);
    }

    /**
     * Header line of file describing all columns
     * @return String containing header line
     */
     String fileHeader() {
        return("ID,leader,record_length,record_status,record_type,bibliographic_level,control_type,character_coding_scheme,indicator_count,subfield_code_count,data_base_address,encoding_level,descriptive_cataloging_form,multipart_resource_record_level,length_of_field_portion_length,starting_character_position_portion_length,implementation_defined_portion_length,undefined");
    }

    /**
     * Build output string
     */
     void buildOutput(){
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+marcRecordID+" value:"+value+")");

        //build output string
        output_string
                .append(CsvConverter.asCsvString(marcRecordID))
                .append(",")
                .append(CsvConverter.asCsvString(value))
                .append(",")
                .append(CsvConverter.asCsvString(record_length))
                .append(",")
                .append(CsvConverter.asCsvString(record_status))
                .append(",")
                .append(CsvConverter.asCsvString(record_type))
                .append(",")
                .append(CsvConverter.asCsvString(bibliographic_level))
                .append(",")
                .append(CsvConverter.asCsvString(control_type))
                .append(",")
                .append(CsvConverter.asCsvString(character_coding_scheme))
                .append(",")
                .append(CsvConverter.asCsvString(indicator_count))
                .append(",")
                .append(CsvConverter.asCsvString(subfield_code_count))
                .append(",")
                .append(CsvConverter.asCsvString(data_base_address))
                .append(",")
                .append(CsvConverter.asCsvString(encoding_level))
                .append(",")
                .append(CsvConverter.asCsvString(descriptive_cataloging_form))
                .append(",")
                .append(CsvConverter.asCsvString(multipart_resource_record_level))
                .append(",")
                .append(CsvConverter.asCsvString(length_of_field_portion_length))
                .append(",")
                .append(CsvConverter.asCsvString(starting_character_position_portion_length))
                .append(",")
                .append(CsvConverter.asCsvString(implementation_defined_portion_length))
                .append(",")
                .append(CsvConverter.asCsvString(undefined));

        //end-of-line
        output_string
                .append("\n");

    }

    /**
     * Split value into separate parts.
     */
    private void split() {
        record_length = (value != null && value.length()>=5) ? value.substring(0,5) : "";
        record_status = (value != null && value.length()>=6) ? String.valueOf(value.charAt(5)) : "";
        record_type = (value != null && value.length()>=7) ? String.valueOf(value.charAt(6)) : "";
        bibliographic_level = (value != null && value.length()>=8) ? String.valueOf(value.charAt(7)) : "";
        control_type = (value != null && value.length()>=9) ? String.valueOf(value.charAt(8)) : "";
        character_coding_scheme = (value != null && value.length()>=10) ? String.valueOf(value.charAt(9)) : "";
        indicator_count = (value != null && value.length()>=11) ? String.valueOf(value.charAt(10)) : "";
        subfield_code_count = (value != null && value.length()>=12) ? String.valueOf(value.charAt(11)) : "";
        data_base_address = (value != null && value.length()>=17) ? value.substring(12,17) : "";
        encoding_level = (value != null && value.length()>=185) ? String.valueOf(value.charAt(17)) : "";
        descriptive_cataloging_form = (value != null && value.length()>=19) ? String.valueOf(value.charAt(18)) : "";
        multipart_resource_record_level = (value != null && value.length()>=20) ? String.valueOf(value.charAt(19)) : "";
        length_of_field_portion_length = (value != null && value.length()>=21) ? String.valueOf(value.charAt(20)) : "";
        starting_character_position_portion_length = (value != null && value.length()>=22) ? String.valueOf(value.charAt(21)) : "";
        implementation_defined_portion_length = (value != null && value.length()>=23) ? String.valueOf(value.charAt(22)) : "";
        undefined = (value != null && value.length()>=24) ? String.valueOf(value.charAt(23)) : "";

    }

}
