package probls;

import java.util.HashSet;
import java.util.Set;

public class CountPoints {
    public static void main(String[] args) {
        CountPoints solution = new CountPoints();
        System.out.println(solution.countPoints(new int[][]{{2,1,2,5},{1,1,4,4},{5,2,1,4}}));
    }
    public int countPoints(int[][] segments) {
        Set<String> points = new HashSet<>();

        for (int[] segment : segments) {
            int x1 = segment[0];
            int y1 = segment[1];
            int x2 = segment[2];
            int y2 = segment[3];

            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);

            int gcd = gcd(dx, dy);

            dx /= gcd;
            dy /= gcd;

            int x = x1;
            int y = y1;

            if(x1 <= x2 && y1 <= y2) {
                while (x <= x2 && y <= y2) {
                    points.add(x + "," + y);
                    x += dx;
                    y += dy;
                }
            } else if(x1 >= x2 && y1 <= y2) {
                while (x >= x2 && y <= y2) {
                    points.add(x + "," + y);
                    x -= dx;
                    y += dy;
                }
            } else if(x1 <= x2) {
                while (x <= x2 && y >= y2) {
                    points.add(x + "," + y);
                    x += dx;
                    y -= dy;
                }
            } else {
                while (x >= x2 && y >= y2) {
                    points.add(x + "," + y);
                    x -= dx;
                    y -= dy;
                }
            }
        }

        return points.size();
    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
