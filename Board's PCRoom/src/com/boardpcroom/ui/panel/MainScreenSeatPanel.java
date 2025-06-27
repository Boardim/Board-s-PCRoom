package com.boardpcroom.ui.panel;

import com.boardpcroom.model.User;
import com.boardpcroom.ui.frame.MainScreenFrame;
import com.boardpcroom.ui.frame.StatusFrame;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//MainScreen Seat Panel Utility
public class MainScreenSeatPanel extends JPanel {
    private int seatCount = 12;
    private static JButton buttonArr[] = new JButton[13];
    private static User[] userArr = new User[13];
    private MainScreenLoginPanel loginPanel;

    public MainScreenSeatPanel(MainScreenLoginPanel loginP) {
        loginPanel = loginP;

        setLayout(new GridLayout(3, 4, 30, 30));
        setPreferredSize(new Dimension(600,400));
        setBackground(new Color(195, 236, 251));
        setOpaque(true);

        //Default Settings
        for(int i = 1; i <= seatCount; i++) {
            buttonArr[i] = new JButton();
            buttonNoUser(i);
            buttonArr[i].addActionListener(new buttonListener());
            buttonArr[i].setBorder(new LineBorder(new Color(94, 237, 151), 3, true));
            add(buttonArr[i]);
            userArr[i] = null;
        }
    }

    //if seat no user?
    private static void buttonNoUser(int i) {
        String html = "<html><div style='text-align:center;'>Seat " + i + " <br><br><b>(empty)</b></div></html>";
        buttonArr[i].setText(html);
        buttonArr[i].setFocusPainted(false);
        buttonArr[i].setFont(new Font(null, Font.PLAIN, 15));
        buttonArr[i].setBackground(new Color(136,218,248));
        buttonArr[i].setBorder(new LineBorder(new Color(94, 237, 151), 3, true));

        userArr[i] = null;
    }
    //if seat has user?
    private static void buttonYesUser(int i) {
        String html = "<html><div style='text-align:center;'>Seat " + i + " <br><br><b>ID : " + userArr[i].getId() + "</b></div></html>";
        buttonArr[i].setText(html);
        buttonArr[i].setFont(new Font(null, Font.BOLD, 15));
        buttonArr[i].setBackground(new Color(235,124,187));
        buttonArr[i].setBorder(new LineBorder(Color.BLACK, 3, true));
    }

    //if seat click event
    private class buttonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(MainScreenFrame.isLogin) {
                JButton bt = (JButton) e.getSource();
                String numberText;
                numberText = bt.getText().split(" ")[2];
                int idx = Integer.parseInt(numberText);

                //if seat is empty?
                if(userArr[idx] == null) {
                    //if user time == 0
                    if(MainScreenFrame.currentUser.getTime() == 0) {
                        JOptionPane.showMessageDialog(MainScreenSeatPanel.this, "You don't have any time.\nPlease add your time.", "Alert", JOptionPane.WARNING_MESSAGE);
                    }
                    else { // else(have time)
                        //change
                        userArr[idx] = MainScreenFrame.currentUser;
                        buttonYesUser(idx);
                        MainScreenFrame.loginUserList.add(userArr[idx]);

                        //popup / set seatId
                        MainScreenFrame.currentUser.setSeatedID(idx);
                        new StatusFrame(MainScreenFrame.currentUser);

                        //logout
                        MainScreenFrame.isLogin = false;
                        MainScreenFrame.currentUser = null;
                        loginPanel.toggleDesign();
                    }
                }
                else { // if Seat is occupied
                    JOptionPane.showMessageDialog(MainScreenSeatPanel.this, "The seat is occupied.", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
            else { // if not login
                JOptionPane.showMessageDialog(MainScreenSeatPanel.this, "Please Login First.", "Alert", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //if exit program
    public void whenUserExitProgram(User userInfo) {
        int idx = userInfo.getSeatedID();
        buttonNoUser(idx);

        userInfo.setSeatedID(0);
    }

}
