package XML;

import java.text.ParseException;

/**
 * Very simple XML parser.
 * Inspired by https://www.youtube.com/watch?v=kPFYfTvMRs8 [Alligood, J. (2020) XML Parser in C (Start to Finish)]
 * Conditions:
 * - XML doesn't contain comments.
 * - XML doesn't contain prolog; '<?xml version="1.0" encoding="UTF-8"?>' won't be processed -> resulting in an error.
 * - attributes don't have spaces surrounding '='; 'key="value"' will be processed, 'key = "value"' won't be processed -? resulting in an error.
  */

public class XMLParser {

    //buffer size for lexical buffer
    private final static int LEX_BUFFER_SIZE = 250000;

    private static char[] lexical_buffer;          //buffer for lexical analysis
    private static int lexical_index;              //index in buffer

    private static String raw_string;              //string to be parsed
    private static int raw_index;                  //index in string to be parsed

    private static XmlNode current_node;           //current XML node to be filled with data
    private static XmlAttribute current_attribute; //current XML attribute to be filled with data

    /**
     * Parse string as XML.
     * @param string_to_be_parsed String to be parsed, containing XML
     * @return XmlNode Root node of a nested XML structure, or null in case of empty string
     * @throws ParseException Error while parsing XML
     */
    public static XmlNode parse(String string_to_be_parsed) throws ParseException {

        //when string is empty return null
        if(string_to_be_parsed.trim().length() == 0) {
            return null;
        }

        //node to store parsed XML tree
        XmlNode root = new XmlNode(null);    //root node of XML
        current_node = null;                 //current node of XML

        //set start position for parsing
        raw_string = string_to_be_parsed;    //store raw string
        raw_index = 0;                       //start parsing at first character

        // reset lexer
        resetLexer();

        //process character by character
        while (raw_index < rawLength()) {

            //start of new xml node
            if(rawCharAt(raw_index) == '<') {

                ///////////////////////////////////////////////////////
                // Inner text
                ///////////////////////////////////////////////////////

                //check if buffer has text
                if(lexical_index > 0) {

                    //add inner text to current node
                    processInnerText();

                }

                ///////////////////////////////////////////////////////
                // End of node
                ///////////////////////////////////////////////////////

                //check if next character is forward slash
                if(rawCharAt(raw_index + 1) == '/') {

                    //close current node; and get parent of current node
                    current_node = processEndOfNode();

                    //next character in raw string
                    raw_index++;

                    //continue in while
                    continue;
                }

                ///////////////////////////////////////////////////////
                // Set current node
                ///////////////////////////////////////////////////////

                //set current node
                if(current_node == null) {
                    //current node will be the root node
                    current_node = root;
                } else {
                    //set current node as parent to the new (child) node
                    //continue working with child node
                    //parent node returns after end-of-node of child node is processed
                    current_node = new XmlNode(current_node);
                }

                ///////////////////////////////////////////////////////
                // Start of node
                ///////////////////////////////////////////////////////

                //next character in raw string
                raw_index++;

                //fill current node with tag and optional attributes
                processStartOfNode();

                //next character in raw string
                raw_index++;

                //continue in while
                continue;

            } else {

                //add characters to buffer
                lexical_buffer[lexical_index++] = rawCharAt(raw_index++);

            }
        }

        //return the root node
        return root;
    }

    /**
     * Reset the lexical buffer and lexical index.
     */
    private static void resetLexer() {

        //set initial values
        lexical_buffer = new char[LEX_BUFFER_SIZE]; //new buffer
        lexical_index = 0;                          //reset index in buffer

    }

    /**
     * Character in raw string at index position.
     * @param index position in string
     * @return character at index position
     * @throws ParseException There is no character in the raw string at the requested index
     */
    private static char rawCharAt(int index) throws ParseException {

        try {
            return (raw_string.charAt(index));
        } catch (Exception e) {
            throw new ParseException("There is no character at position (" + index + ")", -1);
        }

    }

    /**
     * Length of raw string.
     * @return string length
     */
    private static int rawLength() {
        return(raw_string.length());
    }

    /**
     * Store tag into current node.
     * @throws ParseException Current node is NULL
     */
    private static void storeTag() throws ParseException {

        //check if current node exists
        if(current_node == null) {
            throw new ParseException("Current node is NULL", -1);
        }

        //set tag into current node
        String tag = String.valueOf(lexical_buffer).trim();
        current_node.setTag(tag);
    }

    /**
     * Store inner text into current node.
     * @throws ParseException Current node is NULL
     */
    private static void storeInnerText() throws ParseException {

        //check if current node exists
        if(current_node == null) {
            throw new ParseException("Current node is NULL", -1);
        }

        //set inner text into current node
        String inner_text = String.valueOf(lexical_buffer).trim();
        current_node.setInnerText(inner_text);
    }

    /**
     * Store key into current attribute.
     * @throws ParseException Current attribute is NULL
     */
    private static void storeKey() throws ParseException {

        //check if current node exists
        if(current_attribute == null) {
            throw new ParseException("Current attribute is NULL", -1);
        }

        //set key into current attribute
        String key = String.valueOf(lexical_buffer).trim();
        current_attribute.setKey(key);
    }

    /**
     * Store value into current attribute.
     * @throws ParseException Current attribute is NULL
     */
    private static void storeValue() throws ParseException {

        //check if current node exists
        if(current_attribute == null) {
            throw new ParseException("Current attribute is NULL", -1);
        }

        //set value into current attribute
        String value = String.valueOf(lexical_buffer).trim();
        current_attribute.setValue(value);
    }

    /**
     * Process the inner text of an XML node.
     * Inner text will be stored in the current node.
     * @throws ParseException lexical buffer contains text, but XML node doesn't exist
     */
    private static void processInnerText() throws ParseException {

        //if current node does not exist, text is before a xml start token
        if(current_node == null) {
            throw new ParseException("Text outside of xml", -1);
        }

        //set inner text into current node
        storeInnerText();

        // reset lexer
        resetLexer();

    }

    /**
     * Process the end of the node, after the character '/' has been detected.
     * Characters have to be read, but there is no need to store anything in the current node.
     * Parent of node will be the next node to continue processing
     * @return parent of current XML node
     * @throws ParseException XML is malformed, or start tag and end tag are not the same
     */
    private static XmlNode processEndOfNode() throws ParseException {

        //check if current node exists, otherwise xml is malformed
        if(current_node == null) {
            throw new ParseException("Already at the root", -1);
        }

        //skip character '/' in raw string
        raw_index += 2;

        //while not end of node tag, copy characters in buffer
        while(rawCharAt(raw_index) != '>') {
            lexical_buffer[lexical_index++] = rawCharAt(raw_index++);
        }

        //end tag string
        String end_tag = String.valueOf(lexical_buffer).trim();

        //check if start-tag and end-tag are the same
        if(!current_node.getTag().equals(end_tag)) {
            throw new ParseException("Mismatched tags (" + current_node.getTag() + " != " + end_tag + ")", -1);
        }

        //finished processing node: from start-tag to end-tag and everything in between

        // reset lexer
        resetLexer();

         //return parent of current node
        return(current_node.getParent());
    }

    /**
     * Process the start of an XML node.
     * Tag and optional attributes are stored in the current node.
     * @throws ParseException Attribute value without a key
     */
    static private void processStartOfNode() throws ParseException {

        //check if current node exists
        if(current_node == null) {
            throw new ParseException("Current node is NULL", -1);
        }

        //set current attribute
        current_attribute = new XmlAttribute();

        //while not end of node tag
        while(rawCharAt(raw_index) != '>') {

            ///////////////////////////////////////////////////////
            // process end of node without inner text
            ///////////////////////////////////////////////////////

            //process end of node without inner text: <node att="val" /> or <node/>
            if(rawCharAt(raw_index) == '/' && rawCharAt(raw_index + 1) == '>') {

                //In case of no attributes: tag name is not yet set
                if(current_node.getTag() == null) {

                    //set tag into current node
                    storeTag();

                }

                //finished processing current node
                current_node = current_node.getParent();

                //next character in raw string
                raw_index++;

                // reset lexer
                resetLexer();


                //continue in while
                continue;

            }


            ///////////////////////////////////////////////////////
            // copy characters in buffer
            ///////////////////////////////////////////////////////

            //copy characters in buffer
            lexical_buffer[lexical_index++] = rawCharAt(raw_index++);

            ///////////////////////////////////////////////////////
            // Process node tag
            ///////////////////////////////////////////////////////

            //encountered a space AND start-tag not yet processed -> set tag
            if(rawCharAt(raw_index) == ' ' && current_node.getTag() == null) {

                //set tag into current node
                processTag();

                //next character in raw string
                raw_index++;

                //continue in while
                continue;

            }

            ///////////////////////////////////////////////////////
            // spaces
            ///////////////////////////////////////////////////////

            //ignore spaces in tags
            if(lexical_buffer[lexical_index - 1] == ' ') {

                //go back one position in lexical buffer
                lexical_index--;

                //continue in while
                continue;

            }

            ///////////////////////////////////////////////////////
            // Process attribute key
            ///////////////////////////////////////////////////////

            //attribute key is read
            if(rawCharAt(raw_index) == '=') {

                //set key into current attribute
                processKey();

                //continue in while
                continue;

            }

            ///////////////////////////////////////////////////////
            // Process attribute value
            ///////////////////////////////////////////////////////

            //process attribute value
            if(rawCharAt(raw_index) == '"') {

                //set value into current attribute
                processValue();

                //add current attribute to current node
                current_node.addAttribute(current_attribute);

                //reset current attribute
                current_attribute = new XmlAttribute();

                //next character in raw string
                raw_index++;

                //continue in while
                continue;

            }

        }

        //In case of no attributes: tag name is not yet set
        if(current_node.getTag() == null) {

            //set tag into current node
            storeTag();

        }

        // reset lexer
        resetLexer();
    }


    /**
     * Process tag and store into current node.
     * @throws ParseException Current node is NULL
     */
    private static void processTag() throws ParseException {

        //set tag into current node
        storeTag();

        // reset lexer
        resetLexer();
    }


    /**
     * Process key and store into the current attribute.
     * @throws ParseException Current attribute is NULL
     */
    private static void processKey() throws ParseException {

        //set key into current attribute
        storeKey();

        // reset lexer
        resetLexer();
    }

    /**
     * Process value and store into the current attribute.
     * @throws ParseException Attribute without key, but with value
     */
    private static void processValue() throws ParseException {

        //check if current attribute exists
        if(current_attribute == null) {
            throw new ParseException("Current attribute is NULL", -1);
        }

        //check if there is an attribute value without a key
        if(current_attribute.getKey() == null) {
            throw new ParseException("Attribute value has no key", -1);
        }

        // reset lexer
        resetLexer();

        //next character in raw string
        raw_index++;

        //read until closing '"'
        while(rawCharAt(raw_index) != '"') {
            //copy characters in buffer
            lexical_buffer[lexical_index++] = rawCharAt(raw_index++);
        }

        //set value into current attribute
        storeValue();

        // reset lexer
        resetLexer();
    }

}

