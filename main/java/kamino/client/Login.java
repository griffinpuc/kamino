package main.java.kamino.client;

import main.java.kamino.library.Hash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JButton SubmitLogin;
    private JPanel Login;
    private JTextField UnameField;
    private JPasswordField PassField;

    public JFrame loginFrame;
    public String username = null;
    public String password = null;

    Hash hash = new Hash();

    public Login() {

        startUp();

        loginFrame = new JFrame("Kamino Login");
        loginFrame.setContentPane(this.Login);

        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.pack();

        loginFrame.setSize(300, 200);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        loginFrame.setLocation(dim.width/2-loginFrame.getSize().width/2, dim.height/2-loginFrame.getSize().height/2);

        loginFrame.setVisible(true);

    }

    public void startUp(){
        SubmitLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("".equals(UnameField.getText()) || "".equals(PassField.getPassword()) ){
                    JOptionPane.showMessageDialog(null, "Username or password field is incorrect.");
                }
                else{
                    username = hash.hashString(UnameField.getText());
                    password = hash.hashString(PassField.getPassword().toString());

                }
            }
        });
    }
}
