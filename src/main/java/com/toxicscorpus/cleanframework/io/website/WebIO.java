package com.toxicscorpus.cleanframework.io.website;

public interface WebIO {

    public abstract void setSite(String url);

    public abstract String readNextLine();

    public abstract String nextLineThatContains(String sequence);

    public abstract boolean followsLineThatContains(String sequence);

    public abstract boolean skipLines(int lines);

    public abstract String nextLineWithTagAndArrtibute(String tagName, String attrName, String value);

    public abstract String nextLineWithArrtibute(String attrName, String value);

    public abstract void close();

}
