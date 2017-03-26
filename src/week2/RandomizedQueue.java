package week2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;

    private int tail = 0;

    public RandomizedQueue(){
        array = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return tail == 0;
    }

    public int size() {
        return tail;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();
        if (tail == array.length-1)
            resize(2 * (tail+1));
        array[tail++] = item;
    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(tail);
        Item temp = array[randIndex];
        array[randIndex] = array[--tail];

        if (tail > 0  && tail == array.length / 4)
            resize(array.length/2);
        return temp;
    }

    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(tail);
        return array[randIndex];
    }

    private void resize(int capacity){
        Item[] copy = (Item []) new Object[capacity];
        for (int i=0; i<tail; i++)
            copy[i] = array[i];
        array = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator<Item>(array);
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {

        private RandomizedQueue<Item> iterQueue;

        public RandomizedQueueIterator(Object[] items) {
            iterQueue = new RandomizedQueue<>();
            for(Object o: items) {
                if (o == null)
                    break;
                iterQueue.enqueue((Item) o);
            }
        }

        @Override
        public boolean hasNext() {
            return !iterQueue.isEmpty();
        }

        @Override
        public Item next() {
            return iterQueue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
