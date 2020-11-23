package newbank.server;

public class Account {
	
	private String accountName;
	private double balance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.balance = openingBalance;
	}
	
	public String toString() {
		return (accountName + ": " + balance);
	}

	public String name() {
		return accountName;
	}

	public void addFunds(Double amount) {
		this.balance += amount;
		return;
	}

}
