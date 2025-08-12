package my.labs.code.signal.banksystem;

public class Spender {
    String accountId;
    int totalSpent;

    Spender(String accountId,int totalSpent) {
        this.accountId = accountId;
        this.totalSpent = totalSpent;
    }

    @Override
    public String toString() {
        return this.accountId + "(" + totalSpent + ")";
    }
}
