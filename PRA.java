import java.util.*;

class PageReplacementAlgorithm {

    public static void main(String[] args) {
        // 给定页面号引用串
        int[] referenceString = {3, 2, 1, 0, 3, 2, 4, 3, 2, 1, 0, 4};
        // 分配的内存块数
        int numFrames = 4;

        System.out.println("FIFO:");
        // 使用 FIFO 页面置换算法，并计算页面置换次数和缺页率
        int fifoPageFaults = fifoPageReplacement(referenceString, numFrames);
        System.out.println("Page faults: " + fifoPageFaults);
        System.out.println("Page fault rate: " + (float)fifoPageFaults / referenceString.length);

        System.out.println("\nLRU:");
        // 使用 LRU 页面置换算法，并计算页面置换次数和缺页率
        int lruPageFaults = lruPageReplacement(referenceString, numFrames);
        System.out.println("Page faults: " + lruPageFaults);
        System.out.println("Page fault rate: " + (float)lruPageFaults / referenceString.length);
    }

    // FIFO 页面置换算法
    public static int fifoPageReplacement(int[] referenceString, int numFrames) {
        // 使用队列作为内存块，先进先出
        Queue<Integer> frame = new LinkedList<>();
        // 使用集合记录当前内存块中的页面
        Set<Integer> frameSet = new HashSet<>();
        // 页面置换次数
        int pageFaults = 0;

        // 遍历页面引用串
        for (int page : referenceString) {
            // 如果当前页面不在内存块中
            if (!frameSet.contains(page)) {
                // 如果内存块已满，需要置换页面
                if (frame.size() == numFrames) {
                    // 移除队列头部的页面，即最先进入的页面
                    int removedPage = frame.poll();
                    // 从集合中移除相应的页面
                    frameSet.remove(removedPage);
                }
                // 将当前页面加入队列尾部，并更新集合
                frame.offer(page);
                frameSet.add(page);
                // 页面置换次数加一
                pageFaults++;
            }
        }

        return pageFaults;
    }

    // LRU 页面置换算法
    public static int lruPageReplacement(int[] referenceString, int numFrames) {
        // 使用链表作为内存块，最近最少使用的页面位于链表头部
        List<Integer> frame = new LinkedList<>();
        // 页面置换次数
        int pageFaults = 0;

        // 遍历页面引用串
        for (int page : referenceString) {
            // 如果当前页面不在内存块中
            if (!frame.contains(page)) {
                // 如果内存块已满，需要置换页面
                if (frame.size() == numFrames) {
                    // 移除链表头部的页面，即最久未使用的页面
                    frame.remove(0);
                }
                // 将当前页面加入链表尾部
                frame.add(page);
                // 页面置换次数加一
                pageFaults++;
            } else {
                // 如果当前页面已在内存块中，则移除原位置，并加入链表尾部表示最近使用
                frame.remove(Integer.valueOf(page));
                frame.add(page);
            }
        }

        return pageFaults;
    }
}
