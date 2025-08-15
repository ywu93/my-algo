package my.labs.code.signal.banksystem;


import java.util.*;

class BankingSystemV2{
    private final Map<String, Account> accountMap = new HashMap<>();

    private final List<TransferRecord> transferRecords = new ArrayList<>();

    public BankingSystemV2() {
        // TODO: implement
    }

    // TODO: implement interface methods here

    public boolean createAccount(int timestamp, String accountId){
        if (accountId == null || accountId.isBlank()) {
            return false;
        }
        if (accountMap.containsKey(accountId)){
            return false;
        }
        Account account = new Account(accountId, timestamp, timestamp);
        accountMap.put(accountId, account);
        return true;
    }

    public Optional<Integer> deposit(int timestamp, String accountId, int amount) {
        if(!accountMap.containsKey(accountId)){
            return Optional.empty();
        }
        Account account = accountMap.get(accountId);
        account.balance = account.balance + amount;
        account.updatedAt = timestamp;

        return Optional.of(account.balance);
    }

    public Optional<Integer> transfer (int timestamp,String sourceAccountId,String targetAccountId, int amount){
        Account src = accountMap.get(sourceAccountId);
        Account target = accountMap.get(targetAccountId);
        if (src == null || target == null || src.accountId.equals(target.accountId)) {
            return Optional.empty();
        }
        if (src.balance < amount){
            return Optional.empty();
        }
        // transfer
        src.balance = src.balance - amount;
        src.updatedAt = timestamp;
        target.balance = target.balance + amount;
        target.updatedAt = timestamp;
        // add record
        TransferRecord record = new TransferRecord(sourceAccountId, targetAccountId, amount, timestamp);
        transferRecords.add(record);
        return Optional.of(src.balance);
    }

    public List<String> topSenders (int timestamp,int n){
        System.out.print("TransferRecord>>>>>>>>>>>>:"+ transferRecords);
        Map<String, Integer> topSendersMap = new HashMap<>();
        for(Map.Entry<String,Account> entry: accountMap.entrySet()){
            topSendersMap.put(entry.getKey()
                    , 0);
        }
        for (TransferRecord record: transferRecords){
            if(record.occurredAt <= timestamp){
                Integer amount = topSendersMap.get(record.fromAccountId);
                amount = amount + record.amount;
                topSendersMap.put(record.fromAccountId, amount);
            }
        }
        return topSendersMap.entrySet().stream()
                .sorted((a,b) ->{
                    int cmp = b.getValue().compareTo(a.getValue());
                    if (cmp == 0){
                        return a.getKey().compareTo(b.getKey());
                    }
                    return cmp;

                }).limit(n)
                .map (e -> e.getKey() + "(" + e.getValue() + ")").toList();

    }

    public List<TransferRecord> getTransferRecords(){
        return this.transferRecords;
    }
    public static void main(String[] args){
        BankingSystemV2 bank = new BankingSystemV2();
        System.out.println(bank.createAccount(1,"A"));
        System.out.println(bank.createAccount(2,"B"));
        System.out.println(bank.deposit(3,"A",100));
        System.out.println(bank.transfer(4,"A","B",50));
        System.out.println(bank.topSenders(5,2));
    }

}

