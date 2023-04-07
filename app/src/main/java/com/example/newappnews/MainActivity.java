package com.example.newappnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity{
    RecyclerView rw;
    TextView E,B,T,S,H,G,Saved;
    ImageView loader,err;
    TextView temp;
    Button erTry,save,Delete;
    ImageView lastView,back,icon,name;
    DbHandler db;
    int i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        db=new DbHandler(MainActivity.this,"data",null,1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        E=findViewById(R.id.Entertainment);
        G=findViewById(R.id.General);
        rw=findViewById(R.id.Recy);
        lastView=findViewById(R.id.lastView);
        B=findViewById(R.id.Business);
        T=findViewById(R.id.Technology);
        H=findViewById(R.id.Health);
        S=findViewById(R.id.Science);
        Saved=findViewById(R.id.Saved);
        back=findViewById(R.id.back);
        icon=findViewById(R.id.iconN);
        name=findViewById(R.id.name);


//        save=Home.ViewHolder.getSave();
//        Delete=Home.ViewHolder.getDelete();

        loader=(ImageView)findViewById(R.id.imageView2);
        err= findViewById(R.id.error);
        erTry=findViewById(R.id.button2);
        temp=G;

        apiCall("general");
        MakeColor(temp);
        lastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myTag", "En"+db.getCount());

                lastViewList(db.getCount());
            }

        });
        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeColor(temp);
                temp=E;
                apiCall(E.getText().toString().toLowerCase());
                MakeColor(E);



            }
        });
        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeColor(temp);
                temp=H;
                apiCall(H.getText().toString().toLowerCase());
                MakeColor(H);
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeColor(temp);
                temp=B;
                apiCall(B.getText().toString().toLowerCase());
                MakeColor(B);

            }
        });

        S.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeColor(temp);
                temp=S;
                apiCall(S.getText().toString().toLowerCase());
                MakeColor(S);

            }
        });
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeColor(temp);
                temp=T;
                apiCall(T.getText().toString().toLowerCase());
                MakeColor(T);

            }
        });
        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeColor(temp);
                temp=G;
                apiCall(G.getText().toString().toLowerCase());
                MakeColor(G);

            }
        });

    }
    private void apiCall(String s){
        loader.setVisibility(View.VISIBLE);
        err.setVisibility(View.INVISIBLE);
        erTry.setVisibility(View.INVISIBLE);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://saurav.tech/NewsAPI/top-headlines/category/"+s+"/in.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       back.setVisibility(View.GONE);
                        icon.setVisibility(View.GONE);
                        name.setVisibility(View.GONE);
                        Saved.setVisibility(View.GONE);
                        loader.setVisibility(View.INVISIBLE);
                        rw.setVisibility(View.VISIBLE);

                        Log.d("Debug", "yo: ");

                        try {
                            JSONArray arr = response.getJSONArray("articles");
                            rw.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            Home ad=new Home(arr,MainActivity.this,db,0);
                            rw.setAdapter(ad);

//                            String url = response.getString("url");
//                            Log.d("Debug", url);
//                            Glide.with(MainActivity.this).load(url).into(img);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Saved.setVisibility(View.GONE);

                        loader.setVisibility(View.INVISIBLE);
                        rw.setVisibility(View.INVISIBLE);

                        erTry.setVisibility(View.VISIBLE);
                        err.setVisibility(View.VISIBLE);
                        erTry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                apiCall(s);
                            }
                        });

                        Log.d("Debug", "yo3: ");

                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }
    public void fadeColor(TextView temp){
        temp.setBackgroundResource(R.color.white);
    }
    public void MakeColor(TextView temp){
        temp.setBackgroundResource(R.color.green);

    }
public void lastViewList(int k){
    Log.d("myTag", "Entered 1");
    i=0;int h=99;
    JSONArray a =new JSONArray();
    rw.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    while(i<k){
        try {
            if(db.getData(i)==null){i++;continue;}
            else{
            a.put(db.getData(i));
            i++;
            h=0;}
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }    Log.d("myTag", "Entered 2");

    if(h==99){
        Toast.makeText(MainActivity.this, "Nothing saved yet", Toast.LENGTH_SHORT).show();
        MakeColor(G);
        apiCall("general");

    }
    else {
        Log.d("myTag", "Entered 3");

        Saved.setVisibility(View.VISIBLE);
        fadeColor(temp);
        temp = G;
        MakeColor(Saved);
        Home ad = new Home(a, MainActivity.this, db, 1);
        rw.setAdapter(ad);
    }
}


}
