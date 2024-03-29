package sprint;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import java.io.IOException;

//java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class userRegis {
private int underWriterId;
private String username;
    private String dob;
    private String doj;
    private String password;

 userRegis(int underWriterId,String username, String dob, String password, String doj)
{
this.underWriterId=underWriterId;
        this.username = username;
        this.dob = dob;
        this.password = password;
        this.doj = doj;
    }
}

class vehicle {
private int policyNo;
    private String vehicleNo;
    private String vehicleType;//2-wheeler/4-wheeler
    private String customerName;
    private int engineNo;
    private int chasisNo;
   
    private String type;
    private double premiumAmt;// auto calculated from type of insurance

    private int underWriterId;// autocapture by the system with underwriter id
   
public vehicle(int policyNo, String vehicleNo, String vehicleType, String customerName, int engineNo, int chasisNo,
String type, double premiumAmt,  int underWriterId) {
this.policyNo = policyNo;
this.vehicleNo = vehicleNo;
this.vehicleType = vehicleType;
this.customerName = customerName;
this.engineNo = engineNo;
this.chasisNo = chasisNo;
this.type = type;
this.premiumAmt = premiumAmt;
this.underWriterId = underWriterId;
}

}


public class Sprint {
static String url = "jdbc:sqlite:D:\\Users\\2474623\\MySQLiteDB";
static Connection conn = null;

public static Connection getJDBCConnection() throws IOException, SQLException {


// Load and register the driver
try {
Class.forName("org.sqlite.JDBC");
conn = DriverManager.getConnection(url);
if (conn != null) {
//System.out.println("done");
return conn;
}
} catch (ClassNotFoundException e) {
// TODO: handle exception
System.out.println("You not loaded the driver");
e.printStackTrace();
}

// get the connection
System.out.println("not done");
return null;

}
static int selectRole() {
Scanner scanner = new Scanner(System.in);
    System.out.println("Select your role:");
    System.out.println("1. Admin");
    System.out.println("2. UnderWriter");
    return scanner.nextInt();
}

static void adminLogin() throws IOException, SQLException {
Scanner scanner = new Scanner(System.in);
    System.out.println("Admin login:");
    System.out.println("Enter default username:");
    String username = scanner.next();
    System.out.println("Enter default password:");
    String password = scanner.next();
    if (username.equals("admin") && password.equals("admin123")) {
       adminMenu();
    } else {
        System.out.println("Invalid username or password.");
    }
}
static void underWriterLogin() throws IOException, SQLException {
Scanner scanner = new Scanner(System.in);
System.out.println("UnderWriter login:");
System.out.println("Enter UnderWriterId:");
int id = scanner.nextInt();scanner.nextLine();
     
System.out.println("Enter default password:");
String password = scanner.nextLine();
int flag=0;
try {
conn = DriverManager.getConnection(url);
System.out.println("Connection Succesful");
if(conn!=null) {
String upadteSQL = "SELECT UserId,Password from UnderWriter";
PreparedStatement pstmt = conn.prepareStatement(upadteSQL);
ResultSet rst = pstmt.executeQuery();
while(rst.next()) {
if(rst.getInt("UserId")==id && rst.getString("Password")==password) {
     underWriterMenu();
     flag=1;
     break;
}        
}
     rst.close();
     pstmt.close();
     conn.close();
}
}
     catch(SQLException e) {
     e.printStackTrace();
}
if (flag==0) {
System.out.println("Invalid username or password.");
}
       
       
    System.out.println("Enter default userid:");
    int id = scanner.nextInt();
    System.out.println("Enter default password:");
    String password = scanner.next();
    if (id==1 && password.equals("pass")) {
    underWriterMenu();
    } else {
        System.out.println("Invalid username or password.");
    }
}
static void adminMenu() throws IOException, SQLException {
Scanner scanner = new Scanner(System.in);
    int choice;
    do {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. UnderWriter Registration");
        System.out.println("2. Search UnderWriter by Id");
        System.out.println("3. Update UnderWriter password");
        System.out.println("4. Delete UnderWriter by Id");
        System.out.println("5. View All UnderWriters");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                insertCustomer();
                break;
            case 2:
            System.out.println("Enter Id to search");
            int uuid = scanner.nextInt();scanner.nextLine();
                 searchUnderWriterById(uuid);
                break;
            case 3:
            try {
            System.out.println("Enter the Id you want to Update");
            int uid = scanner.nextInt();scanner.nextLine();
            System.out.println("Enter the new Password");
            String pass = scanner.nextLine();
                int ans = updateUnderWriterPassword(pass , uid);
                if(ans>0) {
                System.out.println("Password update succesful and new password is:"+pass);
            }
            else {
            System.out.println("Id not Found");
            }
              }
            catch(Exception e) {
            e.printStackTrace();
            System.out.println("Password update unsuccessful");
            }
                break;
            case 4:
            System.out.println("Enter the Id to delete");
            int id = scanner.nextInt();
                boolean ans = deleteUnderWriterById(id);
                if(ans) {
                System.out.println("Id:"+id+"deleted");
                }
                else {
                System.out.println("User not found to delete");
                }
               
                break;
            case 5:
                viewAllUnderWriters();
                break;
            case 6:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    } while (choice != 6);
}
static void underWriterMenu() throws IOException, SQLException {
Scanner scanner = new Scanner(System.in);
    int choice;
    do {
    System.out.println("\nUnderWriter Menu:");
            System.out.println("1. Create new Vehicle Insurance");
            System.out.println("2. View Policy based on Policy No");
            System.out.println("3. View Policy based on UnderWriterId");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                insertVehicle();
                break;
case 2:
System.out.println("Enter policy no to search");
int policyNo = scanner.nextInt();scanner.nextLine();
viewPolicyByPolicyNo(policyNo);
break;
case 3:
System.out.println("Enter UnderWriterID to search");
int underWriterID = scanner.nextInt();scanner.nextLine();
viewPolicyByUnderWriterID(underWriterID);
break;
case 4:
System.out.println("Exiting...");
break;
default:
System.out.println("Invalid choice.");
        }
    } while (choice != 4);
}

private static void insertVehicle() {
// TODO Auto-generated method stub
Scanner scanner = new Scanner(System.in);

    // Consume newline left-over

System.out.println("Enter Policy Number:");
      int policyNo =scanner.nextInt(); scanner.nextLine();
     System.out.println("Enter Vehicle Number:");
      String vehicleNo = scanner.next();
      System.out.println("Enter Vehicle Type (2-wheeler/4-wheeler):");
      String vehicleType = scanner.next();
      System.out.println("Enter Customer Name:");
      String customerName = scanner.next();
      System.out.println("Enter Engine Number:");
      int engineNo = scanner.nextInt();
      System.out.println("Enter Chassis Number:");
      int chassisNo = scanner.nextInt();
     
      scanner.nextLine(); // Consume newline character
     
      System.out.println("Enter Insurance Type (Full Insurance/Third Party):");
      String type = scanner.nextLine();
     
     

      double premiumAmt;
      if(type.equalsIgnoreCase("Full Insurance")) {
      premiumAmt=1000.0;
      }
      else {
      premiumAmt=2000.0;
      }

      int underWriterId =1;

String sqlQuery = "INSERT INTO Vehicle (PolicyNo, VehicleNo, VehicleType, CustomerName,EngineNo,ChasisNo,Type,PremiumAmt,UnderWriterId) VALUES (?, ?, ?, ?,?, ?, ?, ?, ?);";


try {
conn = getJDBCConnection();// get the connection

PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

        // Set values for parameters
        pstmt.setInt(1, policyNo);
        pstmt.setString(2, vehicleNo);
        pstmt.setString(3, vehicleType);
        pstmt.setString(4, customerName);
        pstmt.setInt(5, engineNo);
        pstmt.setInt(6, chassisNo);
       
        pstmt.setString(7, type);
        pstmt.setDouble(8, premiumAmt);

        pstmt.setInt(9, underWriterId);
       
int b = pstmt.executeUpdate();
if (b >= 0) {

System.out.println(" Vehicle Data saved Succesfully");
} else {
System.out.println("Unsucessful insertion of data");
}
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
}

}

// create table
public static void createTable() throws IOException {
String sqlQuery = "CREATE TABLE IF NOT EXISTS UnderWriter (UserId INTEGER PRIMARY KEY AUTOINCREMENT,"
+ " Username VARCHAR(255), Dob VARCHAR(255), Doj VARCHAR(255), "
+ "Password VARCHAR(255))";

try {
conn = getJDBCConnection();// get the connection

Statement stmt = conn.createStatement();
int b = stmt.executeUpdate(sqlQuery);
if (b >= 0) {

System.out.println("Table created Succesfully");
} else {
System.out.println("Unsucessful Table Creation");
}
} catch (SQLException e) {
// TODO: handle exception
e.printStackTrace();
}

}
// create table
public static void createVehicle() throws IOException {
String sqlQuery = "CREATE TABLE IF NOT EXISTS Vehicle (PolicyNo INTEGER ,"
+ " VehicleNo VARCHAR(255), VehicleType VARCHAR(255), CustomerName VARCHAR(255), EngineNo INTEGER, ChasisNo INTEGER,Type VARCHAR(255),  "
+ "PremiumAmt DOUBLE, UnderWriterId INTEGER)";

try {
conn = getJDBCConnection();// get the connection

Statement stmt = conn.createStatement();
int b = stmt.executeUpdate(sqlQuery);
if (b >= 0) {

System.out.println("Table created Succesfully");
} else {
System.out.println("Unsucessful Table Creation");
}
} catch (SQLException e) {
// TODO: handle exception
e.printStackTrace();
}

}
public static void searchUnderWriterById(int id) {
try {
conn = DriverManager.getConnection(url);
System.out.println("Connection Succesful");
if(conn!=null) {
String upadteSQL = "SELECT * from UnderWriter WHERE UserId = ?";
PreparedStatement pstmt = conn.prepareStatement(upadteSQL);
pstmt.setInt(1, id);
ResultSet rst = pstmt.executeQuery();
if(rst.next()) {
int uid = rst.getInt(1);
String name= rst.getString(2);
String dob = rst.getString(3);
String doj = rst.getString(4);
String pass = rst.getString(5);


System.out.println("Search Succesfull");
System.out.println("UserId:"+uid);
System.out.println("User Name:"+name);
System.out.println("Date of Birth:"+dob);
System.out.println("Date of Joining"+doj);
System.out.println("Password:"+pass);
}
else {
System.out.println("No record found with id:"+id);
}
rst.close();
pstmt.close();
conn.close();
}
}
catch(SQLException e) {
e.printStackTrace();
}
}
public static void viewPolicyByPolicyNo(int policyNo) {
try {
conn = DriverManager.getConnection(url);
System.out.println("Connection Succesful");
if(conn!=null) {
String upadteSQL = "SELECT * from Vehicle WHERE PolicyNo = ?";
PreparedStatement pstmt = conn.prepareStatement(upadteSQL);
pstmt.setInt(1, policyNo);
ResultSet rst = pstmt.executeQuery();
if(rst.next()) {
int policyNo = rst.getInt(1);
String vehicleNo= rst.getString(2);
String vehicleType = rst.getString(3);
String customerName = rst.getString(4);
int engineNo = rst.getInt(5);
int chasisNo = rst.getInt(6);
String type = rst.getString(7);
double premiumAmt = rst.getDouble(8);
int underWriterId = rst.getInt(9);



System.out.println("View Policy Succesfull");
System.out.println("policyNo:"+policyNo);
System.out.println("vehicleNo:"+vehicleNo);
System.out.println("customerName:"+customerName);
System.out.println("engineNo"+engineNo);
System.out.println("chasisNo:"+chasisNo);
System.out.println("type:"+type);
System.out.println("premiumAmt:"+premiumAmt);
System.out.println("underWriterId:"+underWriterId);
}
else {
System.out.println("No record found with policyNo:"+policyNo);
}
rst.close();
pstmt.close();
conn.close();
}
}
catch(SQLException e) {
e.printStackTrace();
}
}
public static void viewAllUnderWriters() {
try {
conn = DriverManager.getConnection(url);
System.out.println("Connection Succesful");
if(conn!=null) {
String upadteSQL = "SELECT * from UnderWriter";
PreparedStatement pstmt = conn.prepareStatement(upadteSQL);
ResultSet rst = pstmt.executeQuery();
while(rst.next()) {
int uid = rst.getInt(1);
String name= rst.getString(2);
String dob = rst.getString(3);
String doj = rst.getString(4);
String pass = rst.getString(5);
System.out.println("Search Succesfull");
System.out.println("UserId:"+uid);
System.out.println("User Name:"+name);
System.out.println("Date of Birth:"+dob);
System.out.println("Date of Joining"+doj);
System.out.println("Password:"+pass);
}
rst.close();
pstmt.close();
conn.close();
}
}
catch(SQLException e) {
e.printStackTrace();
}
}

public static void viewPolicyByUnderWriterID(int underWriterID) {
try {
conn = DriverManager.getConnection(url);
System.out.println("Connection Succesful");
if(conn!=null) {
String upadteSQL = "SELECT * from Vehicle WHERE UnderWriterId = ?";
PreparedStatement pstmt = conn.prepareStatement(upadteSQL);
ResultSet rst = pstmt.executeQuery();
while(rst.next()) {
int policyNo = rst.getInt(1);
String vehicleNo= rst.getString(2);
String vehicleType = rst.getString(3);
String customerName = rst.getString(4);
int engineNo = rst.getInt(5);
int chasisNo = rst.getInt(6);
String type = rst.getString(7);
double premiumAmt = rst.getDouble(8);
int underWriterId = rst.getInt(9);



System.out.println("View Policy Succesfull");
System.out.println("policyNo:"+policyNo);
System.out.println("vehicleNo:"+vehicleNo);
System.out.println("customerName:"+customerName);
System.out.println("engineNo"+engineNo);
System.out.println("chasisNo:"+chasisNo);
System.out.println("type:"+type);
System.out.println("premiumAmt:"+premiumAmt);
System.out.println("underWriterId:"+underWriterId);
}
rst.close();
pstmt.close();
conn.close();
}
}
catch(SQLException e) {
e.printStackTrace();
}
}


public static boolean deleteUnderWriterById(int id) {
int rowsAffected = 0;
try {
conn = DriverManager.getConnection(url);
System.out.println("Connection Succesfull");
if(conn!= null) {
String updateSQL = "DELETE FROM UnderWriter WHERE UserId = ?";
PreparedStatement pstmt = conn.prepareStatement(updateSQL);
pstmt.setInt(1, id);
rowsAffected = pstmt.executeUpdate();
pstmt.close();
conn.close();
}
}
catch(SQLException e) {
e.printStackTrace();
}
return rowsAffected>0;
}
public static int updateUnderWriterPassword(String pass, int id){
int rowsAffected = 0;
try {
conn = DriverManager.getConnection(url);
System.out.println("Connection Successful");
if(conn!=null) {
String updateSQL = "UPDATE UnderWriter SET Password = ? WHERE UserId = ?";
PreparedStatement pstmt = conn.prepareStatement(updateSQL);
pstmt.setString(1,pass);
pstmt.setInt(2, id);
rowsAffected = pstmt.executeUpdate();
// System.out.println(rowsAffected);
pstmt.close();
conn.close();
}
}
catch (SQLException e){
e.printStackTrace();
}
return rowsAffected;
}
// insert customer details if not present
public static void insertCustomer() throws IOException
{
Scanner scanner = new Scanner(System.in);

    // Consume newline left-over

    System.out.println("Enter Name:");
    String name = scanner.nextLine();

    System.out.println("Enter Date of Birth:");
    String dob = scanner.nextLine();
   

    System.out.println("Enter Joining Date:");
     String doj = scanner.nextLine();
   

    System.out.println("Enter Password:");
    String pass = scanner.nextLine();
   

String sqlQuery = "INSERT INTO UnderWriter (Username, Dob, Doj, Password) VALUES (?, ?, ?, ?);";


try {
conn = getJDBCConnection();// get the connection

PreparedStatement pstmt = conn.prepareStatement(sqlQuery);

        // Set values for parameters
        pstmt.setString(1, name);
        pstmt.setString(2, dob);
        pstmt.setString(3, doj);
        pstmt.setString(4, pass);
       
int b = pstmt.executeUpdate();
if (b >= 0) {

System.out.println(" UnderWriter Data saved Succesfully");
} else {
System.out.println("Unsucessful insertion of data");
}
} catch (Exception e) {
// TODO: handle exception
e.printStackTrace();
}

}

public static void main(String[] args) throws IOException, SQLException {
createTable();
createVehicle();
System.out.println("Welcome to Star Protect Vehicle System!");
int role = selectRole();
switch (role) {
    case 1:
        adminLogin();
        break;
    case 2:
        underWriterLogin();
        break;
    default:
        System.out.println("Invalid role selected.");
}
}

}


