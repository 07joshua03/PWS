package helper;

import grid.Grid;
import robot.Maze;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataParser {

    private static final String gridsFileName = "res/grids.txt";
    private static final String mazesFileName = "res/mazes.txt";

    public static void writeGridToSave(Grid grid, int id) throws IOException{
        FileWriter fileWriter = new FileWriter(gridsFileName, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.newLine();
        writer.write(id + ":\"" + grid.getName() + "\"," + grid.getGridWidth() + "," + grid.getGridHeight());
        for(int i = 0; i < grid.getGridWidth(); i++){
            for(int j = 0; j < grid.getGridHeight(); j++){
                if(grid.getRoom(i,j).hasWall()){
                    writer.write(",(" + i + "," + j);
                    for(int k = 0; k < 4; k++){
                        if(grid.getRoom(i,j).getWall(k)) writer.write("," + 1);
                        else writer.write("," + 0);
                    }
                    writer.write(")");
                }
            }
        }
        writer.close();
    }

    public static Grid readGridFromSave(int id) throws IOException{
        Grid grid = new Grid("", 0, 0);

        File file = new File(gridsFileName);
        Scanner scanner = new Scanner(file);
        boolean idFound = false;
        while(scanner.hasNextLine() && !idFound){
            String nextLine = scanner.nextLine();
            if(nextLine.startsWith(Integer.toString(id))){
                idFound = true;
                grid = parseGridLine(nextLine);
            }
        }
        return grid;
    }

    public static Grid parseGridLine(String line){
        Grid grid = new Grid("", 0, 0);
        int state = 0;
        StringBuilder gridNameBuilder = new StringBuilder();
        StringBuilder gridWidthBuilder = new StringBuilder();
        int gridWidth = 0;

        StringBuilder gridHeightBuilder = new StringBuilder();
        int gridHeight;

        StringBuilder xCoordBuilder = new StringBuilder();
        int xCoord = 0;
        StringBuilder yCoordBuilder = new StringBuilder();
        int yCoord = 0;

        for(int i = 0; i < line.length(); i++){
            char character = line.charAt(i);
            switch (state){
                case 0:
                    if(character == '\"') state++;
                    break;
                case 1:
                    if(character == '\"') {
                        state++;
                        String gridName = gridNameBuilder.toString();
                        grid.setGridName(gridName);
                        i++;
                    } else gridNameBuilder.append(character);
                    break;
                case 2:
                    if(character != ',') gridWidthBuilder.append(character);
                    else{
                        state++;
                        gridWidth = Integer.parseInt(gridWidthBuilder.toString());
                    }
                    break;
                case 3:
                    if(character != ',') gridHeightBuilder.append(character);
                    else{
                        state++;
                        gridHeight = Integer.parseInt(gridHeightBuilder.toString());
                        grid.changeGridSize(gridWidth, gridHeight);
                    }
                    break;
                case 4:
                    if(character == '(') state++;
                    break;
                case 5:
                    if(character != ',') xCoordBuilder.append(character);
                    else{
                        state++;
                        xCoord = Integer.parseInt(xCoordBuilder.toString());
                        xCoordBuilder.delete(0, xCoordBuilder.length());
                    }
                    break;
                case 6:
                    if(character != ',') yCoordBuilder.append(character);
                    else{
                        state++;
                        yCoord = Integer.parseInt(yCoordBuilder.toString());
                        yCoordBuilder.delete(0, yCoordBuilder.length());
                    }
                    break;
                case 7:
                    for(int j = 0; j < 4; j++){
                        if(line.charAt(i) == '1') {
                            grid.addWall(xCoord,yCoord,j);
                        }
                        if(j!=3) i+=2;
                    }
                    state = 4;
                    break;
            }
        }
        return grid;
    }

    public static ArrayList<Grid> readAllGridsFromSave() throws IOException{
        ArrayList<Grid> grids = new ArrayList<>();
        File file = new File(gridsFileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            grids.add(parseGridLine(nextLine));
        }
        return grids;
    }

    public static void writeMazeToSave(Maze maze, int id) throws IOException{
        FileWriter fileWriter = new FileWriter(mazesFileName, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.newLine();
        writer.write(id + ":\"" + maze.grid.getName() + "\"," + maze.grid.getGridWidth() + "," + maze.grid.getGridHeight() + ",(" + maze.startingLocation.x + "," + maze.startingLocation.y + "),(" + maze.goalLocation.x + "," + maze.goalLocation.y + ")");
        for(int i = 0; i < maze.grid.getGridWidth(); i++){
            for(int j = 0; j < maze.grid.getGridHeight(); j++){
                if(maze.grid.getRoom(i,j).hasWall()){
                    writer.write(",(" + i + "," + j);
                    for(int k = 0; k < 4; k++){
                        if(maze.grid.getRoom(i,j).getWall(k)) writer.write("," + 1);
                        else writer.write("," + 0);
                    }
                    writer.write(")");
                }
            }
        }
        writer.close();
    }

    public static Maze parseMazeLine(String line){
        Maze maze = new Maze("", 0, 0, new Vec2(0,0), new Vec2(0,0));
        int state = 0;
        StringBuilder gridNameBuilder = new StringBuilder();
        StringBuilder gridWidthBuilder = new StringBuilder();
        int gridWidth = 0;

        StringBuilder gridHeightBuilder = new StringBuilder();
        int gridHeight;

        StringBuilder startingLocationX = new StringBuilder();
        StringBuilder startingLocationY = new StringBuilder();

        StringBuilder goalLocationX = new StringBuilder();
        StringBuilder goalLocationY = new StringBuilder();

        StringBuilder xCoordBuilder = new StringBuilder();
        int xCoord = 0;
        StringBuilder yCoordBuilder = new StringBuilder();
        int yCoord = 0;

        for(int i = 0; i < line.length(); i++){
            char character = line.charAt(i);
            switch (state){
                case 0:
                    if(character == '\"') state++;
                    break;
                case 1:
                    if(character == '\"') {
                        state++;
                        String gridName = gridNameBuilder.toString();
                        maze.grid.setGridName(gridName);
                        i++;
                    } else gridNameBuilder.append(character);
                    break;
                case 2:
                    if(character != ',') gridWidthBuilder.append(character);
                    else{
                        state++;
                        gridWidth = Integer.parseInt(gridWidthBuilder.toString());
                    }
                    break;
                case 3:
                    if(character != ',') gridHeightBuilder.append(character);
                    else{
                        state++;
                        gridHeight = Integer.parseInt(gridHeightBuilder.toString());
                        maze.grid.changeGridSize(gridWidth, gridHeight);
                        i++;
                    }
                    break;
                case 4:
                    if(character != ',') startingLocationX.append(character);
                    else{
                        state++;
                        maze.startingLocation.x = Integer.parseInt(startingLocationX.toString());
                    }
                    break;
                case 5:
                    if(character != ')') startingLocationY.append(character);
                    else{
                        state++;
                        maze.startingLocation.y = Integer.parseInt(startingLocationY.toString());
                        i+=2;
                    }
                    break;
                case 6:
                    if(character != ',') goalLocationX.append(character);
                    else{
                        state++;
                        maze.goalLocation.x = Integer.parseInt(goalLocationX.toString());
                    }
                    break;
                case 7:
                    if(character != ')') goalLocationY.append(character);
                    else{
                        state++;
                        maze.goalLocation.y = Integer.parseInt(goalLocationY.toString());
                        i++;
                    }
                    break;

                case 8:
                    if(character == '(') state++;
                    break;
                case 9:
                    if(character != ',') xCoordBuilder.append(character);
                    else{
                        state++;
                        xCoord = Integer.parseInt(xCoordBuilder.toString());
                        xCoordBuilder.delete(0, xCoordBuilder.length());
                    }
                    break;
                case 10:
                    if(character != ',') yCoordBuilder.append(character);
                    else{
                        state++;
                        yCoord = Integer.parseInt(yCoordBuilder.toString());
                        yCoordBuilder.delete(0, yCoordBuilder.length());
                    }
                    break;
                case 11:
                    for(int j = 0; j < 4; j++){
                        if(line.charAt(i) == '1') {
                            maze.grid.addWall(xCoord,yCoord,j);
                        }
                        if(j!=3) i+=2;
                    }
                    state = 8;
                    break;
            }
        }
        return maze;
    }

    public static Maze readMazeFromSave(int id) throws IOException{
        Maze maze = new Maze("", 0, 0, new Vec2(0,0), new Vec2(0,0));

        File file = new File(mazesFileName);
        Scanner scanner = new Scanner(file);
        boolean idFound = false;
        while(scanner.hasNextLine() && !idFound){
            String nextLine = scanner.nextLine();
            if(nextLine.startsWith(Integer.toString(id))){
                idFound = true;
                maze = parseMazeLine(nextLine);
            }
        }
        return maze;
    }

    public static ArrayList<Maze> readAllMazesFromSave() throws IOException{
        ArrayList<Maze> mazes = new ArrayList<>();
        File file = new File(mazesFileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            mazes.add(parseMazeLine(nextLine));
        }
        return mazes;
    }
}
