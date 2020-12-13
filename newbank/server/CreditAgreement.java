package newbank.server;

public class CreditAgreement {
  private Double interestRate;
  private static Double coeff = -0.434;
  private static Double libor = 1.3;

  public CreditAgreement(Double amount) {
    /**
     * Calculate the interest rate
     * Ask for agreement acceptance
     * 
     * if not accepted, terminate,
     * 
     * else success 
     */
    this.interestRate = coeff * Math.log(amount) + libor;
    this.interestRate = Math.max(0, libor);

  }

  public Double GetAgreement() {
    return this.interestRate;
  }
}
