package collections;

import java.util.LinkedList;
import java.util.Queue;
public class CollectionQueueProbation {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);

        System.out.println("Elements in queue (adding): " + queue);

        System.out.println("Queue iteration:");
        for (Integer num : queue) {
            System.out.print(num + " ");
        }

        System.out.println("\nRemoving elements from queue:");
        while (!queue.isEmpty()) {
            System.out.print(queue.poll() + " ");
        }
    }
}
