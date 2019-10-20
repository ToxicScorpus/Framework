package com.toxicscorpus.cleanframework.io.connection;

public interface MessageHandler {
    
    public abstract void handle(ClientMessage msg);
    
}
