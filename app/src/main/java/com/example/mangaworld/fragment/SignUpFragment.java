package com.example.mangaworld.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mangaworld.R;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.UserController;
import com.example.mangaworld.model.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton btnClose;
    Button btnRegister;
    EditText txtID, txtName, txtEmail, txtPassword, txtConfirmPassword;

    ArrayList<UserModel> data = new ArrayList<>();

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel newUser = new UserModel();
                newUser.setId(txtID.getText().toString());
                newUser.setName(txtName.getText().toString());
                newUser.setPass(txtPassword.getText().toString());
                newUser.setEmail(txtEmail.getText().toString());
                newUser.setIdRole(2);

                UserController controller = new UserController(LoadActivity.url,getActivity());
                controller.getUsers(new IVolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        data.addAll(controller.convertJSONData(result));
                        register(newUser);
                    }
                });

            }
        });
    }

    private void register(@NonNull UserModel newUser) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if (newUser.getId().isEmpty())
        {
            Toast.makeText(getActivity(), "Vui lòng nhập ID!", Toast.LENGTH_SHORT).show();
        } else if (newUser.getName().isEmpty())
        {
            Toast.makeText(getActivity(), "Vui lòng nhập Họ và tên!", Toast.LENGTH_SHORT).show();
        } else if (newUser.getEmail().isEmpty())
        {
            Toast.makeText(getActivity(), "Vui lòng nhập Email!", Toast.LENGTH_SHORT).show();
        } else if (!newUser.getEmail().matches(emailPattern))
        {
            Toast.makeText(getActivity(), "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
        } else if (!checkExist().isEmpty())
        {
            Toast.makeText(getActivity(), checkExist(), Toast.LENGTH_SHORT).show();
        } else if (newUser.getPass().isEmpty())
        {
            Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
        } else if (!txtPassword.getText().toString().trim().equals(txtConfirmPassword.getText().toString().trim()))
        {
            Toast.makeText(getActivity(), "Nhập lại mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
        } else if (!newUser.getEmail().isEmpty())
        {
            int code = ((MainActivity) getActivity()).sendOTPCode(newUser.getEmail());
            ((MainActivity) getActivity()).openFragment(
                    OTPFragment.newInstance("", "", code, newUser));
        }
    }

    private String checkExist() {
        final String[] text = {""};

        for (UserModel user : data)
        {
            if (user.getId().trim().equals(txtID.getText().toString().trim())) {
                text[0] = "ID đã tồn tại xin vui lòng chọn ID khác!";
            }
            if (user.getEmail().trim().equals(txtEmail.getText().toString().trim())) {
                text[0] = "Email đã liên kết với tài khoản khác!";
            }
        }

        return text[0];
    }

    private void setControl(View view) {
        btnClose = view.findViewById(R.id.btnSignUpClose);

        btnRegister = view.findViewById(R.id.btnRegister);
        txtID = view.findViewById(R.id.txtID);
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.txtPassword);
        txtConfirmPassword = view.findViewById(R.id.txtConfirmPassword);
    }

}