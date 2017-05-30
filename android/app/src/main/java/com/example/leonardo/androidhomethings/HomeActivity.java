package com.example.leonardo.androidhomethings;

import java.util.Locale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference likesRef;
    private DatabaseReference playerModeRef;
    private DatabaseReference playerTextRef;
    private String activeMode = "number";
    private TextToSpeech mTtsEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSpeaker();
    }

    private void initSpeaker() {
        mTtsEngine = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR) {
                            Locale locSpanish = new Locale("es", "AR");
                            mTtsEngine.setLanguage(locSpanish);
                            initFirebase();
                        }
                    }
                });
    }

    private void speak(String type, CharSequence toSpeak) {
        if (this.activeMode.equals(type)) {
            mTtsEngine.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mTtsEngine.speak("No reconozco ese modo", TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void initFirebase() {
        mDatabase = FirebaseDatabase.getInstance();

        likesRef = mDatabase.getReference("likes/value");
        likesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Long value = (Long) dataSnapshot.getValue();

                CharSequence toSpeak = value.toString();
                speak("number", toSpeak);

                System.out.println("Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });


        playerModeRef = mDatabase.getReference("player/mode");
        playerModeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activeMode = (String) dataSnapshot.getValue();
                System.out.println("Cambio de modo.");
                mTtsEngine.speak("Cambiando a modo " + activeMode, TextToSpeech.QUEUE_FLUSH, null, null);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });

        playerTextRef = mDatabase.getReference("player/text");
        playerTextRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String toSpeak = (String) dataSnapshot.getValue();
                speak("text", toSpeak);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });
    }
}
