import java.util.*;

public class PageReplacement {
    // FIFO页面置换算法
    public static int fifo(int[] referenceString, int frameCount) {
        Set<Integer> frames = new HashSet<>();
        Queue<Integer> fifoQueue = new LinkedList<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (!frames.contains(page)) {
                if (frames.size() == frameCount) {
                    int removedPage = fifoQueue.poll();
                    frames.remove(removedPage);
                }
                frames.add(page);
                fifoQueue.offer(page);
                pageFaults++;
            }
        }

        return pageFaults;
    }

    // LRU页面置换算法
    public static int lru(int[] referenceString, int frameCount) {
        Set<Integer> frames = new HashSet<>();
        LinkedHashMap<Integer, Integer> lruMap = new LinkedHashMap<>();
        int pageFaults = 0;

        for (int page : referenceString) {
            if (!frames.contains(page)) {
                if (frames.size() == frameCount) {
                    int lruPage = lruMap.entrySet().iterator().next().getKey();
                    frames.remove(lruPage);
                    lruMap.remove(lruPage);
                }
                frames.add(page);
            } else {
                lruMap.remove(page);
            }
            lruMap.put(page, 1);
            pageFaults++;
        }

        return pageFaults;
    }

    public static void main(String[] args) {
        int[] referenceString = {3, 2, 1, 0, 3, 2, 4, 3, 2, 1, 0, 4};
        int frameCount = 3;

        int fifoPageFaults = fifo(referenceString, frameCount);
        double fifoPageFaultRate = (double) fifoPageFaults / referenceString.length * 100;

        int lruPageFaults = lru(referenceString, frameCount);
        double lruPageFaultRate = (double) lruPageFaults / referenceString.length * 100;

        System.out.println("FIFO 页面置换次数：" + fifoPageFaults);
        System.out.printf("FIFO 缺页率：%.2f%%\n", fifoPageFaultRate);
        System.out.println("LRU 页面置换次数：" + lruPageFaults);
        System.out.printf("LRU 缺页率：%.2f%%\n", lruPageFaultRate);
    }
}
