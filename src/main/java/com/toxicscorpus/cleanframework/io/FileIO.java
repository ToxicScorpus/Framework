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
    
    private boolean isOpened;

    public FileIO(File defaultFile) {
        this.file = defaultFile;
    }

    public FileIO() {

    }

    public boolean setFile(String path) {
        closeFile();
        file = new File(path);
        return file.exists();
    }

    public boolean closeFile() {
        if (!isOpened) {
            return false;
        }
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed closing file: " + file.getName());
            return false;
        }
        isOpened = false;
        return true;
    }

    private boolean openFile() {
        if (isOpened) {
            return false;
        }
        try {
            reader = new BufferedReader(new FileReader(file));
            writer = new BufferedWriter(new FileWriter(file));
            currentLine = 1;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed opening file: " + file.getName());
            return false;
        }
        isOpened = true;
        return true;
    }

    public boolean createPathToFile() {
        return file.mkdirs();
    }

    public boolean writeLine(String line, boolean newLine) {
        if (!isOpened) {
            openFile();
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
        if (!isOpened) {
            openFile();
        }
        restartWriter(true);
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
        if (!isOpened) {
            openFile();
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
        if (!isOpened) {
            openFile();
        }
        if(currentLine > 1) {
            restartReader();
        }
        ArrayList<String> data = new ArrayList<>(5);
        while (readLine() != null) {
            data.add(lastLine);
        }
        return data;
    }

    public boolean delete() {
        if (file.isDirectory()) {
            for (File tmpFile : file.listFiles()) {
                new FileIO(tmpFile).delete();
            }
        }
        return file.delete();
    }

    private void restartWriter(boolean clearFile) {
        try {
            writer.close();
            writer = new BufferedWriter(new FileWriter(file, !clearFile));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed restarting writer on file: " + file.getName());
        }
    }

    private void restartReader() {
        try {
            reader.close();
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("Failed restarting reader on file: " + file.getName());
        }
    }

}
