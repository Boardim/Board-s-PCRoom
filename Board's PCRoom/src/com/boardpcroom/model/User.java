package com.boardpcroom.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String password;
    private String name;
    private int point;
    private int time;
    private int seatedID;
    private boolean isUsed;

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public User() {
        this("", "", "");
    }

    public User(String id, String pw, String name) {
        this.id = id;
        this.password = pw;
        this.name = name;
        point = 0;
        time = 0;
        seatedID = 0;
        isUsed = false;
    }

    public int getSeatedID() {
        return seatedID;
    }

    public void setSeatedID(int seatedID) {
        this.seatedID = seatedID;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    //ex : "id,pw,name,~,~,~,~" -> to CSV
    private static User makeUser(String[] content) {
        User user = new User();
        user.setId(content[0]);
        user.setPassword(content[1]);
        user.setName(content[2]);
        user.setPoint(Integer.parseInt(content[3]));
        user.setTime(Integer.parseInt(content[4]));
        user.setSeatedID(Integer.parseInt(content[5]));
        user.setUsed(Boolean.parseBoolean(content[6]));
        return user;
    }

    //CSV file
    private static final File file = new File("csv/user.csv");
    public static List<User> readCSV() {
        ArrayList<User>userList = new ArrayList<User>();

        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));
            String line;

            bf.readLine();

            while((line = bf.readLine()) != null) {
                String[] contents = line.split(",");
                userList.add(makeUser(contents));
            }
            bf.close();
        } catch (Exception e) {
            System.out.println("Error");
        }

        return userList;
    }

    //user include -> CSV
    public static void addCSV(User user) {
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(file, true));
            String str = user.getId() + "," + user.getPassword() + "," + user.getName() + "," + user.getPoint() + "," + user.getTime() + "," + user.getSeatedID() + "," + user.isUsed();

            bf.write(str);
            bf.newLine();
            bf.close();
        } catch (IOException e) {
            System.out.println("CSV Input Error");
        }
    }

    //user information update in CSV
    public static void updateCSV(User user) {
        List<User> userList = readCSV();
        boolean found = false;

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId().equals(user.getId())) {
                userList.set(i, user);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("UpdateCSV Error : com.boardpcroom.model.User not Found");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write("QID,QPW,QName,QPoint,QTime,QSeatID,QUsed");
            bw.newLine();


            for (User u : userList) {
                String str = u.getId() + "," +
                        u.getPassword() + "," +
                        u.getName() + "," +
                        u.getPoint() + "," +
                        u.getTime() + "," +
                        u.getSeatedID() + "," +
                        u.isUsed();
                bw.write(str);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("CSV Update Error: " + e.getMessage());
        }
    }

    // user.id == csv's user.id?
    public static boolean isValidate(String id) {
        List<User>allList = readCSV();
        for (User value : allList) {
            if (value.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // id, pow -> csv find user
    public static User getUser(String id, String pw) {
        List<User>allList = readCSV();
        for (User value : allList) {
            if (value.getId().equals(id) && value.getPassword().equals(pw)) {
                return value;
            }
        }
        return null;
    }

    // findPW
    public static String findPW(String id) {
        List<User>allList = readCSV();
        for (User value : allList) {
            if (value.getId().equals(id)) {
                return value.getPassword();
            }
        }
        return "";
    }
}
