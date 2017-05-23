package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public AnchorPane anchor;
    @FXML
    public VBox MemoryPool;
    @FXML
    public Button firstSubmit;
    @FXML
    public Button bestSubmit;
    @FXML
    public VBox Log;

    private boolean begin;
    public static ArrayList<Rectangle> block;
    public static ArrayList<Label> processNumLabel;
    public static ArrayList<Text> text;
    private ProcessManager processManager;

    @FXML
    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        text = new ArrayList<Text>();
        for(int i = 0; i < Standard.Line; i++){
            text.add(new Text());
            text.get(i).setFont(Font.font("Microsoft YaHei UI Light", 20));
            Log.getChildren().add(text.get(i));
        }
        processManager = new ProcessManager(0);
        processNumLabel = new ArrayList<Label>();
        Group group1 = new Group();
        for(int i = 0; i < Standard.processNum; i++){
            processNumLabel.add(new Label(String.valueOf(Standard.processNum - i)));
            processNumLabel.get(i).setFont(Font.font("Microsoft YaHei UI", 32));
            processNumLabel.get(i).setLayoutX(1470);
            processNumLabel.get(i).setLayoutY(670);
            processNumLabel.get(i).setVisible(false);
            processNumLabel.get(i).setTextFill(Color.ORANGERED);
            group1.getChildren().add(processNumLabel.get(i));
        }
        processNumLabel.get(0).setVisible(true);
        anchor.getChildren().add(group1);
        begin = false;
        block = new ArrayList<Rectangle>();
        Group group = new Group();
        for(int i = 0; i < Standard.processNum; i++){
            block.add(new Rectangle());
            block.get(i).setVisible(false);
            block.get(i).setWidth(Standard.RectWidth);
            block.get(i).setHeight(100);
            block.get(i).setY(0);
            group.getChildren().add(block.get(i));
        }
        MemoryPool.getChildren().add(group);

        firstSubmit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(begin == false){
                    startProcess(1);
                    begin = true ;
                }
            }
        });
        bestSubmit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(begin == false){
                    startProcess(2);
                    begin = true;
                }
            }
        });
    }

    private void startProcess(int type){
        processManager.setAlgorithm(type);
        Thread thread = new Thread(processManager);
        thread.start();
    }
}
