package newbank.server;

import java.util.ArrayList;


import static newbank.server.MD5.getMd5;
public class Customer {
	
	private String DEFAULT_ACCOUNT_NAME = "Default";
	private ArrayList<Account> accounts;
	private String password; // store hashed pass
	
	public Customer(String password) {
		accounts = new ArrayList<>();
		accounts.add(new Account(DEFAULT_ACCOUNT_NAME, 0));
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
		if (this.password.equals(md5Pass)) { return true; }
		else {return false;}
	}

	public double getBalance(Account account) {
		return account.getBalance();
	}

	public boolean transfer(Double amount, String sourceId, String destId) {
		//This structure looks dangerous if only 1 of the 2 accounts is found you could create money
		Account acc1 = null;
		Account acc2 = null;
		int check = 0;
		for (Account account : accounts) {
			if (account.name().equals(sourceId)) {
				acc1 = account;
				//account.addFunds(-amount);
				check++;
			}
			if (account.name().equals(destId)) {
				acc2 = account;
				//account.addFunds(amount);
				check++;
			}
		}
		if (check == 2){
			acc1.addFunds(-amount);
			acc2.addFunds(+amount);
			return true;
		}
		return false;
	}

	public boolean accountNameTaken(String account){
		for(Account a : accounts) {
			if (a.name().equals(account)){
				return true;
			}
		}
		return false;
	}

	public boolean pay(Double amount) {
		Account acc;
		for (Account account : accounts) {
			if (account.name().equals(DEFAULT_ACCOUNT_NAME)) {
				acc = account;
			}
		}

		try {
			// acc will always be initialised because the default account 
			// is created on instantiation
			acc.addFunds(amount);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
