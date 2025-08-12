package my.labs.code.signal.banksystem;

public class ScheduledPayment {
    String paymentId;
    String fromAccountId;
    String toAccountId;
    int amount;
    long executionTime;

    public ScheduledPayment(String paymentId,String fromAccountId, String toAccountId, int amount, long executionTime){
        this.paymentId = paymentId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.executionTime = executionTime;
    }
}
