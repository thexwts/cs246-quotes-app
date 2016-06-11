package edu.groupawesome.quotetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String title = null;
        String author = "William Shakespeare";
        String quoteTextHuge = "Cowards die many times before their deaths; The valiant never taste " +
                "of death but once.\nOf all the wonders that I yet have heard, it seems to me most " +
                "strange that men should fear;\nSeeing that death, a necessary end, will come when " +
                "it will come";
        String quoteTextLong = "To thine own self be true, and it must follow, as the night " +
                "the day, thou canst not then be false to any man.";
        String quoteTextSixWords = "I have not slept one wink.";
        String quoteTextFiveWords = "Nothing will come of nothing.";
        String quoteTextShort = "Et tu, Brute!";
        String quoteTextNewLine = "Haikus are tricky\nI'm not very good at them\nRefrigerator";

        Quote quoteHuge = new Quote(title, author, quoteTextHuge);
        Quote quoteLong = new Quote(title, author, quoteTextLong);
        Quote quoteSix = new Quote(title, author, quoteTextSixWords);
        Quote quoteFive = new Quote(title, author, quoteTextFiveWords);
        Quote quoteShort = new Quote(title, author, quoteTextShort);
        Quote quoteNewLine = new Quote(title, "Ethan Stewart", quoteTextNewLine);
    }
}

// Aaron: This is my comment to push!!!
// Ethan: everything is awesoooooooooooooome...
// Reed commented here.