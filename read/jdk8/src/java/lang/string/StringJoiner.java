package java.lang.string;

/**
 * Develop for split string
 * @author
 * @date 2020/1/3
 **/
public final class StringJoiner {
    // region
    
    private final String prefix;
    private final String delimiter;
    private final String suffix;
    
    private java.lang.StringBuilder value;
    
    private String emptyValue;
    
    public StringJoiner(String delimiter){
        this("", delimiter, "");
    }

    public StringJoiner(String prefix, String delimiter, String suffix) {
        this.prefix = prefix;
        this.delimiter = delimiter;
        this.suffix = suffix;
    }

    // endregion
}
