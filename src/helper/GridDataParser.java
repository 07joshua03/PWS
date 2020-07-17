package helper;

import grid.Grid;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GridDataParser {

    private static final String fileName = "res/grids.txt";

    public static void writeGridToSave(Grid grid, int id) throws IOException{
        FileWriter fileWriter = new FileWriter(fileName, true);
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

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        boolean idFound = false;
        while(scanner.hasNextLine() && !idFound){
            String nextLine = scanner.nextLine();
            if(nextLine.startsWith(Integer.toString(id))){
                idFound = true;
                grid = parseLine(nextLine);
            }
        }
        return grid;
    }

    public static Grid parseLine(String line){
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
                        i++;
                    }
                    break;
                case 7:
                    for(int j = 0; j < 4; j++){
                        if(line.charAt(i) == '1') grid.addWall(xCoord,yCoord,j);
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
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()){
            String nextLine = scanner.nextLine();
            grids.add(parseLine(nextLine));
        }
        return grids;
    }

}
