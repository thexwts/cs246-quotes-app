package edu.groupawesome.quotetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class QuoteDisplayActivity extends AppCompatActivity {

    private static final String LOG_TAG = "QuoteDisplayActivity";

    static String NEW_QUOTE = "edu.groupawesome.quotetracker.NEW_QUOTE";

    // views for displaying
    private static TextView quoteAuthorView;
    private static TextView quoteTextView;
    private static TextView quoteTagsView;

    // views for editing
    private static EditText editQuoteTitleView;
    private static EditText editQuoteAuthorView;
    private static EditText editQuoteTagsView;
    private static EditText editQuoteTextView;

    // so we can swith between them
    private ViewSwitcher viewSwitcher;

    // the quote we operate on
    private Quote mQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_display);
        // FIGURE OUT HOW TO MAKE THIS GO BACK EXACTLY ONE TO WHAT CALLED IT
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set up our ViewSwitcher
        viewSwitcher = (ViewSwitcher) findViewById(R.id.quote_viewSwitcher);

        // make the action bar show or hide on screen click
        viewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "User clicked main view.");
                // TODO: remove this for release version
                Toast.makeText(QuoteDisplayActivity.this, "That tickles!", Toast.LENGTH_SHORT).show();

                if (getSupportActionBar().isShowing()) {
                    Log.i(LOG_TAG, "Hiding Action Bar.");
                    getSupportActionBar().hide();
                } else {
                    Log.i(LOG_TAG, "Showing Action Bar.");
                    getSupportActionBar().show();
                }
            }
        });

        // set up the TextViews and EditTexts
        setUpQuoteDisplayTextViews();
        setUpEditTextViews();

        // get our quote from the intent
        Intent intent = getIntent();

        // figure out which intent we got
        // FIXME: Quote retrieval from Intent
        // THIS METHOD OF RETRIEVING THE RIGHT QUOTE IS FLAWED I KNOW - this assumes that the list the user clicked on is an exhaustive list of all the
        // quotes, and thus the index of the list lines up perfectly with our QuotesList, but if it were a search
        // then the list would only include some of the items and the index of the item on the ListView wouldn't
        // match up with the actual index of the quote in the list - FOR THIS TEST IT WORKS, BUT NOT FOR THE REAL THING
        if (intent.hasExtra(Quote.QUOTE_LIST_INDEX)) {
            // get our quote from the Quote's list using the Intent's messag as an index (This is a temporary hack)
            int quoteIndex = intent.getIntExtra(Quote.QUOTE_LIST_INDEX, 0);
            mQuote = Quote.getQuoteAtIndex(quoteIndex);
            // display the quote
            displayQuote();

        } else if (intent.hasExtra(NEW_QUOTE)) {
            // if we were asked for a new quote then create a new quote
            createNewQuote();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quote_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {

            // FIXME: EDIT/SAVE BUTTON - THIS ISN'T THE MOST USER FRIENDLY THING, I KNOW. IT IS A PROOF OF CONCEPT THAT CAN BE MODIFIED
            // if edit is clicked - I want to find a way to change it to 'Save' when in editting mode
            case R.id.action_quote_edit: {
                 // if we are currently editing the quote
                if (viewSwitcher.getCurrentView() == findViewById(R.id.layout_quote_edit_display)) {
                    saveQuote();
                    displayQuote();
                    return true;
                } else {
                    // if we were viewing then we now we switch to edit
                    editQuote();
                    return true;
                }
            }
            case R.id.action_add: {
                createNewQuote();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpQuoteDisplayTextViews() {
        // get the TextViews
        Log.i(LOG_TAG, "Setting up Quote Display TextViews.");
        quoteAuthorView = (TextView) findViewById(R.id.quote_author);
        quoteTextView = (TextView) findViewById(R.id.quote_text);
        quoteTagsView = (TextView) findViewById(R.id.quote_tags);
        Log.i(LOG_TAG, "Quote Display TextViews successfully set up.");
    }

    private void populateQuoteDisplayTextViews() {
        // refresh the display
        Log.i(LOG_TAG, "Populating Quote Display TextViews.");
        String title = mQuote.getTitle();
        getSupportActionBar().setTitle(title.length() != 0 ? title : getResources().getString(R.string.no_title));
        quoteAuthorView.setText(mQuote.getAuthor());
        quoteTextView.setText(mQuote.getQuoteText());
        quoteTagsView.setText(mQuote.getTagsAsString());
        Log.i(LOG_TAG, "Quote Display TextViews successfully populated.");
    }

    private void displayQuote() {
        // refresh the display and switch to normal display
        Log.i(LOG_TAG, "Switching to Quote Display view");
        populateQuoteDisplayTextViews();
        viewSwitcher.setDisplayedChild(viewSwitcher.indexOfChild(findViewById(R.id.layout_quote_display)));
        Log.i(LOG_TAG, "Successfully switched to Quote Display view");
        // TODO: Remove in release
        Toast.makeText(QuoteDisplayActivity.this, "Display Quote", Toast.LENGTH_SHORT).show();
    }

    private void saveQuote() {
        Log.i(LOG_TAG, "Saving Quote.");
        // we can only save if we were editing
        assert (viewSwitcher.getNextView() == findViewById(R.id.layout_quote_edit_display));

        // TODO: Is this the best way to check if we are creating a new quote before saving?
        // if the user asked to create a new quote mQuote was set to null
        //   we wait to create the quote until we save
        // I DECIDED AGAINST THIS METHOD BECAUSE WHAT IF THE USER BACKS OUT ON ACCIDENT WITHOUT SAVING?
        // EVERYTHING WOULD BE LOST. ITS PROBABLY BEST TO SAVE MORE OFTEN THAN NECESSARY AND RISK
        // SAVING EMPTY QUOTES THAN TO SAVE LESS OFTEN AND RISK LOSING QUOTES
        // --ANOTHER WAY TO MITIGATE THE DANGER OF LOSING A NEW QUOTE IS TO HAVE THE SAVE DIALOG
        // SHOW UP EVERY TIME THE USER TRIES TO LEAVE THE SCREEN, BUT THAT MIGHT BE HARD TO DO,
        // SO FOR NOW I'M GOING WITH "SAVE ALWAYS NO MATTER WHAT THE USER DOES"
        // TODO: Talk about dynamically saving the quote as the user types (like iOS Notes does)
//        if (mQuote == null) { new Quote(); }

        // TODO: One way to dynamically save would be to create a thread that calls saveQuote once a second (or whenever)
        // TODO: The risk there is the costly setTags() call would be called repeatedly even when not necessary
        // TODO: So we would want a way to check what has actually changed and only call saveQuote when something changes
        // TODO: and then saveQuote would only save the EditText that changed.
        // save what is in the EditText boxes to the quote
        // Note: We need to set the QuoteText before setting the Title so we can set the correct Generic Title when creating new Quotes
        mQuote.setQuoteText(editQuoteTextView.getText().toString());
        // TODO: Example: titleChanged is a boolean that gets set if the title EditText was modified
//        if (titleChanged) {
            mQuote.setTitle(editQuoteTitleView.getText().toString());
//            titleChanged = false;
//        }
        mQuote.setAuthor(editQuoteAuthorView.getText().toString());
        mQuote.setTags(editQuoteTagsView.getText().toString());

//        // Quote currently adds itself to the QuotesList on its own so this isn't necessary.
//        // I'm leaving this here in case we change our minds and need it later.
//        // SAVE/UPDATE IN DB - add if not already there
//        if (!Quote.quotesListContains(mQuote)) {
//            Quote.addToQuotesList(mQuote);
//        }

        Log.i(LOG_TAG, "Quote saved.");
        Toast.makeText(QuoteDisplayActivity.this, "Quote saved.", Toast.LENGTH_SHORT).show();
    }

    private void createNewQuote() {
        Log.i(LOG_TAG, "Creating new Quote.");
        // if currently editing a quote we need to save this one first
        if (viewSwitcher.getCurrentView() == findViewById(R.id.layout_quote_edit_display) && mQuote != null) {
            saveQuote();
        }

        // create a new empty quote
        mQuote = new Quote();
        // FIXME: We need to decide on the best way to do this, but for now I want to create a new empty quote here
        // See notes in saveQuote for more information on why this is the way it is for ow
//        // The next time saveQuote is called it will check if mQuote is null before saving and create a new Quote if necessary
//        mQuote = null;

        // now show the edit screen
        editQuote();

        Log.i(LOG_TAG, "New Quote created.");
        Toast.makeText(QuoteDisplayActivity.this, "New Quote created.", Toast.LENGTH_SHORT).show();
    }

    private void setUpEditTextViews() {
        Log.i(LOG_TAG, "Setting up Quote EditText views.");
        // get the EditText views
        editQuoteTitleView = (EditText) findViewById(R.id.quote_title_edit);
        editQuoteAuthorView = (EditText) findViewById(R.id.quote_author_edit);
        editQuoteTagsView = (EditText) findViewById(R.id.quote_tags_edit);
        editQuoteTextView = (EditText) findViewById(R.id.quote_text_edit);
        Log.i(LOG_TAG, "Quote EditText views successfully set up.");
    }

    private void populateQuoteEditTextViews() {
        Log.i(LOG_TAG, "Populating Quote EditText views.");
        // set the title to something generic
        getSupportActionBar().setTitle(R.string.layout_quote_edit_title);
        editQuoteTitleView.setText(mQuote.getTitle());
        editQuoteTextView.setText(mQuote.getQuoteText());
        editQuoteTagsView.setText(mQuote.getTagsAsString());
        editQuoteAuthorView.setText(mQuote.getAuthor());
        Log.i(LOG_TAG, "Quote EditText views successfully populated.");
    }

    private void editQuote() {
        Log.i(LOG_TAG, "Switching to Quote Edit view");
        populateQuoteEditTextViews();
        viewSwitcher.setDisplayedChild(viewSwitcher.indexOfChild(findViewById(R.id.layout_quote_edit_display)));
        Log.i(LOG_TAG, "Successfully switched to Quote Edit view");
        // TODO: Remove in release
        Toast.makeText(QuoteDisplayActivity.this, "Edit Quote", Toast.LENGTH_SHORT).show();
    }
}
