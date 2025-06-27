package com.boardpcroom.ui.panel;

import com.boardpcroom.ui.dialog.FindPWDialog;
import com.boardpcroom.ui.dialog.RegisterDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    public LoginPanel() {
        setLayout(null);

        //make ETC Buttons
        JPanel etcPanel = makeETCButton();

        //etc function

        etcPanel.setBounds(0,170,400,130);

        //add(loginForm);
        add(etcPanel);
        setBounds(0, 0, 400, 300);

    }

    //makeButton (register, find pw)
    private static JPanel makeETCButton() {
        JPanel jp = new JPanel();
        jp.setLayout(null);

        //Line
        JSeparator line = new JSeparator();
        line.setForeground(Color.BLUE);
        line.setBounds(30, 0, 340, 1);  // x, y, width, height
        jp.add(line);

        //REGISTER Button
        JButton registerBtn = new JButton("Register");
        registerBtn.setFocusPainted(false);
        registerBtn.setBounds(70, 30, 100, 30);
        jp.add(registerBtn);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterDialog dialog = new RegisterDialog(null);
                dialog.setVisible(true);
            }
        });

        //findPW Button
        JButton findPWBtn = new JButton("Find PW");
        findPWBtn.setBounds(220,30,100,30);
        findPWBtn.setFocusPainted(false);
        jp.add(findPWBtn);

        findPWBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindPWDialog dialog = new FindPWDialog(null);
                dialog.setVisible(true);
            }
        });

        return jp;
    }

}

