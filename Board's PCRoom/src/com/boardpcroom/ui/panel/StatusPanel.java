package com.boardpcroom.ui.panel;

import com.boardpcroom.model.User;
import com.boardpcroom.ui.dialog.FoodDialog;
import com.boardpcroom.ui.dialog.PointAddDialog;
import com.boardpcroom.ui.dialog.TimeAddDialog;
import com.boardpcroom.ui.frame.MainScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

// show statusInfo (Panel)
public class StatusPanel extends JPanel {
    private int usedTime = 0;
    User userInfo;
    Timer timer;
    MainPanel mp;

    public StatusPanel(User u) {

        setLayout(null);

        //For test //
        //u = testUser();
        // --     //

        //private variable update
        userInfo = u;
        userInfo.setUsed(true);

        //Make MainPanel
        mp = new MainPanel();
        mp.setBounds(0,0,400,300);
        mp.setBackground(new Color(222,222,222));

        //add
        add(mp);
    }

    //forTest User Data
    private User testUser() {
        User user = new User();
        user.setSeatedID(1);
        user.setName("Hello");
        user.setTime(10);
        user.setId("Board");
        user.setPoint(10000);
        user.setPassword("Board");

        return user;
    }

    //when program Exit
    private void exitProgram() {
        MainScreenFrame.logoutFunction(userInfo);

        //this class method
        timer.cancel();
        userInfo.setUsed(false);

        //update user CSV
        User.updateCSV(userInfo);

        JFrame c = (JFrame) this.getTopLevelAncestor();
        c.dispose();
    }

    //Main Visual(design), util
    private class MainPanel extends JPanel {
        //userInfo
        private final JLabel seatedNumberLb = new JLabel("No. " + userInfo.getSeatedID());
        private JLabel nameLb = new JLabel("Name: " + userInfo.getName());
        private JLabel timeUsedLb = new JLabel();
        private JLabel timeLeftLb = new JLabel();
        private JLabel labelArr[] = {nameLb, seatedNumberLb, timeUsedLb, timeLeftLb};
        JButton pointBt = new JButton();

        public MainPanel() {
            //design
            designFunction();

            //1second update
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    updateTime();
                }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);
        }

        //1 second pass -> execute
        private void updateTime() {
            timeUsedLb.setText("Time Used: " + formatTime(usedTime));
            timeLeftLb.setText("Time Left  : "+ formatTime(userInfo.getTime()));

            if(userInfo.getTime() <= 0) {
                JOptionPane.showMessageDialog(null, "Time is up. Program Ends", userInfo.getSeatedID() + " seatID Time out", JOptionPane.WARNING_MESSAGE);
                exitProgram();
            }

            usedTime++;
            userInfo.setTime(userInfo.getTime()-1);
        }

        //point, time setText
        private void updateInfo() {
            pointBt.setText("Point: " + userInfo.getPoint());
            timeLeftLb.setText("Time Left  : " + formatTime(userInfo.getTime()));
        }

        //design && addFunction(event)
        private void designFunction() {
            setLayout(null);

            //add button
            pointBt = new JButton("Point: " + userInfo.getPoint());


            JButton foodBt = new JButton("Order Food");
            JButton exitBt = new JButton("Exit");
            JButton addTimeBt = new JButton("Add Time");

            JButton[] buttonArr = {foodBt, exitBt, addTimeBt, pointBt};

            //event setting
            exitBt.addActionListener(new exitButtonListener());
            pointBt.addActionListener(new pointButtonListener());
            addTimeBt.addActionListener(new timeButtonListener());
            foodBt.addActionListener(new showFoodListener());

            //Font setting
            Font font = new Font(null, Font.BOLD, 20);
            for (JLabel jLabel : labelArr) {
                jLabel.setFont(font);
            }


            //position
            seatedNumberLb.setBounds(20,20,100,30);
            nameLb.setBounds(20,80,200,30);
            timeUsedLb.setBounds(20, 130, 200, 30);
            timeLeftLb.setBounds(20, 170, 200, 30);

            foodBt.setBounds(150,20,100,40);
            exitBt.setBounds(265,20,100,40);
            pointBt.setBounds(20, 220, 150,50);
            addTimeBt.setBounds(200, 220, 150, 50);

            //add
            for (JLabel jLabel : labelArr) {
                add(jLabel);
            }

            for(JButton jbutton : buttonArr) {
                jbutton.setFocusPainted(false);
                add(jbutton);
            }

        }
        //second -> to format
        private String formatTime(int seconds) {
            int hour = seconds / 3600;
            if(seconds >=3600) {
                seconds %= 3600;
            }
            int min = seconds / 60;
            int sec = seconds % 60;
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
    }

    //exitButton -> execute
    private class exitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int check = JOptionPane.showConfirmDialog(StatusPanel.this, "Exit Program?" ,"Alert", JOptionPane.YES_NO_OPTION);

            if(check == JOptionPane.YES_OPTION) {
                exitProgram();
            }
        }

    }

    //pointButton -> execute(add Point)
    private class pointButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //parent Frame
            Container c = getTopLevelAncestor();
            JFrame temp = (JFrame)c;

            PointAddDialog dialog = new PointAddDialog(temp, userInfo);
            mp.updateInfo();

        }

        //is Number?
        public static boolean isNaN(String strValue) {
            try {
                Integer.parseInt(strValue);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }

    //Button event : add time
    private class timeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Container c = getTopLevelAncestor();
            JFrame temp = (JFrame)c;

            TimeAddDialog dialog = new TimeAddDialog(temp, userInfo);
            mp.updateInfo();
        }
    }

    //Button event : show Food Dialog
    private class showFoodListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Container c = getTopLevelAncestor();
            JFrame temp = (JFrame)c;

            FoodDialog dialog = new FoodDialog(temp, userInfo);
            mp.updateInfo();
        }
    }
}
