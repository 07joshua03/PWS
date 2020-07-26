package robot;

import grid.Grid;
import helper.Vec2;

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


}
