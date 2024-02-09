package org.HuynhDucPhu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class SimpleNotepad extends JFrame implements ActionListener {
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem contentMenuItem;
    private JMenuItem aboutMenuItem;
    private JTextArea textArea;

    public SimpleNotepad() throws HeadlessException {
        this("Simple Notepad");
    }

    public SimpleNotepad(String title) throws HeadlessException {
        super(title);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(setupMenubar());
        add(setupTextArea());

        setVisible(true);
    }
    private JTextArea setupTextArea() {
        textArea = new JTextArea();
        return textArea;
    }
    private JMenuBar setupMenubar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        exitMenuItem = new JMenuItem("Exit");

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        newMenuItem.setIcon(new ImageIcon("src/main/resources/images/addIcon.png"));
        openMenuItem.setIcon(new ImageIcon("src/main/resources/images/openIcon.png"));
        saveMenuItem.setIcon(new ImageIcon("src/main/resources/images/saveIcon.png"));
        exitMenuItem.setIcon(new ImageIcon("src/main/resources/images/exitIcon.png"));

        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

        fileMenu.add(newMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        contentMenuItem = new JMenuItem("Content");
        aboutMenuItem = new JMenuItem("About us");

        contentMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);

        contentMenuItem.setIcon(new ImageIcon("src/main/resources/images/contentIcon.png"));
        aboutMenuItem.setIcon(new ImageIcon("src/main/resources/images/aboutIcon.png"));

        helpMenu.add(contentMenuItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutMenuItem);

        return menuBar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == newMenuItem) {
            textArea.setText(null);
        } else if (source == exitMenuItem) {
            System.exit(0);
        } else if (source == openMenuItem) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File filedOpened = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (
                        BufferedReader br = new BufferedReader(new FileReader(filedOpened))
                        )
                {
                    String line;
                    while ((line = br.readLine()) != null) {
                        textArea.append(line + '\n');
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else if (source == saveMenuItem) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File filedOpened = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (
                        BufferedWriter bw = new BufferedWriter(new FileWriter(filedOpened))
                        )
                {
                    bw.write(textArea.getText());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

}
