package problems;

public class MatrixColomnsSwitch {
    public static void main(String[] args) {
        MatrixColomnsSwitch m = new MatrixColomnsSwitch();
        m.switching(matrix, 0, 2);
    }

    static int[][] matrix =
            {{1,  2,  3,  4},
             {5,  6,  7,  8},
             {9,  10, 11, 12},
             {13, 14, 15, 16}};

    int[][] switching(int[][] matrix, int i, int j) {
        int rows = matrix.length;
        int columnsHigth = matrix[0].length;
        for (int ii = 0; ii < columnsHigth; ii++) {
            int k = matrix[ii][i];
            matrix[ii][i] = matrix[ii][j];
            matrix[ii][j] = k;
        }

        return matrix;
    }
}
