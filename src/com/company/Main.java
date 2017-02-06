package com.company;

import com.company.CommandsRecognizers.CommandsRecognizer;
import com.company.CommandsRecognizers.DisplayAllReponce;
import com.company.RecordingThread.RecordingThread;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main (String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        RecordingThread recordingThread = new RecordingThread();
        CommandsRecognizer commandsRecognizer = new CommandsRecognizer();

        recordingThread.addResponceListener(new DisplayAllReponce());
        recordingThread.addResponceListener(commandsRecognizer);

        commandsRecognizer.addListener(new testInterfaceCommandsListener());

        recordingThread.start();
    }
}