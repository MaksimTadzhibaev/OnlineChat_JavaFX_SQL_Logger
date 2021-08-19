package server;


import java.sql.*;
import java.util.logging.Logger;

public class SimpleAuthServise implements AuthService {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement psGetNick;
    private static PreparedStatement psRegistration;
    private static PreparedStatement psChangeNick;
    private static Logger logger = Logger.getLogger(SimpleAuthServise.class.getName());

    public static boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat", "root", "182824");
            statement = connection.createStatement();
            prepareStatements();
            logger.info("Connected!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
            logger.info("Disconnected!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void prepareStatements() throws SQLException {
        psGetNick = connection.prepareStatement("SELECT nick FROM list_chat WHERE login = ? AND password = ?;");
        psRegistration = connection.prepareStatement("INSERT INTO list_chat (login, password, nick) VALUES (?, ?, ?);");
        psChangeNick = connection.prepareStatement("UPDATE list_chat SET nick = ? WHERE nick = ?;");

    }
//    private static class UserData {
//        String login;
//        String password;
//        String nickname;
//
//        public UserData(String login, String password, String nickname) {
//            this.login = login;
//            this.password = password;
//            this.nickname = nickname;
//        }
//    }

//    private List<UserData> users;
//
//    public SimpleAuthServise() {
//        users = new ArrayList<>();
//        users.add(new UserData("qwe", "qwe", "qwe"));
//        users.add(new UserData("asd", "asd", "asd"));
//        users.add(new UserData("zxc", "zxc", "zxc"));
//
//        for (int i = 1; i < 10; i++) {
//            users.add(new UserData("login" + i, "pass" + i, "nick" + i));
//        }
//    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try {
            psGetNick.setString(1, login);
            psGetNick.setString(2, password);
            ResultSet rs = psGetNick.executeQuery();

            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }
//        for (UserData user : users) {
//            if(user.login.equals(login) && user.password.equals(password)){
//                return user.nickname;
//            }
//        }
//
//        return null;
//    }

    @Override
    public boolean registration(String login, String password, String nick) {
        try {
            psRegistration.setString(1, login);
            psRegistration.setString(2, password);
            psRegistration.setString(3, nick);
            psRegistration.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
//        for (UserData user : users) {
//            if(user.login.equals(login) || user.nickname.equals(nickname)){
//                return false;
//            }
//        }
//
//        users.add(new UserData(login, password, nickname));
//        return true;
//    }
//}

    public boolean changeNick(String nick, String newNick) {
        try {
            psChangeNick.setString(1, newNick);
            psChangeNick.setString(2, nick);
            psChangeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}