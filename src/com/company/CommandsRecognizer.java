package com.company;

import com.darkprograms.speech.recognizer.GoogleResponse;
import info.debatty.java.stringsimilarity.JaroWinkler;

import java.util.List;

/**
 * Created by user on 21.11.16.
 */
public class CommandsRecognizer implements ResponseListener {

    JaroWinkler jaroWinkler;

    private static final String startTraining = "начать обучение";
    private static final String poseType = "название новой позы";
    private static final String capturePose = "фиксация";
    private static final String completeTraining = "закончить обучение";


    public CommandsRecognizer() {
        jaroWinkler = new JaroWinkler();
    }


    @Override
    public void onResponce(GoogleResponse response) {
        List<String> responces =  response.getOtherPossibleResponses();

        double startTrainingSimilarity = 0;
        double poseTypeSimilarity = 0;
        double capturePoseSimilarity = 0;
        double completeTrainingSimilarity = 0;


        for(String responce : responces ) {
            if(responce != null) {

                if(startTrainingSimilarity < jaroWinkler.similarity(responce, startTraining)) {
                    startTrainingSimilarity = jaroWinkler.similarity(responce, startTraining);
                }

                if(poseTypeSimilarity < jaroWinkler.similarity(responce, poseType)) {
                    poseTypeSimilarity = jaroWinkler.similarity(responce, poseType);
                }

                if(capturePoseSimilarity < jaroWinkler.similarity(responce, capturePose)) {
                    capturePoseSimilarity = jaroWinkler.similarity(responce, capturePose);
                }

                if(completeTrainingSimilarity < jaroWinkler.similarity(responce, completeTraining)) {
                    completeTrainingSimilarity = jaroWinkler.similarity(responce, completeTraining);
                }
            }
        }

        System.out.println(startTrainingSimilarity);
        System.out.println(poseTypeSimilarity);
        System.out.println(capturePoseSimilarity);
        System.out.println(completeTrainingSimilarity);
    }
}
