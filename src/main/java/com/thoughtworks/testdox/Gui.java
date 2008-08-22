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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;

/**
 * Created by IntelliJ IDEA.
 * User: stevcc
 * Date: 11-Jun-2003
 * Time: 19:08:11
 * To change this template use Options | File Templates.
 */
public class Gui extends JFrame {

    public static String SELECTED_DIRECTORY_KEY = "selectedDirectory";
    static Preferences prefs = Preferences.userNodeForPackage(Gui.class);

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
        setupGuiComponents();
        try {
            prefs.sync();
            String selectedPath = prefs.get(SELECTED_DIRECTORY_KEY, null);
            if ( selectedPath!=null) {
                setSelectedPath(selectedPath);
            }
        } catch (BackingStoreException e) {
            System.err.println("Could not sync preferences");
            e.printStackTrace();
        }
    }

    private ActionListener goActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            gen.reset();
            gen.setInputFile(new File(path.getText()));
            for (int i = 0; i < generatorGuis.size(); i++) {
                DocumentGeneratorGui gui = (DocumentGeneratorGui) generatorGuis.get(i);
                if ( gui.isConfigured() ) {
                    DocumentGenerator documentGenerator = gui.createDocumentGenerator();
                    if (documentGenerator!=null) {
                        gen.addGenerator(documentGenerator);
                    }
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

    private void setupGuiComponents() {
        path = new JTextField(40);
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

    private JComponent makeBrowsePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(path);
        panel.add(browseButton);
        panel.setBorder(BorderFactory.createTitledBorder("Choose a directory containing JUnit tests"));
        return panel;
    }

    private Box makeGoButton() {
        Box box = Box.createHorizontalBox();
        box.add(Box.createGlue());
        box.add(goButton);
        return box;
    }

    private void doBrowseForFile() {
        initializeFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(this) != JFileChooser.CANCEL_OPTION) {
            try {
                String canonicalPath = fileChooser.getSelectedFile().getCanonicalPath();
                setSelectedPath(canonicalPath);
            } catch (IOException e) {
                System.err.println("Could not locate directory");
                e.printStackTrace();
            }
        }
    }

    void initializeFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
            if ( path.getText()!=null ) {
                fileChooser.setSelectedFile(new File(path.getText()));
            }
        }
    }

    private void setSelectedPath(String canonicalPath) {
        try {
            path.setText(canonicalPath);
            prefs.put(SELECTED_DIRECTORY_KEY, canonicalPath);
            prefs.flush();
        } catch (BackingStoreException e) {
            System.err.println("Could not flush preferences");
            e.printStackTrace();
        }
    }

    public void addDocumentGeneratorGui(DocumentGeneratorGui generatorGui) {
        generatorGuis.add(generatorGui);
        contentPane.add(generatorGui.getComponent());
    }
}

