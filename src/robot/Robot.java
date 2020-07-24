package robot;

import grid.Grid;
import helper.Direction;
import helper.Vec2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class Robot {

    public Grid seenGrid;
    public Vec2 location;
    public final Vec2 goalLocation;
    public Stack<Vec2> stack;
    public ArrayList<Vec2> pastLocs;
    public int[][] d2Goal;

    public Robot(Vec2 startingLoc, Vec2 goalLoc, int gridWidth, int gridHeight){
        location = startingLoc;
        goalLocation = goalLoc;
        seenGrid = new Grid("cum" ,gridWidth, gridHeight);
        d2Goal = new int[gridWidth][gridHeight];


        stack = new Stack<>();
        pastLocs = new ArrayList<>();


    }

    public void floodfill(){
        Stack<Vec2> nextStack = new Stack<>();
        ArrayList<Vec2> checkedRooms = new ArrayList<>();
        for(int i = 0; i < seenGrid.getGridWidth(); i++){
            for (int j = 0; j < seenGrid.getGridHeight(); j++){
                d2Goal[i][j] = -1;
            }
        }
        stack.push(goalLocation);
        checkedRooms.add(goalLocation);
        int distance = 0;
        boolean stackEmpty = false;
        while(!stackEmpty){
            Vec2 curRoom = stack.pop();

            d2Goal[curRoom.x][ curRoom.y] = distance;
            for(int i = 0; i < 4; i++){
                if(!seenGrid.getRoom(curRoom.x, curRoom.y).getWall(i)){
                    Vec2 roomToAdd;
                    switch (i){
                        case 0:
                            roomToAdd = new Vec2(curRoom.x, curRoom.y -1);
                            if(!roomToAdd.isIn(checkedRooms)){
                                nextStack.push(roomToAdd);
                                checkedRooms.add(curRoom);
                            }
                            break;
                        case 1:
                            roomToAdd = new Vec2(curRoom.x + 1, curRoom.y);
                            if(!roomToAdd.isIn(checkedRooms)){
                                nextStack.push(roomToAdd);
                                checkedRooms.add(curRoom);
                            }
                            break;
                        case 2:
                            roomToAdd = new Vec2(curRoom.x, curRoom.y + 1);
                            if(!roomToAdd.isIn(checkedRooms)){
                                nextStack.push(roomToAdd);
                                checkedRooms.add(curRoom);
                            }
                            break;
                        case 3:
                            roomToAdd = new Vec2(curRoom.x - 1, curRoom.y);
                            if(!roomToAdd.isIn(checkedRooms)){
                                nextStack.push(roomToAdd);
                                checkedRooms.add(curRoom);
                            }
                            break;
                    }
                }
            }
            if(stack.empty()){
                if(nextStack.empty()) stackEmpty = true;
                else {
                    for(Vec2 i: nextStack) stack.push(i);
                    nextStack.removeAllElements();
                    distance++;
                }
            }
        }
    }

    public void drawCLIFloodfill(){
            System.out.println();
            for(int y = 0; y < seenGrid.getGridHeight();y++){
                //Upper row(s) i.e. *-* * *-*-*
                System.out.print("+");
                for(int x = 0; x < seenGrid.getGridWidth(); x++){

                    if(seenGrid.getRoom(x,y).getWall(Direction.up)) System.out.print("---+");
                    else System.out.print("   +");
                }
                System.out.println();
                //Side row(s) i.e. | | | |   |
                for(int x = 0; x < seenGrid.getGridWidth(); x++){
                    int distance = d2Goal[x][y];
                    if(distance < 10){
                        if(seenGrid.getRoom(x,y).getWall(Direction.left)) System.out.print("| " + distance);
                        else System.out.print("  " + distance);
                    }else{
                        if(seenGrid.getRoom(x,y).getWall(Direction.left)) System.out.print("|" + distance);
                        else System.out.print(" " + distance);
                    }
                    if(x == location.x && y == location.y) System.out.print("*");
                    else System.out.print(" ");
                    if(x == seenGrid.getGridWidth()-1 && seenGrid.getRoom(x,y).getWall(Direction.right)) System.out.print("|");
                }

                System.out.println();
                //Last row i.e. *-* * *-*-*
                if(y == seenGrid.getGridHeight()-1){
                    System.out.print("+");
                    for(int x = 0; x < seenGrid.getGridWidth(); x++){
                        if(seenGrid.getRoom(x,y).getWall(Direction.down)) System.out.print("---+");
                        else System.out.print("   +");
                    }
                }

            }

    }

    public void update(){
        pastLocs.add(location);
        stack.push(goalLocation);
        floodfill();
        location = calcNext();
    }

    public void calcDistances(){

    }

    public Vec2 calcNext(){
        int curDistance = d2Goal[location.x][location.y];
        for(int i = 0; i < 4; i++){
            if(!seenGrid.getRoom(location.x, location.y).getWall(i)){
                switch (i){
                    case 0:
                        if(curDistance == d2Goal[location.x][location.y - 1] + 1){
                            return new Vec2(location.x, location.y - 1);
                        }
                        break;
                    case 1:
                        if(curDistance == d2Goal[location.x + 1][location.y] + 1){
                            return new Vec2(location.x + 1, location.y);
                        }
                        break;
                    case 2:
                        if(curDistance == d2Goal[location.x][location.y + 1] + 1){
                            return new Vec2(location.x, location.y + 1);
                        }
                        break;
                    case 3:
                        if(curDistance == d2Goal[location.x - 1][location.y] + 1){
                            return new Vec2(location.x - 1, location.y);
                        }
                        break;
                }
            }
        }
        return location;
    }

    public void setSeenGrid(Grid grid){
        seenGrid = grid;
    }

    public boolean isGoalReached(){
        if(location.x == goalLocation.x && location.y == goalLocation.y) return true;
        else return false;
    }

    public void draw(Graphics g, int width, int height){
        g.setColor(Color.RED);
        for(int i = 0; i < pastLocs.size() - 1; i++){
            Vec2 point1 = seenGrid.getRoomCenterCoords(width, height, pastLocs.get(i).x , pastLocs.get(i).y);
            Vec2 point2 = seenGrid.getRoomCenterCoords(width, height, pastLocs.get(i + 1).x, pastLocs.get(i + 1).y);
            g.drawLine(point1.x, point1.y, point2.x, point2.y);
        }
    }
}
