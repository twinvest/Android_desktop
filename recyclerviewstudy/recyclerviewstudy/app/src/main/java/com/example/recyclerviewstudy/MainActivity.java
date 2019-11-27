package com.example.recyclerviewstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        queue = Volley.newRequestQueue(this);
        // 1. 화면이 로딩되면 뉴스 정보를 받아온다.
        getNews();
        // 2. 정보를 받아오면 어댑터에 넘겨준다.
        // 3. 어댑터에서 정보를 세팅하고 화면에 보여준다.
    }

    public void getNews(){
        // Instantiate the RequestQueue.
        // 뉴스 API의 주소를 가져옴.
        String url ="https://newsapi.org/v2/top-headlines?country=kr&apiKey=3fc71bf49be14777a2c4fd0eec09500a";

        // Request a string response from the provided URL.
        // 제공된 URL로부터 문자열 응답을 요청.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("NEWS", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray arrayArticle = jsonObject.getJSONArray("articles");
                            List<NewsData> news = new ArrayList<>();

                            for(int i=0, j=arrayArticle.length(); i<j; i++){
                                JSONObject obj = arrayArticle.getJSONObject(i);
                                Log.d("NEWS", obj.toString());

                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("urlToImage"));
                                newsData.setDescription(obj.getString("description"));
                                news.add(newsData);
                            }
                            // send response to NewsData class

                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(news, MainActivity.this);
                            recyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
