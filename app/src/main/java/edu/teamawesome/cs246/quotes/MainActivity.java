package edu.teamawesome.cs246.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** Called when user clicks SMS Test button. */
    public void smsTestClick(View view) {
//        Intent intentSMSTestActivity = new Intent(this, SMSTestActivity.class);
//
//        // start our SMSTestActivity
//        startActivity(intentSMSActivity);
    }


    /** Called when user clicks ListView Test button. */
    public void listViewTestClick(View view) {
        Intent intentListViewTestActivity = new Intent(this, ListViewTestActivity.class);

        // build the string array we will send
        String[] testStrings = {
                "Hello",
                "World!",
                "How",
                "are",
                "you",
                "today?"
        };

        // start our ListViewTestActivity
        startActivity(intentListViewTestActivity);
    }


    /** Called when user clicks Another Test button. */
    public void anotherTestClick(View view) {
//        Intent intentSomeTestActivity = new Intent(this, SomeTestActivity.class);

        // start our SomeTestActivity
//        startActivity(intentSomeTestActivity);
    }
}
