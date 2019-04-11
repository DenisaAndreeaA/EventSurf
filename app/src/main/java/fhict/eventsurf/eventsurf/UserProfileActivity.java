package fhict.eventsurf.eventsurf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    private String message;
    private LinearLayout chatLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("ChosenUser");

        final TextView userNameTV = (TextView) findViewById(R.id.userNameTV);
        final Button msgBtn = (Button) findViewById(R.id.sendmsgBtn);
        chatLayout = (LinearLayout) findViewById(R.id.chatLayout);

        userNameTV.setText(userName);


    }

    public void onClick(View view)
    {
        final EditText msgText = (EditText) findViewById(R.id.writemsg);
       // final TextView textview = (TextView) findViewById(R.id.textView3);

       // textview.setText(msgText.getText());
        TextView textView = new TextView(this);
        textView.setText(msgText.getText());
        chatLayout.addView(textView);
        msgText.setText("");
    }

}
