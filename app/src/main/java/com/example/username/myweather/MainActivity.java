package com.example.username.myweather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView mTitle;
    TextView mDescription;
    TextView mDateLabel0, mDateLabel1;
    TextView mTelop0,mTelop1;
    NetworkImageView mImage0,mImage1;

    ImageLoader mImageLoader;

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDescription = (TextView) findViewById(R.id.description);
        mTitle = (TextView) findViewById(R.id.title);
        mDateLabel0 = (TextView) findViewById(R.id.dateLabel0);
        mTelop0 = (TextView) findViewById(R.id.telop0);
        mDateLabel1 = (TextView) findViewById(R.id.dateLabel1);
        mTelop1 = (TextView) findViewById(R.id.telop1);
        mImage0 = (NetworkImageView) findViewById(R.id.image0);
        mImage1 = (NetworkImageView) findViewById(R.id.image1);

        mImageLoader = MySingleton.getInstance(this).getImageLoader();

        String url = "http://weather.livedoor.com/forecast/webservice/json/v1?city=130010";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
//                                    Log.d(TAG,response.toString(2));
                                    mTitle.setText(response.getString("title"));
                                    mDescription.setText(response
                                            .getJSONObject("description")
                                            .getString("text"));
                                    mDateLabel0.setText(response
                                            .getJSONArray("forecasts")
                                            .getJSONObject(0)
                                            .getString("dateLabel"));
                                    mTelop0.setText(response
                                            .getJSONArray("forecasts")
                                            .getJSONObject(0)
                                            .getString("telop"));
                                    mDateLabel1.setText(response
                                            .getJSONArray("forecasts")
                                            .getJSONObject(1)
                                            .getString("dateLabel"));
                                    mTelop1.setText(response
                                            .getJSONArray("forecasts")
                                            .getJSONObject(1)
                                            .getString("telop"));
                                    String url0 = response
                                    .getJSONArray("forecasts")
                                            .getJSONObject(0)
                                            .getJSONObject("image")
                                            .getString("url");
                                    mImage0.setImageUrl(url0, mImageLoader);
                                    String url1 = response
                                            .getJSONArray("forecasts")
                                            .getJSONObject(1)
                                            .getJSONObject("image")
                                            .getString("url");
                                    mImage1.setImageUrl(url1, mImageLoader);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        }
        );
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}
