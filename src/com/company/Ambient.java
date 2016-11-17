package com.company;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import javaFlacEncoder.FLACFileWriter;

import java.io.File;

/**
 * Created by user on 16.11.16.
 */
public class Ambient {

    public static void main(String[] args) {

        MicrophoneAnalyzer mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
        mic.setAudioFile(new File("AudioTestNow.flac"));
        while(true){
            mic.open();
            final int THRESHOLD = 20;
            int volume = mic.getAudioVolume(10);
            boolean isSpeaking = (volume > THRESHOLD);

            if(isSpeaking){
                try {
                    System.out.println("RECORDING...");
                    mic.captureAudioToFile(mic.getAudioFile());//Saves audio to file.
                    do{
                        Thread.sleep(2000);//Updates every second
                    }
                    while(mic.getAudioVolume(100) > THRESHOLD);
                    System.out.println("Recording Complete!");
                    System.out.println("Recognizing...");
                    Recognizer rec = new Recognizer(Recognizer.Languages.RUSSIAN, "AIzaSyDMRFZsdncfP2udmTbozAQ2owJuL5RRm34");
                    GoogleResponse response = rec.getRecognizedDataForFlac(mic.getAudioFile(), 3);
                    displayResponse(response);//Displays output in Console

                    reckognizeResponce(response);

                    System.out.println("Looping back");//Restarts loops
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("Error Occured");
                }
                finally{
                    mic.close();//Makes sure microphone closes on exit.
                }
            }
        }
    }

    private static void displayResponse(GoogleResponse gr){
        if(gr.getResponse() == null){
            System.out.println((String)null);
            return;
        }
        System.out.println("Google Response: " + gr.getResponse());
        System.out.println("Google is " + Double.parseDouble(gr.getConfidence())*100 + "% confident in"
                + " the reply");
        System.out.println("Other Possible responses are: ");
        for(String s: gr.getOtherPossibleResponses()){
            System.out.println("\t" + s);
        }
    }

    private static void reckognizeResponce(GoogleResponse gr){
        if(gr.getResponse() == null){
            System.out.println((String)null);
            return;
        }

        VoiceCommands vc = new VoiceCommands();

        vc.isCommand(gr.getResponse());


        for(String s: gr.getOtherPossibleResponses()){
            vc.isCommand(s);
        }
    }

}
