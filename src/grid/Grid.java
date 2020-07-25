package grid;

import helper.Direction;
import helper.DrawableObject;
import helper.Line;
import helper.Vec2;

import java.awt.*;
import java.util.ArrayList;

public class Grid extends DrawableObject {

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
        if(     !(x == 0 && direction == Direction.left)&&
                !(x == gridWidth-1 && direction == Direction.right)&&
                !(y == 0 && direction == Direction.up)&&
                !(y == gridHeight-1 && direction == Direction.down)) {
            gridArray[x][y].removeWall(direction);
            switch (direction) {
                case Direction.up:
                    gridArray[x][y - 1].removeWall(Direction.down);
                    break;
                case Direction.right:
                    gridArray[x + 1][y].removeWall(Direction.left);
                    break;
                case Direction.down:
                    gridArray[x][y + 1].removeWall(Direction.up);
                    break;
                case Direction.left:
                    gridArray[x - 1][y].removeWall(Direction.right);
            }
        }
    }

    public Vec2 getScreenCoords(int screenWidth, int screenHeight, int x, int y){
        int locX = (int)(screenWidth * 0.1 + (screenWidth-(screenWidth * 0.2)) / (gridWidth) * x);
        int locY = (int)(screenHeight * 0.1 + (screenHeight-(screenHeight * 0.2)) / (gridHeight) * y);
        return new Vec2(locX, locY);
    }

    public Vec2 getRoomCenterCoords(int screenWidth, int screenHeight, int x, int y){
        Vec2 vec1 = getScreenCoords(screenWidth, screenHeight, 1,1);
        Vec2 vec2 = getScreenCoords(screenWidth, screenHeight, 2,2);
        double halfRoomX = (vec2.x - vec1.x)/2.0;
        double halfRoomY = (vec2.y - vec1.y)/2.0;
        int locX = (int)(getScreenCoords(screenWidth, screenHeight, x,y).x + halfRoomX);
        int locY = (int)(getScreenCoords(screenWidth, screenHeight, x,y).y + halfRoomY);
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
        Vec2 point1 = getScreenCoords(width,height,0,0);
        Vec2 point2 = getScreenCoords(width,height,gridWidth,gridHeight);
        g.setColor(new Color(60, 64, 66));
        g.fillRect(point1.x, point1.y, point2.x - point1.x, point2.y - point1.y);

        g.setColor(new Color(96, 99, 104));
        for(int y = 1; y < gridHeight; y++){
            point1 = getScreenCoords(width,height,0,y);
            point2 = getScreenCoords(width,height,gridWidth,y);
            g.drawLine(point1.x, point1.y, point2.x, point2.y);
        }
        for(int x = 1; x < gridWidth; x++){
            point1 = getScreenCoords(width,height,x,0);
            point2 = getScreenCoords(width,height,x,gridHeight);
            g.drawLine(point1.x, point1.y, point2.x, point2.y);
        }

        //DRAW HORIZONTAL WALLS
        g.setColor(new Color(255, 178, 255));
        for (Line wall: getGridHorWallLines(width, height)) {
            g.fillRect(wall.point1.x, wall.point1.y -1, wall.point2.x - wall.point1.x, 3);
        }
        //DRAW VERTICAL WALLS
        for (Line wall: getGridVerWallLines(width, height)) {
            g.fillRect(wall.point1.x -1, wall.point1.y , 3, wall.point2.y - wall.point1.y);
        }

        //DRAW WALL CORNERS
        g.setColor(new Color(234, 128, 252));
        for (Vec2 coords: getGridCornerVecs(width, height)) {
            g.fillRect(coords.x - 4, coords.y - 4, 8,8);
        }
    }

}
