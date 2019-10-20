package com.toxicscorpus.cleanframework;

import com.toxicscorpus.cleanframework.io.connection.Client;
import com.toxicscorpus.cleanframework.io.connection.Server;
import com.toxicscorpus.cleanframework.io.file.FileData;
import com.toxicscorpus.cleanframework.io.file.FileIO;

public class Test {

    public static void main(String[] args) {
//        Test_FileIO();
//        Test_FileData();
        Test_Server_Client();
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
        Server server = new Server();
        server.openServer(8888);
        new Thread(() -> Server_Listen(server)).start();
        Client client1 = new Client();
        client1.connect("localhost", 8888);
        
        Client client2 = new Client();
        client2.connect("localhost", 8888);
        
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        
        Client serverClient1 = server.getClient(0);
        Client serverClient2 = server.getClient(1);
        
        client1.sendLine("TestClient1");
        System.out.println(serverClient1.receiveLine());
        
        client2.sendLine("TestClient2");
        System.out.println(serverClient2.receiveLine());
        
        server.sendLineToAll("TestBroadcast");
        System.out.println(client1.receiveLine());
        System.out.println(client2.receiveLine());
        
        client2.disconnect();
        client1.disconnect();
        server.closeServer();
    }
    
    private static void Server_Listen(Server server) {
        while(server.waitForConnection()) {
        }
    }

}
