package main.java.kamino.client;

import javax.swing.*;
import java.awt.*;

public class MainGui {
    private JList list1;
    private JPanel Main;
    private JTextArea textArea1;
    private JButton lockButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton syncButton;

    public JFrame mainFrame;

    public MainGui(){

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Edit");
        JMenuItem mi1 = new JMenuItem("New note");
        JMenuItem mi2 = new JMenuItem("Preferences");
        JMenuItem mi3 = new JMenuItem("Logout");

        m1.add(mi1);
        m1.add(mi3);
        m2.add(mi2);
        mb.add(m1);
        mb.add(m2);

        mainFrame = new JFrame("Kamino");
        mainFrame.setContentPane(this.Main);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setJMenuBar(mb);

        mainFrame.setSize(1200, 800);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);

        mainFrame.setVisible(true);
    }
}
