package edu.groupawesome.quotetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // The view we will be working with in this activity
    private ListView mQuotesListView;
    private SearchView mSearchView;
    private ArrayAdapter<String> mAdapter;

    private List<String> mQuotesTitlesList;

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

        // set up the Toolbar so we can have buttons
        final Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // make the action bar show or hide on screen click
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_view);
        mainLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Toast.makeText(MainActivity.this, "That tickles!", Toast.LENGTH_SHORT).show();

                  if (getSupportActionBar().isShowing()) {
                      getSupportActionBar().hide();
                  } else {
                      getSupportActionBar().show();
                  }
              }
          });

        // set up our search and list
        mQuotesListView = (ListView) findViewById(R.id.list_view_test);

        setUpSearchView();
        populateListView();
        registerClickCallbackOnListItem();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {

            // if settings is clicked
            case R.id.action_settings:
                Toast.makeText(MainActivity.this, "We'll add settings soon, we promise!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_add:
                showNewQuoteDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Set up the Search View. Change settings here. **/
    private void setUpSearchView() {
        mSearchView = (SearchView) findViewById(R.id.search_view_test);
        assert mSearchView != null;
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(false);
    }

    /* Fill List View with list of titles from static QuotesList. **/
    private void populateListView() {
        // we may want a new array adapter that can take Quotes and display them in a special way
        // display a list of the Titles of the Quotes
        mQuotesTitlesList = Quote.getQuoteTitlesList();

        mAdapter = new ArrayAdapter<String>(this, R.layout.list_view_text, mQuotesTitlesList);
        mQuotesListView.setAdapter(mAdapter);

        // enable filtering on the ListView so we can use the search bar
        mQuotesListView.setTextFilterEnabled(true);
    }

    /* **/
    private void showNewQuoteDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_add_title)
                .setMessage(R.string.confirm_add_text)
                .setPositiveButton(R.string.confirm_add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        createNewQuote();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void createNewQuote() {
        Intent newQuoteInent = new Intent(MainActivity.this, QuoteDisplayActivity.class);

        newQuoteInent.putExtra(QuoteDisplayActivity.NEW_QUOTE, true);
        startActivity(newQuoteInent);
    }


    /** Register a click callback for items in list to call Quote Display. BROKEN */
    private void registerClickCallbackOnListItem() {

        // create a listener to check for the item clicked and call the other activity
        mQuotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Intent showQuoteIntent = new Intent(MainActivity.this, QuoteDisplayActivity.class);

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

    /* Call onQueryTextChange and just do the same thing. **/
    @Override
    public boolean onQueryTextSubmit(String query) {
        return onQueryTextChange(query);
    }

    /* Filter the listView with the text in search bar. **/
    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            mQuotesListView.clearTextFilter();
        } else {
            mQuotesListView.setFilterText(newText);
        }
        return true;

    }


}

// Aaron: This is my comment to push!!!
// Ethan: everything is awesoooooooooooooome
// Reed commented here.