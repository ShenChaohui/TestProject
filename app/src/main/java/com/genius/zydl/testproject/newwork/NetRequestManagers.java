package com.genius.zydl.testproject.newwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.genius.zydl.testproject.adapters.ListViewCommonAdapter;
import com.genius.zydl.testproject.entity.Movie;
import com.genius.zydl.testproject.utils.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetRequestManagers {

    public static void getMovies(final Context context, int start, int count, final ListViewCommonAdapter adapter, final ArrayList<Movie> movies) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("正在加载...");
        dialog.show();
        RequestParams params = new RequestParams(Urls.GET_MOVIE_TOP250);
        params.addParameter("start", start);
        params.addParameter("count", count);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray moviesJson = object.getJSONArray("subjects");
                    ArrayList<Movie> temp = (ArrayList<Movie>) GsonUtil.parseJsonArrayWithGson(moviesJson.toString(), Movie.class);
                    movies.addAll(temp);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dialog.dismiss();
            }
        });

    }

    public static void getJSONByOkHttp3(final Context context, final TextView textView) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Urls.GET_USER_NAME;
        final Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(result);
                    }
                });
            }
        });


    }

    public static void getImageByOkHttp3(final Context context, final ImageView imageView) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Urls.GET_IMAGE;
        final Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();//得到图片的流
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });


    }

}
