import java.util.*;

public class PageReplacement {
    public static void main(String[] args) {
        // 固定页面号引用串和内存块数
        String[] refString = {"3", "2", "1", "0", "3", "2", "4", "3", "2", "1", "0", "4"};
        int numFrames = 4;

        // 计算并输出 FIFO 页面置换次数和缺页率
        int fifoPageFaults = fifoPageReplacement(refString, numFrames);
        System.out.println("FIFO 页面置换次数: " + fifoPageFaults);
        System.out.println("FIFO 缺页率: " + (float)fifoPageFaults / refString.length);

        // 计算并输出 LRU 页面置换次数和缺页率
        int lruPageFaults = lruPageReplacement(refString, numFrames);
        System.out.println("LRU 页面置换次数: " + lruPageFaults);
        System.out.println("LRU 缺页率: " + (float)lruPageFaults / refString.length);
    }

    // FIFO 页面置换算法
    public static int fifoPageReplacement(String[] refString, int numFrames) {
        Queue<String> frame = new LinkedList<>();
        Set<String> set = new HashSet<>();
        int pageFaults = 0;

        for (String page : refString) {
            if (!set.contains(page)) {
                if (frame.size() == numFrames) {
                    String removed = frame.poll();
                    set.remove(removed);
                }
                frame.offer(page);
                set.add(page);
                pageFaults++;
            }
        }

        return pageFaults;
    }

    // LRU 页面置换算法
    public static int lruPageReplacement(String[] refString, int numFrames) {
        List<String> frame = new ArrayList<>();
        int pageFaults = 0;

        for (String page : refString) {
            if (!frame.contains(page)) {
                if (frame.size() == numFrames) {
                    int lruIndex = findLRU(frame, refString, frame.size());
                    frame.set(lruIndex, page);
                } else {
                    frame.add(page);
                }
                pageFaults++;
            }
        }

        return pageFaults;
    }

    // 查找最近最少使用的页面索引
    public static int findLRU(List<String> frame, String[] refString, int n) {
        int lruIndex = n;
        int farthest = frame.size();

        for (int i = 0; i < frame.size(); i++) {
            int j;
            for (j = n - 1; j >= 0; j--) {
                if (frame.get(i).equals(refString[j])) {
                    if (j < farthest) {
                        farthest = j;
                        lruIndex = i;
                    }
                    break;
                }
            }
            if (j == -1)
                return i;
        }

        return lruIndex;
    }
}
