package helper;

import java.util.ArrayList;

public class Vec2 {

    public int x;
    public int y;

    public Vec2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isIn(ArrayList<Vec2> list){
        for (Vec2 i:list) {
            if(this.x == i.x && this.y == i.y) return true;
        }
        return false;
    }


}
