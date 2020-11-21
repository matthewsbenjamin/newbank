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
		String choice = null;
		String command = null;
		if(customers.containsKey(customer.getKey())) {
			String[] temp = request.split(" ");
			int pars = temp.length;
			if (pars>0) {  choice = temp[0]; }
			if (pars>1) {  command = temp[1]; }
			else {  command = null;}

			switch(choice) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			case "NEWACCOUNT" : return addAccount(customer, command);
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
		if (account == null) { return "missing name for account"; }
		Account tempAccount = new Account(account, 0);
		(customers.get(customer.getKey())).addAccount(tempAccount);
		return "DONE";
	}


}
