package com.fabi;

import java.awt.*;
import javax.swing.*;


public class Bootstrap {
        



@SuppressWarnings("unused")
    private static void test() {
        JFrame frame = new JFrame("Formular");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Textfeld
        JTextField name = new JTextField();
        panel.add(new Label("Name"));
        panel.add(name);

        //Auswahlmenü: start, min, max, steps
        JSpinner alter = new JSpinner(new SpinnerNumberModel(1, 0, 120, 1));
        panel.add(new JLabel("Alter"));
        panel.add(alter);

        //Eine Auswahl mit JRadioButton
        JRadioButton männlich = new JRadioButton("männlich");
        JRadioButton weiblich = new JRadioButton("weiblich");
        ButtonGroup geschlechter = new ButtonGroup();
        geschlechter.add(männlich);
        geschlechter.add(weiblich);
        panel.add(new JLabel("Geschlecht"));
        panel.add(männlich);
        panel.add(weiblich);

        //Auswahlliste
        String[] spieleArray = {"COD", "r6", "Fortiniti", "pubg"};
        JComboBox<String> spiele = new JComboBox<>(spieleArray);
        panel.add(new JLabel("Spiele"));
        panel.add(spiele);

        //Mehrfachauswahl
        JCheckBox hobby1 = new JCheckBox("Lesen");
        JCheckBox hobby2 = new JCheckBox("Coden");
        JCheckBox hobby3 = new JCheckBox("r6");
        JCheckBox hobby4 = new JCheckBox("valorant");
        panel.add(hobby1);
        panel.add(hobby2);
        panel.add(hobby3);
        panel.add(hobby4);

        JButton abschicken = new JButton("Abschicken");

        //Textbereich
        JTextArea ergebnisse = new JTextArea();
        ergebnisse.setEditable(false);

        abschicken.addActionListener(e -> {
            ergebnisse.setText("Ergebnisse: \n \n");
            ergebnisse.append("Name: " + name.getText() + "\n");
            ergebnisse.append("Alter: " + alter.getValue() + "\n");
            String geschlecht = männlich.isSelected() ? "männlich":
                                weiblich.isSelected() ? "weiblich":
                                "Nicht angegeben";
            ergebnisse.append("Geschlecht: " + geschlecht + "\n");
            ergebnisse.append("Spiel: " + spiele.getSelectedItem() + "\n");
            StringBuilder hobbies = new StringBuilder();
            if(hobby1.isSelected()) hobbies.append("Lesen, ");
            if(hobby2.isSelected()) hobbies.append("coden, ");
            if(hobby3.isSelected()) hobbies.append("r6, ");
            if(hobby4.isSelected()) hobbies.append("valorant, ");
            if (hobbies.length() > 0) {
                hobbies.setLength(hobbies.length() - 2);
            }

            ergebnisse.append(hobbies.toString());
        });



        frame.add(panel, BorderLayout.NORTH);
        frame.add(abschicken, BorderLayout.CENTER);
        frame.add(ergebnisse, BorderLayout.SOUTH);

        frame.setVisible(true);

        //JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        //splitPane.setDividerLocation(300);
        //mainframe.add(splitPane);
}

@SuppressWarnings("unused")
    private static void bootstrap2() { //finished template for a splitscreen (30/70)
        //main frame
        JFrame mainframe = new JFrame("Main Frame");
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(600, 400);
        //mainframe.setLayout(new GridLayout());
        mainframe.setLayout(new GridBagLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.LIGHT_GRAY);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);

        //left side
        GridBagConstraints gbc_left = new GridBagConstraints();
        gbc_left.gridx = 0;
        gbc_left.gridy = 0;
        gbc_left.weightx = 0.3;
        gbc_left.weighty = 1;
        gbc_left.fill = GridBagConstraints.BOTH;
        mainframe.add(leftPanel, gbc_left);

        //right side
        GridBagConstraints gbc_right = new GridBagConstraints();
        gbc_right.gridx = 1;
        gbc_right.gridy = 0;
        gbc_right.weightx = 0.7;
        gbc_right.weighty = 1;
        gbc_right.fill = GridBagConstraints.BOTH;
        mainframe.add(rightPanel, gbc_right);

    }

}
