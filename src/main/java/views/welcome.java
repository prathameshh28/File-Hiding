package views;

import DAO.userDAO;
import model.User;
import service.generateOTP;
import service.sendOTP;
import service.userService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class welcome {
    public void welcomeScreen(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("WELCOME TO FILE HIDER");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 0 to Exit");
        int choice = 0;
        try{
            choice = Integer.parseInt(br.readLine());
        }catch (IOException ex){
            ex.printStackTrace();
        }
        switch (choice){
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }
    private void login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Email");
        String email = sc.nextLine();
        try{
            if(userDAO.isExist(email)){
                String OTP = generateOTP.getOTP();
                sendOTP.sendOTP(email,OTP);
                System.out.println("Enter OTP");
                String UserOTP = sc.nextLine();
                if(UserOTP.equals(OTP)){
                    new UserView(email,userDAO.getNameViaEmail(email)).home();
                }else{
                    System.out.println("Incorrect OTP");
                }
            }else{
                System.out.println("User Not Exist");
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Name");
        String name = sc.nextLine();
        System.out.println("Enter Email");
        String email = sc.nextLine();
        String OTP = generateOTP.getOTP();
        sendOTP.sendOTP(email,OTP);
        System.out.println("Enter OTP");
        String UserOTP = sc.nextLine();
        if(UserOTP.equals(OTP)){
            User user = new User(name,email);
            int responce = userService.saveUser(user);
            switch (responce){
                case 0 -> System.out.println("User already registered");
                case 1 -> System.out.println("User Registered");
            }
        }else{
            System.out.println("Incorrect OTP");
        }
    }
}
