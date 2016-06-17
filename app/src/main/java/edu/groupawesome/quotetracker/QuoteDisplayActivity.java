package edu.groupawesome.quotetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class QuoteDisplayActivity extends AppCompatActivity {

    public static String NEW_QUOTE = "edu.groupawesome.quotetracker.NEW_QUOTE";

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
                Toast.makeText(QuoteDisplayActivity.this, "That tickles!", Toast.LENGTH_SHORT).show();

                if (getSupportActionBar().isShowing()) {
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }
            }
        });

        // set up the TextViews and EditTexts
        setUpDisplay();
        setUpEditText();

        // get our quote from the intent
        Intent intent = getIntent();

        // figure out which intent we got
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

            // THIS ISN'T THE MOST USER FRIENDLY THING, I KNOW. IT IS A PROOF OF CONCEPT THAT CAN BE MODIFIED
            // if edit is clicked - I want to find a way to change it to 'Save' when in editting mode
            case R.id.action_quote_edit: {
                 // if we are currently editing the quote
                if (viewSwitcher.getCurrentView() == findViewById(R.id.layout_quote_edit_display)) {
                    // confirm before saving
                    showConfirmSaveDialog();
                    return true;
                } else {
                    // if we were viewing then we now we switch to edit
                    showConfirmEditDialog();
                    return true;
                }
            }
            case R.id.action_add: {
                showConfirmNewDialog();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showConfirmSaveDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_save_title)
                .setMessage(R.string.confirm_save_text)
                .setPositiveButton(R.string.confirm_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveQuote();
                        displayQuote();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(QuoteDisplayActivity.this, "Quote not saved.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /* **/
    private void showConfirmNewDialog() {
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
                        Toast.makeText(QuoteDisplayActivity.this, "Quote creation cancelled.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showConfirmEditDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_edit_title)
                .setMessage(R.string.confirm_edit_text)
                .setPositiveButton(R.string.confirm_edit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editQuote();
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

    private void setUpDisplay() {
        // get the TextViews
        quoteAuthorView = (TextView) findViewById(R.id.quote_author);
        quoteTextView = (TextView) findViewById(R.id.quote_text);
        quoteTagsView = (TextView) findViewById(R.id.quote_tags);
    }

    private void updateDisplay() {
        // refresh the display
        String title = mQuote.getTitle();
        getSupportActionBar().setTitle(title.length() != 0 ? title : getResources().getString(R.string.no_title));
        quoteAuthorView.setText(mQuote.getAuthor());
        quoteTextView.setText(mQuote.getQuoteText());
        quoteTagsView.setText(mQuote.getTagsAsString());
    }

    private void displayQuote() {
        // refresh the display and switch to normal display
        updateDisplay();
        viewSwitcher.setDisplayedChild(viewSwitcher.indexOfChild(findViewById(R.id.layout_quote_display)));
        Toast.makeText(QuoteDisplayActivity.this, "Display Quote", Toast.LENGTH_SHORT).show();
    }

    private void saveQuote() {
        // we can only save if we were editing
        assert(viewSwitcher.getNextView() == findViewById(R.id.layout_quote_edit_display));

        // save what is in the EditText boxes to the quote
        mQuote.setTitle(editQuoteTitleView.getText().toString());
        mQuote.setAuthor(editQuoteAuthorView.getText().toString());
        mQuote.setQuoteText(editQuoteTextView.getText().toString());
        // TODO: I put in new Tag for simplicity sake for now, just to have it do something, but its wrong-this will need to see if there already is a Tag that has this name and add it, if it doesn't exist then create a new Tag
        mQuote.addTag(new Tag(editQuoteTagsView.getText().toString()));

        // SAVE/UPDATE IN DB - add if not already there
        if (!Quote.quotesListContains(mQuote)) {
            Quote.addToQuotesList(mQuote);
        }

        Toast.makeText(QuoteDisplayActivity.this, "Quote saved.", Toast.LENGTH_SHORT).show();
    }

    private void createNewQuote() {
        // if currently editing a quote we should ask to save this one first
        if (viewSwitcher.getCurrentView() == findViewById(R.id.layout_quote_edit_display)) {
//            showConfirmSaveDialog();
            saveQuote();
            // I wanted to ask the user if they want to save the quote currently being edited
            // before creating a new one, but that is impossible to do the way I hoped.
            // The problem is that dialogs get put on a seperate thread so when I call the dialog
            // asking to confirm the save the thread that called the dialog keeps going and creates
            // the new Quote before the user can hit yes for save, so they lose the old Quote and
            // get kicked out of edit mode on the new quote before they can even get started.
            // I guess one solution is to create another dialog that asks both questions in one
            // so they don't have the racing issue, but that is not what I wanted to do.
            // That would be more complicated and harder to undesrtand what was going on, so for
            // now the simple solution is to save every time, without asking.
        }

        // create a new empty quote
        mQuote = new Quote();
        // ADD TO DB NOW OR ONLY ON SAVE?

        Toast.makeText(QuoteDisplayActivity.this, "New Quote created.", Toast.LENGTH_SHORT).show();

        // now show the edit screen
        editQuote();
    }

    private void setUpEditText() {
        // get the EditText views
        editQuoteTitleView = (EditText) findViewById(R.id.quote_title_edit);
        editQuoteAuthorView = (EditText) findViewById(R.id.quote_author_edit);
        editQuoteTagsView = (EditText) findViewById(R.id.quote_tags_edit);
        editQuoteTextView = (EditText) findViewById(R.id.quote_text_edit);
    }

    private void updateEditDisplay() {
        // set the title to something generic
        getSupportActionBar().setTitle(R.string.layout_quote_edit_title);
        editQuoteTitleView.setText(mQuote.getTitle());
        editQuoteTextView.setText(mQuote.getQuoteText());
        editQuoteTagsView.setText(mQuote.getTagsAsString());
        editQuoteAuthorView.setText(mQuote.getAuthor());
    }

    private void editQuote() {
        updateEditDisplay();
        viewSwitcher.setDisplayedChild(viewSwitcher.indexOfChild(findViewById(R.id.layout_quote_edit_display)));
        Toast.makeText(QuoteDisplayActivity.this, "Edit Quote", Toast.LENGTH_SHORT).show();
    }
}
