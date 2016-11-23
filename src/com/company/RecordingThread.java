package com.company;

import com.company.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import javaFlacEncoder.FLACFileWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordingThread extends Thread {

    private String apiKey = "AIzaSyDMRFZsdncfP2udmTbozAQ2owJuL5RRm34";

    private boolean debug = true;

    private int minimumVolumeToStartrecording = 40;
    private int volumeToStopRecording = 20;
    private int maxSamples = 50;
    private int checkVolumeSampleTime = 100;
    private int sampleTime = 100;

    private MicrophoneAnalyzer microphone;
    private File tempAudioFile;
    private Recognizer recognizer;

    private List<ResponseListener> listeners;

    public RecordingThread() {

        microphone = new MicrophoneAnalyzer(FLACFileWriter.FLAC);

        recognizer = new Recognizer(Recognizer.Languages.RUSSIAN, apiKey);
        listeners = new ArrayList<ResponseListener>();

    }

    public void addResponceListener(ResponseListener listener) {
        listeners.add(listener);
    }

    @Override
    public void run() {

        int curSample = 0;

        while (true) {
            microphone.open();

            try {
                tempAudioFile = new File("temp.flac");
                microphone.setAudioFile(tempAudioFile);
                microphone.captureAudioToFile(microphone.getAudioFile());

                /*double dTms = 32;
                int bytes = microphone.getNumOfBytes(dTms/1000);
                int freq = microphone.getFrequency(bytes);
                int vol = microphone.getAudioVolume((int) dTms);
                System.out.println(freq + " " + vol);*/

                Thread.sleep(checkVolumeSampleTime * 3);

                double magnitude =  microphone.magnitude(120, 122);

                int volume = microphone.getAudioVolume(checkVolumeSampleTime);
                //System.out.println(volume);
                //boolean isSpeaking = (volume > minimumVolumeToStartrecording);
                boolean isSpeaking = (magnitude > 100);

                if (isSpeaking) {

                    DebugLog("Start RECORDING...");

                    do {
                        DebugLog("RECORDING proc...");
                        Thread.sleep(sampleTime);//Updates every second
                    } while (microphone.magnitude(120, 122) > 50);


                    DebugLog("Recording Complete!");
                    microphone.close();
                    //Thread.sleep(9000);

                    DebugLog("Recognizing...");

                    GoogleResponse response = recognizer.getRecognizedDataForFlac(microphone.getAudioFile(), 3);
                    notifyListeners(response);

                    DebugLog("Looping back");//Restarts loops


                }
                microphone.getAudioFile().delete();


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
