package com.toxicscorpus.cleanframework;

import com.toxicscorpus.cleanframework.io.FileData;
import com.toxicscorpus.cleanframework.io.FileIO;

public class Test {

    public static void main(String[] args) {
        Test_FileIO();
        Test_FileData();
    }

    private static void Test_FileIO() {
        FileIO file = new FileIO("test/dir/tmpfile.txt");
        file.createPathToFile();
        file.writeLine("This is Line 1", false);
        file.writeLine("This is Line 2", true);
        file.closeFile();
        System.out.println(file.readLine());
        System.out.println(file.readLine());
        file.setFile("test");
        file.delete();
    }

    private static void Test_FileData() {
        FileData data = new FileData();
        data.setFile("test.properties");
        data.setData("url", "localhost");
        data.setData("name", "userName");
        data.setData("pass", "PassWord123");
        data.writeDataToFile();
        data.readDataFromFile();
        System.out.println(data.getData("url"));
        System.out.println(data.getData("name"));
        System.out.println(data.getData("pass"));
    }

}
