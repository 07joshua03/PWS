package grid;

import helper.Direction;

public class Room {

    private boolean leftWall, upWall, rightWall, downWall = false;

    public Room(){
    }

    public void addWall(int direction){
        switch (direction){
            case Direction.up:
                upWall = true;
                break;
            case Direction.right:
                rightWall = true;
                break;
            case Direction.down:
                downWall = true;
                break;
            case Direction.left:
                leftWall = true;
        }
    }

    public void removeWall(int direction){
        switch (direction){
            case Direction.up:
                upWall = false;
                break;
            case Direction.right:
                rightWall = false;
                break;
            case Direction.down:
                downWall = false;
                break;
            case Direction.left:
                leftWall = false;
        }
    }

    public boolean getWall(int direction){
        switch (direction){
            case Direction.up:
                return upWall;
            case Direction.right:
                return rightWall;
            case Direction.down:
                return downWall;
            case Direction.left:
                return leftWall;
            default:
                return false;
        }

    }

    public boolean hasWall(){
        return upWall || rightWall || downWall || leftWall;
    }

}
