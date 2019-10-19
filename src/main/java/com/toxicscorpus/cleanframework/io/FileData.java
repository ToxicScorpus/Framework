package com.toxicscorpus.cleanframework.io;

import java.util.ArrayList;
import java.util.List;

public class FileData {

    private final FileIO file;
    private final List<Data> data;

    public FileData() {
        data = new ArrayList<>();
        file = new FileIO();
    }

    public void setFile(String path) {
        file.setFile(path);
    }

    public void setData(String key, String value) {
        int index = getIndexOfDataWithKey(key);
        if (index > -1) {
            data.get(index).setValue(value);
        } else {
            data.add(new Data(key, value));
        }
    }

    private void addData(String line) {
        data.add(new Data(line));
    }
    
    public String getData(String key) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).hasKey(key)) {
                return data.get(i).getValue();
            }
        }
        throw new RuntimeException("Could not find Data with Key: " + key);
    }

    public void readDataFromFile() {
        data.clear();
        file.readFile().forEach(this::addData);
        file.closeFile();
    }

    public void writeDataToFile() {
        boolean first = true;
        for (int i = 0; i < data.size(); i++) {
            if (first) {
                first = false;
                file.writeLine(data.get(i).getLine(), false);
            } else {
                file.writeLine(data.get(i).getLine(), true);
            }
        }
        file.closeFile();
    }

    private int getIndexOfDataWithKey(String key) {
        int index = -1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).hasKey(key)) {
                index = i;
            }
        }
        return index;
    }

}
