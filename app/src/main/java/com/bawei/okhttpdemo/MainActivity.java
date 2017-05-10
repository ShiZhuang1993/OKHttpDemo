package com.bawei.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private String url = "http://result.eolinker" +
            ".com/X39jyd7dd8d690677289995f22d9d77fafd75839e51385e?";
    private String K = "uri";
    private String V = "tt";


    private String urla = "http://admin.wap.china.com/user/NavigateTypeAction" +
            ".do?processID=getNavigateNews";
    private String Z = "js";
    private TextView te;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            tGet();
            yGet();
            tPost();
            yPost();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //同步GET
    public void tGet() throws Exception {
        HttpUrl builder = HttpUrl.parse(url).newBuilder()
                .addQueryParameter(K, V)
                .build();
        final Request request = new Request.Builder()
                .url(builder)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                    }
                    // te.setText(response.body().string());
                    Log.e("-----a------", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //异步GET
    public void yGet() throws Exception {
        HttpUrl builder = HttpUrl.parse(url).newBuilder()
                .addQueryParameter(K, V)
                .build();
        final Request request = new Request.Builder()
                .url(builder)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String s = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("-----b------", s);
                        try {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " +
                                        response);
                            Headers responseHeaders = response.headers();
                            for (int i = 0; i < responseHeaders.size(); i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders
                                        .value(i));

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    //同步post
    public void tPost() throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("processID", "getNavigateNews")
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();

        final Request request = new Request.Builder()
                .url(urla)
                .post(body)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Log.e("-----c------", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //异步post
    public void yPost() {
        RequestBody body = new FormBody.Builder()
                .add("processID", "getNavigateNews")
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();
        Request builder = new Request.Builder()
                .url(urla).post(body).build();
        client.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String s = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("-----d------", s);
                        try {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " +
                                        response);
                            Headers responseHeaders = response.headers();
                            for (int i = 0; i < responseHeaders.size(); i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders
                                        .value(i));

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}
