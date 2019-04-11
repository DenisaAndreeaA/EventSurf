package fhict.eventsurf.eventsurf;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by denis on 3/29/2018.
 */

public class EventSurf extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
