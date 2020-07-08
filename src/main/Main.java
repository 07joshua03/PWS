package main;

public class Main {

    public static boolean stopLoop = false;

    public static void main(String[] args){
        MainWindow mainWindow = new MainWindow(1000,800);

        while(!stopLoop){
            mainWindow.update();
        }

    }

}
