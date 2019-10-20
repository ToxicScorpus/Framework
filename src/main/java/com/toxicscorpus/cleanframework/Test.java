package com.toxicscorpus.cleanframework;

import com.toxicscorpus.cleanframework.io.connection.BasicClient;
import com.toxicscorpus.cleanframework.io.connection.BasicServer;
import com.toxicscorpus.cleanframework.io.connection.ClientMessage;
import com.toxicscorpus.cleanframework.io.file.FileData;
import com.toxicscorpus.cleanframework.io.file.FileIO;
import com.toxicscorpus.cleanframework.io.website.BufferedWebsiteIO;
import com.toxicscorpus.cleanframework.io.website.WebIO;
import com.toxicscorpus.cleanframework.io.website.WebsiteIO;

public class Test {

    public static void main(String[] args) {
        Test_FileIO();
        Test_FileData();
        Test_Server_Client();
        Test_WebsiteIO();
        System.exit(0);
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

    private static void Test_Server_Client() {
        BasicServer server = new BasicServer(8888, Test::handle);
        BasicClient client = new BasicClient("localhost", 8888, Test::handle);
        BasicClient client2 = new BasicClient("localhost", 8888, Test::handle);
        System.out.println(client.sendWithResponse("Command1"));
        System.out.println(client.sendWithResponse("Command2"));
        client.send("output 120");
        client2.send("output client2");
        server.broadcast("close");
        server.stop();
    }

    private static void handle(ClientMessage msg) {
        if (msg.getText().equals("Command1")) {
            msg.getClient().sendLine("Command1 Response!");
        } else if (msg.getText().equals("Command2")) {
            msg.getClient().sendLine("Command2 Response!");
        } else if (msg.getText().startsWith("output")) {
            System.out.println(msg.getText().split(" ")[1]);
        } else if (msg.getText().equals("close")) {
            System.out.println("Closing NOW!");
        }
    }

    private static void Test_WebsiteIO() {
        WebIO webIO = new WebsiteIO();
        webIO.setSite("https://www.wikipedia.de/");
        System.out.println(webIO.readNextLine());
        System.out.println(webIO.nextLineWithArrtibute("id", "mainbox"));
        System.out.println(webIO.nextLineWithTagAndArrtibute("a", "href", "https://twitter.com/wikimediade"));
        System.out.println(webIO.nextLineThatContains("type"));
        webIO.close();

        WebIO buffered = new BufferedWebsiteIO();
        buffered.setSite("https://www.wikipedia.de/");
        System.out.println(buffered.readNextLine());
        System.out.println(buffered.nextLineWithArrtibute("id", "mainbox"));
        System.out.println(buffered.nextLineWithTagAndArrtibute("a", "href", "https://twitter.com/wikimediade"));
        System.out.println(buffered.nextLineThatContains("type"));
        buffered.close();
    }

}
