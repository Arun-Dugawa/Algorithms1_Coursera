import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private int[][] board;
    private int size;
    private int manhattanDist;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        if (tiles.length != tiles[0].length) throw new IllegalArgumentException();
        size = tiles.length;
        manhattanDist = 0;
        board = new int[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = tiles[i][j];
                int entry = board[i][j];
                if (entry != 0) {
                    int x = (entry - 1) / size, y = (entry - 1) % size;
                    manhattanDist += (Math.abs(i - x) + Math.abs(j - y));
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(size);
        sb.append('\n');
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                sb.append(" " + board[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingDist = 0;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                int id = i * size + j + 1;
                if (id == size * size) break;
                if (id != board[i][j]) ++hammingDist;
            }
        }
        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (size != that.dimension()) return false;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (this.board[i][j] != that.board[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();
        int[] dir = new int[] {0, -1, 0, 1, 0};
//        int[][][] copyBoards = new int[4][size][size];
        int[][] copyBoard =  new int[size][size];
        for (int d = 0; d < 4; ++d) {
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
//                    copyBoards[d][i][j] = board[i][j];
                    copyBoard[i][j] = board[i][j];
                }
            }
        }
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (board[i][j] == 0) {
                    for (int d = 0; d < 4; ++d) {
                        int x = i + dir[d], y = j + dir[d + 1];
                        if (isValidCoordinate(x, y)) {
//                            copyBoards[d][x][y] = board[i][j];
//                            copyBoards[d][i][j] = board[x][y];
//                            neighbors.enqueue(new Board(copyBoards[d]));
                            copyBoard[x][y] = board[i][j];
                            copyBoard[i][j] = board[x][y];
                            neighbors.enqueue(new Board(copyBoard));
                            copyBoard[i][j] = board[i][j];
                            copyBoard[x][y] = board[x][y];
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    private boolean isValidCoordinate(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) return true;
        return false;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (this.size == 1) return null;
        if (this.board == null) return null;
        int[][] copyBoard = new int[size][size];
        boolean flag = false;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (j != size - 1 && board[i][j] != 0 && board[i][j + 1] != 0 && !flag) {
                    copyBoard[i][j] = board[i][j + 1];
                    copyBoard[i][j + 1] = board[i][j];
                    ++j;
                    flag = true;
                }
                else {
                    copyBoard[i][j] = board[i][j];
                }
            }
        }
        return new Board(copyBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}