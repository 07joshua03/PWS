package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GridPanel extends JPanel {

    private ArrayList<Vec2> circles;
    private ArrayList<Line> walls;


    public GridPanel(int width, int height, Grid grid){
        this.setSize(width, height);
        this.setBackground(new Color(255,255,255));
        circles = grid.getGridCornerVecs(width, height);
        walls = grid.getGridWallLines(width, height);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(0,0,0));
        for (Vec2 coords: circles) {
            g.fillOval(coords.x - 5, coords.y - 5, 10,10);
        }
        for (Line wall: walls) {
            g.drawLine(wall.point1.x, wall.point1.y, wall.point2.x, wall.point2.y);
        }
    }
}
