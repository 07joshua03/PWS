package robot;

import grid.Grid;
import grid.Room;
import helper.Vec2;

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
        for(int i = 0; i < gridWidth; i++){
            for (int j = 0; j < gridHeight; j++){
                d2Goal[i][j] = -1;
            }
        }
        d2Goal[goalLoc.x][goalLoc.y] = 0;
        stack.push(goalLocation);
        floodfill();
    }

    public void floodfill(){
        Stack<Vec2> nextStack = new Stack<>();
        ArrayList<Vec2> checkedRooms = new ArrayList<>();
        int distance = 0;
        boolean stackEmpty = false;
        while(!stackEmpty){
            Vec2 curRoom = stack.pop();
            checkedRooms.add(curRoom);
            d2Goal[curRoom.x][ curRoom.y] = distance;
            for(int i = 0; i < 4; i++){
                if(!seenGrid.getRoom(curRoom.x, curRoom.y).getWall(i)){
                    Vec2 roomToAdd;
                    switch (i){
                        case 0:
                            roomToAdd = new Vec2(curRoom.x, curRoom.y -1);
                            if(!roomToAdd.isIn(checkedRooms)) nextStack.push(roomToAdd);
                            break;
                        case 1:
                            roomToAdd = new Vec2(curRoom.x + 1, curRoom.y);
                            if(!roomToAdd.isIn(checkedRooms)) nextStack.push(roomToAdd);
                            break;
                        case 2:
                            roomToAdd = new Vec2(curRoom.x, curRoom.y + 1);
                            if(!roomToAdd.isIn(checkedRooms)) nextStack.push(roomToAdd);
                            break;
                        case 3:
                            roomToAdd = new Vec2(curRoom.x - 1, curRoom.y);
                            if(!roomToAdd.isIn(checkedRooms)) nextStack.push(roomToAdd);
                            break;
                    }
                }
            }
            if(stack.size() == 0){
                if(nextStack.size() == 0) stackEmpty = true;
                else {
                    for(Vec2 i: nextStack) stack.push(i);
                    nextStack.empty();
                    distance++;
                }
            }

        }
    }

    public void update(){
        pastLocs.add(location);
        location = calcNext(goalLocation);

    }

    public void calcDistances(){

    }

    public Vec2 calcNext(Vec2 goalLocation){



        return new Vec2(0,0);
    }

    public void setSeenGrid(Grid grid){
        seenGrid = grid;
    }


}
