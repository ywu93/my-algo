package my.labs.code.signal.banksystem;

public class TransferRecord {
    String fromAccountId;
    String toAccountId;
    int amount;
    int occurredAt;

    public TransferRecord(String fromAccountId,String toAccountId, int amount, int transferTimestamp) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.occurredAt = transferTimestamp;
    }
}
