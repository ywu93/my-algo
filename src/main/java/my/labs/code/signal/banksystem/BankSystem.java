package my.labs.code.signal.banksystem;

import java.util.*;

public class BankSystem {
    private final Map<String,Account> accountMap = new HashMap<String,Account>();
    private final List<TransferRecord> transferRecordList = new ArrayList<>();

    private final Map<String,ScheduledPayment> scheduledPaymentMap = new HashMap<>();

    public boolean createAccount(String accountId, long timestamp) {
        if (accountId == null || accountId.isEmpty()){
            return false;
        }
        if (accountMap.containsKey(accountId)){
            return false;
        }
        Account account = new Account(accountId,0, timestamp);
        accountMap.put(accountId, account);
        return true;
    }

    public boolean deposit(String accountId, int amount, long timestamp){
        boolean existing = validateAccount(accountId);
        if (!existing) {
            return existing;
        }
        Account account = accountMap.get(accountId);
        account.addAmount(amount, timestamp);
        return true;
    }

    public boolean transfer (String fromAccountId, String toAccountId, int amount, int timestamp) {
        boolean fromExisting = validateAccount(fromAccountId);
        boolean toExisting = validateAccount(toAccountId);
        boolean validAmount = amount > 0;
        if (!fromExisting || !toExisting || !validAmount) {
            return false;
        }
        Account fromAccount = accountMap.get(fromAccountId);
        fromAccount.addAmount(- amount, timestamp);
        Account toAccount = accountMap.get(toAccountId);
        toAccount.addAmount(amount, timestamp);
        transferRecordList.add(new TransferRecord(fromAccountId, toAccountId, amount, timestamp));
        return true;
    }

    public List<String> topSenders(long timestamp, int n){
        Map<String,Integer> topSendersMap = new HashMap<>();
        for (TransferRecord record: transferRecordList){
            if (record.occurredAt <= timestamp){
                topSendersMap.put(record.fromAccountId,
                        topSendersMap.getOrDefault(record.fromAccountId, 0) + record.amount);
            }
        }
        return topSendersMap.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = b.getValue().compareTo(a.getValue()); // spend desc
                    if (cmp == 0) {
                        return a.getKey().compareTo(b.getKey()); // accountId asc
                    }
                    return cmp;
                })
                .limit(n)
                .map(e -> e.getKey() + "(" + e.getValue() + ")")
                .toList();
    }

    public String schedulePayment(String fromAccountId,String toAccountId, int amount, long timestamp, long delay) {
        boolean fromExisting = validateAccount(fromAccountId);
        boolean toExisting = validateAccount(toAccountId);
        if (!fromExisting || !toExisting || amount <= 0) {
            throw new RuntimeException("Invalid account or amount");
        }
        long executionTime = System.currentTimeMillis() + delay;
        String paymentId = UUID.randomUUID().toString();
        ScheduledPayment scheduledPayment = new ScheduledPayment(paymentId, fromAccountId, toAccountId, amount, executionTime);
        scheduledPaymentMap.put(paymentId, scheduledPayment);
        return paymentId;
    }

    public ScheduledPayment cancelScheduledPayment(String paymentId) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new RuntimeException("Invalid payment ID");
        }
        if (!scheduledPaymentMap.containsKey(paymentId)){
            return null;
        }
        return scheduledPaymentMap.remove(paymentId);
    }

    public int getBalance(String accountId,long timeAt){
        if (!validateAccount(accountId)){
            throw new RuntimeException("Invalid account ID");
        }
        Account account = accountMap.get(accountId);
        return account.getBalance(timeAt);
    }

    boolean validateAccount(String accountId) {
        if (accountId == null || accountId.isEmpty()
                || !accountMap.containsKey(accountId)){
            return false;
        }
        return true;
    }


    public void mergeAccount (String targetAccountId, String sourceAccountId){
        if (!validateAccount(targetAccountId) || !validateAccount(sourceAccountId)) {
            throw new RuntimeException("Invalid account ID");
        }
        Account targetAccount = accountMap.get(targetAccountId);
        Account sourceAccount = accountMap.get(sourceAccountId);
        // 遍历 sourceAccount 的每个快照
        for (Map.Entry<Long, Integer> sourceEntry : sourceAccount.balanceHistory.entrySet()) {
            long ts = sourceEntry.getKey();
            int sourceBalance = sourceEntry.getValue();

            // 合并该时间点余额
            int targetBalanceAtTs = targetAccount.getBalance(ts);
            targetAccount.balanceHistory.put(ts, targetBalanceAtTs + sourceBalance);

            // 更新 ts 之后的所有快照
            Long nextKey = targetAccount.balanceHistory.higherKey(ts);
            while (nextKey != null) {
                targetAccount.balanceHistory.put(
                        nextKey,
                        targetAccount.balanceHistory.get(nextKey) + sourceBalance
                );
                nextKey = targetAccount.balanceHistory.higherKey(nextKey);
            }
        }
        accountMap.remove(sourceAccountId);
    }

    public static void main(String[] args) {
//        BankSystem bank = new BankSystem();
//
//        // 创建账户
//        System.out.println("Create acc1: " + bank.createAccount("acc1", System.currentTimeMillis()));  // true
//        System.out.println("Create acc2: " + bank.createAccount("acc2", System.currentTimeMillis()));  // true
//        System.out.println("Create acc1 again: " + bank.createAccount("acc1", System.currentTimeMillis()));  // false
//
//        // 存钱
//        System.out.println("Deposit 1000 to acc1: " + bank.deposit("acc1", 1000, System.currentTimeMillis()));  // true
//        System.out.println("Deposit 500 to acc3 (not exist): " + bank.deposit("acc3", 500, System.currentTimeMillis()));  // false
//
//        // 转账
//        System.out.println("Transfer 300 from acc1 to acc2: " + bank.transfer("acc1", "acc2", 300, System.currentTimeMillis()));  // true
//        System.out.println("Transfer 100 from acc2 to acc3 (not exist): " + bank.transfer("acc2", "acc3", 100, System.currentTimeMillis()));  // false
//        System.out.println("Transfer -50 from acc1 to acc2: " + bank.transfer("acc1", "acc2", -50, System.currentTimeMillis()));  // false
//
//        // 查看余额
//        System.out.println("Balance acc1: " + bank.getAccount("acc1").getBalance());  // 应该是 700 (1000 - 300)
//        System.out.println("Balance acc2: " + bank.getAccount("acc2").getBalance());  // 应该是 300
//        BankSystem bank = new BankSystem();
//
//        // 创建账户
//        bank.createAccount("A", 1);
//        bank.createAccount("B", 1);
//        bank.createAccount("C", 1);
//
//        // 充值
//        bank.deposit("A", 500, 1);
//        bank.deposit("B", 500, 1);
//        bank.deposit("C", 500, 1);
//
//        // 转账记录
//        bank.transfer("A", "B", 100, 2); // A spend 100
//        bank.transfer("A", "C", 200, 3); // A spend 200 -> total 300
//        bank.transfer("B", "C", 150, 4); // B spend 150
//        bank.transfer("B", "A", 50, 5);  // B spend +50 -> total 200
//        bank.transfer("C", "A", 400, 6); // C spend 400
//
//        // 测试不同时间
//        System.out.println("Top senders at ts=3, n=2: " + bank.topSenders(3, 2));
//        System.out.println("Top senders at ts=5, n=2: " + bank.topSenders(5, 2));
//        System.out.println("Top senders at ts=10, n=3: " + bank.topSenders(10, 3));
        BankSystem bank = new BankSystem();

        // Create two accounts
        bank.createAccount("A", 1L); // timestamp = 1
        bank.createAccount("B", 1L);

        // Simulate balance history
        Account accountA = bank.accountMap.get("A");
        Account accountB = bank.accountMap.get("B");

        // Add snapshots for A: time 1 -> 20, time 3 -> 50
        accountA.addAmount(20,1L);
        accountA.addAmount(50,3L);

        // Add snapshots for B: time 2 -> 10, time 4 -> 40
        accountB.addAmount(10,2L);
        accountB.addAmount(40,4L);

        System.out.println("Before merge:");
        System.out.println("A history: " + accountA.balanceHistory);
        System.out.println("B history: " + accountB.balanceHistory);

        // Merge B into A
        bank.mergeAccount("A", "B");

        System.out.println("\nAfter merge:");
        System.out.println("A history: " + accountA.balanceHistory);
        System.out.println("B exists? " + bank.accountMap.containsKey("B"));

    }

    public Account getAccount(String accountId) {
        return accountMap.get(accountId);
    }
}
