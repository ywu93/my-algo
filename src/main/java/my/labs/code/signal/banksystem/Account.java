package my.labs.code.signal.banksystem;

import java.util.NavigableMap;
import java.util.TreeMap;

public class Account {
    String accountId;
    int balance;
    long createdAt;
    long updatedAt;

    NavigableMap<Long, Integer> balanceHistory = new TreeMap<>();

    public Account(String accountId, int balance,long timestamp){
        this.accountId = accountId;
        this.balance = balance;
        this. createdAt = timestamp;
        this.updatedAt = timestamp;
        balanceHistory.put(timestamp, balance);
    }


    public void addAmount(int amount,long timestamp){
        this.balance = this.balance + amount;
        this.updatedAt = timestamp;
        balanceHistory.put(timestamp, this.balance);
    }

    public int getBalance(long queryTime) {
       Integer balance =  balanceHistory.floorEntry(queryTime).getValue();
       if (balance == null ){
           return 0;
       }
       return balance;
    }

}
