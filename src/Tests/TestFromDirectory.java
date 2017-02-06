package Tests;

import com.company.CommandsRecognizers.CommandsRecognizer;
import com.company.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import javaFlacEncoder.FLACFileWriter;

import java.io.File;
import java.io.IOException;

/**
 * Created by user on 06.02.17.
 */
public class TestFromDirectory {

    public static String pathToDirectoryWithAudio = "";

    public static CommandsRecognizer commandsRecognizer = new CommandsRecognizer();

    public static TestCommandCounterAndStatistic testCommandCounterAndStatistic = new TestCommandCounterAndStatistic();

    public static void main (String[]args) throws IOException {

        commandsRecognizer.addListener(testCommandCounterAndStatistic);

        File f = new File(pathToDirectoryWithAudio);
        File[] directory = f.listFiles();

        for (File file : directory) {
            testFromFile(file);
        }

        testCommandCounterAndStatistic.printStats();

    }

    public static void testFromFile(File audioFile) throws IOException {

        MicrophoneAnalyzer mic = new MicrophoneAnalyzer(FLACFileWriter.FLAC);

        mic.setAudioFile(audioFile);

        Recognizer rec = new Recognizer(Recognizer.Languages.RUSSIAN, "AIzaSyDMRFZsdncfP2udmTbozAQ2owJuL5RRm34");
        GoogleResponse response = rec.getRecognizedDataForFlac(mic.getAudioFile(), 10);

        commandsRecognizer.onResponce(response);

    }

}
