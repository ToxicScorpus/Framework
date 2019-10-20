package com.toxicscorpus.cleanframework.io.file;

public class Data {

    private static String SEPARATOR = ":";

    private final String key;
    
    private String value;

    public Data(String line) {
        String[] splitLine = line.split(SEPARATOR);
        this.key = splitLine[0];
        this.value = splitLine[1];
    }

    public Data(String key, String value) {
        this.key = key.toUpperCase();
        this.value = value;
    }

    public boolean hasKey(String key) {
        return key.toUpperCase().equals(this.key);
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getLine() {
        return key + Data.SEPARATOR + value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static void setSeparator(String separator) {
        SEPARATOR = separator;
    }
}
