package main;

public class Room {

    private boolean leftWall, upWall, rightWall, downWall = false;

    public Room(){
    }

    public void addWall(int direction){
        switch (direction){
            case Direction.up:
                upWall = true;
            case Direction.right:
                rightWall = true;
            case Direction.down:
                downWall = true;
            case Direction.left:
                leftWall = true;
        }
    }
}
