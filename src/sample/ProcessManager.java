package sample;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by Stephen on 5/19/2017.
 */
public class ProcessManager implements Runnable{
    private boolean[] memoryPool;
    private ArrayList<_Process> processPool;
    private ArrayList<_Process> onProcess;
    private int algorithm = 0;
    private ArrayList<String> logContent;

    ProcessManager(int algorithm){
        logContent = new ArrayList<String>();
        this.algorithm = algorithm;
        memoryPool = new boolean[Standard.memoryNum];
        processPool = new ArrayList<_Process>();
        onProcess = new ArrayList<_Process>();
        for(int i = 0; i < Standard.memoryNum; i++)
            memoryPool[i] = false;
        for(int i = 0; i < Standard.processNum; i++){
            _Process process = new _Process(i, Standard.randomTime(), Standard.randomSize(), -1);
            processPool.add(process);
        }
    }

    public void setAlgorithm(int type){
        algorithm = type;
    }
    //首次适应算法
    public int firstSearch(_Process process){
        int target = -1;
        int mem = 0;
        for(int i = 0; i < Standard.memoryNum; i++){
            if(memoryPool[i] == false){
                if(mem == 0){
                    target = i;
                }
                mem++;
                if(mem == process.size){
                    return target;
                }
            }
            else {
                mem = 0;
            }
        }
        return -1; //未找到
    }

    public int BestSearch(_Process process){
        ArrayList<Integer> target = new ArrayList<Integer>();
        ArrayList<Integer> space = new ArrayList<Integer>();
        int _targer = -1;
        int mem = 0;
        for(int i = 0; i < Standard.memoryNum; i++){
            if(memoryPool[i] == false){
                if(mem == 0){
                    _targer = i;
                }
                mem++;
                if(i == Standard.memoryNum - 1){
                    space.add(mem);
                    target.add(_targer);
                    mem = 0;
                    _targer = -1;
                }
            }
            else {
                if(mem != 0){
                    space.add(mem);
                    target.add(_targer);
                    mem = 0;
                    _targer = -1;
                }
            }
        }
        for (int i = 0; i < space.size(); i++){
            if(space.get(i) >= process.size){
                if(mem == 0){
                    mem = space.get(i);
                    _targer = target.get(i);
                }
                else if(i > 0 && mem > space.get(i)){
                    mem = space.get(i);
                    _targer = target.get(i);
                }
            }

        }
        return _targer;
    }

    public void addProcess(_Process process){
        for(int i = 0; i < process.size; i++){
            memoryPool[i + process.Target] = true;
        }
        RectAnimation.showUp(Controller.block.get(process.processId), process.size, process.Target, process.processId);
        Controller.processNumLabel.get(0).setVisible(false);
        Controller.processNumLabel.remove(0);
        if(Controller.processNumLabel.size() > 0)
            Controller.processNumLabel.get(0).setVisible(true);
        addLog(process.processId, process.Target, process.size);
    }

    public void deleteProcess(_Process process){
        for(int i = 0; i < process.size; i++){
            memoryPool[i + process.Target] = false;
        }
        RectAnimation.Delete(Controller.block.get(process.processId));
        deleteLog(process.processId, process.Target, process.size);
    }

    public void run(){
        while (processPool.size() > 0){
            try{
                for(int i =0; i < onProcess.size(); i++){
                    onProcess.get(i).runTime--;
                    if(onProcess.get(i).runTime <= 0){
                        deleteProcess(onProcess.get(i));
                        Thread.sleep(300);
                        onProcess.remove(i);
                    }
                }
                Thread.sleep(600);
                int target = -1;
                if(algorithm == 1)
                    target = firstSearch(processPool.get(0));
                else if(algorithm == 2){
                    target = BestSearch(processPool.get(0));
                }
                if(target >= 0){
                    processPool.get(0).Target = target;
                    addProcess(processPool.get(0));
                    onProcess.add(processPool.get(0));
                    Thread.sleep(300);
                    processPool.remove(0);
                }
                Thread.sleep(600);
            }catch (Exception e){
                System.err.println("Error!");
            }
        }
    }

    public void addLog(int processId, int target, int size){
        String create = "Create Process : " + processId + " between " + target + " and " + size;
        refresh();
        Controller.text.get(0).setText(create);
        Controller.text.get(0).setFill(Color.BLUE);
    }

    public void deleteLog(int processId, int target, int size){
        String delete = "Delete Process : " + processId + " between " + target + " and " + size;
        refresh();
        Controller.text.get(0).setText(delete);
        Controller.text.get(0).setFill(Color.RED);
    }

    public void refresh(){
        for(int i = Standard.Line - 1; i >= 1; i--){
            Controller.text.get(i).setText(Controller.text.get(i - 1).getText());
            Controller.text.get(i).setFill(Controller.text.get(i - 1).getFill());
        }
    }
}