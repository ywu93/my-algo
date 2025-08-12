package my.labs.code.signal.banksystem;

public class PaymentRecord {
    String fromAccount;
    String toAccount;
    int amount;
    long timestamp;

    public PaymentRecord(String fromAccount,String toAccount,int amount, long timestamp) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
