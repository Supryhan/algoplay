package problems;

import static java.lang.Math.abs;

public class MatrixSelfValue {
    private int dim = 3;
    private double a[][] = new double[dim][dim];
    private double l[][] = new double[dim][dim];
    private double L[] = new double[dim];
    private double e = 0.01;


    public MatrixSelfValue() {
        a[0][0] = 1;
        a[0][1] = 2;
        a[0][2] = 3;
        a[1][0] = 4;
        a[1][1] = 5;
        a[1][2] = 6;
        a[2][0] = 7;
        a[2][1] = 8;
        a[2][2] = 9;
    }

    public static void main(String[] args) {
        MatrixSelfValue c = new MatrixSelfValue();
        c.L = c.mainWorkflow();
        for (int i = 0; i < c.dim; i++) {
            System.out.print(" " + c.L[i]);
        }
    }

    private double[] mainWorkflow() {
        int r = processing();
        while (r == 0) {
            r = processing();
        }
        for (int i = 0; i < this.dim; i++)
            L[i] = a[i][i];
        return L;
    }

    private int processing() {
        int cc = this.dim;
        //1.L
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++)
                if (i >= j)
                    l[i][j] = a[i][j];
                else
                    l[i][j] = 0;
        //2.U
        double u[][] = new double[this.dim][this.dim];
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++)
                if (i < j)
                    u[i][j] = a[i][j];
                else if (i == j)
                    u[i][j] = 1;
                else
                    u[i][j] = 0;
        //3.S
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++) {
                double s = 0;
                if (i >= j) {
                    for (int k = 0; k < (j - 1); k++)
                        s = s + l[i][k] * u[k][j];
                    l[i][j] = a[i][j] - s;
                } else {
                    for (int k = 0; k < (i - 1); k++)
                        s = s + l[i][k] * u[k][j];
                    u[i][j] = (a[i][j] - s) / l[i][j];
                }
            }
        //4.Al
        double al[][] = new double[this.dim][this.dim];
        for (int i = 0; i < this.dim; i++)
            for (int j = 0; j < this.dim; j++) {
                double s = 0;
                for (int k = 0; k < this.dim; k++)
                    s = s + u[i][k] * l[k][j];
                al[i][j] = s;
            }
        //5.abs
        for (int i = 0; i < this.dim; i++)
            if (abs(al[i][i] - a[i][i]) <= e)
                cc = cc - 1;
        a = al;
        return cc;
    }
}
