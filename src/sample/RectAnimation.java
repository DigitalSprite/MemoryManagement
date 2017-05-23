package sample;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by Stephen on 5/20/2017.
 */
public class RectAnimation {
    public static void showUp(Rectangle block, int size, int target, int id){
        block.setFill(Standard.getColor(id % 5));
        block.setY(target);
        block.setHeight(size);
        FadeTransition ft = new FadeTransition(Duration.millis(600), block);
        ft.setFromValue(0.0);
        block.setVisible(true);
        ft.setToValue(1.0);
        ft.play();
    }
    public static void Delete(Rectangle block){
        FadeTransition ft = new FadeTransition(Duration.millis(600), block);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }
}
