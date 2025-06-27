package com.boardpcroom.ui.frame;

import com.boardpcroom.model.User;
import com.boardpcroom.ui.panel.MainScreenLoginPanel;
import com.boardpcroom.ui.panel.MainScreenSeatPanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// MainScreen : visual first screen (seat, login)
public class MainScreenFrame extends JFrame {
    private Timer timer;
    private JLabel timeLabel;
    public static boolean isLogin = false;
    public static User currentUser = null;
    public static ArrayList<User>loginUserList = new ArrayList<User>();

    public static MainScreenLoginPanel loginPanel;
    public static MainScreenSeatPanel seatPanel;

    public static void main(String[] args) {
        new MainScreenFrame();
    }

    public MainScreenFrame() {
        //default setting
        setTitle("Welcome");
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new windowListener());

        setResizable(false);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(217,243,253));

        // Welcome Panel
        JPanel welcomeJPanel = makeWelcomePanel();
        welcomeJPanel.setBounds(50,60,650,90);

        // Login Panel
        loginPanel = new MainScreenLoginPanel();
        loginPanel.setBounds(780, 60, 350, 500);

        // SeatPanel (backgroundPanel = forColor)
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(195, 236, 251));
        backgroundPanel.setBounds(50,150,650,475);

        seatPanel = new MainScreenSeatPanel(loginPanel);
        seatPanel.setBounds(75,175,600,425);

        backgroundPanel.setBorder(new LineBorder(Color.BLACK, 2));

        // Center Line
        JPanel line = new JPanel();
        line.setBackground(Color.black);
        line.setBounds(740, 60, 5, 565);  // x, y, width, height
        add(line);


        //Program Exit Button + Made by Board text
        JLabel Board = new JLabel(" Made by Board");
        Board.setFont(new Font("Arial", Font.ITALIC, 25));
        Board.setBounds(780,570,190,50);
        Board.setForeground(new Color(20,20,20));
        add(Board);

        ImageIcon originalIcon = new ImageIcon("ImageFolder/exit.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JButton programExitButton = new JButton(scaledIcon);
        programExitButton.setBounds(1080,570,50,50);
        programExitButton.addActionListener(new programEndListener());

        //add
        add(welcomeJPanel);
        add(seatPanel);

        add(backgroundPanel);

        add(loginPanel);

        add(programExitButton);

        setVisible(true);
    }

    //Timer Function (reset by 1second)
    void makeTime() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");

        String text = "";

        text+= now.format(format);

        timeLabel.setText(text);
    }
    private void makeTimer() {
        //1second update
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                makeTime();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    //Visual welcome content
    private JPanel makeWelcomePanel() {
        JPanel welcomeJPanel = new JPanel();
        welcomeJPanel.setBackground(Color.WHITE);
        welcomeJPanel.setLayout(null);

        //welcomeText
        Font welcomeFont = new Font(null, Font.BOLD, 35);
        JLabel welcomeLabel = new JLabel("Board's PC Room");
        welcomeLabel.setFont(welcomeFont);
        welcomeLabel.setBounds(20, 10, 300,30);

        JLabel seatText = new JLabel("↓ Seat");
        seatText.setFont(new Font(null, Font.BOLD, 25));
        seatText.setBounds(500,55,200,30);

        // currentTime
        timeLabel = new JLabel();
        timeLabel.setBounds(20,50,300,30);

        Font timeFont = new Font(null, Font.BOLD, 20);
        timeLabel.setFont(timeFont);


        makeTimer();
        welcomeJPanel.add(welcomeLabel);
        welcomeJPanel.add(timeLabel);
        welcomeJPanel.add(seatText);

        return welcomeJPanel;
    }

    //logout Method
    public static void logoutFunction(User userInfo) {
        for(int i = 0; i < loginUserList.size(); i++) {
            if(userInfo.getId().equals(loginUserList.get(i).getId())) {
                loginUserList.remove(i);
                break;
            }
        }
        seatPanel.whenUserExitProgram(userInfo);
    }

    //program end bt event -> updateCSV
    private class programEndListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int check = JOptionPane.showConfirmDialog(MainScreenFrame.this, "Exit Program?" ,"Alert", JOptionPane.YES_NO_OPTION);

            if(check == JOptionPane.YES_OPTION) {
                for (User u : MainScreenFrame.loginUserList) {
                    u.setUsed(false);
                    User.updateCSV(u);
                }

                timer.cancel();

                dispose();

                System.exit(0);
            }
        }
    }

    // title bar X bt not work
    private class windowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            JOptionPane.showMessageDialog(MainScreenFrame.this, "If you want to exit the program,\nPress Exit Button", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
