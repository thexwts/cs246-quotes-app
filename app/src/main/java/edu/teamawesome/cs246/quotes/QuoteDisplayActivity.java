package edu.teamawesome.cs246.quotes;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuoteDisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_display);
// FIGURE OUT HOW TO MAKE THIS GO BACK EXACTLY ONE TO WHAT CALLED IT
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        // THIS IS FLAWED I KNOW - this assumes that the list the user clicked on is an exhaustive list of all the
        // quotes, and thus the index of the list lines up perfectly with our QuotesList, but if it were a search
        // then the list would only include some of the items and the index of the item on the ListView wouldn't
        // match up with the actual index of the quote in the list - FOR THIS TEST IT WORKS, BUT NOT FOR THE REAL THING
        int quoteIndex = intent.getIntExtra(Quote.QUOTE_LIST_INDEX, 0);

        // get the textView used to display the title
        TextView quoteTitleView = (TextView) findViewById(R.id.quote_title);
        quoteTitleView.setText(Quote.getQuotesList().get(quoteIndex).getTitle());

        // get the textView used to display the quote
        TextView quoteTextView = (TextView) findViewById(R.id.quote_text);
        quoteTextView.setText(Quote.getQuotesList().get(quoteIndex).getQuote());

        // get the textView used to display the tags
        TextView quoteTagsView = (TextView) findViewById(R.id.quote_tags);
        quoteTagsView.setText(Quote.getQuotesList().get(quoteIndex).getTag());

        // these experiments where from before I figured out how to edit existing views in the layout
        // these were attempts at understanding how I could customize a view before adding it
        // but creating the views in the layout xml files is much easier so I decided to do it all there
        // and simply set the text values from here
//        quoteTextView.setBackgroundColor(Color.CYAN);
//        quoteTextView.setTextSize(20);
//        quoteTextView.setPadding(10,10,10,10);
//        quoteTextView.setWidth(findViewById(R.id.display_quote).getWidth());


        // we don't need to add a view because we are grabbing existing views and editing them above
        // send the textView to the screen
//        LinearLayout displayQuoteLayout = (LinearLayout) findViewById(R.id.activity_quote_display);
//        displayQuoteLayout.addView(quoteTextView);
    }
}
