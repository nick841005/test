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
		
		//�N�s�b�ɮת��|����ƶפJ�{��
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
		
		//��ܥ\��ﶵ�A�����ϥΪ̿��
		System.out.print("1.Register\n2.Login\n3.Forget Password\nChoose: ");
		try{
			keyIn = new Scanner(System.in);
			switch(keyIn.nextInt()) {
				case 1:
					//�n�D�ϥΪ̿�J�b���B�K�X��K�X����
					//�b���P�K�X�����u�]�t�^��μƦr�åB���צb6��20�Ӧr������
					keyIn = new Scanner(System.in);
					System.out.println("The format of account and password must only include digital or letter,"
							+ "\nand the number of account and password must be from 6 to 20 in length\nPlease enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password: ");
					password = keyIn.nextLine();
					System.out.println("The format of hint is unlimited\nPlease enter password hint: ");
					hint = keyIn.nextLine();
					
					//���P�_�b�����׬O�_�b6��20�Ӧr������
					//�A�P�_�b���O�_�u�t�^��μƦr
					//�̫�P�_�b���O�_����
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
					
					//���P�_�K�X���׬O�_�b6��20�Ӧr������
					//�A�P�_�K�X�O�_�u�t�^��μƦr
					if(lengthCheck(password)) {
						if(!password.matches("[a-zA-Z0-9|\\.]+")) {
							System.out.println("Format of password is incorrect!");
							System.exit(0);
						}
					} else {
						System.out.println("Length of password is incorrect!");
						System.exit(0);
					}
					
					//�N�ŦX�W�h���ϥΪ̱b��[��ArrayList�P�ɮ�
					String[] temp2 = {account, password, hint};
					userList.add(temp2);
					
					FileWriter fw = new FileWriter("User.txt", true);
					fw.write(account + " " + password + " " + hint + "\r\n");
	                fw.flush();
	                fw.close();
	                System.out.println("Register successfully!");
					break;
				case 2:
					//�n�D�ϥΪ̿�J�b���P�K�X
					keyIn = new Scanner(System.in);
					System.out.println("Please enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password: ");
					password = keyIn.nextLine();
					
					//�P�_�ϥΪ̿�J���b���P�K�X�O�_���T
					//���T�N��ܵn�J���\�A�Ϥ��N��ܵn�J����
					if(loginCheck(account, password)) {
						System.out.println("Login successfully!");
					} else {
						System.out.println("Login failed!");
					}
					break;
				case 3:
					//�n�D�ϥΪ̿�J�b���P�K�X����
					keyIn = new Scanner(System.in);
					System.out.println("Please enter account: ");
					account = keyIn.nextLine();
					System.out.println("Please enter password hint: ");
					hint = keyIn.nextLine();
					
					//�P�_�ϥΪ̿�J���b���P�K�X���ܬO�_���T
					//���T�N��ܱK�X�A�Ϥ���ܱb���αK�X���ܿ�J���~
					String string = hintCheck(account, hint);
					if(string.compareTo("not found") == 0) {
						System.out.println("Account or hint is incorrect!");
					} else {
						System.out.println("Your password is: " + string);
					}
					break;
				default:
					//��J�\��H�~���ﶵ��ܿ�J���~
					System.out.println("Input error!!!");
			}
		}catch(Exception e) {
			//��J�D�Ʀr���r����ܿ�J���~
			System.out.println("Input error!!!");
		}
	}
	
	//�P�_�b���αK�X���׬O�_�b6��20�Ӧr������
	public static boolean lengthCheck(String string) {
		boolean flag = false;
		
		if(string.length() >= 6 && string.length() <= 20) {
			flag = true;
		}
		
		return flag;
	}
	
	//�P�_�b���O�_����
	public static boolean repeatCheck(String account) {
		boolean flag = false;
		
		for(int i = 0;!flag && i < userList.size(); i++) {
			if(userList.get(i)[0].compareToIgnoreCase(account) == 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	//�P�_�O�_�n�J���\
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
	
	//�P�_�b���P�K�X���ܬO�_��J���T�A�ìd�߱K�X
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