package com.thoughtworks.testdox;

import com.thoughtworks.testdox.Generator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: stevcc
 * Date: 11-Jun-2003
 * Time: 19:08:11
 * To change this template use Options | File Templates.
 */
public class Gui extends JFrame {

    JButton browseButton;
    JButton goButton;
    JFileChooser fileChooser;
    JTextField path;
    Generator gen;

    private DocumentListener pathChangeListener = new DocumentListener() {
        public void insertUpdate(DocumentEvent e) {
            doPathChange();
        }

        public void removeUpdate(DocumentEvent e) {
            doPathChange();
        }

        public void changedUpdate(DocumentEvent e) {
            doPathChange();
        }
    };

    private void doPathChange() {
        File f = new File(path.getText());
        goButton.setEnabled(f.exists());
        if (goButton.isEnabled())
            getRootPane().setDefaultButton(goButton);
        else
            getRootPane().setDefaultButton(browseButton);

    }

    public Gui(String title, Generator gen) throws HeadlessException {
        super(title);
        this.gen = gen;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setup();
    }

    private ActionListener goActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            gen.generate(new File(path.getText()));
        }
    };

    private ActionListener browseActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            doBrowseForFile();
        }
    };

    private void setup() {
        path = new JTextField(50);
        path.getDocument().addDocumentListener(pathChangeListener);
        browseButton = new JButton("Browse");
        browseButton.addActionListener(browseActionListener);
        goButton = new JButton("Go");
        goButton.setEnabled(false);
        goButton.addActionListener(goActionListener);

        Container pane = getContentPane();
        pane.setLayout(new FlowLayout());
        pane.add(path);
        pane.add(browseButton);
        pane.add(goButton);
        getRootPane().setDefaultButton(browseButton);
        pack();
    }

    private void doBrowseForFile() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(this) != JFileChooser.CANCEL_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            path.setText(selectedFile.getPath());
        }
    }
}

