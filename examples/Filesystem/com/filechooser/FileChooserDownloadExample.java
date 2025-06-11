package com.filechooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileChooserDownloadExample extends JFrame {

    // Native method to be implemented by CheerpJ for file download
    public static native void downloadFileFromCheerpJ(String filePath);

    public FileChooserDownloadExample() {
        setTitle("CheerpJ Save & Download Example");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel savePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField fileNameField = new JTextField("example.txt", 20);
        JTextArea contentArea = new JTextArea("Hello from Java! This is my file content.", 5, 30);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        JButton saveButton = new JButton("Save to /files/");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        savePanel.add(new JLabel("File content:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        savePanel.add(contentScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        savePanel.add(new JLabel("File name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        savePanel.add(fileNameField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        savePanel.add(saveButton, gbc);

        saveButton.addActionListener((ActionEvent e) -> {
            String fileName = fileNameField.getText().trim();
            String content = contentArea.getText();
            if (fileName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a file name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            writeFileToFiles(fileName, content);
        });

        JPanel downloadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the button
        JButton chooseDownloadButton = new JButton("Choose File to Download");

        chooseDownloadButton.addActionListener((ActionEvent e) -> {
            // 1. Instantiate JFileChooser, initially pointing to the /files/ mount point.
            JFileChooser fileChooser = new JFileChooser("/files/");
            fileChooser.setDialogTitle("Select file to download");
            // 2. Show the open dialog to let the user select a file.
            int result = fileChooser.showSaveDialog(this);
            // 3. Check if the user approved the selection (didn't cancel).
            if (result == JFileChooser.APPROVE_OPTION) {
                // 4. Get the selected file from the file chooser.
                // For example, if you wrote "Hello World" to /files/example.txt, "/files/example.txt" will be the selected file.
                // 5. Get the path of the selected file.
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getPath();
                // 6. Call the native JavaScript method to download the file from the virtual filesystem.
                downloadFileFromCheerpJ(filePath);
            }
        });
        downloadPanel.add(chooseDownloadButton);

        mainContentPanel.add(savePanel);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainContentPanel.add(downloadPanel);

        add(mainContentPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void writeFileToFiles(String fileName, String content) {
        File file = new File("/files/" + fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.flush();
            JOptionPane.showMessageDialog(this, "File saved to /files/" + fileName, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileChooserDownloadExample::new);
    }
}
