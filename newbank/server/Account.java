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

	public double getBalance() {
		return this.balance;
	}

	public String name() {
		return accountName;
	}

	public String addFunds(Double amount) {
		this.balance = this.balance + amount;
		return "SUCCESS";
	}

}
