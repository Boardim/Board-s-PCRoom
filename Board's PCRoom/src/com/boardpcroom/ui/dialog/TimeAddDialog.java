package com.boardpcroom.ui.dialog;

import com.boardpcroom.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeAddDialog extends JDialog {
    public TimeAddDialog(Frame frame, User userInfo) {
        super(frame, "Add Time", true);
        setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        if(userInfo == null) {
            System.out.println("Error: com.boardpcroom.ui.dialog.TimeAddDialog error occurred. userInfo is null");
            return;
        }

        //label design
        JLabel pointLb = new JLabel("Your Point: " + userInfo.getPoint());
        pointLb.setFont(new Font(null, Font.BOLD, 15));
        pointLb.setBounds(30, 20, 200, 25);
        add(pointLb);

        //button design
        JButton oneMBt = new JButton("1m : 1000p");
        JButton threeMBt = new JButton("3m : 3000p");
        JButton fiveMBt = new JButton("5m : 5000p");
        JButton oneHBt = new JButton("1h : 30000p");

        oneMBt.setBounds(30, 60, 100, 30);
        threeMBt.setBounds(150, 60, 100, 30);
        fiveMBt.setBounds(30, 100, 100, 30);
        oneHBt.setBounds(150, 100, 100, 30);


        //event time : add time
        ActionListener timeAdder = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //how much Add value
                JButton src = (JButton) e.getSource();
                int minute = 0;
                int cost = 0;
                if (src == oneMBt) {
                    minute = 1;
                    cost = 1000;
                }
                else if (src == threeMBt) {
                    minute = 3;
                    cost = 3000;
                }
                else if (src == fiveMBt) {
                    minute = 5;
                    cost = 5000;
                }
                else if(src == oneHBt) {
                    minute = 60;
                    cost = 30000;
                }


                //if : Point not enough
                if (userInfo.getPoint() < cost) {
                    JOptionPane.showMessageDialog(TimeAddDialog.this, "Not enough points.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    int confirm = JOptionPane.showConfirmDialog(TimeAddDialog.this, "Use " + cost + "P for +" + minute + " minutes?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        int temp = userInfo.getTime();
                        userInfo.setTime(temp + minute*60);

                        int tempPoint = userInfo.getPoint();
                        userInfo.setPoint(tempPoint - cost);
                        dispose();
                    }
                }
            }
        };

        //event
        oneMBt.addActionListener(timeAdder);
        threeMBt.addActionListener(timeAdder);
        fiveMBt.addActionListener(timeAdder);
        oneHBt.addActionListener(timeAdder);

        //add
        add(oneMBt);
        add(threeMBt);
        add(fiveMBt);
        add(oneHBt);

        //etc
        setSize(300, 200);
        setLocationRelativeTo(frame);
        setResizable(false);
        setVisible(true);
    }
}
