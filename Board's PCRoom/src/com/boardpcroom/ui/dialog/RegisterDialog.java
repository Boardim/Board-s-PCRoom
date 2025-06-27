package com.boardpcroom.ui.dialog;

import com.boardpcroom.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterDialog extends JDialog {
    public RegisterDialog(JFrame parent) {
        super(parent, "Register", true);
        setLayout(null);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Font font = new Font(null, Font.PLAIN, 16);

        //warning Text
        JLabel warning = new JLabel("Space is not allowed");
        warning.setForeground(Color.RED);
        warning.setFont(new Font(null, Font.BOLD, 20));

        //input Form
        JLabel idLabel = new JLabel("ID");
        JLabel pwLabel = new JLabel("PW");
        JLabel nameLabel = new JLabel("Name");

        JTextField idField = new JTextField();
        JPasswordField pwField = new JPasswordField();
        JTextField nameField = new JTextField();

        JButton submitBtn = new JButton("Submit");
        submitBtn.setFocusPainted(false);

        // font / positioning
        warning.setBounds(30, 20, 200, 20);
        idLabel.setFont(font); idLabel.setBounds(30, 50, 60, 25);
        pwLabel.setFont(font); pwLabel.setBounds(30, 90, 60, 25);
        nameLabel.setFont(font); nameLabel.setBounds(30, 130, 60, 25);

        idField.setBounds(100, 50, 150, 25);
        pwField.setBounds(100, 90, 150, 25);
        nameField.setBounds(100, 130, 150, 25);

        submitBtn.setBounds(100, 170, 100, 30);

        //add
        add(warning);
        add(idLabel);
        add(idField);
        add(pwLabel);
        add(pwField);
        add(nameLabel);
        add(nameField);
        add(submitBtn);

        setResizable(false);
        setSize(300, 270);
        setLocationRelativeTo(parent);

        // submit event : input Correct?
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String pw = pwField.getText();
                String name = nameField.getText();

                boolean test = id.contains(" ") || pw.contains(" ") || name.contains(" ");
                boolean testId = User.isValidate(id);

                //if == true -> Error      |     else == true -> register(com.boardpcroom.model.User.writeCSV)
                if(id.isEmpty() || pw.isEmpty() || name.isEmpty() || test)
                    JOptionPane.showMessageDialog(null, "Please enter it correctly.", "Alert", JOptionPane.WARNING_MESSAGE);
                else if(testId == true)
                    JOptionPane.showMessageDialog(null, "ID already exists", "Alert", JOptionPane.ERROR_MESSAGE);
                else {
                    User user = new User(id,pw,name);
                    User.addCSV(user);

                    //alert -> register program exit
                    JOptionPane.showMessageDialog(null, "Complete!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
    }
}
