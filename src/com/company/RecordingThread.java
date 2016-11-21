package com.company;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import javaFlacEncoder.FLACFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 20.11.16.
 */
public class RecordingThread extends Thread {

    private String apiKey = "AIzaSyDMRFZsdncfP2udmTbozAQ2owJuL5RRm34";

    private boolean debug = true;

    private int minimumVolumeToStartrecording = 40;
    private int volumeToStopRecording = 20;
    private int checkVolumeSampleTime = 10;
    private int sampleTime = 1000;

    private MicrophoneAnalyzer microphone;
    private File tempAudioFile;
    private Recognizer recognizer;

    private List<ResponseListener> listeners;

    public RecordingThread() {

        microphone = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
        tempAudioFile = new File("temp.flac");
        microphone.setAudioFile(tempAudioFile);
        recognizer = new Recognizer(Recognizer.Languages.RUSSIAN, apiKey);
        listeners = new ArrayList<ResponseListener>();

    }

    public void addResponceListener(ResponseListener listener) {
        listeners.add(listener);
    }

    @Override
    public void run() {

        while (true) {
            microphone.open();

            try {
                microphone.captureAudioToFile(microphone.getAudioFile());
                Thread.sleep(checkVolumeSampleTime * 3);

                int volume = microphone.getAudioVolume(checkVolumeSampleTime);
                boolean isSpeaking = (volume > minimumVolumeToStartrecording);

                if (isSpeaking) {

                    DebugLog("RECORDING...");

                    do {
                        Thread.sleep(sampleTime);//Updates every second
                    } while (microphone.getAudioVolume(sampleTime) > volumeToStopRecording);


                    DebugLog("Recording Complete!");
                    DebugLog("Recognizing...");

                    GoogleResponse response = recognizer.getRecognizedDataForFlac(microphone.getAudioFile(), 3);
                    notifyListeners(response);

                    DebugLog("Looping back");//Restarts loops

                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Error Occured");
            } finally {
                microphone.close();//Makes sure microphone closes on exit.
            }
        }

    }

    private void notifyListeners (GoogleResponse response) {
        for(ResponseListener listener : listeners) {
            if(listener != null) {
                listener.onResponce(response);
            }
        }
    }


    private void DebugLog(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

}
