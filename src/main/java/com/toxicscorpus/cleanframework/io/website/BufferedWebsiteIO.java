package com.toxicscorpus.cleanframework.io.website;

import java.util.ArrayList;

public class BufferedWebsiteIO implements WebIO {

    private final WebsiteIO websiteIO;
    private final ArrayList<String> data;
    private int currentLine;

    public BufferedWebsiteIO() {
        websiteIO = new WebsiteIO();
        data = new ArrayList<String>();
    }

    public void setSite(String url) {
        websiteIO.setSite(url);
        data.clear();
        String line;
        while ((line = websiteIO.readNextLine()) != null) {
            data.add(line);
        }
        currentLine = -1;
    }

    public String readNextLine() {
        skipLines(1);
        if (currentLine >= data.size()) {
            return null;
        }
        return getCurrentLine();
    }

    public String nextLineThatContains(String sequence) {
        while (hasNextLine()) {
            if (readNextLine().contains(sequence)) {
                return getCurrentLine();
            }
        }
        return null;
    }

    public boolean followsLineThatContains(String sequence) {
        return nextLineThatContains(sequence) != null;
    }

    public boolean skipLines(int lines) {
        currentLine += lines;
        return currentLine < data.size();
    }

    public String nextLineWithTagAndArrtibute(String tagName, String attrName, String value) {
        String nextLine = nextLineThatContains(attrName + "=\"" + value + "\"");
        while (nextLine != null && !nextLine.contains("<" + tagName)) {
            nextLine = nextLineThatContains(attrName + "=\"" + value + "\"");
        }
        return getCurrentLine();
    }

    public String nextLineWithArrtibute(String attrName, String value) {
        return nextLineThatContains(attrName + "=\"" + value + "\"");
    }

    public boolean hasNextLine() {
        return currentLine <= data.size() - 1;
    }

    public String getCurrentLine() {
        return data.get(currentLine);
    }

    public void close() {
        data.clear();
    }

}
