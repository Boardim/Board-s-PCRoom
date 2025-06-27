package com.boardpcroom.ui.panel;

import com.boardpcroom.model.User;
import com.boardpcroom.ui.dialog.LoginDialog;
import com.boardpcroom.ui.dialog.PointAddDialog;
import com.boardpcroom.ui.dialog.TimeAddDialog;
import com.boardpcroom.ui.frame.MainScreenFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//MainScreen Login Utility Panel
public class MainScreenLoginPanel extends JPanel{
    private User userInfo = null;
    private JLabel welcomeLabel;

    //beforeLogin
    private JButton loginButton;
    private JLabel beforeLogInIcon;

    //afterLogin
    private JPanel line;
    private JPanel avatarPanel;
    private JLabel avatarIcon, usernameLabel;
    private JPanel timeInfoPanel, timeValuePanel;
    private JLabel timeIcon, timeText, timeValueLabel;
    private JButton timePlusButton;
    private JPanel chargeInfoPanel, chargeActionPanel;
    private JLabel chargeIcon, chargeText;
    private JButton chargeButton;
    private JButton logoutButton;

    public MainScreenLoginPanel() {
        setBackground(new Color(185,233,251));
        setLayout(null);
        setBounds(0,0,350,400);

        updateFalseDesign();
        updateTrueDesign();
        toggleDesign();
    }

    //if seat using == false
    private void updateFalseDesign() {
        //welcomeText
        welcomeLabel = new JLabel("Welcome! Login Please");
        welcomeLabel.setFont(new Font(null, Font.BOLD, 20));
        welcomeLabel.setBounds(40,16,300,30);
        add(welcomeLabel);

        //LoginButton
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font(null, Font.BOLD, 18));
        loginButton.setBackground(new Color(0, 174, 240));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBounds(100,250,150,40);
        loginButton.addActionListener(new loginButtonListener());
        add(loginButton);

        //loginImage
        ImageIcon secIcon = transImg("security.png", 150, 150);
        beforeLogInIcon = new JLabel(secIcon);
        beforeLogInIcon.setBounds(90,70,150,150);
        add(beforeLogInIcon);
    }

    //if seat using == true
    private void updateTrueDesign() {
        //line
        line = new JPanel();
        line.setBounds(16,200,318,5);
        line.setBackground(Color.BLACK);
        add(line);

        //profileCard
        avatarPanel = new JPanel(null);
        avatarPanel.setBackground(new Color(209,239,255));
        avatarPanel.setBounds(16, 58, 318, 130);

        usernameLabel = new JLabel("", SwingConstants.CENTER);
        usernameLabel.setFont(new Font(null, Font.BOLD, 15));
        usernameLabel.setBounds(0,100,318,30);
        avatarIcon = new JLabel(transImg("profile.png", 100, 100));
        avatarIcon.setBounds(110,8,100,100);
        avatarPanel.add(avatarIcon);
        avatarPanel.add(usernameLabel);
        add(avatarPanel);


        // 2) time relative
        // 2-1) icon
        timeInfoPanel = new JPanel(null);
        timeInfoPanel.setBackground(Color.WHITE);
        timeInfoPanel.setBounds(16, 216, 100, 80);
        timeIcon = new JLabel(transImg("time.png",40,40));
        timeIcon.setBounds(30, 8, 40, 40);

        //2-2 text
        timeText = new JLabel("Time Left", SwingConstants.CENTER);
        timeText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        timeText.setBounds(0, 52, 100, 16);
        timeInfoPanel.add(timeIcon);
        timeInfoPanel.add(timeText);
        add(timeInfoPanel);

        // 2-3) time value
        timeValuePanel = new JPanel(null);
        timeValuePanel.setBackground(new Color(0, 174, 240));
        timeValuePanel.setBounds(122, 216, 140, 80);

        timeValueLabel = new JLabel("", SwingConstants.CENTER);
        timeValueLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        timeValueLabel.setBounds(0, 28, 140, 24);
        timeValueLabel.setForeground(Color.WHITE);
        timeValuePanel.add(timeValueLabel);
        add(timeValuePanel);

        // 2-4) time add button
        timePlusButton = new JButton("+");
        timePlusButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        timePlusButton.setBackground(new Color(95, 223, 120));
        timePlusButton.setForeground(Color.WHITE);
        timePlusButton.setFocusPainted(false);
        timePlusButton.setBounds(266, 216, 68, 80);
        add(timePlusButton);

        // 3) charge
        // 3-1) visible point
        chargeInfoPanel = new JPanel(null);
        chargeInfoPanel.setBackground(Color.WHITE);
        chargeInfoPanel.setBounds(16, 304, 100, 80);
        chargeIcon = new JLabel(transImg("coin.png", 40, 40));
        chargeIcon.setBounds(30, 8, 40, 40);

        chargeText = new JLabel("", SwingConstants.CENTER);
        chargeText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        chargeText.setBounds(0, 52, 100, 16);
        chargeInfoPanel.add(chargeIcon);
        chargeInfoPanel.add(chargeText);
        add(chargeInfoPanel);

        // 3-2) charge button
        chargeActionPanel = new JPanel(null);
        chargeActionPanel.setBackground(new Color(95, 223, 120));
        chargeActionPanel.setBounds(122, 304, 212, 80);
        chargeButton = new JButton("CHARGE");
        chargeButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        chargeButton.setForeground(Color.WHITE);
        chargeButton.setBackground(new Color(95, 223, 120));
        chargeButton.setFocusPainted(false);
        chargeButton.setBounds(0, 0, 212, 80);
        chargeActionPanel.add(chargeButton);
        add(chargeActionPanel);

        // 4) logout
        logoutButton = new JButton(transImg("logout.png",48,48));
        logoutButton.setBounds(266, 405, 48, 48);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(new logoutButtonListener());
        add(logoutButton);


        // 5) addEvent (time, point)
        Container c = getTopLevelAncestor();
        chargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PointAddDialog dialog = new PointAddDialog((Frame) c, userInfo);
                whenUserInfoChanged();
                User.updateCSV(userInfo);
            }
        });
        timePlusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimeAddDialog dialog = new TimeAddDialog((Frame) c, userInfo);
                whenUserInfoChanged();
                User.updateCSV(userInfo);
            }
        });

    }

    //if userInfo change
    private void whenUserInfoChanged() {
        timeValueLabel.setText(formatTime(userInfo.getTime()));
        chargeText.setText(String.valueOf((userInfo.getPoint())));
        usernameLabel.setText(userInfo.getName());
    }

    //design toggle
    public void toggleDesign() {
        boolean loggedIn = MainScreenFrame.isLogin;

        //set WelcomeText
        welcomeLabel.setText(loggedIn
                ? "Welcome! " + MainScreenFrame.currentUser.getName()
                : "Welcome! Login Please");

        if(loggedIn) {
            whenUserInfoChanged();
        }

        // before-login
        loginButton.setVisible(!loggedIn);
        beforeLogInIcon.setVisible(!loggedIn);

        // after-login
        avatarPanel.setVisible(loggedIn);
        line.setVisible(loggedIn);
        timeInfoPanel.setVisible(loggedIn);
        timeValuePanel.setVisible(loggedIn);
        timePlusButton.setVisible(loggedIn);
        chargeInfoPanel.setVisible(loggedIn);
        chargeActionPanel.setVisible(loggedIn);
        logoutButton.setVisible(loggedIn);

        revalidate();
        repaint();
    }

    //string to ImageIcon
    private ImageIcon transImg(String filename, int x, int y) {
        String text = "ImageFolder/";
        text += filename;

        ImageIcon originalIcon = new ImageIcon(text);
        Image scaledImg = originalIcon.getImage().getScaledInstance(x,y,Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    //loginButton event : login
    private class loginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginDialog loginF = new LoginDialog((JFrame)getTopLevelAncestor());
            User user = loginF.getLoggedInUser();
            if(user == null) {
                System.out.println("Error : com.boardpcroom.ui.dialog.LoginDialog user returning error");
            }
            else if(MainScreenFrame.loginUserList.stream().anyMatch(u -> u.getId().equals(user.getId()))) {
                JOptionPane.showMessageDialog(MainScreenLoginPanel.this, "Be already logged in.", "Alert", JOptionPane.WARNING_MESSAGE);
            }
            else {
                MainScreenFrame.isLogin = true;
                MainScreenFrame.currentUser = user;
                userInfo = user;

                toggleDesign();
                //System.out.println("Login Complete");
            }
        }
    }

    //logoutButton event : logout
    private class logoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(MainScreenFrame.isLogin == false) {
                System.out.println("Error : LogoutDialog user returning error");
            }
            else {
                MainScreenFrame.isLogin = false;
                MainScreenFrame.currentUser = null;
                userInfo = null;

                toggleDesign();
            }
        }
    }

    //second -> time
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
