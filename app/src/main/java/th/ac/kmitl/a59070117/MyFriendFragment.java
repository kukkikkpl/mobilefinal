package th.ac.kmitl.a59070117;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import th.ac.kmitl.a59070117.albums.AlbumsFragment;
import th.ac.kmitl.a59070117.friend.FriendFragment;
import th.ac.kmitl.a59070117.post.PostFragment;
import th.ac.kmitl.a59070117.todo.TodoFragment;

public class MyFriendFragment extends Fragment {

    private Bundle bundle;
    private String friendId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            friendId = bundle.getString("friendId");
            bundle.putString("friendId", friendId);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initButton();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initButton() {

        Button todosBtn = (Button) getView().findViewById(R.id.my_friend_todos_btn);
        todosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoFragment todoFragment = new TodoFragment();
                todoFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, todoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button postBtn = (Button) getView().findViewById(R.id.my_friend_post_btn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFragment postFragment = new PostFragment();
                postFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, postFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button albumsBtn = (Button) getView().findViewById(R.id.my_friend_albums_btn);
        albumsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlbumsFragment albumsFragment = new AlbumsFragment();
                albumsFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, albumsFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        Button backBtn = (Button) getView().findViewById(R.id.my_friend_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new FriendFragment())
                        .commit();
            }
        });
    }
}
