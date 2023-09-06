package views;


import DAO.dataDAO;
import model.data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    private String name;
    UserView(String email,String name){
        this.email=email;
        this.name=name;
    }
    public void home(){
       do {
           System.out.println("WELCOME "+name);
           System.out.println("Press 1 to Show Hidden Files");
           System.out.println("Press 2 to Hide File");
           System.out.println("Press 3 to Unhide File");
           System.out.println("Press 0 to exit");
           Scanner sc = new Scanner(System.in);
           int choice = Integer.parseInt(sc.nextLine());
           switch (choice){
               case 1 -> {
                   try {
                       List<data> files = dataDAO.getAllFiles(this.email);
                       System.out.println("ID - FILE NAME");
                       for(data file : files){
                           System.out.println(file.getId()+" - "+file.getFileName());
                       }
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
               case 2 -> {
                   System.out.println("Enter the File Path");
                   String path = sc.nextLine();
                   File f = new File(path);
                   data file = new data(0,f.getName(),path,this.email);
                   try {
                       dataDAO.hideFile(file);
                   } catch (SQLException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               case 3 -> {
                   List<data> files = null;
                   try {
                   files = dataDAO.getAllFiles(this.email);

                   System.out.println("ID - FILE NAME");
                   for(data file : files){
                       System.out.println(file.getId()+" - "+file.getFileName());
                   }
                   System.out.println("Enter The ID of file to UnHide");
                   int id = Integer.parseInt(sc.nextLine());
                   boolean isValidID = false;
                   for(data file : files){
                       if(file.getId()==id){
                           isValidID = true;
                           break;
                       }
                   }
                   if(isValidID){
                       dataDAO.unHide(id);
                   }else{
                       System.out.println("Wrong ID");
                   }
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }catch (IOException e){
                       e.printStackTrace();
                   }
               }
               case 0 -> {
                   System.exit(0);
               }
           }
       }while(true);
    }
}
