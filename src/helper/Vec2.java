package helper;

import java.util.ArrayList;

public class Vec2 {

    public int x;
    public int y;

    public Vec2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isNotIn(ArrayList<Vec2> list){
        for (Vec2 vector:list) {
            if(vector.x == x && vector.y == y) return false;
        }
        return true;
    }


}
