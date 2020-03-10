package main.java.kamino.client;

import main.java.kamino.library.Hash;

import javax.swing.*;
import java.awt.*;

public class LoginGui {
    public JButton SubmitLogin;
    public JPanel Login;
    public JTextField UnameField;
    public JPasswordField PassField;

    public JFrame loginFrame;

    Hash hash = new Hash();

    public LoginGui() {

        loginFrame = new JFrame("Kamino Login");
        loginFrame.setContentPane(this.Login);

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();

        loginFrame.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        loginFrame.setLocation(dim.width/2-loginFrame.getSize().width/2, dim.height/2-loginFrame.getSize().height/2);

        loginFrame.setVisible(true);

    }
}
