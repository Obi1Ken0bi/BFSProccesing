import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.Arrays;
import java.util.function.Predicate;

public class Main extends PApplet {

    private static final int BFS_SIZE = 100;
    private static BFS bfs= new BFS(BFS_SIZE);
    private final int WINDOWS_SIZE = 1000;
    private final int RECT_SIZE=WINDOWS_SIZE/BFS_SIZE;

    private Point[] winPath;

    public static void main(String[] args) {
        PApplet.main("Main",args);
    }
    @Override
    public void setup(){
    }



    @Override
    public void mouseDragged(MouseEvent event) {

        if(mouseButton==RIGHT)
            bfs.setWall(new Point(mouseX/RECT_SIZE,mouseY/RECT_SIZE));

    }

    @Override
    public void mousePressed() {
        if(mouseButton==LEFT)
            winPath=bfs.search(new Point(mouseX/RECT_SIZE,mouseY/RECT_SIZE));
    }

    public void settings(){
        size(WINDOWS_SIZE,1000);
    }
    @Override
    public void draw(){
        Cell[][] matrix = bfs.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            Cell[] cells = matrix[i];
            for (int j = 0; j < matrix.length; j++) {

                   Cell cell = cells[j];

                float gray = map(cell.getRank(),0,BFS_SIZE*4,0,255);
                fill(gray);
//                if(cell.isVisited())
//                    fill(123);
                if(cell.getType()==CellType.WALL)
                    fill(245,97,252);
                int finalJ = j;
                int finalI = i;
                boolean match=false;
                if(winPath!=null) {
                    match = Arrays.stream(winPath).anyMatch(new Predicate<Point>() {
                        @Override
                        public boolean test(Point point) {
                            return point.equals(new Point(finalI, finalJ));
                        }
                    });
                    if (match) {
                        fill(226, 241, 103);
                    }
                }
                rect(i*RECT_SIZE, j*RECT_SIZE, RECT_SIZE, RECT_SIZE);


            }
        }
        println(frameRate);
    }
}
