package com.google.firebase.app.chatz;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class Main2Activity extends AppCompatActivity implements AIListener, View.OnClickListener{
    private static final String TAG ="tag" ;
    private ImageButton btnVoice;
    private TextView tvResult;
    private ListView mMessageListView;
    private AIService aiService;
    private MessageAdapter mMessageAdapter;
    private static final int REQUEST_INTERNET = 200;
    EditText medit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnVoice = (ImageButton) findViewById(R.id.voice_button);
        tvResult = (TextView) findViewById(R.id.messageTextView);
        medit = (EditText)findViewById(R.id.messageEditText);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);
        validateOS();

        final AIConfiguration config = new AIConfiguration("fe2c1b80d6b24c2e92f25e6984d31261",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        btnVoice.setOnClickListener(this);
    }

    private void validateOS() {
        if (ContextCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_INTERNET);
        }
    }
     @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();

        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue();
            }
        }
        medit.setText(result.getResolvedQuery());
         Log.d(TAG, "onResult:"+result.getResolvedQuery() + result.getFulfillment().getSpeech());
         mMessageAdapter.add(new FriendlyMessage(result.getResolvedQuery(),"user",null));
         mMessageAdapter.add(new FriendlyMessage(result.getFulfillment().getSpeech(),"bot",null));
        // show result in textview
      /*  tvResult.setText("Query: " + result.getResolvedQuery() +
        "\nAction: " + result.getAction() +
        "\nParameters: " + parameterString + "\nresponse:" + result.getFulfillment().getSpeech());*/

    }

    @Override
    public void onError(AIError error) {
        tvResult.setText(error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice_button:
                aiService.startListening();
                break;
        }
    }
}
