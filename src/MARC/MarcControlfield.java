package MARC;

import XML.XmlNode;
import Utils.*;

import java.io.IOException;

public class MarcControlfield extends Marc {

    //Constants
    public static final String MARC_RECORD_ID_TAG = "001";

    //identifying XML string
    public static final String XML_TOKEN = "marc:controlfield";

    //output filename
    private static final String OUTPUT_FILE_NAME = "marc_controlfield";
    private static final String OUTPUT_FILE_EXTENSION = ".csv";

    //attributes
    private String tag;
    private String value;
    private MarcRecord parent;
    private int controlfieldIndex;

    //details
    private String CF_005_year;
    private String CF_005_month;
    private String CF_005_day;
    private String CF_005_hour;
    private String CF_005_minute;
    private String CF_005_second;
    private String CF_005_second_fraction;
    private String CF_008_year;
    private String CF_008_month;
    private String CF_008_day;

    //Getters & Setters
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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

    public int getControlfieldIndex() {
        return controlfieldIndex;
    }

    public void setControlfieldIndex(int controlfieldIndex) {
        this.controlfieldIndex = controlfieldIndex;
    }

    //Constructor
    public MarcControlfield() {
        this.tag = null;
        this.value = null;
        this.parent = null;
    }

    //Create string
    @Override
    public String toString() {
        return "MarcControlfield{" +
                "tag='" + tag + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    /**
     * Create marc control field from XML node
     * @param node XML node to be processed
     * @param parent connection to parent
     * @return new marc control field
     */
    public static MarcControlfield create(XmlNode node, MarcRecord parent) {
        MarcControlfield controlfield = new MarcControlfield();
        controlfield.setTag(node.getFirstAttributeValue());
        controlfield.setValue(node.getInnerText());
        controlfield.setParent(parent);
        return(controlfield);
    }

    /**
     * Delete output files.
     */
    public static void deleteOutputFiles(String path) {
        FileDeleter.deleteOutputFiles(path, OUTPUT_FILE_NAME, OUTPUT_FILE_EXTENSION);
    }

    /**
     * Actions to be taken before write to output file
     */
    void preWrite() {

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
        return(OUTPUT_FILE_NAME + "_" + tag + OUTPUT_FILE_EXTENSION);
    }

    /**
     * Header line of file describing all columns
     * @return String containing header line
     */
    String fileHeader() {
        //default header
        StringBuilder header = new StringBuilder("ID,index,tag,value");

        //specific extensions
        switch (tag) {
            case "005" -> header.append(",year,month,day,hour,minute,second,second_fraction");
            case "008" -> header.append(",year,month,day");
        }

        //return header
        return(header.toString());
    }

    /**
     * Build output string
     */
    void buildOutput(){
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+marcRecordID+" tag:"+tag+" value:"+value+")");

        //build output string
        output_string
                .append(CsvConverter.asCsvString(marcRecordID))
                .append(",")
                .append(controlfieldIndex)
                .append(",")
                .append(CsvConverter.asCsvString(tag))
                .append(",")
                .append(CsvConverter.asCsvString(value));

        //specific output
        switch (tag) {
            case "005" -> output005();
            case "008" -> output008();
        }

        //end-of-line
        output_string
                .append("\n");

    }

    /**
     * Build 005 output
     */
    private void output005() {
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+parent.getUniqueMarcRecordID()+" tag:"+tag+" value:"+value+")");
        //split 005 parts
        split_005();
        //build 005 output
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(CF_005_year))
                .append(",")
                .append(CsvConverter.asCsvString(CF_005_month))
                .append(",")
                .append(CsvConverter.asCsvString(CF_005_day))
                .append(",")
                .append(CsvConverter.asCsvString(CF_005_hour))
                .append(",")
                .append(CsvConverter.asCsvString(CF_005_minute))
                .append(",")
                .append(CsvConverter.asCsvString(CF_005_second))
                .append(",")
                .append(CsvConverter.asCsvString(CF_005_second_fraction));
    }

    /**
     * Build 008 output
     */
    private void output008() {
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+parent.getUniqueMarcRecordID()+" tag:"+tag+" value:"+value+")");
        //split 008 parts
        split_008();
        //build 008 output
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(CF_008_year))
                .append(",")
                .append(CsvConverter.asCsvString(CF_008_month))
                .append(",")
                .append(CsvConverter.asCsvString(CF_008_day));
    }

    /**
     * Split value into separate parts for index 005
     */
    private void split_005() {
        //yyyy mm dd hh mm ss .f
        //0123 45 67 89 01 23 45
        CF_005_year = (value != null && value.length()>=4) ? value.substring(0,4) : "";
        CF_005_month = (value != null && value.length()>=6) ? value.substring(4,6) : "";
        CF_005_day = (value != null && value.length()>=8) ? value.substring(6,8) : "";
        CF_005_hour = (value != null && value.length()>=10) ? value.substring(8,10) : "";
        CF_005_minute = (value != null && value.length()>=12) ? value.substring(10,12) : "";
        CF_005_second = (value != null && value.length()>=14) ? value.substring(12,14) : "";
        CF_005_second_fraction = (value != null && value.length()>=16) ? String.valueOf(value.charAt(15)) : "";
    }

    /**
     * Split value into separate parts for index 008
     */
    private void split_008() {
        //yy mm dd
        //01 23 45
        CF_008_year = (value != null && value.length()>=2) ? value.substring(0,2) : "";
        CF_008_month = (value != null && value.length()>=4) ? value.substring(2,4) : "";
        CF_008_day = (value != null && value.length()>=6) ? value.substring(4,6) : "";
    }

}
