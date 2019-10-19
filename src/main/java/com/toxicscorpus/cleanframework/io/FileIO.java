package com.toxicscorpus.cleanframework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {

    private File file;

    private BufferedReader reader;
    private BufferedWriter writer;
    private String lastLine;

    private int currentLine;

    private boolean writerOpened;
    private boolean readerOpened;

    public FileIO(File defaultFile) {
        this.file = defaultFile;
    }

    public FileIO(String defaultFilePath) {
        setFile(defaultFilePath);
    }

    public FileIO() {

    }

    public boolean setFile(String path) {
        closeFile();
        file = new File(path);
        return file.exists();
    }

    public boolean closeFile() {
        closeReader();
        closeWriter();
        return true;
    }

    public boolean openFile(boolean append) {
        openReader();
        openWriter(append);
        return true;
    }

    public boolean createPathToFile() {
        if (file == null) {
            return false;
        }

        File parent = file.getParentFile();
        if (parent == null) {
            return false;
        }

        return parent.mkdirs();
    }

    public boolean writeLine(String line, boolean newLine) {
        if (!writerOpened) {
            openWriter(false);
        }
        try {
            if (newLine) {
                writer.newLine();
            }
            writer.write(line);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed writing to file: " + file.getName());
            return false;
        }
        return true;
    }

    public boolean writeFile(ArrayList<String> data) {
        restartWriter(false);
        boolean first = true;
        for (String s : data) {
            if (first) {
                first = false;
                writeLine(s, false);
            } else {
                writeLine(s, true);
            }
        }
        return true;
    }

    public String readLine() {
        if (!readerOpened) {
            openReader();
        }
        try {
            lastLine = reader.readLine();
            currentLine++;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed reading from file: " + file.getName());
        }
        return lastLine;
    }

    public ArrayList<String> readFile() {
        if (!readerOpened) {
            openReader();
        }
        if (currentLine > 1) {
            restartReader();
        }
        ArrayList<String> data = new ArrayList<>(5);
        while (readLine() != null) {
            data.add(lastLine);
        }
        return data;
    }

    public boolean delete() {
        closeFile();
        if (file.isDirectory()) {
            for (File tmpFile : file.listFiles()) {
                new FileIO(tmpFile).delete();
            }
        }
        return file.delete();
    }

    private void restartWriter(boolean append) {
        closeWriter();
        openWriter(append);
    }

    private void restartReader() {
        closeReader();
        openReader();
    }

    private void closeReader() {
        if (!readerOpened) {
            return;
        }
        try {
            reader.close();
            readerOpened = false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed closing reader on file: " + file.getName());
        }
    }

    private void openReader() {
        if (readerOpened) {
            closeReader();
        }
        try {
            reader = new BufferedReader(new FileReader(file));
            currentLine = 1;
            readerOpened = true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed opening reader on file: " + file.getName());
        }
    }

    private void closeWriter() {
        if (!writerOpened) {
            return;
        }
        try {
            writer.close();
            writerOpened = false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed closing writer on file: " + file.getName());
        }
    }

    private void openWriter(boolean append) {
        if (writerOpened) {
            closeWriter();
        }
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writerOpened = true;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed opening writer on file: " + file.getName());
        }
    }

}
