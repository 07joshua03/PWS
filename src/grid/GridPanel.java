package grid;

import helper.Line;
import helper.Vec2;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GridPanel extends JPanel {

    private ArrayList<Vec2> circles;
    private ArrayList<Line> walls;
    private final int width;
    private final int height;

    public GridPanel(int width, int height){
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.setBackground(new Color(255,255,255));
        circles = new ArrayList<>();
        walls = new ArrayList<>();
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                if(mouseX < (int)(width * 0.1)) mouseX = (int)(width * 0.1);
                else if(mouseX > width - (int)(width * 0.1)) mouseX = (int)(width - (width * 0.1));
                if(mouseY < (int)(height * 0.1)) mouseY = (int)(height * 0.1);
                else if(mouseY > height - (int)(height * 0.1)) mouseY = (int)(height - (height * 0.1));
                int x =  (int)(mouseX / (width - (width * 0.2)) * Main.grid.getGridWidth());
                int y = (int)(mouseY / (height - (height * 0.2)) * Main.grid.getGridHeight());

                System.out.println(x + "   " + y);
            }
        });
    }

    public void updateGrid(Grid grid){
        circles = grid.getGridCornerVecs(width, height);
        walls = grid.getGridWallLines(width, height);
        repaint();
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
