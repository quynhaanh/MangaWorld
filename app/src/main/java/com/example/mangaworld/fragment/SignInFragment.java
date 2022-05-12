package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.UserController;
import com.example.mangaworld.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton btnClose;
    Button btnLogin;
    EditText txtID, txtPassword;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        setControl(view);
        setEvent();

        return view;
    }

    private void setEvent() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = txtID.getText().toString();
                String pass = txtPassword.getText().toString();

                UserModel user = new UserModel();
                user.setId(userID);
                user.setPass(pass);

                login(user);
            }
        });
    }

    private void setControl(View view) {
        btnClose = view.findViewById(R.id.btnSignInClose);
        btnLogin = view.findViewById(R.id.btnSignInLogin);

        txtID = view.findViewById(R.id.txtSignInID);
        txtPassword = view.findViewById(R.id.txtSignInPassword);
    }

    private void login(UserModel user)
    {
        String urlAPI = LoadActivity.url+"/api/truyenchu/login.php";
        UserController controller = new UserController(urlAPI,getActivity());
        controller.login(user, new IVolleyCallback() {
            @Override
            public void onSuccess(String result) {
                UserModel userModel = controller.convertJSONUser(result);
                ((MainActivity)getActivity()).setLoggedUser(userModel);
                ((MainActivity) getActivity()).openFragment(
                        AccountInfoFragment.newInstance("", "", userModel));
            }
        });
    }

}