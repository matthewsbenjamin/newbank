package newbank.server;

import java.util.ArrayList;

import static newbank.server.MD5.getMd5;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String password; // store hashed pass
	
	public Customer(String password) {
		accounts = new ArrayList<>();
		this.password = getMd5(password);
	}
	
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString() + "\n"; //new line for multiple account
		}
		return s;
	}

	public void addAccount(Account account) {
		accounts.add(account);		
	}

	/* Add CheckPassword Function */
	public boolean checkPassword(String password) {
		String md5Pass = getMd5(password);
		if (password.equals(md5Pass)) { return true; }
		else {return false;}
	}

}
