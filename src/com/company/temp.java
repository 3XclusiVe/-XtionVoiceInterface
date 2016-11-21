package com.company;

import com.darkprograms.speech.recognizer.GoogleResponse;

/**
 * Created by user on 21.11.16.
 */
public class temp implements ResponseListener {
    @Override
    public void onResponce(GoogleResponse response) {

        displayResponse(response);

    }


    private static void displayResponse(GoogleResponse gr) {
        if (gr.getResponse() == null) {
            System.out.println((String) null);
            return;
        }
        System.out.println("Google Response: " + gr.getResponse());
        System.out.println("Google is " + Double.parseDouble(gr.getConfidence()) * 100 + "% confident in"
                + " the reply");
        System.out.println("Other Possible responses are: ");
        for (String s : gr.getOtherPossibleResponses()) {
            System.out.println("\t" + s);
        }
    }
}
