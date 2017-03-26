package week2;

import edu.princeton.cs.algs4.In;

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        In in = new In(args[2]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!in.isEmpty()) {
            String str = in.readString();
            queue.enqueue(str);
        }
        for (int i=0; i<k && !queue.isEmpty(); i++) {
            System.out.println(queue.dequeue());
        }
    }
}


