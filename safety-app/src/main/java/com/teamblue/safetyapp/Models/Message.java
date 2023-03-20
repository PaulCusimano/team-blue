package com.teamblue.safetyapp.Models;

public class Message {
    private String sender;
    private String Message;
    private String reciever;

    public Message(String sender, String Message, String reciever){
        this.Message = Message;
        this.reciever = reciever;
        this.sender = sender;
    }

    public String getSender(){
        return sender;
    }

    public String getMessage(){
        return Message;
    }

    public String getReciever(){
        return reciever;
    }

}
