package com.teamblue.safetyapp;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class chatHandler {
    private static String sender;
    private static String receiver;
    private static String message;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        // Initialize Firebase
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\hagri\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(credentials)
                .build();
        Firestore db = options.getService();

        // Create a new document in "chats" collection with an automatically generated
        // ID
        CollectionReference chatsCollection = db.collection("chats");
        DocumentReference chatDocument = chatsCollection.document();
        String chatId = chatDocument.getId();

        // Create a new document in "messages" sub-collection under the chat document
        CollectionReference messagesCollection = chatDocument.collection("messages");
        DocumentReference messageDocument = messagesCollection.document();

        // Set the data for the message document
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("user", receiver);
        messageData.put("police", sender);
        messageData.put("message", message);
        ApiFuture<WriteResult> messageFuture = messageDocument.set(messageData);

        // Update the chat document with the IDs of the users
        Map<String, Object> chatData = new HashMap<>();
        chatData.put("user1", sender);
        chatData.put("user2", receiver);
        chatData.put("lastMessage", message);
        chatData.put("timestamp", System.currentTimeMillis());
        ApiFuture<WriteResult> chatFuture = chatDocument.set(chatData);

        // Wait for the write operations to complete
        messageFuture.get();
        chatFuture.get();

        System.out.println("Message sent to Firebase Firestore successfully.");

        String chat = chatDocument.getId(); // Update chatId to the actual chat document ID
        getMessages(chat);
    }

    public static void getMessages(String chatId) throws ExecutionException, InterruptedException, IOException {
        Firestore db = FirestoreDatabase.getFirestore();
        CollectionReference chatRef = db.collection("chats").document(chatId).collection("messages");
        ApiFuture<QuerySnapshot> messagesFuture = chatRef.get();
        QuerySnapshot messagesSnapshot = messagesFuture.get();

        for (QueryDocumentSnapshot messageDocument : messagesSnapshot) {
            // Retrieve message data
            String user = (String) messageDocument.get("user");
            String police = (String) messageDocument.get("police");
            String message = (String) messageDocument.get("message");

            System.out.println("User: " + user);
            System.out.println("Police: " + police);
            System.out.println("Message: " + message);
        }
    }
}
