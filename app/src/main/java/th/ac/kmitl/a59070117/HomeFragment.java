package th.ac.kmitl.a59070117;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import th.ac.kmitl.a59070117.friend.FriendFragment;

public class HomeFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showName();
        initButton();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void initButton() {
        Button profileSetupBtn = (Button) getView().findViewById(R.id.home_profile_setup_btn);
        profileSetupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new ProfileFragment())
                        .addToBackStack(null).commit();
            }
        });

        Button friendsBtn = (Button) getView().findViewById(R.id.home_friends_btn);
        friendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new FriendFragment())
                        .addToBackStack(null).commit();
            }
        });

        Button signOutBtn = (Button) getView().findViewById(R.id.home_sign_out_btn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences setting = getContext().getSharedPreferences("Pref", 0);
                setting.edit().remove("id").commit();
                setting.edit().remove("name").commit();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
            }
        });
    }

    private void showName() {
        SharedPreferences setting = getContext().getSharedPreferences("Pref", 0);
        String nameStr = setting.getString("name", "");
        TextView name = (TextView) getView().findViewById(R.id.home_name);
        name.setText("Hello " + nameStr);
    }
}
