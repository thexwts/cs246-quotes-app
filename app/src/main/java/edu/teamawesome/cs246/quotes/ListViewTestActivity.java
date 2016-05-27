package edu.teamawesome.cs246.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ListViewTestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_test);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the scripture reference sent from the other activity
        Intent intent = getIntent();
    }

}
