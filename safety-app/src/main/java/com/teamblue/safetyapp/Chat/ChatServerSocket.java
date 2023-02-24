package com.teamblue.safetyapp.Chat;

import java.net.*;

class ChatServerSocket{
public static void main(String[] args) {
    try {
            ServerSocket serverSocket = new ServerSocket(9999); 
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
