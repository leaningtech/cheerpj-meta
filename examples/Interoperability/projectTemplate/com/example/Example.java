package com.example;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Example extends JFrame {

    // Native method to set the Java application instance in JavaScript
    public static native void nativeSetApplication(Example myApplication);

    // Native method to send text to the HTML UI
    public static native void sendToHTML(String s);

    private JTextField javaTextField; // Input text field
    private JTextField javaTextField2; // Output display field

    public Example() {
        // Set up the JFrame (Java window)
        setTitle("Example App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create a text field for sending input to JavaScript
        javaTextField = new JTextField("");
        javaTextField.setBounds(50, 50, 300, 30);
        add(javaTextField);

        // Create a read-only text field for displaying input from JavaScript
        javaTextField2 = new JTextField("");
        javaTextField2.setEditable(false); // Disable editing
        javaTextField2.setBounds(50, 200, 300, 30);
        add(javaTextField2);

        // Create a button to send input to JavaScript
        JButton sendButton = new JButton("Send to HTML");
        sendButton.setBounds(100, 100, 200, 50);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToSend = javaTextField.getText();
                sendToHTML(textToSend); // Call the native method
            }
        });
        add(sendButton);
    }

    // Example method to process input from JavaScript
    public String processInput(String input) {
        System.out.println("Received input from JavaScript: " + input);
        javaTextField2.setText(input);
        return "Java received: " + input; // Send response back to JavaScript
    }

    public static void main(String[] args) {
        Example app = new Example();

        // Start a thread for initializing the Java application instance in JavaScript
        new Thread(() -> {
            nativeSetApplication(app);
            System.out.println("Starting Thread");
        }).start();

        app.setVisible(true);
    }
}
