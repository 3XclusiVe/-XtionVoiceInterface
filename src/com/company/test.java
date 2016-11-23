package com.company;

/**
 * Created by user on 21.11.16.
 */
public class test implements CommandsListener {
    @Override
    public void onStartTraining() {
        System.out.println("**************");
        System.out.println("Start Training");
        System.out.println("**************");
    }

    @Override
    public void onPoseType(String name) {
        System.out.println("**************");
        System.out.println(name);
        System.out.println("**************");
    }

    @Override
    public void onCapturePose() {
        System.out.println("**************");
        System.out.println("Pose Fixation");
        System.out.println("**************");
    }

    @Override
    public void onCompleteTraining() {
        System.out.println("**************");
        System.out.println("End Training");
        System.out.println("**************");
    }
}
