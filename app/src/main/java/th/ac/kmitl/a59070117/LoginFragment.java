package th.ac.kmitl.a59070117;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    private DBHelper helper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLoginBtn();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    void initLoginBtn() {
        Button _loginBtn = (Button) getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userId = (EditText) getView().findViewById(R.id.login_id);
                EditText password = (EditText) getView().findViewById(R.id.login_password);
                String userIdStr = userId.getText().toString();
                String passwordStr = password.getText().toString();
                if (userIdStr.isEmpty() || passwordStr.isEmpty()) {
                    Toast.makeText(
                            getActivity(),
                            "Please fill out this form",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER", "USER OR PASSWORD IS EMPTY");
                } else {
                    logIn(userIdStr, passwordStr);
                }
            }
        });
    }

    void initRegisterBtn() {
        TextView _registerBtn = (TextView) getView().findViewById(R.id.login_register_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null).commit();
                Log.d("USER", "GO TO REGISTER");
            }
        });

    }

    private void logIn(String id, String password) {
        SharedPreferences setting = getContext().getSharedPreferences("Pref", 0);
        if (setting.getString("id", null) != null) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new HomeFragment())
                    .addToBackStack(null).commit();
        } else {
            if (helper.checkUser(id, password)) {
                User user = helper.getUser(id);
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("id", id);
                editor.putString("name", user.getName());
                editor.commit();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new HomeFragment())
                        .addToBackStack(null).commit();
            } else {
                Toast.makeText(
                        getActivity(),
                        "Invalid user or password",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}
