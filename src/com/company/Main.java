package com.company;

import com.company.CommandsRecognizers.CommandsRecognizer;
import com.company.CommandsRecognizers.DisplayAllReponce;
import com.company.RecordingThread.RecordingThread;

public class Main {

    public static void main (String[] args) {
        RecordingThread recordingThread = new RecordingThread();
        CommandsRecognizer commandsRecognizer = new CommandsRecognizer();

        recordingThread.addResponceListener(new DisplayAllReponce());
        recordingThread.addResponceListener(commandsRecognizer);

        commandsRecognizer.addListener(new testInterfaceCommandsListener());

        recordingThread.start();
    }
}