package com.simplilearn.lockedme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
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
		
		//checks if file is created 
		if(users.createNewFile()) {
			filecheck = true;
			System.out.println(filecheck);
		}
		write = new PrintWriter(new FileWriter(users,true));	
	}
	
	//function to terminate JVM
	public static void exit() {
		System.out.println("Exiting...");
		System.out.println("Thank you for using LockedMe!");
		System.exit(0);
	}
	
	//welcome screen
	public static void welcomeScreen() throws IOException {
		System.out.println();
		System.out.println("************************************");
		System.out.println("*        Welcome to LockedMe       *");
		System.out.println("*   Your personal digital locker   *");
		System.out.println("*       Developed by Ashwin R      *");
		System.out.println("************************************");
		System.out.println();
	}
	
	//Home screen options
	public static void loginOptions() throws IOException {
		System.out.println("------------------------------------");
		System.out.println("                Home                ");
		System.out.println("------------------------------------");
		System.out.println("1. Register");
		System.out.println("2. User Login");
		System.out.println("3. Admin login");
		System.out.println("4. Exit");
		try {
			scan = new Scanner(System.in);
			int choice = scan.nextInt();		
			switch(choice) {
			case 1: register();
			break;
			case 2 : login();
			break;
			case 3 : adminLogin();
			break;
			case 4 : exit();
			break;
			
			default : System.out.println("Invalid entry");		//if user enters value other than 1,2,3,4
			break;
			}
		}
		
		//if user enters value other than integer
		catch(InputMismatchException ie) { 
			System.out.println();
			System.out.println("Input Mismatch, check your entry");
			System.out.println();
			loginOptions();
		}
		scan.close();
	}

	public static void login() throws IOException{
		System.out.println("------------------------------------");
		System.out.println("               Login                ");
		System.out.println("------------------------------------");
		scan = new Scanner(System.in);
		System.out.println("Enter Username :");
		String inpUsername = scan.next();
		Scanner pwdCheck = new Scanner(users);
		boolean found = false;
		//Login validation
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
		System.out.println("------------------------------------");
		System.out.println("              Register              ");
		System.out.println("------------------------------------");
		Scanner scan = new Scanner(System.in);
		FileWriter fw = new FileWriter(users, true);
	    Scanner usernameCheck = new Scanner(users);
	    String user = "";
	    while (user.equals("")) {
	    	System.out.println("Enter username ");
	    	System.out.println("(Note: The username you provide is case sensitive)");
	        user = scan.nextLine().trim();
	        if(user.equals("")) {
		    	System.err.println("Username cannot be empty! Enter y to retry or any other character to exit");
		    	try {
					char c = scan.next().charAt(0);
					if(c == 'y') {
						register();
					}
					else {
						exit();
					}
				} catch (InputMismatchException e) {
					System.err.println("\nInput mismatch, you entered an invalid input");
					exit();
				}
		    }
	        
	        //validation to check if username exits
	        
	        else {
		        while (usernameCheck.hasNextLine()) {
		            String existingUsername = usernameCheck.nextLine().trim();
		            //userlist.add(existingUsername);
		            try {
			            if (user.equalsIgnoreCase(existingUsername)) {
			                System.err.println("Username already exists! Enter y to retry or any other character to exit");
			                char c = scan.next().charAt(0);
			                switch(c) {
			                case 'y' : register();
			                			break;
			                default : exit();
			                }
			                
			                user = "";
			                break;
			            }
		            }
		            catch(InputMismatchException ie) {
		            	System.err.println("Please enter valid input");
		            }
		        }
	        }
	    }
	    usernameCheck.close(); 
	    String pass = "";
	    while (pass.equals("")) {
	        System.out.println("Enter password");
	        pass = scan.nextLine();
	        if (pass.equals("")) {
	            System.err.println("Password is mandatory to secure your locker!!!\n");
	            System.err.println("Enter password");
	            pass = scan.nextLine();
	        }
	    }
	    //push the values(username and password) to text file
	    writeToFile(user, pass);
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
		System.out.println("------------------------------------");
		System.out.println("             Admin Login            ");
		System.out.println("------------------------------------");
		Scanner scan = new Scanner(System.in);
		//Admin credentials
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
			System.err.println("Press enter to go back to homescreen");
			try{ 
				System.in.read();
				welcomeScreen();
				loginOptions();
			} catch (Exception e) {	
				e.printStackTrace();
			}
			
		}
		scan.close();
		
	}
	
	public static void adminChoice() throws IOException{
		System.out.println("------------------------------------");
		System.out.println("           Welcome Admin            ");
		System.out.println("------------------------------------");
		System.out.println("1: Display users");
		System.out.println("2: Delete user");
		System.out.println("3: Exit to homescreen");
		try{
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			switch(choice) {
			case 1 : displayUsers();
			break;
			case 2 : deleteUser();
			break;
			case 3 : welcomeScreen();
			break;
			default : System.out.println("Invalid entry...");
			scan.close();
			}
		}
		catch(InputMismatchException ie) {
			System.err.println("Please enter valid input");
		}
	}
	
	public static void displayUsers() throws IOException {
		List<String> userList = new ArrayList<String>();
		File[] files = new File("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\Digilocker").listFiles();
		
		for(File file : files ) {
			if(file.isFile()) {
				//to remove extension of the filename using regex
				String fileNameWithOutExt = file.getName().replaceFirst("[.][^.]+$", "");   
				userList.add(fileNameWithOutExt);
			}
		}
		Collections.sort(userList);
		System.out.println("List of active users :");
		System.out.println("----------------------");
		Iterator<String> itr = userList.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
	
	public static void deleteUser() throws IOException {
		System.out.println("Enter username of the user that is to be deleted");
		scan = new Scanner(System.in);
		String uname = scan.next();
		File dir = new File("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\Digilocker");
		//File[] dir_contents = dir.listFiles();
		String temp = uname + ".txt";
		boolean check = new File(dir, temp).exists();  
		if(check) {
			System.out.println("User found");
			File file = new File("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\Digilocker\\"+temp);
			if (file.delete()) {
                System.out.println("User deleted!");
            } else {
                System.out.println("Sorry, unable to delete the user. Retry");
            }
		}
		else {
			System.out.println("User not found");
			System.out.println("Enter y to retry or any other character to exit");
			String c = scan.next();
			switch(c) {
            case "y" : deleteUser();
            			break;
            default : exit();
            }
		}
		
		
	}
	
	//function to write into text file
	public static void writeToFile(String username, String password) throws IOException {
		   write.write(username + System.lineSeparator());
		   write.write(password + System.lineSeparator());
		   write.write("\n");
		   write.close();
	}
	
	//personal locker function
	public static void lockedMe(String username) throws IOException {
		System.out.println("------------------------------------");
		System.out.println("         Welcome "+username+"        ");
		System.out.println("------------------------------------");
		user = new File("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\DigiLocker\\"+username+".txt");
		if(user.createNewFile()) {
			System.out.println("Personal Locker created!");
		}
		System.out.println("1 : Fetch stored credentials");
		System.out.println("2 : Store new data");
		Scanner scan = new Scanner(System.in);
		try {
			int choice = scan.nextInt();
			switch(choice) {
			case 1 : displayData(username);
			break;
			case 2 : addData(username);
			break;
			default : System.out.println("Inavlid input");
			break;
			}
		}catch(InputMismatchException ie) {
			System.out.println("Invalid input type");
		}
		scan.close();
	}
	
	//this function adds data to the locker
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
		System.out.println("Press Enter to return to homescreen");
		try{ 
			System.in.read();
			welcomeScreen();
			loginOptions();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		add.close(); 
	}
	
	public static void displayData(String username) throws IOException {
		InputStream ipStream = new FileInputStream("C:\\Users\\AsP\\Phase1-workspace\\FileHandlingtest\\Users\\DigiLocker\\"+username+".txt");
		Scanner lockerScan = new Scanner(ipStream);
		StringBuffer sb = new StringBuffer();
		if(!lockerScan.hasNext()) {
				System.out.println("Your locker is empty, go on and store some before you lose");
		}else {
				while(lockerScan.hasNextLine()) {
				// System.out.println(lockerInput.hasNext());
				sb.append(lockerScan.nextLine()+"\n");
			}
			System.out.println("Hey "+username+", welcome to your digital locker!");
			System.out.println();
			System.out.println(sb);
		}
		System.out.println("Press Enter to return to homescreen");
		try{ 
			System.in.read();
			welcomeScreen();
			loginOptions();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		lockerScan.close();
	}

	public static void main(String[] args) throws IOException {
		fileInit();
		welcomeScreen();
		loginOptions();
	}

}
