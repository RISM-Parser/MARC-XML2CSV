package XML;

public class XmlAttribute {
    private String key;
    private String value;

    //Getters & Setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //Constructors
    public XmlAttribute() {}

    public XmlAttribute(String key) {
        this.key = key;
    }

    public XmlAttribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    //Create string
    @Override
    public String toString() {
        return "XmlAttribute{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
