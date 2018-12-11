package th.ac.kmitl.a59070117.friend;

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
import th.ac.kmitl.a59070117.MyFriendFragment;
import th.ac.kmitl.a59070117.R;

public class FriendFragment extends Fragment {
    ListView friendListView;
    FriendAdapter friendAdapter;

    String link = "https://jsonplaceholder.typicode.com/users/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, container, false);
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
                Log.e("FRIEND", e.getMessage());
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
            final ArrayList<JSONObject> friendList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                friendList.add(object);
            }
            friendListView = getView().findViewById(R.id.friend_list);
            friendAdapter = new FriendAdapter(getContext(), R.layout.fragment_friend_item, friendList);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    friendListView.setAdapter(friendAdapter);

                }
            });
            friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Bundle bundle = new Bundle();
                        bundle.putString("friendId", friendList.get(position).getString("id"));
                        bundle.putString("friendName", friendList.get(position).getString("name"));
                        Log.d("FRIEND", "Click on userId = " + friendList.get(position).getString("id"));

                        MyFriendFragment myFriendFragment = new MyFriendFragment();
                        myFriendFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, myFriendFragment)
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
        Button _backBtn = (Button) getView().findViewById(R.id.friend_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
