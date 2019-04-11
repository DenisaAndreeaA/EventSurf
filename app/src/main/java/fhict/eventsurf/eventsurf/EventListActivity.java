package fhict.eventsurf.eventsurf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class EventListActivity extends AppCompatActivity {

    public ArrayList<String> events = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listview;
    private Toolbar toolbar;
    public String venue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.listview = (ListView) findViewById(R.id.eventList);
        setSupportActionBar(toolbar);

        //  events.clear();

        //this.listview.setMovementMethod(new ScrollingMovementMethod());  // This makes our text box scrollable

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, events);
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView)view).getText().toString();

                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                Intent eventIntent = new Intent(EventListActivity.this, EventActivity.class);
                eventIntent.putExtra("ChosenEvent", item);
                eventIntent.putExtra("EventVenue", venue);
                EventListActivity.this.startActivity(eventIntent);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        populateList();
    }


    public void populateList(){


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.songkick.com/api/3.0/metro_areas/31380/calendar.json?apikey=jVKE6YySkYe97Jwo";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            JSONObject resultspage = response.getJSONObject("resultsPage");
                            JSONObject results = resultspage.getJSONObject("results");
                            JSONArray eventArray = results.getJSONArray("event");

                            for(int i = 0; i < eventArray.length(); i++) {
                                JSONObject eventObject = eventArray.getJSONObject(i);
                                //JSONArray performance = eventObject.getJSONArray("performance");
                                //Log.d("PERFORMANCE", performance.toString());
                                JSONObject eventLocation = eventObject.getJSONObject("venue");
                                String eventName = eventObject.getString("displayName");
                                String eventId = eventObject.getString("id");
                                venue = eventLocation.getString("displayName");
                                Log.d("eventName", eventName);
                                events.add(eventName);
                                new Festival(eventId, eventName, venue);

                            }


                        } catch (JSONException error) {
                            Log.d("POLYGLOT", error.getMessage());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("POLYGLOT", error.getMessage());
            }
        });
        queue.add(stringRequest);

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
                Intent intent = new Intent(EventListActivity.this, SignIn.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
