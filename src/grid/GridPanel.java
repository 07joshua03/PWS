package grid;

import helper.Direction;
import helper.DrawableObject;
import helper.Vec2;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GridPanel extends JPanel {

    private final int width;
    private final int height;

    private final Vec2 roomChosen;
    private int directionChosen = -1;
    public ArrayList<DrawableObject> drawableObjects;

    public GridPanel(int width, int height){
        this.width = width;
        this.height = height;

        drawableObjects = new ArrayList<>();

        roomChosen = new Vec2(0,0);

        this.setSize(width, height);
        this.setBackground(new Color(32, 33, 36));
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                calcChosenRoom(e);
                calcDirection(e);
            }
        });
        this.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                switch (Main.configWindow.mode){
                    case 0:
                        if(!Main.maze.grid.getRoom(roomChosen.x, roomChosen.y).getWall(directionChosen)) Main.maze.grid.addWall(roomChosen.x, roomChosen.y, directionChosen);
                        else Main.maze.grid.removeWall(roomChosen.x, roomChosen.y, directionChosen);
                        Main.shouldSolve = true;
                        Main.render();
                        break;
                    case 1:
                        Main.robot.startLocation.x = roomChosen.x;
                        Main.robot.startLocation.y = roomChosen.y;
                        Main.maze.startingLocation.x = roomChosen.x;
                        Main.maze.startingLocation.y = roomChosen.y;
                        Main.shouldSolve = true;
                        Main.render();
                        break;
                    case 2:
                        Main.robot.goalLocation.x = roomChosen.x;
                        Main.robot.goalLocation.y = roomChosen.y;
                        Main.maze.goalLocation.x = roomChosen.x;
                        Main.maze.goalLocation.y = roomChosen.y;
                        Main.shouldSolve = true;
                        Main.render();
                        break;

                }

            }

            public void mouseClicked(MouseEvent e) { }

            public void mouseReleased(MouseEvent e) { }

            public void mouseEntered(MouseEvent e) { }

            public void mouseExited(MouseEvent e) { }
        });
    }

    public void renderPanel(){
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
//        for(DrawableObject o: drawableObjects){
//            o.draw(g, width, height);
//        }

        Main.maze.grid.draw(g, width, height);
        Main.robot.draw(g, width ,height);

//        int[] xs = {100,300,200};
//        int[] ys = {100,100,400};     polygons for robot triangle(for measurements)
//        g.fillPolygon(xs, ys, 3);
    }


    private void calcChosenRoom(MouseEvent e){
        int mouseX = 0;
        int mouseY = 0;
        try {
            mouseX = (int)(e.getX() - (0.1*width));
            mouseY = (int)(e.getY() - (0.1*height));
        }catch(Exception ex){
            System.out.println("WARN: Mouse coord not found");
        }
        if(mouseX < 0) mouseX = 0;
        else if(mouseX > width- (int)(width * 0.2)) mouseX = (int)(width - (width * 0.2));
        if(mouseY < 0) mouseY = 0;
        else if(mouseY > height - (int)(height * 0.2)) mouseY = (int)(height - (height * 0.2));
        int x = (int)(mouseX / (width - (width * 0.2)) * (Main.maze.grid.getGridWidth()));
        int y = (int)(mouseY / (height - (height * 0.2)) * (Main.maze.grid.getGridHeight()));
        if(x > Main.maze.grid.getGridWidth() - 1) x = Main.maze.grid.getGridWidth() - 1;
        if(y > Main.maze.grid.getGridHeight() - 1) y = Main.maze.grid.getGridHeight() - 1;
        roomChosen.x = x;
        roomChosen.y = y;

    }

    private void calcDirection(MouseEvent e){
        Vec2 topLeft = Main.maze.grid.getScreenCoords(width,height, roomChosen.x, roomChosen.y);
        Vec2 botRight = Main.maze.grid.getScreenCoords(width,height, roomChosen.x + 1, roomChosen.y + 1);
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

        double roomWidth = ((width - (width*0.2))/Main.maze.grid.getGridWidth());
        double roomHeight = ((height - (height*0.2))/Main.maze.grid.getGridHeight());
        double dy = ((roomHeight/roomWidth)*x2);
        if(y2<dy && y2<roomHeight-dy){
            directionChosen = Direction.up;
        }else if(y2>=dy && y2<roomHeight-dy){
            directionChosen = Direction.left;
        }else if (y2<dy && y2>=roomHeight-dy){
            directionChosen = Direction.right;
        }else if (y2>=dy && y2>=roomHeight-dy){
            directionChosen = Direction.down;
        }
    }

}
