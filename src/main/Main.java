package main;

import config.ConfigWindow;
import grid.Grid;
import helper.Direction;
import helper.DataParser;
import helper.Vec2;
import robot.Maze;
import robot.Robot;

import java.io.IOException;

public class Main {

    public static MainWindow mainWindow;
    public static Robot robot;
    public static ConfigWindow configWindow;
    public static Maze maze;

    public static void main(String[] args){
        try{
            maze = DataParser.readMazeFromSave(0);
            //DataParser.writeMazeToSave(maze, 0);
            drawCLI(maze.grid);
        } catch(IOException e){
            e.printStackTrace();
        }
        robot = new Robot(maze);
        solve();
        robot.drawCLIFloodfill();
        initGUI();
        mainWindow.update();
        //mainWindow.mainPanel.drawableObjects.add(grid);
    }

    public static void solve(){
        robot.setSeenGrid(maze.grid);
        robot.startLocation = maze.startingLocation;
        robot.goalLocation = maze.goalLocation;
        robot.reset();

        //mainWindow.mainPanel.drawableObjects.add(robot);
        robot.floodfill();
        while(!robot.isGoalReached()){

            robot.update();
            //robot.drawCLIFloodfill();

        }
    }

    public static void initGUI(){

        mainWindow = new MainWindow(800,800);
        configWindow = new ConfigWindow(200,400);
        configWindow.setResizable(false);
    }

    public static void drawCLI(Grid grid){
        System.out.println();
        for(int i = 0; i < grid.getGridHeight();i++){
            //Upper row(s) i.e. *-* * *-*-*
            System.out.print("*");
            for(int j = 0; j < grid.getGridWidth(); j++){

                if(grid.getRoom(j,i).getWall(Direction.up)) System.out.print("---*");
                else System.out.print("   *");
            }
            System.out.println();
            //Side row(s) i.e. | | | |   |
            for(int j = 0; j < grid.getGridWidth(); j++){
                if(grid.getRoom(j,i).getWall(Direction.left)) System.out.print("|   ");
                else System.out.print("    ");
                if(j == grid.getGridWidth()-1 && grid.getRoom(j,i).getWall(Direction.right)) System.out.print("|");
            }

            System.out.println();
            //Last row i.e. *-* * *-*-*
            if(i == grid.getGridHeight()-1){
                System.out.print("*");
                for(int j = 0; j < grid.getGridWidth(); j++){
                    if(grid.getRoom(j,i).getWall(Direction.down)) System.out.print("---*");
                    else System.out.print("   *");
                }
            }

        }
    }


}
