package com.boardpcroom.ui.dialog;

import com.boardpcroom.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PointAddDialog extends JDialog {
    public PointAddDialog(Frame frame, User userInfo) {

        super(frame, "Point Add Window", true);
        setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        if(userInfo == null) {
            System.out.println("Error: com.boardpcroom.ui.dialog.PointAddDialog error occurred. userInfo is null");
            return;
        }

        //text, btn
        JLabel lb = new JLabel("Enter Amount. 1Won = 1Point");
        JTextField tf = new JTextField(10);
        JButton bt = new JButton("CheckOut");
        JButton cancelBt = new JButton("Cancel");

        lb.setFont(new Font(null, Font.BOLD, 15));

        //location
        lb.setBounds(30,20, 250,25);
        tf.setBounds(30,50,200,30);
        bt.setBounds(30,100,95,30);
        cancelBt.setBounds(135,100,95,30);

        //event (if exit -> quit)
        cancelBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        //eventListener : input value
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //testing
                String text = tf.getText();
                boolean check = isNaN(text);

                if(text.contains(" ") || text.isEmpty()) {
                    JOptionPane.showMessageDialog(PointAddDialog.this, "Blank prohibited.", "Alert", JOptionPane.ERROR_MESSAGE);
                }
                else if(!check) {
                    JOptionPane.showMessageDialog(PointAddDialog.this, "Only Digit", "Alert", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int value = Integer.parseInt(text);
                    if (value <= 0) {
                        JOptionPane.showMessageDialog(PointAddDialog.this, "You can input only positive integer.", "Alert", JOptionPane.ERROR_MESSAGE);
                    } else {
                        int checking = JOptionPane.showConfirmDialog(PointAddDialog.this, "Confirm payment?", "Checkout", JOptionPane.YES_NO_OPTION);
                        if (checking == JOptionPane.YES_OPTION) {

                            userInfo.setPoint(userInfo.getPoint() + value);

                            dispose();
                        }
                    }
                }
            }
        });

        //add
        add(lb);
        add(tf);
        add(bt);
        add(cancelBt);

        setResizable(false);
        setSize(300,200);
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    //isNumber = integer?
    public static boolean isNaN(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
