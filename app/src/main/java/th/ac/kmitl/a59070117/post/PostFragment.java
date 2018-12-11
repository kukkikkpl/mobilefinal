package th.ac.kmitl.a59070117.post;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.*;
import th.ac.kmitl.a59070117.R;
import th.ac.kmitl.a59070117.comment.CommentFragment;

public class PostFragment extends Fragment {
    ListView postListView;
    PostAdapter postAdapter;
    private Bundle bundle;
    private String friendId;
    private String link;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            friendId = bundle.getString("friendId");
            bundle.putString("friendId", friendId);
            link = "https://jsonplaceholder.typicode.com/users/" + friendId + "/posts";
        } else {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBackBtn();
        readJSON();

    }

    void readJSON() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(link).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("POST", e.getMessage());
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
            final ArrayList<JSONObject> postList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                postList.add(object);
            }
            postListView = getView().findViewById(R.id.post_list);
            postAdapter = new PostAdapter(getContext(), R.layout.fragment_post_item, postList);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    postListView.setAdapter(postAdapter);

                }
            });
            postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString("postId", postList.get(position).getString("id"));
                        Log.d("POST", "Click on postId = " + postList.get(position).getString("id"));
                        CommentFragment commentFragment = new CommentFragment();
                        commentFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, commentFragment)
                                .addToBackStack(null)
                                .commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void initBackBtn() {
        Button _backBtn = (Button) getView().findViewById(R.id.post_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
