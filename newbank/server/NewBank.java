package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	private HashMap<String, Loan> creditAgreements;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	private void addTestData() {
		Customer bhagy = new Customer("pass1");
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer("pass2");
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer("pass3");
		john.addAccount(new Account("Checking", 250.0));
		john.addAccount(new Account("Savings", 9999.0)); //Check if we can see more than one account
		customers.put("John", john);
	}
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		// Need to check password!! //
		if(customers.containsKey(userName)) {
			Customer temp = customers.get(userName);
			if (temp.checkPassword(password)) {
				return new CustomerID(userName);
			}
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			// Split the input by space into 'COMMAND' ...
			String[] commands = request.split(" ");

			// Commands 0 should equal the command input to the system
			// 1... may be any arguments applicable to that command
			switch(commands[0]) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			case "NEWACCOUNT" : return addAccount(customer, commands);
			case "MOVE" : return transfer(customer, commands);
			case "LOAN" : return loan(customer, commands);
			case "PAY" : return pay(customer, commands); //NS: feels wrong
			case "ADDFUNDS" : return deposit(customer, commands);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String pay(CustomerID customer, String[] account) {
		// PAY <Person/Company> <Ammount>
		CustomerID payeeCustomerId = new CustomerID(account[1]);
		Customer payeeCustomer = customers.get(payeeCustomerId.toString());
		Customer payingCustomer = customers.get(customer.getKey());

		Double amount = Double.parseDouble(account[2]);
		// Cannot pay negative or zero amounts
		if (amount <= 0) {
			return "FAIL";
		}

		if (payeeCustomer != null && payingCustomer != null) {
			return payeeCustomer.pay(amount) && payingCustomer.pay(-amount) ? "SUCCESS" : "FAIL";
		} 
		return "FAIL";
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	// For the customerID add a new account....
	private String addAccount(CustomerID customer, String[] account){
		try {
			Customer customerTemp = customers.get(customer.getKey());
			if (account == null) {
				return "FAIL: missing name for account";
			}

			if (customerTemp.accountNameTaken(account[1])) {
				return "FAIL: account exists";
			}
			Account tempAccount = new Account(account[1], 0);
			(customers.get(customer.getKey())).addAccount(tempAccount);
			return "SUCCEED";
		}
		catch (Exception e){
			return "FAIL (unknown)";
		}
	}

	private String deposit(CustomerID customer, String[] account){
		try {
			Customer customerTemp = customers.get(customer.getKey());
			if (account == null) {
				return "FAIL: paramters";
			}

			if (!customerTemp.accountNameTaken(account[1])) {
				return "FAIL: account does not exist";
			}
			Account accountTemp = customerTemp.getAccount(account[1]);

			Double amount = Double.parseDouble(account[2]);

			if (amount < 0 ) {
				return "FAIL: incorrect amount";
			}

			return accountTemp.addFunds(amount);

		}
		catch (Exception e){
			return "FAIL (unknown)";
		}
	}

	private String transfer(CustomerID customerId, String[] commands) {
		boolean transferSuccess;
		Double amountToMove = Double.parseDouble(commands[1]);
		String sourceAccountName = commands[2];
		String destinationAccountName = commands[3];

		Customer customer = customers.get(customerId.getKey());
		transferSuccess = customer.transfer(amountToMove, sourceAccountName, destinationAccountName);
		if (transferSuccess) {
			return "SUCCESS";
		}
		return "FAIL";
	}

	private String loan(CustomerID customerId, String[] commands) {
		/** 
		 * Set up a new loan
		 * 
		 */
		try {
			Double amount = Double.parseDouble(commands[1]);
			Integer term = Integer.parseInt(commands[2]);
			CreditAgreement agreement = new CreditAgreement(amount);
			Loan loan = new Loan(amount, "M", agreement.GetAgreement(), term);

	
			if (loan.isValid()) {
				creditAgreements.put(customerId.getKey(), loan);
				customers.get(customerId.getKey()).pay(amount);
				return "SUCCESS";
			}
			return "FAIL";
		}
		catch (Exception e){
			return "LOAN FAIL";
		}
	}
}
