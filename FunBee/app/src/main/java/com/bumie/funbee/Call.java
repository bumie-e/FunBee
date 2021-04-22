package com.bumie.funbee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexmo.client.NexmoCall;
import com.nexmo.client.NexmoCallEventListener;
import com.nexmo.client.NexmoCallHandler;
import com.nexmo.client.NexmoCallMember;
import com.nexmo.client.NexmoCallMemberStatus;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoMediaActionState;
import com.nexmo.client.request_listener.NexmoApiError;
import com.nexmo.client.request_listener.NexmoConnectionListener;
import com.nexmo.client.request_listener.NexmoRequestListener;

public class Call extends AppCompatActivity {
    private TextView connectionStatusTextView, caller;
    private TextView waitingForIncomingCallTextView;
    private Button answerCallButton;
    private Button rejectCallButton;
    private Button endCallButton;
    private NexmoClient client;
    private String otherUser = "";
    private NexmoCall onGoingCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        // request permissions
        String[] callsPermissions = { Manifest.permission.RECORD_AUDIO };
        ActivityCompat.requestPermissions(this, callsPermissions, 123);
        client = new NexmoClient.Builder().build(this);
        // init views
        connectionStatusTextView = findViewById(R.id.connectionStatusTextView);
        waitingForIncomingCallTextView = findViewById(R.id.waitingForIncomingCallTextView);
        caller = findViewById(R.id.caller);
        answerCallButton = findViewById(R.id.answerCallButton);
        rejectCallButton = findViewById(R.id.rejectCallButton);
        endCallButton = findViewById(R.id.endCallButton);
        Intent i = getIntent();
        String otherUsers = i.getStringExtra("User_name");
        otherUser = otherUsers;

        client.setConnectionListener((connectionStatus, connectionStatusReason) -> {
            runOnUiThread(() -> {
                connectionStatusTextView.setText(connectionStatus.toString());
            });

            if (connectionStatus == NexmoConnectionListener.ConnectionStatus.CONNECTED) {
                runOnUiThread(() -> {
                    hideUI();
                    connectionStatusTextView.setVisibility(View.VISIBLE);
                   // startCallButton.setVisibility(View.VISIBLE);
                    waitingForIncomingCallTextView.setVisibility(View.VISIBLE);
                });
            }
        });

        startCall();
        client.addIncomingCallListener(it -> {
            onGoingCall = it;

            runOnUiThread(() -> {
                hideUI();
                answerCallButton.setVisibility(View.VISIBLE);
                rejectCallButton.setVisibility(View.VISIBLE);
            });
        });
        answerCallButton.setOnClickListener(view -> { answerCall();});
        rejectCallButton.setOnClickListener(view -> { rejectCall();});
        endCallButton.setOnClickListener(view -> { endCall();});

        SharedPreferences sh = getSharedPreferences("User", MODE_PRIVATE);
        Boolean value = sh.getBoolean("user", false);
        user(value);
    }
    @SuppressLint("MissingPermission")
    private void startCall() {
        caller.setText(otherUser);
        client.call(otherUser, NexmoCallHandler.SERVER, new NexmoRequestListener<NexmoCall>() {
            @Override
            public void onError(@NonNull NexmoApiError nexmoApiError) {

            }

            @Override
            public void onSuccess(@Nullable NexmoCall call) {
                runOnUiThread(() -> {
                    hideUI();
                    endCallButton.setVisibility(View.VISIBLE);
                    waitingForIncomingCallTextView.setText("Ongoing call");
                    waitingForIncomingCallTextView.setVisibility(View.VISIBLE);
                });

                onGoingCall = call;

                onGoingCall.addCallEventListener(new
                                                         NexmoCallEventListener() {
                                                             @Override
                                                             public void onMemberStatusUpdated(NexmoCallMemberStatus callStatus, NexmoCallMember nexmoCallMember) {
                                                                 if (callStatus == NexmoCallMemberStatus.COMPLETED || callStatus == NexmoCallMemberStatus.CANCELLED) {
                                                                     onGoingCall = null;

                                                                     runOnUiThread(() -> {
                                                                                 hideUI();
                                                                                // startCallButton.setVisibility(View.VISIBLE);
                                                                             }
                                                                     );
                                                                 }
                                                             }

                                                             @Override
                                                             public void onMuteChanged(NexmoMediaActionState nexmoMediaActionState, NexmoCallMember nexmoCallMember) {

                                                             }

                                                             @Override
                                                             public void onEarmuffChanged(NexmoMediaActionState nexmoMediaActionState, NexmoCallMember nexmoCallMember) {

                                                             }

                                                             @Override
                                                             public void onDTMF(String s, NexmoCallMember nexmoCallMember) {

                                                             }
                                                         });
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void answerCall() {
        onGoingCall.answer(new NexmoRequestListener<NexmoCall>() {
            @Override
            public void onError(@NonNull NexmoApiError nexmoApiError) {

            }

            @Override
            public void onSuccess(@Nullable NexmoCall nexmoCall) {
                runOnUiThread(() -> {
                    hideUI();
                    endCallButton.setVisibility(View.VISIBLE);
                });
            }
        });
    }
    private void rejectCall() {
        onGoingCall.hangup(new NexmoRequestListener<NexmoCall>() {
            @Override
            public void onError(@NonNull NexmoApiError nexmoApiError) {

            }

            @Override
            public void onSuccess(@Nullable NexmoCall nexmoCall) {
                runOnUiThread(() -> {
                    hideUI();
                  //  startCallButton.setVisibility(View.VISIBLE);
                    waitingForIncomingCallTextView.setVisibility(View.VISIBLE);
                });
            }
        });
        onGoingCall = null;
    }
    private void endCall() {
        onGoingCall.hangup(new NexmoRequestListener<NexmoCall>() {
            @Override
            public void onError(@NonNull NexmoApiError nexmoApiError) {

            }

            @Override
            public void onSuccess(@Nullable NexmoCall nexmoCall) {
                runOnUiThread(() -> {
                    hideUI();
             //       startCallButton.setVisibility(View.VISIBLE);
                    waitingForIncomingCallTextView.setVisibility(View.VISIBLE);
                });
            }
        });

        onGoingCall = null;
    }
    private void hideUI() {
        ConstraintLayout content = findViewById(R.id.content);

        for (int i = 0; i < content.getChildCount(); i++) {
            View view = content.getChildAt(i);
            view.setVisibility(View.GONE);
        }
    }

    public void loginAsAlice() {
        otherUser = "Bob";

        client.login("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MTg1ODU5MDMsImp0aSI6IjBkOTUxNzgwLTllYzYtMTFlYi1hNDNmLTBmOTFlZGI4NWNjMCIsImV4cCI6MTYxODY3MjI5NywiYWNsIjp7InBhdGhzIjp7Ii8qL3VzZXJzLyoqIjp7fSwiLyovY29udmVyc2F0aW9ucy8qKiI6e30sIi8qL3Nlc3Npb25zLyoqIjp7fSwiLyovZGV2aWNlcy8qKiI6e30sIi8qL2ltYWdlLyoqIjp7fSwiLyovbWVkaWEvKioiOnt9LCIvKi9hcHBsaWNhdGlvbnMvKioiOnt9LCIvKi9wdXNoLyoqIjp7fSwiLyova25vY2tpbmcvKioiOnt9LCIvKi9sZWdzLyoqIjp7fX19LCJzdWIiOiJBbGljZSIsImFwcGxpY2F0aW9uX2lkIjoiZDBlYTkwZmEtNjE0OS00N2I2LWEwZDAtMjllMzM1Mjc5MzQ3In0.glYYqaU3xMuohNyGNI7hOh6YnXWOa60OOPEct7xQtcmjYNXwUDJi8E9-w9V9ZBbaolslWe8DZu46Ku2_X7bf3LLlBI5zrILbABN9XtKfjhFuXvBHymbRT9aXswu_VSjJ52MCwgsiOeraJH0ErKhBmfH5BZffJs1mNSJvQQ26kTgJyo8lrMxs0jFaRz3_nDrAtcQNh-5OzMSCqfAnysYMIlGRnUo0BOqrlHrPOdAmog4_jKE6-i1YM_mH66mtwSHWycx6fK9mXVbXFvf9bzvgHThEu4z_IMwo07JpCMujMfC5PdPBBICeKd_pUdM_scOK6OmqKEhw4mT-kD-dv9Qw9g");
    }

    public void loginAsBob() {
        otherUser = "Alice";

        client.login("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE2MTg1ODU5NDcsImp0aSI6IjI4MGM5YWMwLTllYzYtMTFlYi1hNmQyLWNkNDI3Y2YzMWM1ZCIsImV4cCI6MTYxODY3MjM0MCwiYWNsIjp7InBhdGhzIjp7Ii8qL3VzZXJzLyoqIjp7fSwiLyovY29udmVyc2F0aW9ucy8qKiI6e30sIi8qL3Nlc3Npb25zLyoqIjp7fSwiLyovZGV2aWNlcy8qKiI6e30sIi8qL2ltYWdlLyoqIjp7fSwiLyovbWVkaWEvKioiOnt9LCIvKi9hcHBsaWNhdGlvbnMvKioiOnt9LCIvKi9wdXNoLyoqIjp7fSwiLyova25vY2tpbmcvKioiOnt9LCIvKi9sZWdzLyoqIjp7fX19LCJzdWIiOiJCb2IiLCJhcHBsaWNhdGlvbl9pZCI6ImQwZWE5MGZhLTYxNDktNDdiNi1hMGQwLTI5ZTMzNTI3OTM0NyJ9.3t2Ejtv__uS1Hpux5bnOnJB8Htq_vJ7xZimcxsHja0p6k7miAHsBPgc85yGkcs3CK86Y-4_cSHPlcIiLWem0H0Fjn6VPt4F2KW1lFM3sHKakb4CbGUkHecd5Yf1uK8st2ztYqRtMdW7tv-gfBQLDACGwPbjaMwRirGZLY7Z3oCEqwB8bqMF2fdjUHNbqHJTfdHaM-yIxH46tCvRs_tmXenA2U9t3CnKGDQps-jmG1y6mP5DPGjyFOEOPkYLcDjHOe8JerM1kZwcEAPm8EaSQ_gI6hADkojEqGWBG7DmBxh2vUcWM7yXbSQo9ezxzYscao1LUH14atuNzMdEvPTJ9Ew");
    }
    public void user(Boolean value){

        if (value == true){
            loginAsAlice();
        }
        else{
            loginAsBob();
        }
    }
}