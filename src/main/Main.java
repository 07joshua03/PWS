package main;

import config.ConfigWindow;
import grid.Grid;
import helper.Direction;
import helper.GridDataParser;
import helper.Vec2;
import robot.Robot;

import java.io.IOException;

public class Main {

    public static boolean stopLoop = false;
    public static MainWindow mainWindow;
    public static Grid grid;

    public static void main(String[] args){
        try{
            grid = GridDataParser.readGridFromSave(5);
        } catch(IOException e){
            e.printStackTrace();
        }
        Robot robot = new Robot(new Vec2(0,0), new Vec2( 4,4), grid.getGridWidth(), grid.getGridHeight());
        robot.setSeenGrid(grid);
        robot.floodfill();
        drawCLI(grid);
        initGUI();

    }

    public static void initGUI(){
        mainWindow = new MainWindow(1000,1025);
        ConfigWindow configWindow = new ConfigWindow(200,400);
        mainWindow.update(grid);
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
