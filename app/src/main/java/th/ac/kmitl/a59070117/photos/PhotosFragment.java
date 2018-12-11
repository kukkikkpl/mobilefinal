package th.ac.kmitl.a59070117.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import th.ac.kmitl.a59070117.R;

public class PhotosFragment extends Fragment {

    private Bundle bundle;
    private String albumsId;
    private String link;
    ListView photosListView;
    PhotosAdapter photosAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            albumsId = bundle.getString("friendId");
            link = "https://jsonplaceholder.typicode.com/users/" + albumsId + "/photoss";
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        readJSON();
        initBackBtn();
    }

    void readJSON() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(link).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TODO", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                convertJSON(responseStr);
            }
        });
    }

    void convertJSON(String json) {
        try {
            JSONArray array = new JSONArray(json);
            final ArrayList<JSONObject> photosList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                photosList.add(object);
            }
            photosListView = getView().findViewById(R.id.photos_list);
            photosAdapter = new PhotosAdapter(getContext(), R.layout.fragment_photos_item, photosList);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    photosListView.setAdapter(photosAdapter);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void initBackBtn() {
        Button _backBtn = (Button) getView().findViewById(R.id.photos_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
