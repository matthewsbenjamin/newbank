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

	public void addFunds(Double amount) {
		Double newBalance = this.balance + amount;
		if (newBalance >= 0) {
			this.balance = newBalance;
		};
		return;
	}

}
