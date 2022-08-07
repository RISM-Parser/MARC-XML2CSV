package MARC;

import XML.XmlNode;
import Utils.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MarcDatafield extends Marc {

    //identifying XML string
    public static final String XML_TOKEN = "marc:datafield";

    //output: separate multiple values in one column
    private static final String VALUE_SEPARATOR = "^^^";

    //output filename
    private static final String OUTPUT_FILE_NAME = "marc_datafield";
    private static final String OUTPUT_FILE_EXTENSION = ".csv";

    //attributes
    private String tag;
    private String indicator1;
    private String indicator2;
    private List<MarcSubfield> subfields;
    private MarcRecord parent;
    private int datafieldIndex;

    //Getters & Setters
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIndicator1() {
        return indicator1;
    }

    public void setIndicator1(String indicator1) {
        this.indicator1 = indicator1;
    }

    public String getIndicator2() {
        return indicator2;
    }

    public void setIndicator2(String indicator2) {
        this.indicator2 = indicator2;
    }

    public List<MarcSubfield> getSubfields() {
        return subfields;
    }

    public void setSubfields(List<MarcSubfield> subfields) {
        this.subfields = subfields;
    }

    public MarcRecord getParent() {
        return parent;
    }

    public void setParent(MarcRecord parent) {
        this.parent = parent;
    }

    public int getDatafieldIndex() {
        return datafieldIndex;
    }

    public void setDatafieldIndex(int datafieldIndex) {
        this.datafieldIndex = datafieldIndex;
    }

    //Constructor
    public MarcDatafield() {
        this.tag = null;
        this.indicator1 = null;
        this.indicator2 = null;
        this.subfields = null;
        this.parent = null;
    }

    //Create string
    @Override
    public String toString() {
        return "MarcDatafield{" +
                "tag='" + tag + '\'' +
                ", indicator1='" + indicator1 + '\'' +
                ", indicator2='" + indicator2 + '\'' +
                ", subfields=" + subfields +
                '}';
    }

    /**
     * Create initial list of control fields
     */
    private void initializeSubfields() {
        this.subfields = new ArrayList<>();
    }

    /**
     * Add marc subfield to list of subfields.
     * @param subfield subfield to be added
     * @return indicator if addition succeeded
     */
    public boolean addSubfield(MarcSubfield subfield) {
        //exists subfield list? if no: initialize list
        if(this.subfields == null) {
            initializeSubfields();
        }
        //add subfield to list
        return(this.subfields.add(subfield));
    }

    /**
     * Create marc data field from XML node
     * @param node XML node to be processed
     * @param parent connection to parent
     * @return new marc data field
     */
    public static MarcDatafield create(XmlNode node, MarcRecord parent) {
        MarcDatafield datafield = new MarcDatafield();
        datafield.setTag(node.getFirstAttributeValue());
        datafield.setIndicator1(node.getSecondAttributeValue());
        datafield.setIndicator2(node.getThirdAttributeValue());
        datafield.setParent(parent);
        for(XmlNode child : node.getChildren()) {
            MarcSubfield subfield = MarcSubfield.create(child, datafield);
            datafield.addSubfield(subfield);
        }
        return(datafield);
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
    void postWrite() throws IOException, ParseException {

//        int subfield_index = 1;
//        //write subfields to file
//        for(MarcSubfield subfield : subfields) {
//            subfield.setDatafieldIndex(datafieldIndex);
//            subfield.setSubfieldIndex(subfield_index++);
//            subfield.writeToFile();
//        }

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
    String fileHeader() throws ParseException {
        //default header
        StringBuilder header = new StringBuilder("ID,index,tag,indicator1,indicator2");

        //specific extensions
        switch (tag) {
            case "028" -> header.append(header028());
            case "031" -> header.append(header031());
            case "035" -> header.append(header035());
            case "040" -> header.append(header040());
            case "041" -> header.append(header041());
            case "100" -> header.append(header100());
            case "130" -> header.append(header130());
            case "240" -> header.append(header240());
            case "245" -> header.append(header245());
            case "246" -> header.append(header246());
            case "260" -> header.append(header260());
            case "300" -> header.append(header300());
            case "340" -> header.append(header340());
            case "383" -> header.append(header383());
            case "500" -> header.append(header500());
            case "505" -> header.append(header505());
            case "506" -> header.append(header506());
            case "510" -> header.append(header510());
            case "518" -> header.append(header518());
            case "520" -> header.append(header520());
            case "525" -> header.append(header525());
            case "541" -> header.append(header541());
            case "546" -> header.append(header546());
            case "561" -> header.append(header561());
            case "563" -> header.append(header563());
            case "588" -> header.append(header588());
            case "590" -> header.append(header590());
            case "591" -> header.append(header591());
            case "592" -> header.append(header592());
            case "593" -> header.append(header593());
            case "594" -> header.append(header594());
            case "595" -> header.append(header595());
            case "596" -> header.append(header596());
            case "597" -> header.append(header597());
            case "598" -> header.append(header598());
            case "599" -> header.append(header599());
            case "650" -> header.append(header650());
            case "651" -> header.append(header651());
            case "657" -> header.append(header657());
            case "690" -> header.append(header690());
            case "691" -> header.append(header691());
            case "692" -> header.append(header692());
            case "693" -> header.append(header693());
            case "694" -> header.append(header694());
            case "695" -> header.append(header695());
            case "696" -> header.append(header696());
            case "697" -> header.append(header697());
            case "698" -> header.append(header698());
            case "699" -> header.append(header699());
            case "700" -> header.append(header700());
            case "710" -> header.append(header710());
            case "730" -> header.append(header730());
            case "760" -> header.append(header760());
            case "762" -> header.append(header762());
            case "765" -> header.append(header765());
            case "767" -> header.append(header767());
            case "770" -> header.append(header770());
            case "772" -> header.append(header772());
            case "773" -> header.append(header773());
            case "774" -> header.append(header774());
            case "775" -> header.append(header775());
            case "776" -> header.append(header776());
            case "777" -> header.append(header777());
            case "780" -> header.append(header780());
            case "785" -> header.append(header785());
            case "787" -> header.append(header787());
            case "786" -> header.append(header786());
            case "852" -> header.append(header852());
            case "856" -> header.append(header856());
            case "930" -> header.append(header930());
            case "973" -> header.append(header973());
            case "980" -> header.append(header980());
            default -> header.append(headerAllColumns());
        }

        //return header
        return(header.toString());

    }

    /**
     * Build output string
     */
    void buildOutput() throws ParseException {
        //check
        if(output_string == null) throw new RuntimeException("output string is null ("+marcRecordID+" tag:"+tag+" indicator1:"+indicator1+" indicator2:"+indicator2+")");

        //build output string
        output_string
                .append(CsvConverter.asCsvString(marcRecordID))
                .append(",")
                .append(datafieldIndex)
                .append(",")
                .append(CsvConverter.asCsvString(tag))
                .append(",")
                .append(CsvConverter.asCsvString(indicator1))
                .append(",")
                .append(CsvConverter.asCsvString(indicator2));

        switch (tag) {
            case "028" -> output028();
            case "031" -> output031();
            case "035" -> output035();
            case "040" -> output040();
            case "041" -> output041();
            case "100" -> output100();
            case "130" -> output130();
            case "240" -> output240();
            case "245" -> output245();
            case "246" -> output246();
            case "260" -> output260();
            case "300" -> output300();
            case "340" -> output340();
            case "383" -> output383();
            case "500" -> output500();
            case "505" -> output505();
            case "506" -> output506();
            case "510" -> output510();
            case "518" -> output518();
            case "520" -> output520();
            case "525" -> output525();
            case "541" -> output541();
            case "546" -> output546();
            case "561" -> output561();
            case "563" -> output563();
            case "588" -> output588();
            case "590" -> output590();
            case "591" -> output591();
            case "592" -> output592();
            case "593" -> output593();
            case "594" -> output594();
            case "595" -> output595();
            case "596" -> output596();
            case "597" -> output597();
            case "598" -> output598();
            case "599" -> output599();
            case "650" -> output650();
            case "651" -> output651();
            case "657" -> output657();
            case "690" -> output690();
            case "691" -> output691();
            case "692" -> output692();
            case "693" -> output693();
            case "694" -> output694();
            case "695" -> output695();
            case "696" -> output696();
            case "697" -> output697();
            case "698" -> output698();
            case "699" -> output699();
            case "700" -> output700();
            case "710" -> output710();
            case "730" -> output730();
            case "760" -> output760();
            case "762" -> output762();
            case "765" -> output765();
            case "767" -> output767();
            case "770" -> output770();
            case "772" -> output772();
            case "773" -> output773();
            case "774" -> output774();
            case "775" -> output775();
            case "776" -> output776();
            case "777" -> output777();
            case "780" -> output780();
            case "785" -> output785();
            case "786" -> output786();
            case "787" -> output787();
            case "852" -> output852();
            case "856" -> output856();
            case "930" -> output930();
            case "973" -> output973();
            case "980" -> output980();
            default -> {
                outputAllColumns();
                output_string.append("\n");  //end-of-line
                throw new ParseException("No implementation for datafield with tag: " + tag,-1);
            }
        }

        //end-of-line
        output_string
                .append("\n");

    }


    /**
     * Get subfield value for code.
     * @param code Code of subfield to be found
     * @return Subfield value for code, empty when subfield is not found
     */
    private String getSubfieldValue(String code) {
        //check
        if(subfields == null) return("");

        //iterate until subfield is found
        for(MarcSubfield subfield : subfields) {
            if(code.equals(subfield.getCode()))
                return(subfield.getValue());
        }
        //return subfield is not found
        return("");
    }

    /**
     * Get subfield values for code.
     * @param code Code of subfield to be found
     * @return Subfield values for code, empty when subfield is not found
     */
    private String getSubfieldValues(String code) {
        //check
        if(subfields == null) return("");

        StringBuilder out = new StringBuilder();
        //iterate until subfield is found
        for(MarcSubfield subfield : subfields) {
            if(code.equals(subfield.getCode())) {
                if(!out.isEmpty()) {
                    out.append(VALUE_SEPARATOR);
                }
                out.append(subfield.getValue());
            }
        }
        //return subfield is not found
        return(out.toString());
    }

    /**
     * Header for 028 detail columns
     * @return header string
     */
    private String header028() {
        return(",a,b,q,6,8");
    }

    /**
     * Extend output string with 031 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Publisher or distributor number (NR)<br/>
     * $b - Source (NR)<br/>
     * $q - Qualifying information (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output028() {
        //Extend output string with 028 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 031 detail columns
     * @return header string
     */
    private String header031() {
        return(",a,b,c,d,e,g,m,n,o,p,q,r,s,t,u,y,z,2,6,8");
    }

    /**
     * Extend output string with 031 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Number of work (NR)<br/>
     * $b - Number of movement (NR)<br/>
     * $c - Number of excerpt (NR)<br/>
     * $d - Caption or heading (R)<br/>
     * $e - Role (NR)<br/>
     * $g - Clef (NR)<br/>
     * $m - Voice/instrument (NR)<br/>
     * $n - Key signature (NR)<br/>
     * $o - Time signature (NR)<br/>
     * $p - Musical notation (NR)<br/>
     * $q - General note (R)<br/>
     * $r - Key or mode (NR)<br/>
     * $s - Coded validity note (R)<br/>
     * $t - Text incipit (R)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $y - Link text (R)<br/>
     * $z - Public note (R)<br/>
     * $2 - System code (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output031() {
        //Extend output string with 031 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 035 detail columns
     * @return header string
     */
    private String header035() {
        return(",a,z,6,8");
    }

    /**
     * Extend output string with 035 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - System control number (NR)<br/>
     * $z - Canceled/invalid control number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output035() {
        //Extend output string with 035 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 040 detail columns
     * @return header string
     */
    private String header040() {
        return(",a,b,c,d,e,6,8");
    }

    /**
     * Extend output string with 040 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Original cataloging agency (NR)<br/>
     * $b - Language of cataloging (NR)<br/>
     * $c - Transcribing agency (NR)<br/>
     * $d - Modifying agency (R)<br/>
     * $e - Description conventions (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output040() {
        //Extend output string with 040 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 041 detail columns
     * @return header string
     */
    private String header041() {
        return(",a,b,d,e,f,g,h,i,j,k,m,n,p,q,r,t,2,6,8");
    }

    /**
     * Extend output string with 041 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Language code of text/sound track or separate title (R)<br/>
     * $b - Language code of summary or abstract (R)<br/>
     * $d - Language code of sung or spoken text (R)<br/>
     * $e - Language code of librettos (R)<br/>
     * $f - Language code of table of contents (R)<br/>
     * $g - Language code of accompanying material other than librettos and transcripts (R)<br/>
     * $h - Language code of original (R)<br/>
     * $i - Language code of intertitles (R)<br/>
     * $j - Language code of subtitles (R)<br/>
     * $k - Language code of intermediate translations (R)<br/>
     * $m - Language code of original accompanying materials other than librettos (R)<br/>
     * $n - Language code of original libretto (R)<br/>
     * $p - Language code of captions (R)<br/>
     * $q - Language code of accessible audio (R)<br/>
     * $r - Language code of accessible visual language (non-textual) (R)<br/>
     * $t - Language code of accompanying transcripts for audiovisual materials (R)<br/>
     * $2 - Source of code (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output041() {
        //Extend output string with 041 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("j")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 100 detail columns
     * @return header string
     */
    private String header100() {
        return(",a,b,c,d,e,f,g,j,k,l,n,p,q,t,u,0,1,2,4,6,8");
    }

    /**
     * Extend output string with 100 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Personal name (NR)<br/>
     * $b - Numeration (NR)<br/>
     * $c - Titles and words associated with a name (R)<br/>
     * $d - Dates associated with a name (NR)<br/>
     * $e - Relator term (R)<br/>
     * $f - Date of a work (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $j - Attribution qualifier (R)<br/>
     * $k - Form subheading (R)<br/>
     * $l - Language of a work (NR)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $q - Fuller form of name (NR)<br/>
     * $t - Title of a work (NR)<br/>
     * $u - Affiliation (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output100() {
        //Extend output string with 100 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("j")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 130 detail columns
     * @return header string
     */
    private String header130() {
        return(",a,d,f,g,h,k,l,m,n,o,p,r,s,t,0,1,2,6,8");
    }

    /**
     * Extend output string with 130 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Uniform title (NR)<br/>
     * $d - Date of treaty signing (R)<br/>
     * $f - Date of a work (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $h - Medium (NR)<br/>
     * $k - Form subheading (R)<br/>
     * $l - Language of a work (NR)<br/>
     * $m - Medium of performance for music (R)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $o - Arranged statement for music (NR)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $r - Key for music (NR)<br/>
     * $s - Version (R)<br/>
     * $t - Title of a work (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output130() {
        //Extend output string with 130 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 240 detail columns
     * @return header string
     */
    private String header240() {
        return(",a,d,f,g,h,k,l,m,n,o,p,r,s,0,1,2,6,8");
    }

    /**
     * Extend output string with 240 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Uniform title (NR)<br/>
     * $d - Date of treaty signing (R)<br/>
     * $f - Date of a work (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $h - Medium (NR)<br/>
     * $k - Form subheading (R)<br/>
     * $l - Language of a work (NR)<br/>
     * $m - Medium of performance for music (R)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $o - Arranged statement for music (NR)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $r - Key for music (NR)<br/>
     * $s - Version (R)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output240() {
        //Extend output string with 240 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 245 detail columns
     * @return header string
     */
    private String header245() {
        return(",a,b,c,f,g,h,k,n,p,s,6,8");
    }

    /**
     * Extend output string with 245 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Title (NR)<br/>
     * $b - Remainder of title (NR)<br/>
     * $c - Statement of responsibility, etc. (NR)<br/>
     * $f - Inclusive dates (NR)<br/>
     * $g - Bulk dates (NR)<br/>
     * $h - Medium (NR)<br/>
     * $k - Form (R)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $s - Version (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output245() {
        //Extend output string with 245 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 246 detail columns
     * @return header string
     */
    private String header246() {
        return(",a,b,f,g,h,i,n,p,5,6,8");
    }

    /**
     * Extend output string with 246 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Title proper/short title (NR)<br/>
     * $b - Remainder of title (NR)<br/>
     * $f - Date or sequential designation (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $h - Medium (NR)<br/>
     * $i - Display text (NR)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output246() {
        //Extend output string with 246 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 260 detail columns
     * @return header string
     */
    private String header260() {
        return(",a,b,c,e,f,g,3,6,8");
    }

    /**
     * Extend output string with 260 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Place of publication, distribution, etc. (R)<br/>
     * $b - Name of publisher, distributor, etc. (R)<br/>
     * $c - Date of publication, distribution, etc. (R)<br/>
     * $e - Place of manufacture (R)<br/>
     * $f - Manufacturer (R)<br/>
     * $g - Date of manufacture (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output260() {
        //Extend output string with 260 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 300 detail columns
     * @return header string
     */
    private String header300() {
        return(",a,b,c,e,f,g,3,6,8");
    }

    /**
     * Extend output string with 300 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Extent (R)<br/>
     * $b - Other physical details (NR)<br/>
     * $c - Dimensions (R)<br/>
     * $e - Accompanying material (NR)<br/>
     * $f - Type of unit (R)<br/>
     * $g - Size of unit (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output300() {
        //Extend output string with 300 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 340 detail columns
     * @return header string
     */
    private String header340() {
        return(",a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,0,1,2,3,6,8");
    }

    /**
     * Extend output string with 340 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Material base and configuration (R)<br/>
     * $b - Dimensions (R)<br/>
     * $c - Materials applied to surface (R)<br/>
     * $d - Information recording technique (R)<br/>
     * $e - Support (R)<br/>
     * $f - Production rate/ratio (R)<br/>
     * $g - Color content (R)<br/>
     * $h - Location within medium (R)<br/>
     * $i - Technical specifications of medium (R)<br/>
     * $j - Generation (R)<br/>
     * $k - Layout (R)<br/>
     * $l - Binding (R)<br/>
     * $m - Book format (R)<br/>
     * $n - Font size (R)<br/>
     * $o - Polarity (R)<br/>
     * $p - Illustrative content (R)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output340() {
        //Extend output string with 340 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("j")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 383 detail columns
     * @return header string
     */
    private String header383() {
        return(",a,b,c,d,e,2,3,6,8");
    }

    /**
     * Extend output string with 383 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Serial number (R)<br/>
     * $b - Opus number (R)<br/>
     * $c - Thematic index number (R)<br/>
     * $d - Thematic index code (NR)<br/>
     * $e - Publisher associated with opus number (NR)<br/>
     * $2 - Source (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output383() {
        //Extend output string with 383 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 500 detail columns
     * @return header string
     */
    private String header500() {
        return(",a,3,5,6,8");
    }

    /**
     * Extend output string with 500 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - General note (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output500() {
        //Extend output string with 500 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 505 detail columns
     * @return header string
     */
    private String header505() {
        return(",a,g,r,t,u,6,8");
    }

    /**
     * Extend output string with 505 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Formatted contents note (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $r - Statement of responsibility (R)<br/>
     * $t - Title (R)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output505() {
        //Extend output string with 505 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 506 detail columns
     * @return header string
     */
    private String header506() {
        return(",a,b,c,d,e,f,g,q,u,2,3,5,6,8");
    }

    /**
     * Extend output string with 506 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Terms governing access (NR)<br/>
     * $b - Jurisdiction (R)<br/>
     * $c - Physical access provisions (R)<br/>
     * $d - Authorized users (R)<br/>
     * $e - Authorization (R)<br/>
     * $f - Standardized terminology for access restriction (R)<br/>
     * $g - Availability date (R)<br/>
     * $q - Supplying agency (NR)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $2 - Source of term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output506() {
        //Extend output string with 506 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 510 detail columns
     * @return header string
     */
    private String header510() {
        return(",a,b,c,u,x,3,6,8");
    }

    /**
     * Extend output string with 510 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Name of source (NR)<br/>
     * $b - Coverage of source (NR)<br/>
     * $c - Location within source (NR)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output510() {
        //Extend output string with 510 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 518 detail columns
     * @return header string
     */
    private String header518() {
        return(",a,d,o,p,0,1,2,3,6,8");
    }

    /**
     * Extend output string with 518 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Date/time and place of an event note (NR)<br/>
     * $d - Date of event (R)<br/>
     * $o - Other event information (R)<br/>
     * $p - Place of event (R)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of term (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output518() {
        //Extend output string with 518 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 520 detail columns
     * @return header string
     */
    private String header520() {
        return(",a,b,c,u,2,3,6,8");
    }

    /**
     * Extend output string with 520 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Summary, etc. (NR)<br/>
     * $b - Expansion of summary note (NR)<br/>
     * $c - Assigning source (NR)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $2 - Source (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output520() {
        //Extend output string with 520 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 525 detail columns
     * @return header string
     */
    private String header525() {
        return(",a,6,8");
    }

    /**
     * Extend output string with 525 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Supplement note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output525() {
        //Extend output string with 525 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 541 detail columns
     * @return header string
     */
    private String header541() {
        return(",a,b,c,d,e,f,h,n,o,3,5,6,8");
    }

    /**
     * Extend output string with 541 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Source of acquisition (NR)<br/>
     * $b - Address (NR)<br/>
     * $c - Method of acquisition (NR)<br/>
     * $d - Date of acquisition (NR)<br/>
     * $e - Accession number (NR)<br/>
     * $f - Owner (NR)<br/>
     * $h - Purchase price (NR)<br/>
     * $n - Extent (R)<br/>
     * $o - Type of unit (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output541() {
        //Extend output string with 541 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 546 detail columns
     * @return header string
     */
    private String header546() {
        return(",a,b,3,6,8");
    }

    /**
     * Extend output string with 546 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Language note (NR)<br/>
     * $b - Information code or alphabet (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output546() {
        //Extend output string with 546 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 561 detail columns
     * @return header string
     */
    private String header561() {
        return(",a,u,3,5,6,8");
    }

    /**
     * Extend output string with 561 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - History (NR)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output561() {
        //Extend output string with 561 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 563 detail columns
     * @return header string
     */
    private String header563() {
        return(",a,u,3,5,6,8");
    }

    /**
     * Extend output string with 563 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Binding note (NR)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output563() {
        //Extend output string with 563 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 588 detail columns
     * @return header string
     */
    private String header588() {
        return(",a,5,6,8");
    }

    /**
     * Extend output string with 588 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Source of description note (NR)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output588() {
        //Extend output string with 588 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 590 detail columns
     * @return header string
     */
    private String header590() {
        return(",a,b,c,3,6,8");
    }

    /**
     * Extend output string with 590 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output590() {
        //Extend output string with 590 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 591 detail columns
     * @return header string
     */
    private String header591() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 591 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output591() {
        //Extend output string with 591 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 592 detail columns
     * @return header string
     */
    private String header592() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 592 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output592() {
        //Extend output string with 592 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 593 detail columns
     * @return header string
     */
    private String header593() {
        return(",a,6,8");
    }

    /**
     * Extend output string with 593 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output593() {
        //Extend output string with 593 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 594 detail columns
     * @return header string
     */
    private String header594() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 594 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output594() {
        //Extend output string with 594 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 595 detail columns
     * @return header string
     */
    private String header595() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 595 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output595() {
        //Extend output string with 595 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 596 detail columns
     * @return header string
     */
    private String header596() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 596 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output596() {
        //Extend output string with 596 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 597 detail columns
     * @return header string
     */
    private String header597() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 597 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output597() {
        //Extend output string with 597 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 598 detail columns
     * @return header string
     */
    private String header598() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 598 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output598() {
        //Extend output string with 598 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 599 detail columns
     * @return header string
     */
    private String header599() {
        return(",a,b,c,6,8");
    }

    /**
     * Extend output string with 599 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local note (NR)<br/>
     * $b - Local note (NR)<br/>
     * $c - Local note (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output599() {
        //Extend output string with 599 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 650 detail columns
     * @return header string
     */
    private String header650() {
        return(",a,b,c,d,e,g,v,x,y,z,0,1,2,3,4,6,8");
    }

    /**
     * Extend output string with 650 detail columns
     * <br/>
     * Subfield Codes<br/>
     * Main term portion<br/>
     * $a - Topical term or geographic name entry element (NR)<br/>
     * $b - Topical term following geographic name entry element (NR)<br/>
     * $c - Location of event (NR)<br/>
     * $d - Active dates (NR)<br/>
     * $e - Relator term (R)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $4 - Relationship (R)<br/>
     * Subject subdivision portion<br/>
     * $v - Form subdivision (R)<br/>
     * $x - General subdivision (R)<br/>
     * $y - Chronological subdivision (R)<br/>
     * $z - Geographic subdivision (R)<br/>
     * Control subfields<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output650() {
        //Extend output string with 650 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("v")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 651 detail columns
     * @return header string
     */
    private String header651() {
        return(",a,e,g,v,x,y,z,0,1,2,3,4,6,8");
    }

    /**
     * Extend output string with 651 detail columns
     * <br/>
     * Subfield Codes<br/>
     * Name portion<br/>
     * $a - Geographic name (NR)<br/>
     * $e - Relator term (R)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $4 - Relationship (R)<br/>
     * Subject subdivision portion<br/>
     * $v - Form subdivision (R)<br/>
     * $x - General subdivision (R)<br/>
     * $y - Chronological subdivision (R)<br/>
     * $z - Geographic subdivision (R)<br/>
     * Control subfields<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output651() {
        //Extend output string with 651 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("v")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 657 detail columns
     * @return header string
     */
    private String header657() {
        return(",a,v,x,y,z,0,1,2,3,6,8");
    }

    /**
     * Extend output string with 657 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Function (NR)<br/>
     * $v - Form subdivision (R)<br/>
     * $x - General subdivision (R)<br/>
     * $y - Chronological subdivision (R)<br/>
     * $z - Geographic subdivision (R)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output657() {
        //Extend output string with 657 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("v")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 690 detail columns
     * @return header string
     */
    private String header690() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 690 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output690() {
        //Extend output string with 690 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 691 detail columns
     * @return header string
     */
    private String header691() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 691 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output691() {
        //Extend output string with 691 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 692 detail columns
     * @return header string
     */
    private String header692() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 692 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output692() {
        //Extend output string with 692 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 693 detail columns
     * @return header string
     */
    private String header693() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 693 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output693() {
        //Extend output string with 693 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 694 detail columns
     * @return header string
     */
    private String header694() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 694 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output694() {
        //Extend output string with 694 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 695 detail columns
     * @return header string
     */
    private String header695() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 695 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output695() {
        //Extend output string with 695 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 696 detail columns
     * @return header string
     */
    private String header696() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 696 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output696() {
        //Extend output string with 696 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 697 detail columns
     * @return header string
     */
    private String header697() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 697 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output697() {
        //Extend output string with 697 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 698 detail columns
     * @return header string
     */
    private String header698() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 698 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output698() {
        //Extend output string with 698 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 699 detail columns
     * @return header string
     */
    private String header699() {
        return(",a,n,0,6,8");
    }

    /**
     * Extend output string with 699 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Local subject note (NR)<br/>
     * $n - Local subject note (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output699() {
        //Extend output string with 699 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 700 detail columns
     * @return header string
     */
    private String header700() {
        return(",a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,x,0,1,2,3,4,5,6,8");
    }

    /**
     * Extend output string with 700 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Personal name (NR)<br/>
     * $b - Numeration (NR)<br/>
     * $c - Titles and other words associated with a name (R)<br/>
     * $d - Dates associated with a name (NR)<br/>
     * $e - Relator term (R)<br/>
     * $f - Date of a work (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $h - Medium (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $j - Attribution qualifier (R)<br/>
     * $k - Form subheading (R)<br/>
     * $l - Language of a work (NR)<br/>
     * $m - Medium of performance for music (R)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $o - Arranged statement for music (NR)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $q - Fuller form of name (NR)<br/>
     * $r - Key for music (NR)<br/>
     * $s - Version (R)<br/>
     * $t - Title of a work (NR)<br/>
     * $u - Affiliation (NR)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output700() {
        //Extend output string with 700 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("j")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 710 detail columns
     * @return header string
     */
    private String header710() {
        return(",a,b,c,d,e,f,g,h,i,k,l,m,n,o,p,r,s,t,u,x,0,1,2,3,4,5,6,8");
    }

    /**
     * Extend output string with 710 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Corporate name or jurisdiction name as entry element (NR)<br/>
     * $b - Subordinate unit (R)<br/>
     * $c - Location of meeting (R)<br/>
     * $d - Date of meeting or treaty signing (R)<br/>
     * $e - Relator term (R)<br/>
     * $f - Date of a work (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $h - Medium (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Form subheading (R)<br/>
     * $l - Language of a work (NR)<br/>
     * $m - Medium of performance for music (R)<br/>
     * $n - Number of part/section/meeting (R)<br/>
     * $o - Arranged statement for music (NR)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $r - Key for music (NR)<br/>
     * $s - Version (R)<br/>
     * $t - Title of a work (NR)<br/>
     * $u - Affiliation (NR)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output710() {
        //Extend output string with 710 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 730 detail columns
     * @return header string
     */
    private String header730() {
        return(",a,d,f,g,h,i,k,l,m,n,o,p,r,s,t,x,0,1,2,3,4,5,6,8");
    }

    /**
     * Extend output string with 730 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Uniform title (NR)<br/>
     * $d - Date of treaty signing (R)<br/>
     * $f - Date of a work (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $h - Medium (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Form subheading (R)<br/>
     * $l - Language of a work (NR)<br/>
     * $m - Medium of performance for music (R)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $o - Arranged statement for music (NR)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $r - Key for music (NR)<br/>
     * $s - Version (R)<br/>
     * $t - Title of a work (NR)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output730() {
        //Extend output string with 730 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 760 detail columns
     * @return header string
     */
    private String header760() {
        return(",a,b,c,d,g,h,i,m,n,o,s,t,w,x,y,4,6,7,8");
    }

    /**
     * Extend output string with 760 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * 0 - Type of main entry heading<br/>
     * 1 - Form of name<br/>
     * 2 - Type of record<br/>
     * 3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output760() {
        //Extend output string with 760 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 762 detail columns
     * @return header string
     */
    private String header762() {
        return(",a,b,c,d,g,h,i,m,n,o,s,t,w,x,y,4,6,7,8");
    }

    /**
     * Extend output string with 762 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * 0 - Type of main entry heading<br/>
     * 1 - Form of name<br/>
     * 2 - Type of record<br/>
     * 3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output762() {
        //Extend output string with 762 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 765 detail columns
     * @return header string
     */
    private String header765() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 765 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * 0 - Type of main entry heading<br/>
     * 1 - Form of name<br/>
     * 2 - Type of record<br/>
     * 3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output765() {
        //Extend output string with 765 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 767 detail columns
     * @return header string
     */
    private String header767() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 767 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * 0 - Type of main entry heading<br/>
     * 1 - Form of name<br/>
     * 2 - Type of record<br/>
     * 3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output767() {
        //Extend output string with 767 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 770 detail columns
     * @return header string
     */
    private String header770() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 770 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * 0 - Type of main entry heading<br/>
     * 1 - Form of name<br/>
     * 2 - Type of record<br/>
     * 3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output770() {
        //Extend output string with 770 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 772 detail columns
     * @return header string
     */
    private String header772() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 772 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * 0 - Type of main entry heading<br/>
     * 1 - Form of name<br/>
     * 2 - Type of record<br/>
     * 3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output772() {
        //Extend output string with 772 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 773 detail columns
     * @return header string
     */
    private String header773() {
        return(",a,b,d,g,h,i,k,m,n,o,p,q,r,s,t,u,w,x,y,z,3,4,6,7,8");
    }

    /**
     * Extend output string with 773 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $p - Abbreviated title (NR)<br/>
     * $q - Enumeration and first page (NR)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output773() {
        //Extend output string with 773 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 774 detail columns
     * @return header string
     */
    private String header774() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 774 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output774() {
        //Extend output string with 774 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 775 detail columns
     * @return header string
     */
    private String header775() {
        return(",a,b,c,d,e,f,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 775 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $e - Language code (NR)<br/>
     * $f - Country code (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output775() {
        //Extend output string with 775 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 776 detail columns
     * @return header string
     */
    private String header776() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 776 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output776() {
        //Extend output string with 776 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 777 detail columns
     * @return header string
     */
    private String header777() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 777 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output777() {
        //Extend output string with 777 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 780 detail columns
     * @return header string
     */
    private String header780() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 780 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output780() {
        //Extend output string with 780 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 785 detail columns
     * @return header string
     */
    private String header785() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 785 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output785() {
        //Extend output string with 785 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 786 detail columns
     * @return header string
     */
    private String header786() {
        return(",a,b,c,d,g,h,i,j,k,m,n,o,p,r,s,t,u,v,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 786 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $j - Period of content (NR)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $p - Abbreviated title (NR)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $v - Source Contribution (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output786() {
        //Extend output string with 786 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("j")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 787 detail columns
     * @return header string
     */
    private String header787() {
        return(",a,b,c,d,g,h,i,k,m,n,o,r,s,t,u,w,x,y,z,4,6,7,8");
    }

    /**
     * Extend output string with 787 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $c - Qualifying information (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output787() {
        //Extend output string with 787 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 852 detail columns
     * @return header string
     */
    private String header852() {
        return(",a,b,c,d,e,f,g,h,i,j,k,l,m,n,p,q,s,t,u,x,z,2,3,6,8");
    }
    /**
     * Extend output string with 852 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Location (NR)<br/>
     * $b - Sublocation or collection (R)<br/>
     * $c - Shelving location (R)<br/>
     * $d - Former shelving location (R)<br/>
     * $e - Address (R)<br/>
     * $f - Coded location qualifier (R)<br/>
     * $g - Non-coded location qualifier (R)<br/>
     * $h - Classification part (NR)<br/>
     * $i - Item part (R)<br/>
     * $j - Shelving control number (NR)<br/>
     * $k - Call number prefix (R)<br/>
     * $l - Shelving form of title (NR)<br/>
     * $m - Call number suffix (R)<br/>
     * $n - Country code (NR)<br/>
     * $p - Piece designation (NR)<br/>
     * $q - Piece physical condition (NR)<br/>
     * $s - Copyright article-fee code (R)<br/>
     * $t - Copy number (NR)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $x - Nonpublic note (R)<br/>
     * $z - Public note (R)<br/>
     * $2 - Source of classification or shelving scheme (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Sequence number (NR)
     */
    private void output852() {
        //Extend output string with 852 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("j")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 856 detail columns
     * @return header string
     */
    private String header856() {
        return(",a,c,d,f,m,o,p,q,s,u,v,w,x,y,z,2,3,6,7,8");
    }

    /**
     * Extend output string with 856 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Host name (R)<br/>
     * $c - Compression information (R)<br/>
     * $d - Path (R)<br/>
     * $f - Electronic name (R)<br/>
     * $m - Contact for access assistance (R)<br/>
     * $o - Operating system (NR)<br/>
     * $p - Port (NR)<br/>
     * $q - Electronic format type (NR)<br/>
     * $s - File size (R)<br/>
     * $u - Uniform Resource Identifier (R)<br/>
     * $v - Hours access method available (R)<br/>
     * $w - Record control number (R)<br/>
     * $x - Nonpublic note (R)<br/>
     * $y - Link text (R)<br/>
     * $z - Public note (R)<br/>
     * $2 - Access method (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Access status (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output856() {
        //Extend output string with 856 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("v")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 930 detail columns
     * @return header string
     */
    private String header930() {
        return(",a,d,f,g,h,i,k,l,m,n,o,p,r,s,t,x,0,1,2,3,4,5,6,8");
    }

    /**
     * Extend output string with 930 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Uniform title (NR)<br/>
     * $d - Date of treaty signing (R)<br/>
     * $f - Date of a work (NR)<br/>
     * $g - Miscellaneous information (R)<br/>
     * $h - Medium (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Form subheading (R)<br/>
     * $l - Language of a work (NR)<br/>
     * $m - Medium of performance for music (R)<br/>
     * $n - Number of part/section of a work (R)<br/>
     * $o - Arranged statement for music (NR)<br/>
     * $p - Name of part/section of a work (R)<br/>
     * $r - Key for music (NR)<br/>
     * $s - Version (R)<br/>
     * $t - Title of a work (NR)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $0 - Authority record control number or standard number (R)<br/>
     * $1 - Real World Object URI (R)<br/>
     * $2 - Source of heading or term (NR)<br/>
     * $3 - Materials specified (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $5 - Institution to which field applies (NR)<br/>
     * $6 - Linkage (NR)<br/>
     * $8 - Field link and sequence number (R)
     */
    private void output930() {
        //Extend output string with 930 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 980 detail columns
     * @return header string
     */
    private String header980() {
        return(",a,b,c,d,e,f,g,6,8");
    }

    /**
     * Extend output string with 980 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Code for project (NR)<br/>
     * $b - Sublibrary code (NR)<br/>
     * $c - Holdings covered by library-of-record decision (NR)<br/>
     * $d - Date of library-of-record decision (NR) 8 digits in form YYYYMMDD<br/>
     * $e - Person/Committee making library-of-record decision (NR)<br/>
     * $f - Locally defined codes (NR)<br/>
     * $g - Note concerning library-of-record decision (NR)<br/>
     * https://wiki.harvard.edu/confluence/display/LibraryStaffDoc/Harvard+defined+MARC+fields#HarvarddefinedMARCfields-980%E2%80%93Libraryofrecordfield
     */
    private void output980() {
        //Extend output string with 980 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for 973 detail columns
     * @return header string
     */
    private String header973() {
        return(",a,b,d,g,h,i,k,m,n,o,p,q,r,s,t,u,w,x,y,z,3,4,6,7,8");
    }

    /**
     * Extend output string with 973 detail columns
     * <br/>
     * Subfield Codes<br/>
     * $a - Main entry heading (NR)<br/>
     * $b - Edition (NR)<br/>
     * $d - Place, publisher, and date of publication (NR)<br/>
     * $g - Related parts (R)<br/>
     * $h - Physical description (NR)<br/>
     * $i - Relationship information (R)<br/>
     * $k - Series data for related item (R)<br/>
     * $m - Material-specific details (NR)<br/>
     * $n - Note (R)<br/>
     * $o - Other item identifier (R)<br/>
     * $p - Abbreviated title (NR)<br/>
     * $q - Enumeration and first page (NR)<br/>
     * $r - Report number (R)<br/>
     * $s - Uniform title (NR)<br/>
     * $t - Title (NR)<br/>
     * $u - Standard Technical Report Number (NR)<br/>
     * $w - Record control number (R)<br/>
     * $x - International Standard Serial Number (NR)<br/>
     * $y - CODEN designation (NR)<br/>
     * $z - International Standard Book Number (R)<br/>
     * $3 - Materials specified (NR)<br/>
     * $4 - Relationship (R)<br/>
     * $6 - Linkage (NR)<br/>
     * $7 - Control subfield (NR)<br/>
     * /0 - Type of main entry heading<br/>
     * /1 - Form of name<br/>
     * /2 - Type of record<br/>
     * /3 - Bibliographic level<br/>
     * $8 - Field link and sequence number (R)<br/>
     */
    private void output973() {
        //Extend output string with 973 detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValue("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")));
    }

    /**
     * Header for all detail columns
     * @return header string
     */
    private String headerAllColumns() {
        return(",a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9");
    }

    /**
     * Extend output string with all detail columns
     */
    private void outputAllColumns() {
        //Extend output string with all detail columns
        output_string
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("a")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("b")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("c")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("d")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("e")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("f")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("g")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("h")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("i")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("j")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("k")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("l")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("m")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("n")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("o")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("p")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("q")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("r")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("s")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("t")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("u")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("v")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("w")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("x")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("y")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("z")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("0")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("1")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("2")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("3")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("4")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("5")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("6")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("7")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("8")))
                .append(",")
                .append(CsvConverter.asCsvString(getSubfieldValues("9")));
    }

}
