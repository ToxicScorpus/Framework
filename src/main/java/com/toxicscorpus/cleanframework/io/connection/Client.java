package com.toxicscorpus.cleanframework.io.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    
    protected boolean connected;

    protected BufferedWriter writer;
    protected BufferedReader reader;

    protected String lastLine;

    protected Socket connection;

    public Client() {
    }

    protected void init(Socket socket) {
        connection = socket;
        openWriter();
        openReader();
        connected = true;
    }

    public void connect(String host, int port) {
        if (connected) {
            disconnect();
        }
        try {
            init(new Socket(host, port));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed connecting to: " + host + " on Port: " + port);
        }
    }

    public boolean disconnect() {
        if (!connected) {
            return false;
        }
        try {
            closeWriter();
            closeReader();
            connection.close();
            connected = false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed closing connection");
            return false;
        }
        return true;
    }

    private void openWriter() {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed opening writer!");
        }
    }

    private void openReader() {
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed opening reader!");
        }
    }

    private void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed closing writer!");
        }
    }

    private void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed closing reader!");
        }
    }

    public void sendLine(String text) {
        if (!connected) {
            return;
        }
        try {
            writer.write(text);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed sending line: " + text);
        }
    }

    public String receiveLine() {
        if (!connected) {
            return null;
        }
        try {
            lastLine = reader.readLine();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("[Client] Failed receiving line!");
            return null;
        }
        return lastLine;
    }

    public boolean isConnected() {
        return connected;
    }

}
