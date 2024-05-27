package com.example.meditationapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static android.content.Context.CLIPBOARD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class Quotes extends Fragment {


    public Quotes() {
        // Required empty public constructor
    }



    TextView quotes , auth, gender;
    RequestQueue mqueue;
    String getquote;
    String author;
    ConstraintLayout layout;
    Button copyButton,shareButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_quotes, container, false);


        quotes = view.findViewById(R.id.quotes);

        auth = view.findViewById(R.id.author);
        layout = view.findViewById(R.id.layout);


        copyButton = view.findViewById(R.id.copyTOClipboard);
        shareButton = view .findViewById(R.id.sharebutton);



        mqueue = Volley.newRequestQueue(getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Inside the First Runnable thread ");
                jsonparse();
            }
        }).start();
//



        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "Inside the Second Runnable thread ");
                        jsonparse();


                    }


                }).start();
                Log.i(TAG, "onClick: quotes are connected to the textview");
                if(getquote == null)
                {
                    getquote = "Click one more time :)";
                    author = "";
                }
                quotes.setText("\"" + getquote + "\"");
                auth.setText("-" + author);
            }
        });



        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager myClipboard;
                myClipboard = (ClipboardManager)getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip;
                String Text = quotes.getText().toString();
                myClip = ClipData.newPlainText("text", Text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getContext(), "Copied To Clipboard", Toast.LENGTH_SHORT).show();
            }
        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Text = quotes.getText().toString();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,Text);
                intent.setType("text/plain");
                intent = Intent.createChooser(intent,"Share Through...");
                startActivity(intent);

            }
        });


        return view;
    }

    private void jsonparse() {
        // URL API для получения случайной цитаты на русском языке
        String url = "https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=ru";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Получение цитаты и автора из JSON-объекта
                            getquote = response.getString("quoteText");
                            author = response.getString("quoteAuthor");

                            // Обновление текстовых полей с полученными данными
                            quotes.setText("\"" + getquote + "\"");
                            auth.setText("- " + author);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Обработка ошибки при получении цитаты
                quotes.setText("Ошибка при получении цитаты");
                auth.setText("");
                error.printStackTrace();
            }
        });

        // Добавление запроса в очередь Volley
        mqueue.add(request);
    }
    //onclick method on the button to set the text in the text box


}