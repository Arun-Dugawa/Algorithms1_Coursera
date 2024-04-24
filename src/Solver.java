import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private boolean solvable;
    private final Stack<Board> solution;
    private SearchNode lastSearchNode;
    private int minMoves = 0;
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode prev;
        private SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        @Override
        public int compareTo(SearchNode node) {
            int o1Manhattan = this.board.manhattan();
            int o2Manhattan = node.board.manhattan();
            int o1Priority = this.moves + o1Manhattan;
            int o2Priority = node.moves + o2Manhattan;
            if (o1Priority < o2Priority) {
                return -1;
            }
            if (o1Priority > o2Priority) {
                return 1;
            }
            if (o1Manhattan < o2Manhattan) {
                return -1;
            }
            if (o1Manhattan > o2Manhattan) {
                return 1;
            }
            return 0;
        }
    }
    private class SearchNodeComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int o1Manhattan = o1.board.manhattan();
            int o2Manhattan = o2.board.manhattan();
            int o1Priority = o1.moves + o1Manhattan;
            int o2Priority = o2.moves + o2Manhattan;
            if (o1Priority < o2Priority) {
                return -1;
            }
            if (o1Priority > o2Priority) {
                return 1;
            }
            if (o1Manhattan < o2Manhattan) {
                return -1;
            }
            if (o1Manhattan > o2Manhattan) {
                return 1;
            }
            return 0;
        }
    }
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        solution = new Stack<>();
        MinPQ<SearchNode> searchPQ = new MinPQ<>(new SearchNodeComparator());
        MinPQ<SearchNode> parallelPQ = new MinPQ<>(new SearchNodeComparator());
//        solution = new Queue<>();
        searchPQ.insert(new SearchNode(initial, 0, null));
        parallelPQ.insert(new SearchNode(initial.twin(), 0, null));
        int moves = 0;
        int twinMoves = 0;
        boolean solved = false, twinSolved = false;
        while (!solved && !twinSolved) {
            SearchNode curr = searchPQ.delMin();
//            solution.enqueue(curr.board);
            solved = curr.board.isGoal();
            SearchNode twin = parallelPQ.delMin();
            twinSolved = twin.board.isGoal();

            for (Board neighbor : curr.board.neighbors()) {
//                System.out.println(neighbor.toString());
                if (curr.prev == null || !curr.prev.board.equals(neighbor)) {
                    searchPQ.insert(new SearchNode(neighbor, curr.moves + 1, curr));
                }
            }
            for (Board neighbor : twin.board.neighbors()) {
                if (twin.prev == null || !twin.prev.board.equals(neighbor)) {
                    parallelPQ.insert(new SearchNode(neighbor, twin.moves + 1, twin));
                }
            }
            moves = curr.moves + 1;
            twinMoves = twin.moves + 1;
            lastSearchNode = curr;
        }
        solvable = !twinSolved;
        minMoves = moves - 1;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? minMoves: -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) return null;
        while (lastSearchNode != null) {
            solution.push(lastSearchNode.board);
            lastSearchNode = lastSearchNode.prev;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] tiles = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                tiles[i][j] = in.readInt();
        int[][] tiles = new int[][] {{0, 1 , 3}, {4, 2, 5}, {7, 8, 6}};
        Board initial = new Board(tiles);

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