import edu.princeton.cs.algs4.*;

import java.util.Comparator;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentSolver {

    //private SearchNode solution;
    private ValueLatch<SearchNode> solution;
    private Board board;
    private ExecutorService executorService;

    // find a solution to the initial board (using the A* algorithm)
    public ConcurrentSolver(Board initial) {
        this.board = initial;
        this.solution = new ValueLatch<>();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public SearchNode solve() throws InterruptedException {
        try {
            executorService.execute(new SearchTask(board, null, 0));
            return solution.getValue();
        }
        finally {
            executorService.shutdown();
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() throws InterruptedException {
        return solution.getValue() != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() throws InterruptedException {
        return isSolvable() ? solution.getValue().moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() throws InterruptedException {
        Stack<Board> boards = new Stack<Board>();
        SearchNode node = solution.getValue();
        while (node != null) {
            boards.push(node.board);
            node = node.prev;
        }

        Queue<Board> result = new Queue<>();
        while (!boards.isEmpty()) {
            result.enqueue(boards.pop());
        }

        if (result.size() < 1) {
            result = null;
        }
        return result;
    }

    class SearchTask extends SearchNode implements Runnable {

        public SearchTask(Board board, SearchNode prev, int moves) {
            super(board, prev, moves);
        }

        @Override
        public void run() {

            if (solution.isSet()) {
                return;
            }

            if (this.board.isGoal()) {
                solution.setValue(this);
            } else {
                Iterable<Board> neighbours = board.neighbors();
                for (Board neighbour : neighbours) {
                    if (prev != null) {
                        SearchNode parent = prev;
                        boolean matchAncestor = false;
                        while (parent != null) {
                            if (neighbour.equals(parent.board)) {
                                matchAncestor = true;
                                break;
                            }

                            parent = parent.prev;
                        }

                        if (matchAncestor) {
                            continue;
                        }
                    }
                    executorService.execute(new SearchTask(neighbour, new SearchNode(board, prev, moves), moves + 1));
                }
            }
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) throws InterruptedException {

        In in = new In("C:\\work\\SandBox\\Coursera\\Princeton_Algorithms_1\\4_8puzzle\\src\\main\\resources\\puzzle05.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        ConcurrentSolver solver = new ConcurrentSolver(initial);
        solver.solve();
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode {
        protected Board board;
        protected SearchNode prev;
        protected int moves;

        SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }
    }
}