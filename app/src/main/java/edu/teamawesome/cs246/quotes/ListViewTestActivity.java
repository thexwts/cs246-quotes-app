package edu.teamawesome.cs246.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListViewTestActivity extends ActionBarActivity {

    // The view we will be working with in this activity
    private ListView quotesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quotesListView = (ListView) findViewById(R.id.list_view_test);

        populateListView();
        registerClickCallback();
    }

    private void populateListView() {

        /* This was some early test code, here for posterity's sake
        List<String> stringsArray = new ArrayList<String>();
        for ( int i = 0; i < 15; i++){
            stringsArray.add("Hello" + i );
        }

        String[] stringsArray = {"Hello", "there", "test", "four"};

        quotesArray = new ArrayList<Quote>();

        for ( int i = 0; i < 15; i++){
            Quote newQuote = new Quote("Hello" + (i+1), "Tag");
            Quote.quotesList.add(newQuote);
        }
        */

        // we may want a new array adapter that can take Quotes
        // display a list of the Titles of the Quotes
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_text, Quote.getQuoteTitlesList());

        quotesListView.setAdapter(adapter);
    }

    private void registerClickCallback() {

        // create a listener to check for the item clicked
        quotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Intent showQuoteIntent = new Intent(ListViewTestActivity.this, QuoteDisplayActivity.class);

                // Here I am sending an int representing which item in the list was clicked.
                // THIS IS FLAWED I KNOW - this assumes that the list the user clicked on is an exhaustive list of all the
                // quotes, and thus the index of the list lines up perfectly with our QuotesList, but if it were a search
                // then the list would only include some of the items and the index of the item on the ListView wouldn't
                // match up with the actual index of the quote in the list - FOR THIS TEST IT WORKS, BUT NOT FOR THE REAL THING
                // We'll have to find a new way to tell which quote the user clicked on.
                showQuoteIntent.putExtra(Quote.QUOTE_LIST_INDEX, position);

                // broadcast our showQuoteIntent
                startActivity(showQuoteIntent);
            }
        });

    }

}
