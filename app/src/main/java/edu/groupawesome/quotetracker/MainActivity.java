package edu.groupawesome.quotetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String LOG_TAG = "MainActivity";

    // The view we will be working with in this activity
    private ListView mQuotesListView;
    private SearchView mSearchView;
    private ArrayAdapter<String> mAdapter;

    private List<String> mFilteredList = new ArrayList<>();
    private List<Integer> mFilteredListIDs = new ArrayList<>();

    private static boolean testQuotesSetUpComplete = false;

    // FIXME: I've never actually made an ENUM, so I need to make sure I got this right. I want to make a list of the options to sort by.
    private enum SortOptions {
        TAG, TITLE, TEXT, AUTHOR
    }

    private SortOptions mSortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up some quotes for testing on first onCreate only
        if (!testQuotesSetUpComplete) {
            // make a lot of quotes for heavy testing
//            for (int i = 0; i < 100; i++) {
                Quote.generateGenericQuotes();
//            }
            // we only do this once
            testQuotesSetUpComplete = true;
        }

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
        setUpSearchView();
        mQuotesListView = (ListView) findViewById(R.id.list_view_test);

        // fixme: This sets the sort method. Once we have the sort menu working we want this to be changed by the user.
        // select default sort method and put the Quotes on screen
        sortBy(SortOptions.TITLE);

        // set up the callback for when the user clicks on an item in the list
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
                createNewQuote();
                return true;

            case R.id.action_sort_tag:
                sortBy(SortOptions.TAG);
                return true;
            case R.id.action_sort_title:
                sortBy(SortOptions.TITLE);
                return true;
            case R.id.action_sort_author:
                sortBy(SortOptions.AUTHOR);
                return true;
            case R.id.action_sort_text:
                sortBy(SortOptions.TEXT);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Set up the Search View. Change settings here. **/
    private void setUpSearchView() {
        Log.i(LOG_TAG, "Setting up SearchView");
        mSearchView = (SearchView) findViewById(R.id.search_view_test);
        assert mSearchView != null;
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(false);
        Log.i(LOG_TAG, "Done Setting up SearchView");
    }


    private void sortBy(SortOptions sortBy) {
        mSortBy = sortBy;
        // clear the list so we can
        mFilteredList.clear();
        mFilteredListIDs.clear();

        String title;
        switch (mSortBy) {
            case TAG:
                title = "Tags";
                // FIXME: this puts everything in reverse order, and we don't want that
                for (Tag tag : Tag.getTagsSet()) {
                    mFilteredList.add(tag.getTagName());
                    mFilteredListIDs.add(tag.getID());
                }
                break;
            case TITLE:
                title = "Quotes by Title";
                // FIXME: this puts everything in reverse order, and we don't want that
                for (Quote quote : Quote.getQuotesList()) {
                    mFilteredList.add(quote.getTitle());
                    mFilteredListIDs.add(quote.getID());
                }
                break;
            case TEXT:
                title = "Quotes by Text";
                // FIXME: this puts everything in reverse order, and we don't want that
                // TODO: Maybe make the option to show the full text as a user configurable setting
                // here we only show the short version of the text, but the search function searches the full text
                for (Quote quote : Quote.getQuotesList()) {
                    mFilteredList.add(quote.getShortText());
                    mFilteredListIDs.add(quote.getID());
                }
                break;
            case AUTHOR:
                title = "Quotes by Author";
                // FIXME: this puts everything in reverse order, and we don't want that
                for (Quote quote : Quote.getQuotesList()) {
                    mFilteredList.add(quote.getAuthor());
                    mFilteredListIDs.add(quote.getID());
                }
                break;
            default:
                title = "sortBy Switch defaulted";
                Log.wtf(LOG_TAG, "sortBy Switch defaulted");
                break;
        }

        getSupportActionBar().setTitle(title);

        Toast.makeText(MainActivity.this, "Showing " + title, Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "Showing " + title);


        populateListView();
    }

    /* Fill List View with list of titles from static QuotesList. **/
    private void populateListView() {
        Log.i(LOG_TAG, "Populating ListView");

        // display mFilteredList as set by sortBy
        mAdapter = new ArrayAdapter<String>(this, R.layout.list_view_text, mFilteredList);
        mQuotesListView.setAdapter(mAdapter);

        // TODO: decide which method to use; clear the search bar, or update filter with its contents
        // and clear the search bar in case there is something typed in
        if (mSearchView != null) {
            mSearchView.setQuery("", false);
        }
//        // update the filter with the query in the search bar
//        if (mSearchView != null) {
//            String query = mSearchView.getQuery().toString();
//            if (query.length() != 0) {
//                onQueryTextChange(query);
//            }
//        }

        Log.i(LOG_TAG, "Done Populating ListView");
    }

    private void createNewQuote() {
        Intent newQuoteInent = new Intent(MainActivity.this, QuoteDisplayActivity.class);

        newQuoteInent.putExtra(Quote.NEW_QUOTE, true);
        startActivity(newQuoteInent);
    }

    /** Register a click callback for items in list to call Quote Display. BROKEN */
    private void registerClickCallbackOnListItem() {

        // create a listener to check for the item clicked and call the other activity
        mQuotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                // TODO: add the position or id
                Log.i(LOG_TAG, String.format("ListView item %d clicked", position));

                Log.d(LOG_TAG, "Position clicked: " + position);
                Log.d(LOG_TAG, "Row id clicked:   " + id);

                // only do anything if there is at least one item to click
                // when there are "No results" mFilteredList has 1 String and mFilteredListIDs has none
                // so if mFilteredListIDs has at least one String then we know we are displaying something
                if (mFilteredListIDs.size() >= 1) {
                    Intent showQuoteIntent = new Intent(MainActivity.this, QuoteDisplayActivity.class);

                    // TODO: Send the Quote's ID and have QuoteDisplayActivity find the Quote by it's ID

                    // react differently if showing Tag or Quote
                    switch (mSortBy) {
                        case TAG:
                            // TODO: make this show the Quotes with the given tag
                            break;
                        case AUTHOR:
                            // FIXME: right now the Author list shows a list of all the Quotes, so Authors get repeated
                            // we could/should probably make this show each author once and when clicked the Quotes
                            // with that Author are shown, like the Tag list
                            // for now this acts just like Title and Text
                            // uncomment break; when ready to implement a separate Author action
                            // break;
                        case TITLE:
                        case TEXT:
                            // We can get the ID here from the filtered list being displayed because it is still in scope
                            int quoteId = mFilteredListIDs.get(position);
                            Log.d(LOG_TAG, "User clicked Quote: " + quoteId);

                            showQuoteIntent.putExtra(Quote.QUOTE_ID, quoteId);

                            // broadcast our showQuoteIntent
                            startActivity(showQuoteIntent);

                            break;
                        default:
                            Log.wtf(LOG_TAG, "OnItemClickListener Switch defaulted");
                            break;
                    }

                }

            }
        });

    }

    /* Call onQueryTextChange and just do the same thing. **/
    @Override
    public boolean onQueryTextSubmit(String query) {
//        return onQueryTextChange(query);
        return true;
    }

    /* Filter the listView with the text in search bar. **/
    @Override
    public boolean onQueryTextChange(String newText) {
        String cmp = newText.toLowerCase();

        mFilteredList.clear();
        mFilteredListIDs.clear();

        switch (mSortBy) {
            case TAG:
                for (Tag tag : Tag.getTagsSet()) {
                    String tagName = tag.getTagName();
                    String tagNameLower = tagName.toLowerCase();

                    if (tagNameLower.contains(cmp)) {
                        mFilteredList.add(tagName);
                        mFilteredListIDs.add(tag.getID());
                    }
                }
                break;
            case TITLE:
                for (Quote quote : Quote.getQuotesList()) {
                    String title = quote.getTitle();
                    String titleLower = title.toLowerCase();

                    if (titleLower.contains(cmp)) {
                        mFilteredList.add(title);
                        mFilteredListIDs.add(quote.getID());
                        Log.d(LOG_TAG, "QuoteID: " + quote.getID());
                    }
                }
                break;
            case TEXT:
                for (Quote quote : Quote.getQuotesList()) {
                    // we search the full text of each Quote
                    String textLower = quote.getFullText().toLowerCase();

                    if (textLower.contains(cmp)) {
                        // but only show the short version
                        mFilteredList.add(quote.getShortText());
                        mFilteredListIDs.add(quote.getID());
                    }
                }
                break;
            case AUTHOR:
                for (Quote quote : Quote.getQuotesList()) {
                    String author = quote.getAuthor();
                    String authorLower = author.toLowerCase();

                    if (authorLower.contains(cmp)) {
                        mFilteredList.add(author);
                        mFilteredListIDs.add(quote.getID());
                    }
                }
            default:
                Log.wtf(LOG_TAG, "onQueryTextChange Switch defaulted");
                break;
        }

        if (mFilteredList.size() < 1) {
            mFilteredList.add(String.format("No results matching %s", newText));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });

        return true;
    }

}

// Aaron: This is my comment to push!!!
// Ethan: everything is awesoooooooooooooome
// Reed commented here.