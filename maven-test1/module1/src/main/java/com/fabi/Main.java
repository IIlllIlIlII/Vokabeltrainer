package com.fabi;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.http.WebSocket.Listener;
import java.util.Scanner;

public class Main {
    private JFrame mainframe;
    private JPanel cards;
    private int score = 0;
    private String word1;
    private String solution;
    private JLabel word; //Label which shows the word to be translated by the player

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI()); //GUI thread
        Storage.loadFromFile();
    }


    private void createAndShowGUI() {

        //main frame
        mainframe = new JFrame("Vokabeltrainer v.1.0.0");
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(600, 400);

        cards = new JPanel(new CardLayout()); //create JPanel as menu storage space

        //create all menu JPanels
        JPanel mainMenu = createMainMenu();
        JPanel enterVocabMenu = createEnterVocabMenu();
        JPanel trainVocabMenu = createTrainVocabMenu();

        //add menus to cards JPanel storage
        cards.add(mainMenu, "mainMenu");
        cards.add(enterVocabMenu, "enterVocabMenu");
        cards.add(trainVocabMenu, "trainVocabMenu");

        mainframe.add(cards); //add card storage to mainframe
        mainframe.setVisible(true);
    

}

    private void switchCard(String name) {
        CardLayout c1 = (CardLayout) cards.getLayout(); //cast
        c1.show(cards, name);
        if (name == "trainVocabMenu") {
            onShow(); //get data + refresh gui
        }
    }

    public void onShow() {
        word1 = getVocab();
        if (word1 == null) {
            JOptionPane.showMessageDialog(mainframe,
            "Es wurden noch keine Vokabeln eingetragen", "Hinweis", 0);
            switchCard("mainMenu");
        } else {
            word.setText(word1);
        }
    }

    private String getVocab() {
        return Storage.getRandomWord(); //access Storage.java
    }

    private JPanel createMainMenu() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 0.25;
        
        //Titel
        Label label = new Label("Vokabeltrainer");
        label.setFont(new Font("Arial", Font.BOLD, 34));
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        //Button: enter new words
        JButton enterVocabButton = new JButton("neue Vokabeln eintragen");
        enterVocabButton.addActionListener(e -> switchCard("enterVocabMenu"));
        gbc.gridy = 1;
        gbc.weighty = 0.05;
        gbc.ipady = 10;
        gbc.ipadx = 103;
        panel.add(enterVocabButton, gbc);

        //Button: train vocab
        JButton trainVocabButton = new JButton("Vokabeln üben");
        trainVocabButton.addActionListener(e -> switchCard("trainVocabMenu"));
        gbc.ipadx = 165;
        gbc.gridy = 2;
        panel.add(trainVocabButton, gbc);

        //Button: exit programm
        JButton exit = new JButton("Zurück zum Desktop");
        exit.addActionListener(e -> System.exit(0));
        gbc.gridy = 3;
        gbc.ipadx = 125;
        panel.add(exit, gbc);

        //GUI padding below
        gbc.gridy = 4;
        gbc.weighty = 0.2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(Box.createVerticalStrut(0), gbc);
        
        

        return panel;
    }

    private JPanel createTrainVocabMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.DARK_GRAY);
        

        //Top panel
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
       

        //Titel
        JLabel titel = new JLabel("Vokabeltraining");
        titel.setFont(new Font("Arial", Font.BOLD, 24));
        titel.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        topPanel.add(titel, gbc);

        //Label: score
        JLabel scoreCounter = new JLabel("Punktestand: " + score);
        scoreCounter.setForeground(Color.WHITE);
        gbc.gridy = 2;
        topPanel.add(scoreCounter, gbc);

        panel.add(topPanel, BorderLayout.NORTH);

        //Body panel
        JPanel bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(Color.DARK_GRAY);

        //Label: task
        JLabel taskLabel = new JLabel("Übersetzen Sie folgendes Wort auf Englisch:");
        taskLabel.setFont(new Font("Arial", Font.BOLD, 15));
        taskLabel.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        bodyPanel.add(taskLabel, gbc);

        //Label: word
        word = new JLabel(word1);
        word.setFont(new Font("Arial", Font.BOLD, 15));
        word.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 3;
        bodyPanel.add(word, gbc);

        //Textfield
        JTextField enterWord2 = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bodyPanel.add(enterWord2, gbc);

        //Button: check if user input is correct
        JButton checkButton = new JButton("Kontrollieren");
        checkButton.addActionListener(e -> {
            solution = Storage.getSolution(word1);
            if (solution.equalsIgnoreCase(enterWord2.getText())) {
                score++;
                scoreCounter.setText("Punktestand: " + score);

                //get new random word + update gui
                word1 = getVocab();
                word.setText(word1);
                enterWord2.setText("");
            } else {
                JOptionPane.showMessageDialog(mainframe,
                 "Das ist leider falsch\n Lösung: " + solution +
                 "\n Ihre Eingabe: " + enterWord2.getText(), 
                 "Hinweis", 1);
            }
            
        });
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        bodyPanel.add(checkButton, gbc);


        panel.add(bodyPanel, BorderLayout.CENTER);

        //Button: back to menu
        JButton backToMenuButton = new JButton("zurück");
        backToMenuButton.setPreferredSize(new Dimension(140, 35));
        backToMenuButton.addActionListener(e -> switchCard("mainMenu"));
        panel.add(backToMenuButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createEnterVocabMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.DARK_GRAY);

        //Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        //Label
        JLabel label = new JLabel("Neue Vokabel hinzufügen", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        topPanel.add(label, BorderLayout.NORTH);

        panel.add(topPanel, BorderLayout.NORTH);//move to top panel section

        //Body panel
        JPanel bodyPanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5); 
        bodyPanel.setLayout(new GridBagLayout());
        bodyPanel.setBackground(Color.DARK_GRAY);

        //Invisible grid left
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        bodyPanel.add(Box.createHorizontalStrut(10), gbc);
        
        //Label + textfield 1
        JLabel label2 = new JLabel("Deutsch: ");
        label2.setForeground(Color.WHITE);
        JTextField word1 = new JTextField();
        word1.setBackground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.weighty = 0.25;
        gbc.weightx = 0;
        bodyPanel.add(label2, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bodyPanel.add(word1, gbc);

        //Invisible grid right
        gbc.gridx = 3;
        gbc.weightx = 0.1;
        bodyPanel.add(Box.createHorizontalStrut(10), gbc);

        //------------------------------ row: 2 ------------------------------\\

        //Label + textfield 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        bodyPanel.add(Box.createHorizontalStrut(10), gbc);
        
        //Label + textfield 2
        JLabel label3 = new JLabel("Englisch: ");
        label3.setForeground(Color.WHITE);
        JTextField word2 = new JTextField();
        word2.setBackground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.weighty = 0.25;
        gbc.weightx = 0;
        bodyPanel.add(label3, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bodyPanel.add(word2, gbc);

        //Invisible grid right
        gbc.gridx = 3;
        gbc.weightx = 0.1;
        bodyPanel.add(Box.createHorizontalStrut(10), gbc);

        //-------------------------------- row: 3 --------------------------\\

        //padding between the 2 buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 1;
        bodyPanel.add(Box.createVerticalStrut(10), gbc);

        //save Button
        JButton saveButton = new JButton("speichern");
        saveButton.setPreferredSize(new Dimension(170, 35));
        saveButton.addActionListener(e -> {
            if (word1.getText().length() > 0 && word2.getText().length() > 0) {
                if (!Storage.addWords(word1.getText(), word2.getText(), false)) {
                    JOptionPane.showMessageDialog(mainframe,
                     "Wort ist bereits vorhanden", "Fehler", 0);
                } else {
                    word1.setText("");
                    word2.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(mainframe,
                "Felder dürfen nicht leer sein!", "Warnung", 2);
            }
        });
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        bodyPanel.add(saveButton, gbc);

        //Button: back to menu
        JButton backToMenuButton = new JButton("zurück");
        backToMenuButton.addActionListener(e -> switchCard("mainMenu"));
        backToMenuButton.setPreferredSize(new Dimension(370, 25));
        gbc.gridy = 5;
        bodyPanel.add(backToMenuButton, gbc);


        panel.add(bodyPanel, BorderLayout.CENTER);


        return panel;
    }



}
