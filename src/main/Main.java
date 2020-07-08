package main;

public class Main {

    public static boolean stopLoop = false;

    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow(1000,1025);
        Grid grid = new Grid(5,5);

        drawCLI(grid);



    }

    public static void drawCLI(Grid grid){
        for(int i = 0; i < grid.getGridHeight();i++){
            //Upper row(s) i.e. *-* * *-*-*
            System.out.print("*");
            for(int j = 0; j < grid.getGridWidth(); j++){

                if(grid.getRoom(j,i).getWall(Direction.up)) System.out.print("-*");
                else System.out.print(" *");
            }
            System.out.println();
            //Side row(s) i.e. | | | |   |
            for(int j = 0; j < grid.getGridWidth(); j++){
                if(grid.getRoom(j,i).getWall(Direction.left)) System.out.print("| ");
                else System.out.print("  ");
                if(j == grid.getGridWidth()-1 && grid.getRoom(j,i).getWall(Direction.right)) System.out.print("|");
            }

            System.out.println();
            //Last row i.e. *-* * *-*-*
            if(i == grid.getGridHeight()-1){
                System.out.print("*");
                for(int j = 0; j < grid.getGridWidth(); j++){
                    if(grid.getRoom(j,i).getWall(Direction.down)) System.out.print("-*");
                    else System.out.print(" *");
                }
            }

        }
    }


}
