package com.boardpcroom.ui.dialog;

import com.boardpcroom.model.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class FoodDialog extends JDialog {
    private JPanel checkListPanel = new JPanel();
    private final JLabel sumLabel = new JLabel("Total: 0 Point");
    private final JButton checkoutButton = new JButton("Checkout");
    private int total = 0;
    private final User userInfo;
    private final Map<String, JPanel> checkListMap = new HashMap<>();

    //food List
    private static final ArrayList<List<MenuItem>> MENU = new ArrayList<>();

    public FoodDialog(Frame frame, User user) {
        super(frame, "Food Order", true);
        userInfo = user;

        //In case the qty is not zero
        resetEverything();
        //image scaling
        setMenu();

        setSize(900,600);
        setLayout(new BorderLayout(10,10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(217,243,253));

        //makeTop List
        JTabbedPane tabs = new JTabbedPane();
        String[] categories = {"Ramyeon", "Snack", "Drink", "etc"};
        for(int i = 0; i < 4; i++) {
            tabs.addTab(categories[i], makeInnerContent(MENU.get(i)));
        }

        add(tabs, BorderLayout.CENTER);

        setResizable(false);
        setLocationRelativeTo(null);
        JPanel checkoutPanel = checklistPanel();
        add(checkoutPanel, BorderLayout.EAST);
        setVisible(true);

        revalidate();
        repaint();
    }

    private void resetEverything() {
        total = 0;
        checkListMap.clear();
        for (List<MenuItem> list : MENU) {
            for (MenuItem mi : list) {
                mi.qty = 0;
            }
        }
    }

    //set MENU Item
    private static void setMenu() {
        if(!MENU.isEmpty()) return;

        // ramen
        List<MenuItem> ramenList = new ArrayList<>();
        ramenList.add(new MenuItem("Paldo", 1500, "ImageFolder/menuImage/ramen/pramen.png"));
        ramenList.add(new MenuItem("Shrimp", 1300, "ImageFolder/menuImage/ramen/sramen.png"));
        ramenList.add(new MenuItem("Teumsae", 1300, "ImageFolder/menuImage/ramen/tramen.png"));
        ramenList.add(new MenuItem("Shin ramyun", 2500, "ImageFolder/menuImage/ramen/sinramen.png"));
        ramenList.add(new MenuItem("Zzaphagetti", 2500, "ImageFolder/menuImage/ramen/zramen.png"));


        MENU.add(ramenList);

        // snack
        List<MenuItem> snackList = new ArrayList<>();
        snackList.add(new MenuItem("Mintchocoball", 700, "ImageFolder/menuImage/snack/csnack.png"));
        snackList.add(new MenuItem("Doritos", 1800, "ImageFolder/menuImage/snack/dsnack.png"));
        snackList.add(new MenuItem("Matdongsan", 1500, "ImageFolder/menuImage/snack/msnack.png"));
        snackList.add(new MenuItem("Shrimp Snack", 1500, "ImageFolder/menuImage/snack/ssnack.png"));
        MENU.add(snackList);

        // drink
        List<MenuItem> drinkList = new ArrayList<>();
        drinkList.add(new MenuItem("Morning shine", 1500, "ImageFolder/menuImage/drink/adrink.png"));
        drinkList.add(new MenuItem("Melon Soda", 2000, "ImageFolder/menuImage/drink/melonsoda.png"));
        drinkList.add(new MenuItem("Coke", 1800, "ImageFolder/menuImage/drink/cdrink.png"));
        drinkList.add(new MenuItem("Cidar", 1800, "ImageFolder/menuImage/drink/sdrink.png"));
        drinkList.add(new MenuItem("Zico", 1200, "ImageFolder/menuImage/drink/zdrink.png"));
        MENU.add(drinkList);

        // etc
        List<MenuItem> etcList = new ArrayList<>();
        etcList.add(new MenuItem("Mandoo", 5000, "ImageFolder/menuImage/etc/metc.png"));
        etcList.add(new MenuItem("KimPiTang", 12000, "ImageFolder/menuImage/etc/ketc.png"));
        etcList.add(new MenuItem("Apple", 3000, "ImageFolder/menuImage/etc/apple.png"));
        etcList.add(new MenuItem("Mandarin", 1000, "ImageFolder/menuImage/etc/gyul.png"));
        etcList.add(new MenuItem("PineapplePizza", 15000, "ImageFolder/menuImage/etc/petc.png"));
        MENU.add(etcList);
    }

    //make InnerContent
    private JScrollPane makeInnerContent(List<MenuItem> items) {
        JPanel jp = new JPanel(new GridLayout(0,3,10,10));
        jp.setBackground(new Color(217,243,253));

        for(MenuItem item : items) {
            JButton btn = new JButton();
            btn.setLayout(new BorderLayout(5,5));

            //original Code (not optimized)
            /*
            // image setting
            ImageIcon icon = new ImageIcon(item.imagePath);
            Image img = icon.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
            btn.add(new JLabel(new ImageIcon(img)), BorderLayout.CENTER);
            */


            // optimize code by CHATGPT
            new SwingWorker<ImageIcon, Void>() {
                @Override
                protected ImageIcon doInBackground() {
                    ImageIcon icon = new ImageIcon(item.imagePath);
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    return new ImageIcon(img);
                }
                @Override
                protected void done() {
                    try {
                        ImageIcon icon = get();
                        btn.add(new JLabel(icon), BorderLayout.CENTER);
                        btn.revalidate();
                        btn.repaint();
                    } catch (Exception ignore) {}
                }
            }.execute();


            // food name / price setting
            String text = String.format("<html><center>" + item.name + "<br>" + item.price + "</center></html>");
            btn.add(new JLabel(text, SwingConstants.CENTER), BorderLayout.SOUTH);
            btn.setPreferredSize(new Dimension(200, 200));
            jp.add(btn);

            btn.addActionListener(new addChecklistListener(item));
        }
        return new JScrollPane(jp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    //make CheckList
    private JPanel checklistPanel() {
        //east = window's right position
        JPanel east = new JPanel(new BorderLayout(0,5));
        east.setBackground(new Color(217,243,253));

        //Top
        //jp = checkList innerContent
        checkListPanel.setLayout(new BoxLayout(checkListPanel, BoxLayout.Y_AXIS));
        checkListPanel.setBackground(new Color(217,243,253));

        //Scroll = scroll of jp
        JScrollPane scroll = new JScrollPane(checkListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(200,300));

        //Bottom
        //bottomjp = checkout Btn, label(text)
        JPanel bottomjp = new JPanel(null);
        bottomjp.setBackground(new Color(217,243,253));
        bottomjp.setPreferredSize(new Dimension(200,200));

        //design label, button
        sumLabel.setFont(new Font(null,Font.BOLD,15));
        checkoutButton.setFocusPainted(false);
        checkoutButton.addActionListener(new checkoutButtonListener());

        //add && setBounds
        bottomjp.add(sumLabel);
        bottomjp.add(checkoutButton);
        sumLabel.setBounds(0,0,200,30);
        checkoutButton.setBounds(60,100,100,50);

        east.add(scroll, BorderLayout.CENTER);
        east.add(bottomjp, BorderLayout.SOUTH);

        return east;
    }

    private void addCheckList(MenuItem item) {
        item.qty++;

        //if qty > 1  (put twice or more than) not add, modify text
        if(item.qty > 1) {
            JPanel original = checkListMap.get(item.name);
            JLabel proPrice = (JLabel) original.getComponent(1);
            JLabel proQty = (JLabel) original.getComponent(2);

            proPrice.setText(String.valueOf(item.price * item.qty));
            proQty.setText(String.valueOf(item.qty));
        }
        else {
            //container setting
            JPanel container = new JPanel(null) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    g.setColor(new Color(136, 218, 248));
                    g.fillRect(0, 0, getWidth(), getHeight()-10);
                }
            };
            container.setBackground(new Color(217,243,253));

            container.setPreferredSize(new Dimension(160, 100));
            container.setMaximumSize(new Dimension(160, 100));
            container.setMinimumSize(new Dimension(160, 100));

            //setting margin, border
            Border marginBorder = new EmptyBorder(0,0,10,0);
            Border line   = new LineBorder(Color.BLACK, 2);
            container.setBorder(new CompoundBorder(marginBorder,line));

            //set text, button
            JLabel proName = new JLabel(item.name);
            JLabel proPrice = new JLabel(String.valueOf(item.price));
            JLabel proQty = new JLabel(String.valueOf(item.qty));

            JButton eraseButton = new JButton("Delete");
            eraseButton.addActionListener(new deleteButtonListener(container, item));

            //positioning
            proName.setBounds(10, 10, 100, 30);
            proPrice.setBounds(10, 50, 50, 30);
            proQty.setBounds(100, 10, 50, 30);
            eraseButton.setBounds(70, 50, 70, 30);
            eraseButton.setFocusPainted(false);

            //add
            container.add(proName);
            container.add(proPrice);
            container.add(proQty);
            container.add(eraseButton);

            checkListPanel.add(container, checkListPanel.getSize());

            checkListMap.put(item.name, container);
        }
        total += item.price;

        updateSum();
        revalidate();
        repaint();

    }

    //Total = ? Point
    private void updateSum() {
        sumLabel.setText("Total: " + total + " Point");
    }

    //information : name, price, url
    private static class MenuItem {
        final String name;
        final int price;
        final String imagePath;
        int qty = 0;
        public MenuItem(String name, int price, String imagePath) {
            this.name = name;
            this.price = price;
            this.imagePath = imagePath;
        }
    }

    //if Product click -> execute
    private class addChecklistListener implements ActionListener {
        private final MenuItem item;
        private addChecklistListener(MenuItem item) {
            this.item = item;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            addCheckList(item);
        }
    }

    //Checkout Button
    private class checkoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int userPoint = userInfo.getPoint();
            if(userPoint < total) {
                JOptionPane.showMessageDialog(FoodDialog.this, "Your Point is less than total", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                int checking = JOptionPane.showConfirmDialog(FoodDialog.this, "Confirm payment?", "Checkout", JOptionPane.YES_NO_OPTION);
                if (checking == JOptionPane.YES_OPTION) {
                    userInfo.setPoint(userPoint - total);
                    JOptionPane.showMessageDialog(FoodDialog.this, "Your order has been placed!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    User.updateCSV(userInfo);
                    dispose();
                }
            }
        }
    }

    //if delete Menu in checklist -> execute
    private class deleteButtonListener implements ActionListener {
        private final JPanel jp;
        private final MenuItem item;
        public deleteButtonListener(JPanel jp, MenuItem item) {
            this.jp = jp;
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            total -= item.price * item.qty;
            item.qty = 0;

            updateSum();

            checkListMap.remove(item.name);
            checkListPanel.remove(jp);



            checkListPanel.revalidate();
            checkListPanel.repaint();
        }
    }
}