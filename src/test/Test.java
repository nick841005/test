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
		
		//將存在檔案的會員資料匯入程式
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
		
		//顯示功能選項，並讓使用者選取
		System.out.print("1.Register\n2.Login\n3.Forget Password\nChoose: ");
		try{
			keyIn = new Scanner(System.in);
			switch(keyIn.nextInt()) {
				case 1:
					//要求使用者輸入帳號、密碼跟密碼提示
					//帳號與密碼必須只包含英文或數字並且長度在6到20個字元之間
					keyIn = new Scanner(System.in);
					System.out.println("The format of account and password must only include digital or letter,"
							+ "\nand the number of account and password must be from 6 to 20 in length\nPlease enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password: ");
					password = keyIn.nextLine();
					System.out.println("The format of hint is unlimited\nPlease enter password hint: ");
					hint = keyIn.nextLine();
					
					//先判斷帳號長度是否在6到20個字元之間
					//再判斷帳號是否只含英文或數字
					//最後判斷帳號是否重複
					if(lengthCheck(account)) {
						if(!account.matches("[a-zA-Z0-9|\\.]+")) {
							System.out.println("Format of account is incorrect!");
							System.exit(0);
						}
						
						if(repeatCheck(account)) {
							System.out.println("Account is repeated!");
							System.exit(0);
						}
					} else {
						System.out.println("Length of account is incorrect!");
						System.exit(0);
					}
					
					//先判斷密碼長度是否在6到20個字元之間
					//再判斷密碼是否只含英文或數字
					if(lengthCheck(password)) {
						if(!password.matches("[a-zA-Z0-9|\\.]+")) {
							System.out.println("Format of password is incorrect!");
							System.exit(0);
						}
					} else {
						System.out.println("Length of password is incorrect!");
						System.exit(0);
					}
					
					//將符合規則的使用者帳戶加到ArrayList與檔案
					String[] temp2 = {account, password, hint};
					userList.add(temp2);
					
					FileWriter fw = new FileWriter("User.txt", true);
					fw.write(account + " " + password + " " + hint + "\r\n");
	                fw.flush();
	                fw.close();
	                System.out.println("Register successfully!");
					break;
				case 2:
					//要求使用者輸入帳號與密碼
					keyIn = new Scanner(System.in);
					System.out.println("Please enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password: ");
					password = keyIn.nextLine();
					
					//判斷使用者輸入的帳號與密碼是否正確
					//正確就顯示登入成功，反之就顯示登入失敗
					if(loginCheck(account, password)) {
						System.out.println("Login successfully!");
					} else {
						System.out.println("Login failed!");
					}
					break;
				case 3:
					//要求使用者輸入帳號與密碼提示
					keyIn = new Scanner(System.in);
					System.out.println("Please enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password hint: ");
					hint = keyIn.nextLine();
					
					//判斷使用者輸入的帳號與密碼提示是否正確
					//正確就顯示密碼，反之顯示帳號或密碼提示輸入錯誤
					String string = hintCheck(account, hint);
					if(string.compareTo("not found") == 0) {
						System.out.println("Account or hint is incorrect!");
					} else {
						System.out.println("Your password is: " + string);
					}
					break;
				default:
					//輸入功能以外的選項顯示輸入錯誤
					System.out.println("Input error!!!");
			}
		}catch(Exception e) {
			//輸入非數字的字元顯示輸入錯誤
			System.out.println("Input error!!!");
		}
	}
	
	//判斷帳號或密碼長度是否在6到20個字元之間
	public static boolean lengthCheck(String string) {
		boolean flag = false;
		
		if(string.length() >= 6 && string.length() <= 20) {
			flag = true;
		}
		
		return flag;
	}
	
	//判斷帳號是否重複
	public static boolean repeatCheck(String account) {
		boolean flag = false;
		
		for(int i = 0;!flag && i < userList.size(); i++) {
			if(userList.get(i)[0].compareToIgnoreCase(account) == 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	//判斷是否登入成功
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
	
	//判斷帳號與密碼提示是否輸入正確，並查詢密碼
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