package grid;

import helper.Direction;
import helper.Line;
import helper.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class Grid {

    private String gridName;
    private int gridWidth;
    private int gridHeight;
    private Room[][] gridArray;


    public Grid(String gn, int gw, int gh){    //(0,0) at top-left
        gridName = gn;
        changeGridSize(gw,gh);
    }

    public void changeGridSize(int gw, int gh){
        this.gridWidth = gw;
        this.gridHeight = gh;
        generateRoom();
    }

    public void generateRoom(){
        gridArray = new Room[gridWidth][gridHeight];
        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                gridArray[i][j] = new Room();

                //Creating border walls
                if(i==0) gridArray[i][j].addWall(Direction.left);
                if(i==gridWidth-1) gridArray[i][j].addWall(Direction.right);
                if(j==0) gridArray[i][j].addWall(Direction.up);
                if(j==gridHeight-1) gridArray[i][j].addWall(Direction.down);
            }
        }
    }

    public Room getRoom(int x,int y){
        return gridArray[x][y];
    }

    public void addWall(int x,int y, int direction){
        gridArray[x][y].addWall(direction);
        switch (direction){
            case Direction.up:
                if (y-1 >= 0) gridArray[x][y-1].addWall(Direction.down);
                break;
            case Direction.right:
                if (x+1 < gridWidth) gridArray[x+1][y].addWall(Direction.left);
                break;
            case Direction.down:
                if (y+1 < gridHeight) gridArray[x][y+1].addWall(Direction.up);
                break;
            case Direction.left:
                if (x-1 >= 0) gridArray[x-1][y].addWall(Direction.right);
        }
    }

    public void removeWall(int x,int y, int direction){
        gridArray[x][y].removeWall(direction);
        switch (direction){
            case Direction.up:
                if (y-1 >= 0) gridArray[x][y-1].removeWall(Direction.down);
                break;
            case Direction.right:
                if (x+1 < gridWidth) gridArray[x+1][y].removeWall(Direction.left);
                break;
            case Direction.down:
                if (y+1 < gridHeight) gridArray[x][y+1].removeWall(Direction.up);
                break;
            case Direction.left:
                if (x-1 >= 0) gridArray[x-1][y].removeWall(Direction.right);
        }
    }

    public Vec2 getScreenCoords(int screenWidth, int screenHeight, int x, int y){
        int locX = (int)(screenWidth * 0.1 + (screenWidth-(screenWidth * 0.2)) / (gridWidth) * x);
        int locY = (int)(screenHeight * 0.1 + (screenHeight-(screenHeight * 0.2)) / (gridHeight) * y);
        return new Vec2(locX, locY);
    }

    public Vec2 getRoomCenterCoords(int screenWidth, int screenHeight, int x, int y){
        int locX = (int)(screenWidth * 0.15 + (screenWidth-(screenWidth * 0.3)) / (gridWidth - 1) * x);
        int locY = (int)(screenHeight * 0.15 + (screenHeight-(screenHeight * 0.3)) / (gridHeight - 1) * y);
        return new Vec2(locX, locY);
    }

    public ArrayList<Vec2> getGridCornerVecs(int screenWidth, int screenHeight){
        ArrayList<Vec2> circles = new ArrayList<>();
        for(int i = 0; i <= gridWidth; i++){
            for (int j = 0; j <= gridHeight; j++){
                circles.add(getScreenCoords(screenWidth,screenHeight,i,j));
            }
        }
        return circles;


    }

    public ArrayList<Line> getGridWallLines(int screenWidth, int screenHeight){
        ArrayList<Line> lines = new ArrayList<>();
        for(int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                if(getRoom(i,j).getWall(Direction.up)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i,j), getScreenCoords(screenWidth, screenHeight, i + 1,j)));
                if(getRoom(i,j).getWall(Direction.right)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i+1,j), getScreenCoords(screenWidth, screenHeight, i + 1,j + 1)));
                if(getRoom(i,j).getWall(Direction.down)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i,j+1), getScreenCoords(screenWidth, screenHeight, i + 1,j+1)));
                if(getRoom(i,j).getWall(Direction.left)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i,j), getScreenCoords(screenWidth, screenHeight, i,j + 1)));
            }
        }
        return lines;
    }

    public ArrayList<Line> getGridHorWallLines(int screenWidth, int screenHeight){
        ArrayList<Line> lines = new ArrayList<>();
        for(int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                if(getRoom(i,j).getWall(Direction.up)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i,j), getScreenCoords(screenWidth, screenHeight, i + 1,j)));
                if(getRoom(i,j).getWall(Direction.down)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i,j+1), getScreenCoords(screenWidth, screenHeight, i + 1,j+1)));
            }
        }
        return lines;
    }

    public ArrayList<Line> getGridVerWallLines(int screenWidth, int screenHeight){
        ArrayList<Line> lines = new ArrayList<>();
        for(int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                if(getRoom(i,j).getWall(Direction.right)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i+1,j), getScreenCoords(screenWidth, screenHeight, i + 1,j + 1)));
                if(getRoom(i,j).getWall(Direction.left)) lines.add(new Line(getScreenCoords(screenWidth, screenHeight, i,j), getScreenCoords(screenWidth, screenHeight, i,j + 1)));
            }
        }
        return lines;
    }

    public String getName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public int getGridWidth(){
        return gridWidth;
    }

    public int getGridHeight(){
        return gridHeight;
    }

    public String toString(){
        return getName();
    }

    public void draw(Graphics g, int width, int height){
        //DRAW HORIZONTAL WALLS
        g.setColor(new Color(2, 6, 50, 255));
        for (Line wall: getGridHorWallLines(width, height)) {
            g.fillRect(wall.point1.x, wall.point1.y -1, wall.point2.x - wall.point1.x, 3);
        }
        //DRAW VERTICAL WALLS
        for (Line wall: getGridVerWallLines(width, height)) {
            g.fillRect(wall.point1.x -1, wall.point1.y , 3, wall.point2.y - wall.point1.y);
        }

        //DRAW WALL CORNERS
        g.setColor(new Color(7, 49, 42));
        for (Vec2 coords: getGridCornerVecs(width, height)) {
            g.fillOval(coords.x - 4, coords.y - 4, 8,8);
        }
    }

}
