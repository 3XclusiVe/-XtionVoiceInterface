package com.company.RecordingThread;

import com.company.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import javaFlacEncoder.FLACFileWriter;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordingThread extends Thread {

    private double Silence = 0.6;
    private double CurrentNoiseLevel;

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

        int TimeInMsToCalculateNoiseLevel = 1000;
        System.out.println("Start Calculating Noise Level");
        CurrentNoiseLevel =  CalculateNoiseLevel(TimeInMsToCalculateNoiseLevel);
        System.out.println("Noise Level = " + CurrentNoiseLevel);

    }

    private double CalculateNoiseLevel(int TimeInMsToCalculateNoiseLevel) {

        tempAudioFile = new File("DisplayAllReponce.flac");
        microphone.setAudioFile(tempAudioFile);
        try {
            microphone.captureAudioToFile(microphone.getAudioFile());
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        int SampleTime = 100;
        int SampleCount = 0;
        double NoiseLevel = 0;
        int CurrentTimeInMs = 0;

        while (CurrentTimeInMs <= TimeInMsToCalculateNoiseLevel) {

            double magnitude =  microphone.magnitude(120, 122);
            try {
                Thread.currentThread().sleep(SampleTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SampleCount++;
            NoiseLevel += magnitude;
            CurrentTimeInMs += SampleTime;

        }

        NoiseLevel = NoiseLevel / SampleCount;

        microphone.getAudioFile().delete();


        return NoiseLevel;
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
                tempAudioFile = new File("DisplayAllReponce.flac");
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
                System.out.println(magnitude);
                //boolean isSpeaking = (volume > minimumVolumeToStartrecording);
                boolean isSpeaking = (magnitude > 200);

                if (isSpeaking) {

                    DebugLog("Start RECORDING...");

                    do {
                        DebugLog("RECORDING proc...");
                        Thread.sleep(sampleTime);//Updates every second
                    } while (microphone.magnitude(120, 122) > 100);


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
