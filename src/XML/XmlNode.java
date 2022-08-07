package XML;

import java.util.ArrayList;
import java.util.List;

public class XmlNode {
    private String tag;
    private String innerText;
    private XmlNode parent;
    private List<XmlAttribute> attributes;
    private List<XmlNode> children;

    //Getters & Setters
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getInnerText() {
        return innerText;
    }

    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public XmlNode getParent() {
        return parent;
    }

    public void setParent(XmlNode parent) {
        this.parent = parent;
    }

    public List<XmlAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<XmlAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<XmlNode> getChildren() {
        return children;
    }

    public void setChildren(List<XmlNode> children) {
        this.children = children;
    }

    //Constructor
    public XmlNode(XmlNode parent) {
        this.parent = parent;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        //add self to parent's children
        if(parent != null) {
            parent.addChild(this);
        }
    }

    public XmlNode(String tag, XmlNode parent) {
        this.tag = tag;
        this.parent = parent;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        //add self to parent's children
        if(parent != null) {
            parent.addChild(this);
        }
    }

    public XmlNode(String tag, String innerText, XmlNode parent) {
        this.tag = tag;
        this.innerText = innerText;
        this.parent = parent;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
        //add self to parent's children
        if(parent != null) {
            parent.addChild(this);
        }
    }

    //Create string
    @Override
    public String toString() {
        return "XmlNode{" +
                "tag='" + tag + '\'' +
                ", innerText='" + innerText + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }

    /**
     * Add XmlAttribute to list of attributes.
     * @param attribute XmlAttribute to be added
     * @return boolean indicating if addition succeeded
     */
    public boolean addAttribute(XmlAttribute attribute) {
        return(this.attributes.add(attribute));
    }

    /**
     * Add XmlNode to list of children.
     * @param child XmlNode to be added
     * @return boolean indicating if addition succeeded
     */
    public boolean addChild(XmlNode child) {
        return(this.children.add(child));
    }

    /**
     * Get attribute value at index position.
     * @param index list position of value
     * @return value at position; or null
     */
    private String getAttributeValueAt(int index) {
        //exists attributes?
        if(this.attributes == null) return null;
        //attributes not empty?
        if(this.attributes.isEmpty()) return null;
        //return value at index position; or null
        try {
            XmlAttribute attribute = this.attributes.get(index);
            return(attribute.getValue());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get first attribute value
     * @return first attribute value; or null
     */
    public String getFirstAttributeValue() {
        return(getAttributeValueAt(0));
    }

    /**
     * Get second attribute value
     * @return second attribute value; or null
     */
    public String getSecondAttributeValue() {
        return(getAttributeValueAt(1));
    }

    /**
     * Get third attribute value
     * @return third attribute value; or null
     */
    public String getThirdAttributeValue() {
        return(getAttributeValueAt(2));
    }
}
