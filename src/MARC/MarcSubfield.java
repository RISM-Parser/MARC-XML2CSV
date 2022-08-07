package MARC;

import XML.XmlNode;
import Utils.*;

import java.io.IOException;

public class MarcSubfield extends Marc {

    //identifying XML string
    public static final String XML_TOKEN = "marc:subfield";

    //output filename
    private static final String OUTPUT_FILE_NAME = "marc_subfield";
    private static final String OUTPUT_FILE_EXTENSION = ".csv";

    //attributes
    private String code;
    private String value;
    private MarcDatafield parent;
    private int datafieldIndex;
    private int subfieldIndex;


    //Getters & Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MarcDatafield getParent() {
        return parent;
    }

    public void setParent(MarcDatafield parent) {
        this.parent = parent;
    }

    public int getDatafieldIndex() {
        return datafieldIndex;
    }

    public void setDatafieldIndex(int datafieldIndex) {
        this.datafieldIndex = datafieldIndex;
    }

    public int getSubfieldIndex() {
        return subfieldIndex;
    }

    public void setSubfieldIndex(int subfieldIndex) {
        this.subfieldIndex = subfieldIndex;
    }

    //Constructor
    public MarcSubfield() {
        this.code = null;
        this.value = null;
        this.parent = null;
    }

    //Create string
    @Override
    public String toString() {
        return "MarcSubfield{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    /**
     * Create marc subfield from XML node
     * @param node XML node to be processed
     * @param parent connection to parent
     * @return new marc subfield
     */
    public static MarcSubfield create(XmlNode node, MarcDatafield parent) {
        MarcSubfield subfield = new MarcSubfield();
        subfield.setCode(node.getFirstAttributeValue());
        subfield.setValue(node.getInnerText());
        subfield.setParent(parent);
        return(subfield);
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
        return(OUTPUT_FILE_NAME + "_" + parent.getTag() + OUTPUT_FILE_EXTENSION);
    }

    /**
     * Header line of file describing all columns
     * @return String containing header line
     */
    String fileHeader() {
        return("ID,tag,indicator1,indicator2,datafield_index,subfield_index,code,value");
    }

    /**
     * Build output string
     */
    void buildOutput(){
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+marcRecordID+" tag:"+parent.getTag()+" code:"+code+" value:"+value+")");

        //build output string
        output_string
                .append(CsvConverter.asCsvString(parent.getParent().getUniqueMarcRecordID()))
                .append(",")
                .append(CsvConverter.asCsvString(parent.getTag()))
                .append(",")
                .append(CsvConverter.asCsvString(parent.getIndicator1()))
                .append(",")
                .append(CsvConverter.asCsvString(parent.getIndicator2()))
                .append(",")
                .append(datafieldIndex)
                .append(",")
                .append(subfieldIndex)
                .append(",")
                .append(CsvConverter.asCsvString(code))
                .append(",")
                .append(CsvConverter.asCsvString(value));

        //end-of-line
        output_string
                .append("\n");

    }

}
