package my.labs.leetcode;

import java.util.*;

public class InsertDeleteGetRandom_lc380 {

    private List<Integer> randomList = new ArrayList<>();
    private Map<Integer,Integer>  pos = new HashMap<>();
    private final Random rand= new Random();



    public boolean insert(int val) {
        if(pos.containsKey(val)){
            return false;
        }
        int addPos = randomList.size();
        randomList.add(addPos,val);
        pos.put(val,addPos);
        return true;
    }

    public boolean remove(int val) {
        if (!pos.containsKey(val)) {
            return false;
        }
        Integer lastValue = randomList.get(randomList.size() -1);
        int valPos = pos.get(val);
        randomList.set(valPos,lastValue);
        pos.put(lastValue,valPos);

        randomList.remove(randomList.size() -1);
        pos.remove(val);
        return true;
    }

    public int getRandom() {
        return randomList.get(rand.nextInt(randomList.size()));
    }

    public static void main(String[] args){
        InsertDeleteGetRandom_lc380 randomSet = new InsertDeleteGetRandom_lc380();
        boolean result1 =  randomSet.insert(1);
        System.out.println(result1);
        boolean result2 = randomSet.insert(1);
        System.out.println(result2);
        System.out.println(randomSet.insert(3));
        System.out.println(randomSet.insert(4));
        System.out.println(randomSet.insert(5));
        System.out.println(randomSet.remove(1));
        System.out.println(randomSet.getRandomList());
        System.out.println(randomSet.getRandom());
        System.out.println(randomSet.getRandom());
    }

    private List<Integer> getRandomList() {
        return randomList;
    }
}
