package service;

import DAO.userDAO;
import model.User;

import java.sql.SQLException;

public class userService {
    public static Integer saveUser(User user){
        try{
            if(userDAO.isExist(user.getEmail())){
                return 0;
            }else{
                return userDAO.saveUser(user);

            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
       return null;
    }
}
