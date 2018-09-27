package com.genius.zydl.testproject.newwork;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;

import com.genius.zydl.testproject.R;
import com.genius.zydl.testproject.adapters.CommonAdapter;
import com.genius.zydl.testproject.adapters.ViewHolder;
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

    public static void getMovies(final Context context, int start, int count, final ListView listView) {
        RequestParams params = new RequestParams(Urls.GET_MOVIE_TOP250);
        params.addParameter("start", start);
        params.addParameter("count", count);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray moviesJson = object.getJSONArray("subjects");
                    ArrayList<Movie> mMovies = (ArrayList<Movie>) GsonUtil.parseJsonArrayWithGson(moviesJson.toString(), Movie.class);
                    listView.setAdapter(new CommonAdapter<Movie>(context, mMovies, R.layout.item_list_one) {
                        @Override
                        public void convert(ViewHolder holder, Movie item) {
                            x.image().bind((ImageView) holder.getView(R.id.iv_item_one), item.getImages().getLarge());
                            holder.setText(R.id.tv_item_one_name, item.getTitle());
                            holder.setText(R.id.tv_item_one_year, item.getYear());
                            holder.setText(R.id.tv_item_one_average, String.valueOf(item.getRating().getAverage()));
                        }
                    });
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

            }
        });

    }
}
