package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView chatBotRV;
    private MessageAdapter messageAdapter;
    private FloatingActionButton sendButton;
    private EditText queryText;
    private String USER_KEY = "USER";
    private String BOT_KEY = "CHAT_BOT";
    private Retrofit retrofit;
    private APIservice apIservice;
    private ArrayList<ChatModel> chatModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatBotRV = findViewById(R.id.chat_bot_rv);
        sendButton = findViewById(R.id.send_button);
        queryText = findViewById(R.id.chat_ev);
        chatModelArrayList = new ArrayList<>();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!queryText.getText().toString().isEmpty()){
                    String message = queryText.getText().toString();
                    if (queryText.length() > 0) {
                        TextKeyListener.clear(queryText.getText());
                    }
                    getMessage(message);

                }else{
                    Toast.makeText(MainActivity.this, "Please enter your message :)", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        messageAdapter = new MessageAdapter(chatModelArrayList, MainActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatBotRV.setLayoutManager(layoutManager);
        chatBotRV.setAdapter(messageAdapter);
    }

    private void getMessage(String msg){
        ChatModel chatModel_user = new ChatModel(msg, USER_KEY);
        chatModelArrayList.add(chatModel_user);
        messageAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=156741&key=JvI2uFO5l4j1kCIi&uid=[uid]&msg="+msg;
        String BASE_URL = "http://api.brainshop.ai/";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apIservice = retrofit.create(APIservice.class);
        Call<MessageModel> call = apIservice.getMessage(url);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                if(response.isSuccessful()){
                    MessageModel model = response.body();
                    chatModelArrayList.add(new ChatModel(model.getCnt(), BOT_KEY));
                    messageAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "Some error on our side", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                chatModelArrayList.add(new ChatModel("Please revert your query", BOT_KEY));
                messageAdapter.notifyDataSetChanged();

            }
        });
    }

}