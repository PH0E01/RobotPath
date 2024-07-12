import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

        public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

        public static void main(String[] args){
            int numThreads = 1000;
            Thread[] threads = new Thread[numThreads];

            for(int i = 0; i < numThreads; i++) {
                threads[i] = new Thread(() -> {
                    String route = generateRoute("RLRFR", 100);
                    int count = countTurns(route);
                    updateSizeToFreq(count);
                    System.out.println("Thread: " + Thread.currentThread().getId() + ", Count: " + count);

                });
                threads[i].start();
            }

            for(Thread thread : threads){
                try {
                    thread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
printSizeToFreq();
        }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countTurns (String route) {
            int count = 0;
            for(char c : route.toCharArray()){
                if(c == 'R') {
                    count++;
                }
            }
            return count;
    }

    public static synchronized void updateSizeToFreq(int count){
            sizeToFreq.put(count,sizeToFreq.getOrDefault(count,0) +1);
    }

    public static void printSizeToFreq(){
            int maxCount = 0;
            int maxFrequency = 0;

        System.out.println("Frequencies: ");

        for(Map.Entry<Integer,Integer> entry : sizeToFreq.entrySet()){
            int count = entry.getKey();
            int frequency = entry.getValue();

            if (frequency > maxFrequency){
                maxFrequency = frequency;
                maxCount = count;
            }

            System.out.println("- " + count + "(" + frequency + "times)");
        }

        System.out.println("Most frequent count: " + maxCount + " (occurred " + maxFrequency + " times)");
    }


}
