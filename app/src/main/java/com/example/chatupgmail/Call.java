package com.example.chatupgmail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Call extends Fragment {


    EditText shareCode;
    Button joinBtn, shareBtn;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat, container, false);
        shareCode = view.findViewById(R.id.codeBox);
        joinBtn = view.findViewById(R.id.joinBtn);
        shareBtn = view.findViewById(R.id.shareBtn);

        URL serverURL;


        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .setWelcomePageEnabled(false)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(shareCode.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

//                JitsiMeetActivity.launch(Call.this, options);
            }
        });


        return view;
    }


}
