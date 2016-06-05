package edu.groupawesome.quotetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // make the action bar show or hide on click
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
                showConfimDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* **/
    private void showConfimDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_add_title)
                .setMessage(R.string.confirm_add_text)
                .setPositiveButton(R.string.confirm_add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        saveNewQuote();
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

    private void saveNewQuote() {
        Toast.makeText(MainActivity.this, "We'll add the quote feature soon, I promise!", Toast.LENGTH_SHORT).show();
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
        Intent intentListViewTestActivity = new Intent(this, ListViewSearchTestActivity.class);

        // start our ListViewSearchTestActivity
        startActivity(intentListViewTestActivity);
    }


    /** Called when user clicks Another Test button. */
    public void anotherTestClick(View view) {
//        Intent intentSomeTestActivity = new Intent(this, SomeTestActivity.class);

        // start our SomeTestActivity
//        startActivity(intentSomeTestActivity);
    }


}

// Aaron: This is my comment to push!!!
// Ethan: everything is awesoooooooooooooome
// Reed commented here.