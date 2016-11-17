package com.company;

import info.debatty.java.stringsimilarity.JaroWinkler;

/**
 * Created by user on 17.11.16.
 */
public class VoiceCommands {

    private static final String startTraining = "начать";
    private static final String poseType = "новая поза";
    private static final String capturePose = "фиксация";
    private static final String completeTraining = "закончить";

    private JaroWinkler  jaroWinkler = new JaroWinkler();

    public boolean isCommand(String command) {

        if(isLooksLike(command, startTraining)) {
            System.out.println(startTraining);
        }

        if(isLooksLike(command, poseType)) {
            System.out.println(poseType);
        }

        if(isLooksLike(command, capturePose)) {
            System.out.println(capturePose);
        }

        if(isLooksLike(command, completeTraining)) {
            System.out.println(completeTraining);
        }

        return false;

    }

    private boolean isLooksLike(String command, String etalon) {

        double tresHold = 0.7;
        double similaruty = jaroWinkler.similarity(command, etalon);

        if(similaruty >= tresHold) {
            return true;
        }

        return false;
    }

}
