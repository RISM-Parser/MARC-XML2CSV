package MARC;

import Utils.FileAppender;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public abstract class Marc {

    //attributes
    protected String marcRecordID;

    //output
    protected StringBuilder output_string;

    //Getters & setters
    public String getMarcRecordID() {
        return marcRecordID;
    }

    public void setMarcRecordID(String marcRecordID) {
        this.marcRecordID = marcRecordID;
    }

    /**
     * write marc data to file
     * @throws IOException Error while writing to file
     * @throws ParseException Exception when unknown tag occurred
     */
    public void writeToFile() throws IOException, ParseException {

        //Actions to be taken before write to output file
        preWrite();

        //string to be written to file
        output_string = new StringBuilder();

        //output file
        File f = new File(outputFilename());

        //if file not exists
        if(!f.exists()){
            //header line of file
            buildHeader();
        }

        try {
            //build output string
            buildOutput();
        } finally {
            //always write line to output file
            FileAppender.appendFile(f, output_string);
        }

        //Actions to be taken after write to output file
        postWrite();

    }

    /**
     * Build header
     */
    private void buildHeader() throws ParseException {
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+marcRecordID+")");

        //header line of file
        output_string
                .append(fileHeader())
                .append("\n");

    }

    /**
     * Actions to be taken before write to output file
     */
    abstract void preWrite();

    /**
     * Actions to be taken after write to output file
     */
    abstract void postWrite() throws IOException, ParseException;

    /**
     * Construct output filename
     * @return filename
     */
    abstract String outputFilename();

    /**
     * Header line of file describing all columns
     * @return String containing header line
     */
    abstract String fileHeader() throws ParseException;

    /**
     * Build output string
     */
    abstract void buildOutput() throws ParseException;



}
