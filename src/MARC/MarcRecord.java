package MARC;

import XML.XmlNode;
import Utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MarcRecord extends Marc {

    //identifying XML string
    public static final String XML_TOKEN = "marc:record";

    //output filename
    private static final String OUTPUT_FILE = "marc_record.csv";

    //attributes
    private MarcLeader leader;
    private List<MarcControlfield> controlfields;
    private List<MarcDatafield> datafields;

    //Getters & Setters
    public MarcLeader getLeader() {
        return leader;
    }

    public void setLeader(MarcLeader leader) {
        this.leader = leader;
    }

    public List<MarcControlfield> getControlfields() {
        return controlfields;
    }

    public void setControlfields(List<MarcControlfield> controlfields) {
        this.controlfields = controlfields;
    }

    public List<MarcDatafield> getDatafields() {
        return datafields;
    }

    public void setDatafields(List<MarcDatafield> datafields) {
        this.datafields = datafields;
    }

    //Constructor
    public MarcRecord() {
        this.leader = null;
        this.controlfields = null;
        this.datafields = null;
    }

    //Create string
    @Override
    public String toString() {
        return "MarcRecord{" +
                "leader=" + leader +
                ", controlfield=" + controlfields +
                ", datafields=" + datafields +
                '}';
    }

    /**
     * Create initial list of control fields
     */
    private void initializeControlfields() {
        this.controlfields = new ArrayList<>();
    }

    /**
     * Create initial list of data fields
     */
    private void initializeDatafields() {
        this.datafields = new ArrayList<>();
    }

    /**
     * Add marc control field to list of control fields.
     * @param controlfield control field to be added
     * @return indicator if addition succeeded
     */
    public boolean addControlField(MarcControlfield controlfield) {
        //exists control field list? if no: initialize list
        if(this.controlfields == null) {
            initializeControlfields();
        }
        //add control field to list
        return(this.controlfields.add(controlfield));
    }

    /**
     * Add marc data field to list of data fields.
     * @param datafield data field to be added
     * @return indicator if addition succeeded
     */
    public boolean addDataField(MarcDatafield datafield) {
        //exists data field list? if no: initialize list
        if(this.datafields == null) {
            initializeDatafields();
        }
        //add data field to list
        return(this.datafields.add(datafield));
    }

    /**
     * Create marc record from XML node and sub-nodes
     * @param node XML node to be processed
     * @return new marc record
     */
    public static MarcRecord create(XmlNode node) {

        //check if node is a marc record
        if(!node.getTag().equals(MarcRecord.XML_TOKEN)) {
            return null;
        }

        //new marc record
        MarcRecord record = new MarcRecord();

        //traverse through record children and sub-children
        //build a marc record tree
        for(XmlNode child : node.getChildren()) {
            switch (child.getTag()) {
                case MarcLeader.XML_TOKEN:
                    MarcLeader leader = MarcLeader.create(child, record);
                    record.setLeader(leader);
                    break;
                case MarcControlfield.XML_TOKEN:
                    MarcControlfield controlfield = MarcControlfield.create(child, record);
                    record.addControlField(controlfield);
                    break;
                case MarcDatafield.XML_TOKEN:
                    MarcDatafield datafield = MarcDatafield.create(child, record);
                    record.addDataField(datafield);
                    break;
            }
        }

        //return marc record
        return(record);
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
        //set marc record ID
        marcRecordID = getUniqueMarcRecordID();
    }

    /**
     * Actions to be taken after write to output file
     */
    void postWrite() throws IOException, ParseException {

        //write leader to output file
        if(leader != null) {
            leader.setMarcRecordID(marcRecordID);
            leader.writeToFile();
        }

        int controlfield_index = 1;
        //write control fields to file
        for(MarcControlfield controlfield : controlfields) {
            controlfield.setMarcRecordID(marcRecordID);
            controlfield.setControlfieldIndex(controlfield_index++);
            controlfield.writeToFile();
        }

        int datafield_index = 1;
        //write data fields to file
        for(MarcDatafield datafield : datafields) {
            datafield.setMarcRecordID(marcRecordID);
            datafield.setDatafieldIndex(datafield_index++);
            datafield.writeToFile();
        }

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
        return("ID");
    }

    /**
     * Build output string
     */
     void buildOutput(){
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+marcRecordID+")");

        //build output string
        output_string
                .append(CsvConverter.asCsvString(marcRecordID));

        //end-of-line
        output_string
                .append("\n");

    }

    /**
     * Get unique marc record ID.
     * @return unique marc record ID, or null when list with control fields is empty or when no control field exists with MARC_RECORD_ID_TAG
     */
    public String getUniqueMarcRecordID() {
        //check control field exists
        if(this.controlfields == null) {
            return null;
        }

        //iterate through list
        for (MarcControlfield controlfield : this.controlfields) {
            //get value for MARC_RECORD_ID_TAG control field
            //only one such control field exists according to MARC specifications
            if (controlfield.getTag().equals(MarcControlfield.MARC_RECORD_ID_TAG)) {
                //return value when found
                return (controlfield.getValue());
            }
        }

        //return default value
        return null;
    }


}
