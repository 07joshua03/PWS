package main;

import java.awt.*;

public class Grid {

    private int gridWidth;
    private int gridHeight;
    private Room[][] gridArray;


    public Grid(int gw, int gh){    //(0,0) at top-left
        this.gridWidth = gw;
        this.gridHeight = gh;
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

    public Grid(int gw, int gh, Room[][] ra){    //(0,0) at top-left
        this.gridWidth = gw;
        this.gridHeight = gh;
        this.gridArray = ra;
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
//        switch (direction){
//            case Direction.up:
//                if (y-1 >= 0) gridArray[x][y-1].addWall(Direction.down);
//            case Direction.right:
//                if (x+1 < gridWidth) gridArray[x+1][y].addWall(Direction.left);
//            case Direction.down:
//                if (y+1 < gridHeight) gridArray[x][y+1].addWall(Direction.up);
//            case Direction.left:
//                if (x-1 >= 0) gridArray[x-1][y].addWall(Direction.right);
//        }
    }

    public void displayGrid(Graphics g){

    }

    public int getGridWidth(){
        return gridWidth;
    }

    public int getGridHeight(){
        return gridHeight;
    }

}
