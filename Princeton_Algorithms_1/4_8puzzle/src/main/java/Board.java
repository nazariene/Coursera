import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {

    private int n;
    private int[][] blocks;

    private int hamming = -1;
    private int manhattan = -1;
    private boolean isGoal = false;
    private boolean goalCalculated = false;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }


    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming == -1) {
            int result = 0;
            int curNum = 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.blocks[i][j] != curNum++ && this.blocks[i][j] != 0) {
                        result++;
                    }
                }
            }

            hamming = result;
        }

        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan == -1) {
            int result = 0;

            int currentNumber = 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (this.blocks[i][j] != currentNumber && this.blocks[i][j] != 0) {
                        int shouldBeX = (this.blocks[i][j] - 1) / n;
                        int shouldBeY = (this.blocks[i][j] - 1) % n;
                        int diff = Math.abs(i - shouldBeX) + Math.abs(j - shouldBeY);
                        result += diff;
                    }

                    currentNumber++;
                }
            }

            manhattan = result;
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (!goalCalculated) {
            isGoal = calculateIsGoal();
            goalCalculated = true;
        }

        return isGoal;
    }

    private boolean calculateIsGoal() {
        int currentNumber = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != currentNumber++ && this.blocks[i][j] != 0) {
                    return false;
                }
            }
        }

        return (this.blocks[n - 1][n - 1] == 0);
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board newBoard = new Board(this.blocks);
        int x1 = -1, x2 = -1, y1 = -1, y2 = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != 0) {
                    if (x1 == -1) {
                        x1 = i;
                        y1 = j;
                    } else {
                        x2 = i;
                        y2 = j;
                        break;
                    }
                }
            }
        }
        int temp = newBoard.blocks[x1][y1];
        newBoard.blocks[x1][y1] = newBoard.blocks[x2][y2];
        newBoard.blocks[x2][y2] = temp;

        return newBoard;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (!(this.dimension() == other.dimension())) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        int zeroX = 0;
        int zeroY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                }
            }
        }

        Board newBoard;
        if (zeroX > 0) {
            newBoard = new Board(this.blocks);
            newBoard.blocks[zeroX][zeroY] = newBoard.blocks[zeroX - 1][zeroY];
            newBoard.blocks[zeroX - 1][zeroY] = 0;
            stack.push(newBoard);
        }

        if (zeroY > 0) {
            newBoard = new Board(this.blocks);
            newBoard.blocks[zeroX][zeroY] = newBoard.blocks[zeroX][zeroY - 1];
            newBoard.blocks[zeroX][zeroY - 1] = 0;
            stack.push(newBoard);
        }

        if (zeroX < n - 1) {
            newBoard = new Board(this.blocks);
            newBoard.blocks[zeroX][zeroY] = newBoard.blocks[zeroX + 1][zeroY];
            newBoard.blocks[zeroX + 1][zeroY] = 0;
            stack.push(newBoard);
        }

        if (zeroY < n - 1) {
            newBoard = new Board(this.blocks);
            newBoard.blocks[zeroX][zeroY] = newBoard.blocks[zeroX][zeroY + 1];
            newBoard.blocks[zeroX][zeroY + 1] = 0;
            stack.push(newBoard);
        }


        return stack;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(n);
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" " + blocks[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial.toString());
        System.out.println(initial.isGoal());
        System.out.println(initial.manhattan());

    }
}