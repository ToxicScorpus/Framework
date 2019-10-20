package com.toxicscorpus.cleanframework.io.website;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebsiteIO implements WebIO {

    private BufferedReader reader;
    private URLConnection conn;
    private String lastLine;

    public WebsiteIO() {

    }

    public void setSite(String url) {
        close();
        try {
            conn = new URL(url).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            conn.setConnectTimeout(1500);
            conn.connect();
            openReader();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[WebsiteIO] Failed connecting to site: " + url);
        }
    }

    public String readNextLine() {
        try {
            lastLine = reader.readLine();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[WebsiteIO] Failed reading line");
            return null;
        }
        return lastLine;
    }

    public String nextLineThatContains(String sequence) {
        while (readNextLine() != null) {
            if (lastLine.contains(sequence)) {
                return lastLine;
            }
        }
        return null;
    }

    public boolean followsLineThatContains(String sequence) {
        return nextLineThatContains(sequence) != null;
    }

    public boolean skipLines(int lines) {
        try {
            for (int i = 0; i < lines; i++) {
                reader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[WebsiteIO] Failed skipping lines: " + lines);
            return false;
        }
        return true;
    }

    public String nextLineWithTagAndArrtibute(String tagName, String attrName, String value) {
        while (!nextLineThatContains(attrName + "=\"" + value + "\"").contains("<" + tagName)) {
        }
        return lastLine;
    }

    public String nextLineWithArrtibute(String attrName, String value) {
        return nextLineThatContains(attrName + "=\"" + value + "\"");
    }

    public void close() {
        closeReader();
    }

    public String getLastLine() {
        return lastLine;
    }

    private void openReader() {
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[WebsiteIO] Failed opening reader");
        }
    }

    private void closeReader() {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[WebsiteIO] Failed closing reader");
        }
    }

}
