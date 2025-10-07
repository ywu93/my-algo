package my.labs.leetcode;

import java.util.*;

public class InsertDeleteGetRandom_lc381 {
    private List<Integer> randomList = new ArrayList<>();
    // value, index set
    private Map<Integer, Set<Integer>> pos = new HashMap<>();
    private final Random rand = new Random();

    public boolean insert(int val) {
        if (!pos.containsKey(val) || pos.get(val).isEmpty()) {
            int index = randomList.size();
            randomList.add(index, val);
            HashSet<Integer> set = new HashSet<>();
            set.add(index);
            pos.put(val, set);
            return true;
        }
        int index = randomList.size();
        Set<Integer> set = pos.get(val);
        randomList.add(index, val);
        set.add(index);
        return false;
    }

    public boolean remove(int val) {
        if (!pos.containsKey(val) || pos.get(val).isEmpty()) {
            return false;
        }
        int index = pos.get(val).iterator().next();
        pos.get(val).remove(index);
        int lastVal = randomList.get(randomList.size() - 1);
        randomList.set(index, lastVal);
        pos.get(lastVal).add(index);
        pos.get(lastVal).remove(randomList.size() - 1);
        randomList.remove(randomList.size() - 1);
        return true;
    }
    public int getRandom() {
        return randomList.get(rand.nextInt(randomList.size()));
    }

    public List<Integer> getRandomList() {
        return randomList;
    }

    public static void main(String[] args){
        InsertDeleteGetRandom_lc381 randomList = new InsertDeleteGetRandom_lc381();
        randomList.insert(1);
        System.out.println(randomList.getRandomList());
        randomList.remove(1);
        System.out.println(randomList.getRandomList());
        randomList.insert(1);
        System.out.println(randomList.getRandomList());
    }
}
