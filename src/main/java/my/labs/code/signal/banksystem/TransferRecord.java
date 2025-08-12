package my.labs.code.signal.banksystem;

public class TransferRecord {
    String fromAccountId;
    String toAccountId;
    int amount;
    long transferTimestamp;

    public TransferRecord(String fromAccountId,String toAccountId, int amount, long transferTimestamp) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transferTimestamp = transferTimestamp;
    }
}
