package com.thoughtworks.testdox;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.peer.TextComponentPeer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.io.IOException;

public abstract class SaveAsGui extends JPanel implements DocumentGeneratorGui{
    JFileChooser fileChooser;
    JButton saveAsButton;
    JTextField fileName;
    TitledBorder titledBorder;

    private ActionListener saveAsButtonActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            fileChooser.setDialogTitle("Save As");
            if (fileChooser.showSaveDialog(SaveAsGui.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    fileName.setText( fileChooser.getSelectedFile().getCanonicalPath() );
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    };

    public SaveAsGui(String helpfulText, String saveAsText) {

        fileChooser = new JFileChooser();
        saveAsButton = new JButton(saveAsText);
        fileName = new JTextField(30);

        titledBorder = BorderFactory.createTitledBorder(helpfulText);
        setBorder(titledBorder);

        setLayout(new FlowLayout());
        add(fileName);
        add(saveAsButton);

        saveAsButton.addActionListener(saveAsButtonActionListener);
    }

    public boolean isConfigured() {
        return fileName.getText().length()>0;
    }

    public Component getComponent() {
        return this;
    }

}
