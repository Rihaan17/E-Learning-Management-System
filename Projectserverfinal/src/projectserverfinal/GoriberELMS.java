/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectserverfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.lang.*;

/**
 *
 * @author Hridoy Saha
 */
public class GoriberELMS {
    
    static String[] str;
    static String Returnvalue = "false"; //just in case login is not accpeted the value is false :(
    static String aa;

    public GoriberELMS(String in) {
        str = in.split(",");  // the string received from input message is split into the str array.
    }

    String start() {
        switch (str[0]) {
            case "1": {
                aa = userchecker(str[1], str[2]); // str[0] is the parameter that triggers the switch case, once accepted the value is sent for processing in userchecker method.
                return aa;// returns processed string
            }
            case "2": {
                aa = afterlogin(str[1]); // str[0] is the parameter that triggers the switch case,and the rest is sent inside the method as paramenter.
                return aa;// returns processed string
            }
            case "3":
            {
                aa = insidecourse(str[1],str[2]);// same stuff as the above comments.
                return aa;
            }
        }
        return aa;//just incase the code is re-edited returns null if anything other than 123 is input as switch trigger. 
    }

    public static void main(String[] args) {
        System.out.println(aa);// checking return String
    }

    public static String userchecker(String a, String b) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// here comes the real pera 1 din e shiksi :3
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
            Statement stmt = con.createStatement();//idk what it its probably connects to the database sending pass and user
            ResultSet rs = stmt.executeQuery("select * from student");// result-set stores the raw  data from database
            int i = 0;
            while (rs.next()) {

                if ((a.equalsIgnoreCase(rs.getString(1))) && (b.equals(rs.getString(2)))) { // caps and non caps are checked in ignorecase
                    System.out.println(rs.getString(1) + " " + rs.getString(2));//prints whats being checked
                    Returnvalue = "true\n";// if pass matches else it remains false
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Returnvalue+"\n";//false is retruned from here if the "IF" block denies access.
    }

    public static String afterlogin(String id) {// subject , id 
        ResultSet rs;
        String btn = "";// initially the buttons have just a space displayed.
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "admin");
            Statement stmt = con.createStatement();
            System.out.println("select * from FinalEnrolled where S_ID='" + id + "'");
            rs = stmt.executeQuery("select * from FinalEnrolled where S_ID='" + id + "'");
            //data base connection code ^.
            while (rs.next()) {
                btn += rs.getString(1);// storing subs by student.
                btn += ",";
            }

        } catch (Exception e) {

        }
        return btn + "\n";// this stuff took me 3 hrous long to understand and finally got help for this "\n" 
    }
    public String insidecourse(String cid,String sid){// counselling and remarks.
        String str="";
        ResultSet rs,rs2;// 2 rs for 2 queries never took the risk of making 1.
        String hrs;
        try{  
                Class.forName("oracle.jdbc.driver.OracleDriver");  
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","admin");  
            Statement stmt=con.createStatement();  
            System.out.println("select * from COURSES where C_ID='"+cid+"'");
            rs=stmt.executeQuery("select * from COURSES where C_ID='"+cid+"'");  
            int i = 0;
             while(rs.next()){
                 str+=rs.getString(3);// converting raw message into string array.
                 str+=",";// 3 number column contains counsellinghours.
             }
             rs2=stmt.executeQuery("select * from FINALREMARK where (S_ID='"+sid+"') AND (C_ID='"+cid+"')");
             System.out.println("select * from FINALREMARK where (S_ID='"+sid+"') AND (C_ID='"+cid+"')");// just checking whats being sent to query as AND statement was added to see if anything happens
             while(rs2.next()){
                 str+=rs2.getString(1);// raw to array
                 str+=",";// 1no column contains course name for example OOPB
             }
              
            }catch(Exception e){
                e.printStackTrace();
            }
        return str+"\n";// returning string.
    }
}
