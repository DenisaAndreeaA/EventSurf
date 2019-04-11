package fhict.eventsurf.eventsurf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    ListView usersList;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<String> listUsers;
    ArrayAdapter<String> adapter;
    User us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("ChosenEvent");
        String eventVenue = intent.getStringExtra("EventVenue");


        final TextView eventTitleTV = (TextView) findViewById(R.id.textViewEventTitle);
        final TextView eventPlaceTV = (TextView) findViewById(R.id.textViewEventPlace);
        eventTitleTV.setText(eventName);
        eventPlaceTV.setText(eventVenue);


        us = new User();
        usersList = (ListView)findViewById(R.id.users);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        listUsers = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listUsers);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    us = ds.getValue(User.class);
                    listUsers.add(us.getName().toString() + " is attending this event");
                }
                usersList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();

                Intent eventIntent = new Intent(EventActivity.this, UserProfileActivity.class);
                eventIntent.putExtra("ChosenUser", item);
                EventActivity.this.startActivity(eventIntent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(EventActivity.this, SignIn.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
