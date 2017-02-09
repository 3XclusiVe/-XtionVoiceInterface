package Tests;

import com.apple.eawt.AppEvent;
import com.company.CommandsRecognizers.CommandsListener;

/**
 * Created by user on 06.02.17.
 */
public class TestCommandCounterAndStatistic implements CommandsListener {

    private int correctCommandCounter = 0;
    private int commandCounter = 0;

    
    public void onStartTraining() {
        correctCommandCounter += 1;
        commandCounter+=1;
    }

    
    public void onPoseType(String name) {
        commandCounter+=1;
    }

    
    public void onCapturePose() {
        commandCounter+=1;
    }

    
    public void onCompleteTraining() {
        commandCounter+=1;
    }

    
    public void noCommand() {
        commandCounter+=1;
    }

    public void printStats() {
        System.out.println("Точность распознавания: " + correctCommandCounter / commandCounter * 100);
    }
}
