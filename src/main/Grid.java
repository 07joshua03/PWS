package main;

public class Grid {

    private int gridWidth;
    private int gridHeight;
    private Room[][] gridArray;


    public Grid(int gridWidth, int gridHeight){
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                gridArray[i][j] = new Room();
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
                if (y-1 >= 0) gridArray[x][y-1].addWall(direction);
            case Direction.right:
                if (x+1 < gridWidth) gridArray[x+1][y].addWall(direction);
            case Direction.down:
                if (y+1 < gridHeight) gridArray[x][y+1].addWall(direction);
            case Direction.left:
                if (x-1 >= 0) gridArray[x-1][y].addWall(direction);
        }
    }

    public void displayGrid(){

    }

}
