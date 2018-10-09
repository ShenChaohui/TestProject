package com.genius.zydl.testproject.newwork;

import android.app.ProgressDialog;
import android.content.Context;

import com.genius.zydl.testproject.adapters.ListViewCommonAdapter;
import com.genius.zydl.testproject.entity.Movie;
import com.genius.zydl.testproject.utils.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

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
}
