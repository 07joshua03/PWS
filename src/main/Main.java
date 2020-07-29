package main;

import config.ConfigWindow;
import helper.DataParser;
import robot.Maze;
import robot.Robot;

import java.io.IOException;

public class Main {

    public static MainWindow mainWindow;
    public static Robot robot;
    public static ConfigWindow configWindow;
    public static Maze maze;
    public static boolean shouldSolve = true;
    private static final int FPS = 60;
    private static final int SKIP_TICKS = 1000 / FPS;
    private static final int MAX_FRAMESKIP = 5;

    public static void main(String[] args){
        init();
        loop();
    }

    public static void init(){
        try{
            maze = DataParser.readMazeFromSave(0);
        } catch(IOException e){
            e.printStackTrace();
        }
        robot = new Robot(maze);
        configWindow = new ConfigWindow(200,400);
        configWindow.setResizable(false);
        mainWindow = new MainWindow(800,800);

    }

    public static void loop(){
        //boolean complete = false;
        while(true){
            if(shouldSolve){
                solve();
            }
            render();
        }
    }

    public static void solve(){
        shouldSolve = false;

        robot.totalGrid = maze.grid;
        robot.startLocation = maze.startingLocation;
        robot.goalLocation = maze.goalLocation;
        robot.reset();
        robot.floodfill();

        //LOOP
        double next_game_tick = System.currentTimeMillis();
        int loops;
        while(!robot.isGoalReached()){
            loops = 0;
            while (System.currentTimeMillis() > next_game_tick && loops < MAX_FRAMESKIP) {

                robot.update();
                next_game_tick += SKIP_TICKS;
                loops++;
            }
            //double interpolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick / (double) SKIP_TICKS);
            render();
        }
    }

    public static void render(){
        mainWindow.mainPanel.renderPanel();
    }

}
