package problemslab;

public class Main {

    public static void main(String[] args) {
        System.out.println(new Main().go());
    }
    public int go(){
        return solve(board);
    }

    char[][] board =
           {{'X', 'X', 'X', 'X'},
            {'X', 'D', 'D', 'X'},
            {'X', 'X', 'D', 'X'},
            {'X', 'D', 'X', 'X'}};
    boolean[][] passed =
           {{false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false}};
    int count = 0;

    int solve(char[][] board) {
        int result = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!passed[i][j]) node(i, j);
                if (count > 0) {
                    result++;
                    count = 0;
                }
            }
        }
        return result;
    }

    void node(int i, int j) {
        passed[i][j] = true;
        if (board[i][j] == 'D') {
            count++;
            if (i > 2 && i < board[i].length && !passed[i - 1][j]) {
                node(i - 1, j);
            } else {
                count = 0;
                return;
            }
            if (i > 0 && i < board[i].length - 1 && !passed[i + 1][j]) {
                node(i + 1, j);
            } else {
                count = 0;
                return;
            }
            if (j > 0 && j < board[i].length - 1 && !passed[i][j + 1]) {
                node(i, j + 1);
            } else {
                count = 0;
                return;
            }
            if (j > 2 && j < board[i].length && !passed[i][j - 1]) {
                node(i, j - 1);
            } else {
                count = 0;
            }
        }

    }
}
