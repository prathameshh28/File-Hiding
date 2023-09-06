package DAO;

import database.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
    public static boolean isExist(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select email from users");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String e = rs.getString(1);
            if(e.equals(email)){
                return true;
            }
        }
       return false;
    }
    public static String getNameViaEmail(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("select name from users where email = ?");
        ps.setString(1,email);
        ResultSet rs =  ps.executeQuery();
        while (rs.next()){
            String e = rs.getString(1);
            return e;
        }
       return null;
    }

    public static int saveUser(User user) throws SQLException{
        Connection connection = MyConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert into users values(default ,?, ?)");
        ps.setString(1,user.getName());
        ps.setString(2,user.getEmail());
        return ps.executeUpdate();
    }
}
