package org.huynhducphu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;

public class SimpleNotepad extends JFrame implements ActionListener {
    private JFileChooser fileChooser;
    private File fileChosen;
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

        setupJFileChooser();
        setJMenuBar(setupMenubar());
        add(setupTextArea());

        setVisible(true);
    }
    private void setupJFileChooser() {
        fileChooser = new JFileChooser();
        fileChosen = null;

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

        newMenuItem.setIcon(new ImageIcon(SimpleNotepad.class.getResource("/images/addIcon.png")));
        openMenuItem.setIcon(new ImageIcon(getClass().getResource("/images/openIcon.png")));
        saveMenuItem.setIcon(new ImageIcon(getClass().getResource("/images/saveIcon.png")));
        exitMenuItem.setIcon(new ImageIcon(getClass().getResource("/images/exitIcon.png")));

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

        contentMenuItem.setIcon(new ImageIcon(getClass().getResource("/images/contentIcon.png")));
        aboutMenuItem.setIcon(new ImageIcon(getClass().getResource("/images/aboutIcon.png")));

        helpMenu.add(contentMenuItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutMenuItem);

        return menuBar;
    }
    private void aboutUSFrame() {
        JFrame aboutUs = new JFrame("About us");
        aboutUs.setLocationRelativeTo(null);
        aboutUs.setSize(400, 400);
        aboutUs.setLayout(new BoxLayout(aboutUs.getContentPane(), BoxLayout.Y_AXIS));
        aboutUs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutUs.getContentPane().setBackground(Color.green);


        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.green);
        JLabel infoLabel = new JLabel(new ImageIcon(this.getClass().getResource("/images/personIcon.png")));

        infoLabel.setText("Author: Huynh Duc Phu");
        infoLabel.setVerticalTextPosition(JLabel.BOTTOM);
        infoLabel.setHorizontalTextPosition(JLabel.CENTER);
        infoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        infoPanel.add(infoLabel);

        JLabel descriptionLabel = new JLabel("This is a simple notepad, it doesn't have many features yet!");
        descriptionLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        infoPanel.add(descriptionLabel);

        aboutUs.add(Box.createVerticalGlue());
        aboutUs.add(infoPanel);
        aboutUs.add(Box.createVerticalGlue());
        aboutUs.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == newMenuItem) {
            fileChosen = null;
            textArea.setText(null);
        } else if (source == exitMenuItem) {
            System.exit(0);
        } else if (source == openMenuItem) {
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                fileChosen = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try (
                        BufferedReader br = new BufferedReader(new FileReader(fileChosen))
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
            if (fileChosen == null) {
                int response = fileChooser.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION)
                    fileChosen = new File(fileChooser.getSelectedFile().getAbsolutePath());
                else return;
            }
            try (
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileChosen))
            )
            {
                bw.write(textArea.getText());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (source == aboutMenuItem) {
            aboutUSFrame();
        }
    }

}
