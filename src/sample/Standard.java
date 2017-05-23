package sample;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Stephen on 5/19/2017.
 */
public class Standard {
    public final static int RectWidth = 500;
    public final static int memoryNum = 640;
    public final static int processNum = 320;
    public static Random random = new Random(400);

    //返回1~5的随机时间
    public static int randomTime(){
        int time = Math.abs((random.nextInt()) % 200) / 40 + 1;
        return time;
    }

    //返回1~200的随机数
    public static int randomSize(){
        int size = Math.abs(random.nextInt() % 200) + 1;
        return size;
    }
    public static Color getColor(int id){
        Color color = new Color(0, 0, 0, 0);
        if(id == 0)
            color = Color.CHOCOLATE;
        else if(id == 1)
            color = Color.GOLD;
        else if(id == 2)
            color = Color.GREENYELLOW;
        else if(id == 3)
            color = Color.PINK;
        else
            color = Color.CYAN;
        return color;
    }
    public static int Line = 20;
}
