import java.util.LinkedList;
import java.util.function.Consumer;

public class BFS {
    private static final Point[] moves = new Point[]{new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1)};
    private final Cell[][] matrix;

    private final LinkedList<Point> queue = new LinkedList<>();

    public BFS(int size) {
        this.matrix = new Cell[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = new Cell();
            }
        }
        this.matrix[size - 1][size - 1].setType(CellType.TARGET);
    }


    public Point[] search(Point cords)   {
        Point start = cords;

        init(cords);
        while (!queue.isEmpty()) {
            cords = queue.poll();

            traverse(cords);


        }
        return closestPath(start,new Point(matrix.length - 1, matrix.length - 1));


    }

    public Point[] search(Point cords, Consumer<Point> consumer) {
        Point start = cords;
        init(cords);
        while (!queue.isEmpty()) {
            cords = queue.poll();
            consumer.accept(cords);

            traverse(cords);


        }
        return closestPath(start, new Point(matrix.length - 1, matrix.length - 1));


    }


    private void init(Point cords) {
        Cell start = matrix[cords.getX()][cords.getY()];
        start.setVisited(true);
        start.setRank(0);
        queue.add(cords);
    }


    public void setWall(Point p) {

        this.matrix[p.getX()][p.getY()].setType(CellType.WALL);
    }


    private void traverse(Point cords) {
        for (Point move : moves) {
            int newX = cords.getX() + move.getX();
            int newY = cords.getY() + move.getY();
            Point nextPoint = new Point(newX, newY);
            if (isOutOfBorder(nextPoint))
                continue;
            if (matrix[newX][newY].getType() != CellType.WALL) {

                if (!matrix[newX][newY].isVisited()) {
                    int min = Math.min(matrix[cords.getX()][cords.getY()].getRank() + 1,
                            matrix[nextPoint.getX()][nextPoint.getY()].getRank());
                    matrix[nextPoint.getX()][nextPoint.getY()]
                            .setRank(min);
                    queue.add(nextPoint);
                    matrix[newX][newY].setVisited(true);
                }
            }
        }

    }

    public Point[] closestPath(Point point, Point target) {
        Point[] path = new Point[matrix[target.getX()][target.getY()].getRank()];
        int j = 0;
        int min = Integer.MAX_VALUE;
        int minID = 0;
        while (!target.equals(point)) {
            for (int i = 0; i < moves.length; i++) {
                int newX = target.getX() + moves[i].getX();
                int newY = target.getY() + moves[i].getY();
                if (isOutOfBorder(new Point(newX, newY)))
                    continue;
                if (matrix[newX][newY].getRank() < min) {
                    min = matrix[newX][newY].getRank();
                    minID = i;
                }
            }
            target = new Point(target.getX() + moves[minID].getX(), target.getY() + moves[minID].getY());
            path[j] = target;
            j++;
        }
        return path;
    }


    private boolean isOutOfBorder(Point point) {
        return point.getX() >= matrix.length || point.getY() >= matrix.length || point.getY() < 0 || point.getX() < 0;
    }

    public Cell[][] getMatrix() {
        return this.matrix;
    }
}
