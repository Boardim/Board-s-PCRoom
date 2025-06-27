package com.boardpcroom.ui.frame;

import com.boardpcroom.model.User;
import com.boardpcroom.ui.panel.StatusPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//StatusFrame : show User Status(time, point..)
public class StatusFrame extends JFrame {

    public StatusFrame(User user) {
        super("Welcome " + user.getName());
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new windowListener());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - 400;
        int y = 0;
        setLocation(x, y);


        StatusPanel sp = new StatusPanel(user);
        add(sp);

        setResizable(false);
        setSize(400,330);
        setVisible(true);

    }

    //when closeButton -> alert error
    private class windowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            JOptionPane.showMessageDialog(StatusFrame.this, "If you want to exit the program,\nPress Exit Button", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
