package com.simplilearn.lockedme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;




import com.simplilearn.lockedme.model.Digilocker;


public class LockedMeApp {
	
	static PrintWriter write, add;
	static Scanner scan;
	static File users, user;
	static Digilocker locker;
	public static void fileInit() throws IOException{
		users = new File("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\users.txt");
		boolean filecheck = false;
		if(users.createNewFile()) {
			filecheck = true;
			System.out.println(filecheck);
		}
		
		
		write = new PrintWriter(new FileWriter(users,true));
		
	}
	
	public static void welcomeScreen() throws IOException {
		System.out.println();
		System.out.println("************************************");
		System.out.println("*        Welcome to LockedMe       *");
		System.out.println("*   Your personal digital locker   *");
		System.out.println("*       Developed by Ashwin R      *");
		System.out.println("************************************");
		System.out.println();
	}
	
	public static void loginOptions() throws IOException {
		System.out.println("----------Home-----------");
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. Admin login");
		scan = new Scanner(System.in);
		int choice = scan.nextInt();
		switch(choice) {
		case 1: register();
		break;
		case 2 : login();
		break;
		case 3 : adminLogin();
		break;
		default : System.out.println("Invalid entry, exiting...");
		}
		scan.close();
	}

	public static void login() throws IOException{
		scan = new Scanner(System.in);
		System.out.println("Enter Username :");
		String inpUsername = scan.next();
		Scanner pwdCheck = new Scanner(users);
		boolean found = false;
		while(pwdCheck.hasNext() && !found) {
			if(pwdCheck.next().equals(inpUsername)) {
				System.out.println("Enter Password :");
				String inpPassword = scan.next();
				if(pwdCheck.next().equals(inpPassword)) {
					System.out.println("Login Successful!!!");
					found = true;
					lockedMe(inpUsername);
					break;
				}
				else {
					System.err.println("Wrong password");
				}
			}
		}
		if(!found) {
			System.out.println("User Not Found : Login Failure : 404");
		}
		scan.close();
		pwdCheck.close();
		
	}

	public static void register() throws IOException {
		
		Scanner scan = new Scanner(System.in);
		FileWriter fw = new FileWriter(users, true);
	    Scanner usernameCheck = new Scanner(users);
	    String user = "";
	    while (user.equals("")) {
	    	System.out.println("Enter username ");
	        user = scan.nextLine().trim();
	        while (usernameCheck.hasNextLine()) {
	            String existingUsername = usernameCheck.nextLine().trim();
	            //userlist.add(existingUsername);
	            if (user.equalsIgnoreCase(existingUsername)) {
	                System.err.println("Username already exists! Press y to retry or n to exit\n");
	                char c = scan.next().charAt(0);
	                switch(c) {
	                case 'y' : register();
	                			break;
	                case 'n' : System.out.println("Exiting...");
	                			welcomeScreen();
	                			loginOptions();
	                break;
	                default : System.out.println("Invalid entry");
	                }
	                
	                user = "";
	                break;
	            }
	        }
	    }
	    usernameCheck.close(); // Close the Scanner file object
	    String pass = "";
	    while (pass.equals("")) {
	        System.out.println("Enter password");
	        pass = scan.nextLine();
	        if (pass.equals("")) {
	            System.err.println("Invalid Password! Try Again.\n");
	        }
	    }

	    writeToFile(user, pass);// close the FileWriter object.
	    System.out.println("Account created!");
	    System.out.println("Press enter to continue");
		try{ 
			System.in.read();
			welcomeScreen();
			loginOptions();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		scan.close();
		fw.close();
	}
	
	public static void adminLogin() throws IOException {
		Scanner scan = new Scanner(System.in);
		String adminUsername = "admin";
		String adminPassword = "admin";
		System.out.println("Enter admin username :");
		String aname = scan.next();
		System.out.println("Enter admin password :");
		String apassword = scan.next();
		boolean usernameMatch = aname.equalsIgnoreCase(adminUsername);
		boolean passwordMatch = apassword.equalsIgnoreCase(adminPassword);
		if(usernameMatch == true && passwordMatch == true) {
			System.out.println("Admin Login successful");
			//adminWelcome();
			adminChoice();			
		}
		else {
			System.err.println("Invalid credentials");
			System.out.println("Press enter to go back to homescreen");
			try{ 
				System.in.read();
				welcomeScreen();
				loginOptions();
			} catch (Exception e) {	
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static void adminChoice() throws IOException{
		System.out.println("1: Display users");
		System.out.println("2: Delete user");
		System.out.println("3: Exit to homescreen");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		switch(choice) {
		case 1 : displayUsers();
		break;
		//case 2 : deleteUser();
		//break;
		case 3 : adminLogin();
		break;
		default : System.out.println("Invalid entry...");
		}
		scan.close();
	}
	
	public static void displayUsers() throws IOException {
		List<String> userList = new ArrayList<String>();
		File[] files = new File("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\Digilocker").listFiles();
		
		for(File file : files ) {
			if(file.isFile()) {
				String fileNameWithOutExt = file.getName().replaceFirst("[.][^.]+$", "");
				String formattedString = fileNameWithOutExt
					    .replace(",", "")  //remove the commas
					    .replace("[", "")  //remove the right bracket
					    .replace("]", "")  //remove the left bracket
					    .trim();    
				userList.add(formattedString);
			}
		}
		Collections.sort(userList);
		Iterator<String> itr = userList.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
	
	
	public static void writeToFile(String username, String password) throws IOException {
		   write.write(username + System.lineSeparator());
		   write.write(password + System.lineSeparator());
		   write.write("\n");
		   write.close();
		}
	
	public static void lockedMe(String username) throws IOException {
		user = new File("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\DigiLocker\\"+username+".txt");
		if(user.createNewFile()) {
			System.out.println("Personal Locker created!");
		}
		System.out.println("1 : Fetch stored credentials");
		System.out.println("2 : Store new data");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		switch(choice) {
		case 1 : displayData(username);
			break;
		case 2 : addData(username);
		}
	}
	
	private static void addData(String username) throws IOException {
		System.out.println("Hi "+username+", Welcome to your digital Locker");
		locker = new Digilocker();
		locker.setLoggedInUser(username);
		scan = new Scanner(System.in);
		System.out.println("Enter Site Name :");
		String siteName = scan.next();
		locker.setSiteName(siteName);
		
		System.out.println("Enter Username :");
		String username1 = scan.next();
		locker.setUsername(username1);
		
		System.out.println("Enter Password :");
		String password = scan.next();
		locker.setPassword(password);
		add = new PrintWriter(new FileWriter("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\DigiLocker\\"+username+".txt"));
		add.println("Website : "+locker.getSiteName());
		add.println("Username : "+locker.getUsername());
		add.println("Password : "+locker.getPassword());
		
		System.out.println("New credentials added to the locker!!!");
		add.close(); 
	}
	
	public static void displayData(String username) throws IOException {
		InputStream ipStream = new FileInputStream("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\DigiLocker\\"+username+".txt");
		Scanner lockerScan = new Scanner(ipStream);
		StringBuffer sb = new StringBuffer();
		while(lockerScan.hasNextLine()) {
			// System.out.println(lockerInput.hasNext());
			sb.append(lockerScan.nextLine()+"\n");
		}
		System.out.println("Hey "+username+", welcome to your digital locker!");
		System.out.println();
		System.out.println(sb);
		System.out.println("Press Enter to go back");
		try{ 
			System.in.read();
			welcomeScreen();
			loginOptions();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		fileInit();
		welcomeScreen();
		loginOptions();
	}

}
