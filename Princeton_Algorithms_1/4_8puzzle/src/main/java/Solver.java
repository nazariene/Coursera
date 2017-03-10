import edu.princeton.cs.algs4.*;

import java.util.Comparator;
import java.util.Stack;

public class Solver {

    private SearchNode solution;
    private SearchNode twinSolution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        SearchNode initialSearchNode = new SearchNode(initial, null, 0);

        solution = solve(initialSearchNode);
        if (solution == null) {
            twinSolution = solve(new SearchNode(initial.twin(), null, 0));
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? solution.moves : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> boards = new Stack<Board>();
        SearchNode node = solution;
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

    private SearchNode solve(SearchNode initialNode) {
        MinPQ<SearchNode> maxPQ = new MinPQ<SearchNode>(new SearchNodeComparator());
        maxPQ.insert(initialNode);

        while (!maxPQ.isEmpty()) {
            SearchNode currentMove = maxPQ.delMin();
            if (currentMove.board.isGoal()) {
                solution = currentMove;
                break;
            } else {
                Iterable<Board> neighbours = currentMove.board.neighbors();
                for (Board neighbour : neighbours) {
                    if (currentMove.prev != null) {
                        SearchNode parent = currentMove.prev;
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
                    maxPQ.insert(new SearchNode(neighbour, currentMove, currentMove.moves + 1));
                }
            }
        }

        return solution;
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode o1, SearchNode o2) {
            int board1Weight = o1.board.manhattan() + o1.moves;
            int board2Weight = o2.board.manhattan() + o2.moves;
            return Integer.compare(board1Weight, board2Weight);
        }
    }


    // solve a slider puzzle (given below)
    public static void main(String[] args) {

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

    private class SearchNode {
        private Board board;
        private SearchNode prev;
        private int moves;

        SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }
    }
}