package main;

import config.ConfigWindow;
import grid.Grid;
import helper.Direction;
import helper.GridDataParser;

import java.io.IOException;

public class Main {

    public static boolean stopLoop = false;
    public static MainWindow mainWindow;
    public static Grid grid;

    public static void main(String[] args){
        mainWindow = new MainWindow(1000,1025);
        ConfigWindow configWindow = new ConfigWindow(200,400);
        grid = new Grid("Test0", 5,5);
        //drawCLI(grid);
        mainWindow.update(grid);
        try{
            //GridDataParser.writeGridToSave(grid, 2);
            drawCLI(GridDataParser.readGridFromSave(2));
        } catch(IOException e){
            e.printStackTrace();
        }
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
