package com.boardpcroom.ui.dialog;

import com.boardpcroom.model.User;
import com.boardpcroom.ui.panel.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
    //for return -> mainscreen?
    private User returnUser = null;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        //loginForm
        JPanel jp = new makeLoginForm();
        add(jp);

        //Almost design
        LoginPanel lp = new LoginPanel();
        add(lp);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }

    public User getLoggedInUser() {
        return returnUser;
    }

    //LoginForm Class
    private class makeLoginForm extends JPanel {
        public makeLoginForm() {
            setLayout(null);

            //greeting Text
            Font greetingFont = new Font(null, Font.BOLD, 30);
            JLabel grLb = new JLabel("Board's PC ROOM");
            grLb.setFont(greetingFont);
            grLb.setBounds(50, 30, 300, 50);
            add(grLb);

            //ID,PW Text
            Font font = new Font(null, Font.BOLD, 20);
            JLabel idLb = new JLabel(" ID ");
            JLabel pdLb = new JLabel("PW ");

            idLb.setFont(font);
            pdLb.setFont(font);

            idLb.setBounds(70, 90, 50, 20);
            pdLb.setBounds(70, 110, 50, 50);
            add(idLb);
            add(pdLb);

            //TextField
            JTextField idField = new JTextField();
            JPasswordField pwField = new JPasswordField();

            idField.setBounds(120, 90, 120, 25);
            pwField.setBounds(120, 125, 120, 25);

            add(idField);
            add(pwField);

            //makeLoginButton
            JButton loginButton = getJButton(idField, pwField);
            setBounds(0,0,400,170);
            add(loginButton);
            setVisible(true);
        }

        //make loginButton design && method
        private JButton getJButton(JTextField idField, JPasswordField pwField) {
            JButton loginButton = new JButton("Login");
            loginButton.setBounds(260, 100, 70, 30);
            loginButton.setFocusPainted(false);
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String id = idField.getText();
                    String pw = new String(pwField.getPassword());
                    User userData = User.getUser(id, pw);
                    if (userData != null) {
                        //System.out.println("hi");
                        returnUser = userData;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect ID or Password.", "Alert", JOptionPane.WARNING_MESSAGE);
                    }

                }
            });
            return loginButton;
        }
    }
};
