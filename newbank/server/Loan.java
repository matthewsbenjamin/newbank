package newbank.server;

public class Loan {
  /** 
   * Represents a monthly amortized loan format
   * where the 
   */
  private Double initialBalance;
  private Double principal;
  private Double periodicity; // number of payments per year
  private Double nPayments; // total number of payments
  private Double interestRate; // Annual interest rate as a decimal (actual, compounded rate)
  private Double periodPayment; // Payment per period
  private Integer term; // total length of credit agreement (years)

  public Loan(Double ib, String p, Double ir, Integer t) {
    this.initialBalance = ib;
    this.interestRate = ir;
    this.term = t;

    switch (p) {
      case "Y":
        this.periodicity = 1.0;
        break;
      case "M": 
        this.periodicity = 12.0;
        break;
      case "W":
        this.periodicity = 52.0;
        break;
      default: 
        throw new Error("Periodicity value is not recognised");
    }

    this.principal = this.initialBalance * Math.pow( 1 + this.interestRate, this.term);
    this.nPayments = term * this.periodicity;
    this.periodPayment = this.principal / this.nPayments; // This may well be wrong

    return;
  }
  
  public Double OutstandingBalance() {
    return this.principal;
  }

  public Double PaymentAmount() {
    return this.periodPayment;
  }

  public void MakePayment(Double amount) {
    this.principal -= amount;
    return;
  }

  public boolean isValid() {
    // TODO - returns whether the agreement is valid
    return true;
  }
}
