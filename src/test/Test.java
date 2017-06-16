package test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	private static ArrayList<String[]> userList  = new ArrayList<String[]>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner keyIn;
		String temp, account, password, hint;
		
		try {
			FileReader fr = new FileReader("User.txt");
			BufferedReader br = new BufferedReader(fr);
			while (br.ready()) {
				temp = br.readLine();
				userList.add(temp.split(" "));
			}
			fr.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.print("1.Register\n2.Login\n3.Forget Password\nChoose: ");
		try{
			keyIn = new Scanner(System.in);
			switch(keyIn.nextInt()) {
				case 1:
					keyIn = new Scanner(System.in);
					System.out.println("The format of account and password must only include digital or letter,\nand the number of account and password must be from 6 to 20 in length\nPlease enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password: ");
					password = keyIn.nextLine();
					System.out.println("The format of hint is unlimited\nPlease enter password hint: ");
					hint = keyIn.nextLine();
					
					if(lengthCheck(account)) {
						if(repeatCheck(account)) {
							System.out.println("Account is repeated!");
							System.exit(0);
						}
						
						if(!account.matches("[a-zA-Z0-9|\\.]+")) {
							System.out.println("Format of account is incorrect!");
							System.exit(0);
						}
					} else {
						System.out.println("Length of account is incorrect!");
						System.exit(0);
					}
					
					if(lengthCheck(password)) {
						if(!password.matches("[a-zA-Z0-9|\\.]*")) {
							System.out.println("Format of password is incorrect!");
							System.exit(0);
						}
					} else {
						System.out.println("Length of password is incorrect!");
						System.exit(0);
					}
					
					String[] temp2 = {account, password, hint};
					userList.add(temp2);
					
					FileWriter fw = new FileWriter("User.txt", true);
					fw.write(account + " " + password + " " + hint + "\r\n");
	                fw.flush();
	                fw.close();
	                System.out.println("Register successfully!");
					break;
				case 2:
					keyIn = new Scanner(System.in);
					System.out.println("Please enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password: ");
					password = keyIn.nextLine();
					
					if(loginCheck(account, password)) {
						System.out.println("Login successfully!");
					} else {
						System.out.println("Login failed!");
					}
					break;
				case 3:
					keyIn = new Scanner(System.in);
					System.out.println("Please enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password hint: ");
					hint = keyIn.nextLine();
					
					String string = hintCheck(account, hint);
					if(string.compareTo("not found") == 0) {
						System.out.println("Account or hint is incorrect!");
					} else {
						System.out.println("Your password is: " + string);
					}
					break;
				default:
					System.out.println("Input error!!!");
			}
		}catch(Exception e) {
			System.out.println("Input error!!!");
		}
	}
	
	public static boolean lengthCheck(String string) {
		boolean flag = false;
		
		if(string.length() >= 6 && string.length() <= 20) {
			flag = true;
		}
		
		return flag;
	}
	
	public static boolean repeatCheck(String account) {
		boolean flag = false;
		
		for(int i = 0;!flag && i < userList.size(); i++) {
			if(userList.get(i)[0].compareToIgnoreCase(account) == 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public static boolean formatCheck(String string) {
		boolean flag = false;
		
		
		return flag;
	}
	
	public static boolean loginCheck(String account, String password) {
		boolean flag = false;
		
		for(int i = 0;!flag && i < userList.size(); i++) {
			if(userList.get(i)[0].compareToIgnoreCase(account) == 0 
					&& userList.get(i)[1].compareTo(password) == 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	public static String hintCheck(String account, String hint) {
		String returnString = "not found";
		
		for(int i = 0;returnString.compareTo("not found") == 0 && i < userList.size(); i++) {
			if(userList.get(i)[0].compareTo(account) == 0 && 
					userList.get(i)[2].compareTo(hint) == 0) {
				returnString = userList.get(i)[1];
			}
		}
		return returnString;
	}
}