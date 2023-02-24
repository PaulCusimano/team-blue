package com.teamblue.safetyapp.Chat;

import java.net.*;

class ChatClient{

public static void main(String[] args) {
     try {
            Socket clientSocket = new Socket("localhost", 9999);

            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
