package com.thoughtworks.testdox;

import com.thoughtworks.testdox.Generator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.HeadlessException;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private List generatorGuis = new ArrayList();

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
    private Container contentPane;

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
            gen.setInputFile(new File(path.getText()));
            for (int i = 0; i < generatorGuis.size(); i++) {
                DocumentGeneratorGui gui = (DocumentGeneratorGui) generatorGuis.get(i);
                DocumentGenerator documentGenerator = gui.createDocumentGenerator();
                if (documentGenerator!=null) {
                    gen.addGenerator(documentGenerator);
                }
            }
            gen.generate();
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

        contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(makeBrowsePanel());

        addDocumentGeneratorGui(new HtmlSaveAsGui());
        addDocumentGeneratorGui(new TextSaveAsGui());

        contentPane.add(makeGoButton());

        getRootPane().setDefaultButton(browseButton);
        pack();
    }

    private JPanel makeBrowsePanel() {
        JPanel panel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        panel.setLayout(flowLayout);
        panel.add(path);
        panel.add(browseButton);
        return panel;
    }

    private Box makeGoButton() {
        Box box = Box.createHorizontalBox();
        box.add(Box.createGlue());
        box.add(goButton);
        return box;
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

    public void addDocumentGeneratorGui(DocumentGeneratorGui generatorGui) {
        generatorGuis.add(generatorGui);
        contentPane.add(generatorGui.getComponent());
    }
}

