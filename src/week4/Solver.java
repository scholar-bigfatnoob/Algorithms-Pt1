package week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private static Comparator<Node> pqComparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.board.manhattan() + o1.moves, o2.board.manhattan() + o2.moves);
        }
    };

    private MinPQ<Node> minPQ;

    private MinPQ<Node> auxMinPQ;

    private  Node solution;

    private class Node {
        private Board board;
        private int moves;
        private Node previous;

        Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }

    public Solver(Board initial) {
        if (initial == null)
            throw new NullPointerException("week4.Board is null");
        Node current = new Node(initial, 0, null);
        minPQ = new MinPQ<>(pqComparator);
        minPQ.insert(current);
        Node twinCurrent = new Node(initial.twin(), 0, null);
        auxMinPQ = new MinPQ<>(pqComparator);
        auxMinPQ.insert(twinCurrent);
    }

    public boolean isSolvable() {
        while (!minPQ.isEmpty()) {
            Node current = minPQ.delMin();
            if (current.board.isGoal()) {
                solution = current;
                return true;
            }
            for (Board board: current.board.neighbors()) {
                if (current.previous != null && current.previous.board.equals(board))
                    continue;
                minPQ.insert(new Node(board, current.moves + 1, current));
            }
            Node twinCurrent = auxMinPQ.delMin();
            if (twinCurrent.board.isGoal())
                return false;
            for (Board board: twinCurrent.board.neighbors()) {
                if (twinCurrent.previous != null && twinCurrent.previous.board.equals(board))
                    continue;
                auxMinPQ.insert(new Node(board, twinCurrent.moves + 1, current));
            }
        }
        return false;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            if (solution == null)
                return null;
            List<Board> boards = new ArrayList<>();
            Node current  = solution;
            while (current != null) {
                boards.add(current.board);
                current = current.previous;
            }
            Collections.reverse(boards);
            return boards;
        }
        return null;
    }

    public int moves() {
        if (isSolvable() && solution != null)
            return solution.moves;
        return -1;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
