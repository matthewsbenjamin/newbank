package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
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
			return new CustomerID(userName);
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
			case "NEWACCOUNT" : return addAccount(customer, commands[1]);
			case "MOVE" : return transfer(customer, commands);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

	// For the customerID add a new account....
	private String addAccount(CustomerID customer, String account){
		if (account == null) { return "FAIL: missing name for account"; }
		Account tempAccount = new Account(account, 0);
		(customers.get(customer.getKey())).addAccount(tempAccount);
		return "SUCCESS";
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

}
