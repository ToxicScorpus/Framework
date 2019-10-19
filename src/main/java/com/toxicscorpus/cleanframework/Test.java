package com.toxicscorpus.cleanframework;

import com.toxicscorpus.cleanframework.io.FileIO;

public class Test {

    public static void main(String[] args) {
        Test_FileIO();
    }

    public static void Test_FileIO() {
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

}
