package grid;

import helper.Direction;
import helper.Line;
import helper.Vec2;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GridPanel extends JPanel {

    private ArrayList<Vec2> circles;
    private ArrayList<Line> walls;
    private ArrayList<Line> horWalls;
    private ArrayList<Line> verWalls;

    private final int width;
    private final int height;

    private final Vec2 roomChosen;
    private int directionChosen = -1;

    public GridPanel(int width, int height){
        this.width = width;
        this.height = height;

        roomChosen = new Vec2(0,0);

        this.setSize(width, height);
        this.setBackground(new Color(255,255,255));
        circles = new ArrayList<>();
        walls = new ArrayList<>();
        horWalls = new ArrayList<>();
        verWalls = new ArrayList<>();
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = 0;
                int mouseY = 0;
                try {
                    mouseX = e.getX() - 100;
                    mouseY = e.getY() - 100;
                }catch(Exception ex){
                    System.out.println("WARN: Mouse coord not found");
                }
                if(mouseX < 0) mouseX = 0;
                else if(mouseX > width- (int)(width * 0.2)) mouseX = (int)(width - (width * 0.2));
                if(mouseY < 0) mouseY = 0;
                else if(mouseY > height - (int)(height * 0.2)) mouseY = (int)(height - (height * 0.2));
                int x = (int)(mouseX / (width - (width * 0.2)) * (Main.grid.getGridWidth()));
                int y = (int)(mouseY / (height - (height * 0.2)) * (Main.grid.getGridHeight()));
                if(x > Main.grid.getGridWidth() - 1) x = Main.grid.getGridWidth() - 1;
                if(y > Main.grid.getGridHeight() - 1) y = Main.grid.getGridHeight() - 1;
                roomChosen.x = x;
                roomChosen.y = y;
                Vec2 topLeft = Main.grid.getScreenCoords(width,height, x, y);
                Vec2 botRight = Main.grid.getScreenCoords(width,height, x + 1, y + 1);
                int x2 = 0;
                int y2 = 0;
                try{
                    x2 = e.getX() - topLeft.x;
                    y2 = e.getY() - topLeft.y;
                    if(x2 < 0) x2 = 0;
                    else if(e.getX() > botRight.x) x2 = botRight.x - topLeft.x;
                    if(y2 < 0) y2 = 0;
                    else if(e.getY() > botRight.y) y2 = botRight.y - topLeft.y;
                }catch(Exception ex){
                    System.out.println("WARN: Mouse coord not found");
                }

                int roomWidth = (int)((width - (width*0.2))/Main.grid.getGridWidth());
                int roomHeight = (int)((width - (width*0.2))/Main.grid.getGridWidth());
                int x3 = ((roomHeight/roomWidth)*x2);
                if(y2<x3 && y2<roomWidth-x3){
                    directionChosen = Direction.up;
                }else if(y2>=x3 && y2<roomWidth-x3){
                    directionChosen = Direction.left;
                }else if (y2<x3 && y2>=roomWidth-x3){
                    directionChosen = Direction.right;
                }else{
                    directionChosen = Direction.down;
                }
            }
        });
        this.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                if(!Main.grid.getRoom(roomChosen.x, roomChosen.y).getWall(directionChosen)) Main.grid.addWall(roomChosen.x, roomChosen.y, directionChosen);
                else Main.grid.removeWall(roomChosen.x, roomChosen.y, directionChosen);
                Main.mainWindow.update(Main.grid);
                Main.drawCLI(Main.grid);
            }

            public void mouseClicked(MouseEvent e) { }

            public void mouseReleased(MouseEvent e) { }

            public void mouseEntered(MouseEvent e) { }

            public void mouseExited(MouseEvent e) { }
        });
    }

    public void updateGrid(Grid grid){
        circles = grid.getGridCornerVecs(width, height);
        walls = grid.getGridWallLines(width, height);
        horWalls = grid.getGridHorWallLines(width, height);
        verWalls = grid.getGridVerWallLines(width, height);
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(new Color(2, 6, 50, 255));
        for (Line wall: horWalls) {
            g.fillRect(wall.point1.x, wall.point1.y -1, wall.point2.x - wall.point1.x, 3);
        }
        for (Line wall: verWalls) {
            g.fillRect(wall.point1.x -1, wall.point1.y , 3, wall.point2.y - wall.point1.y);
        }

        g.setColor(new Color(7, 49, 42));
        for (Vec2 coords: circles) {
            g.fillOval(coords.x - 4, coords.y - 4, 8,8);
        }
//        int[] xs = {100,300,200};
//        int[] ys = {100,100,400};     polygons for robot triangle(for measurements)
//        g.fillPolygon(xs, ys, 3);

    }
}
