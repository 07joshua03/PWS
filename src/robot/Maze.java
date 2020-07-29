package robot;

import grid.Grid;
import helper.Vec2;

import java.util.ArrayList;
import java.util.Stack;

public class Maze {

    public Grid grid;
    public Vec2 startingLocation;
    public Vec2 goalLocation;

    public Maze(Grid grid, Vec2 startingLocation, Vec2 goalLocation) {
        this.grid = grid;
        this.startingLocation = startingLocation;
        this.goalLocation = goalLocation;
    }

    public Maze(String gn, int gw, int gh, Vec2 startingLocation, Vec2 goalLocation){
        grid = new Grid(gn, gw, gh);
        this.startingLocation = startingLocation;
        this.goalLocation = goalLocation;
    }

    public String toString(){
        return grid.getName();
    }

    public static Maze generateMaze(String gn, int gw, int gh,Vec2 startingLocation, Vec2 goalLocation){
        Grid newGrid = new Grid(gn, gw, gh);
        //Add walls everywhere
        for(int y = 0; y < gh; y++){
            for(int x = 0; x < gw; x++){
                for (int dir = 0; dir < 4; dir++){
                    newGrid.addWall(x,y,dir);
                }
            }
        }
        Stack<Vec2> stack = new Stack<>();
        ArrayList<Vec2> visited = new ArrayList<>();
        stack.push(goalLocation);
        visited.add(goalLocation);
        while(!stack.empty()){
            Vec2 currentCell = stack.pop();
            ArrayList<Vec2> neighbours = new ArrayList<>();
            ArrayList<Integer> neighbourDirs = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                Vec2 curNeighbour = newGrid.getNeighbourVec(currentCell.x, currentCell.y, i);
                switch (i){
                    case 0:
                        if(currentCell.y != 0 && curNeighbour.isNotIn(visited)){
                            neighbours.add(curNeighbour);
                            neighbourDirs.add(i);
                        }
                        break;
                    case 1:
                        if(currentCell.x != newGrid.getGridWidth() - 1 && curNeighbour.isNotIn(visited)){
                            neighbours.add(curNeighbour);
                            neighbourDirs.add(i);
                        }
                        break;
                    case 2:
                        if(currentCell.y != newGrid.getGridHeight() - 1 && curNeighbour.isNotIn(visited)){
                            neighbours.add(curNeighbour);
                            neighbourDirs.add(i);
                        }
                        break;
                    case 3:
                        if(currentCell.x != 0 && curNeighbour.isNotIn(visited)){
                            neighbours.add(curNeighbour);
                            neighbourDirs.add(i);
                        }
                        break;
                }

            }
            if(neighbours.size()!=0){
                stack.push(currentCell);
                int rand = (int)(Math.random()*neighbours.size());
                newGrid.removeWall(currentCell.x, currentCell.y, neighbourDirs.get(rand));
                visited.add(neighbours.get(rand));
                stack.push(neighbours.get(rand));
            }
        }
        return new Maze(newGrid,startingLocation, goalLocation);
    }


}
