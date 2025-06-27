package com.boardpcroom.ui.dialog;

import com.boardpcroom.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindPWDialog extends JDialog {
    public FindPWDialog(JFrame parent) {
        super(parent, "Register", true);

        setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Font font = new Font(null, Font.PLAIN, 16);

        //WarningText
        JLabel warning = new JLabel("Space is not allowed");
        warning.setForeground(Color.RED);
        warning.setFont(new Font(null, Font.BOLD, 20));


        //input Form
        JLabel idLabel = new JLabel("ID");
        JLabel pwLabel = new JLabel("");

        JTextField idField = new JTextField();

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFocusPainted(false);


        // font / positioning
        warning.setBounds(30, 20, 200, 20);
        pwLabel.setFont(font); pwLabel.setBounds(30, 80, 270, 25);
        idLabel.setFont(font); idLabel.setBounds(30, 50, 60, 25);
        idField.setBounds(100, 50, 150, 25);
        submitBtn.setBounds(100, 120, 100, 30);


        //add
        add(warning);
        add(idLabel);
        add(pwLabel);
        add(idField);
        add(submitBtn);

        setResizable(false);
        setSize(300, 200);
        setLocationRelativeTo(parent);

        // submit
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();

                boolean test = id.contains(" ");
                if(id.isEmpty() || test) {
                    pwLabel.setText("");
                    JOptionPane.showMessageDialog(null, "Input your ID correctly", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String pw = User.findPW(id);
                    if(pw.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Your Account does not exist.", "Alert", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        pwLabel.setText("Your Password is : " + pw);
                    }
                }
            }
        });
    }
}
