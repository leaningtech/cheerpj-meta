package com.reader;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderExample extends JFrame {
    private JTextArea fileContentArea;
    private JTextField readFileNameField;

    public FileReaderExample() {
        setTitle("Preview file from /str/");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout(8, 8));

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(new JLabel("File name in /str/:"), gbc);

        readFileNameField = new JTextField("test.txt", 20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        topPanel.add(readFileNameField, gbc);

        JButton readButton = new JButton("Preview");
        readButton.addActionListener(e -> {
            String fileName = readFileNameField.getText().trim();
            if (!fileName.isEmpty()) {
                readAndDisplayFromStr(fileName);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a file name to read.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        topPanel.add(readButton, gbc);
        
        add(topPanel, BorderLayout.NORTH);

        fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        fileContentArea.setLineWrap(true);
        fileContentArea.setWrapStyleWord(true);
        add(new JScrollPane(fileContentArea), BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void readAndDisplayFromStr(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("/str/" + fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            fileContentArea.setText(content.toString());
        } catch (IOException e) {
            fileContentArea.setText("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FileReaderExample::new);
    }
}
