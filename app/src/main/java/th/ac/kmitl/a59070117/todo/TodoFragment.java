package th.ac.kmitl.a59070117.todo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import okhttp3.*;
import th.ac.kmitl.a59070117.R;

public class TodoFragment extends Fragment {

    private Bundle bundle;
    private String friendId;
    private String link;
    ListView todoListView;
    TodoAdapter todoAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            friendId = bundle.getString("friendId");
            bundle.putString("friendId", friendId);
            link = "https://jsonplaceholder.typicode.com/users/" + friendId + "/todos";
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
            final ArrayList<JSONObject> todoList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                todoList.add(object);
            }
            todoListView = getView().findViewById(R.id.todo_list);
            todoAdapter = new TodoAdapter(getContext(), R.layout.fragment_todo_item, todoList);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    todoListView.setAdapter(todoAdapter);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void initBackBtn() {
        Button _backBtn = (Button) getView().findViewById(R.id.todo_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
