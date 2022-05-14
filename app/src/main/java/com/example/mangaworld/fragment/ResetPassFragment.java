package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.UserController;
import com.example.mangaworld.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPassFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton btnClose;
    Button btnResetPass;
    EditText edNewPass, edConfirmPass;
    TextView tvUserID;

    UserModel user;

    public ResetPassFragment() {
        // Required empty public constructor
    }

    public ResetPassFragment(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPassFragment newInstance(String param1, String param2, UserModel user) {
        ResetPassFragment fragment = new ResetPassFragment(user);
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
        View view = inflater.inflate(R.layout.fragment_reset_pass, container, false);
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

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidate()) {
                    user.setPass(edNewPass.getText().toString().trim());


                    UserController controller = new UserController(LoadActivity.url, getActivity());
                    controller.updateUser(user, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(getActivity(), "Đặt lại mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                            ((MainActivity) getActivity()).openFragment(
                                    SignInFragment.newInstance("", ""));
                        }

                    });
                }
            }
        });
    }

    private void setControl(View view) {
        btnClose = view.findViewById(R.id.btnChangePassClose);
        btnResetPass = view.findViewById(R.id.btnComfirmResetPass);
        edNewPass = view.findViewById(R.id.edNewPass);
        edConfirmPass = view.findViewById(R.id.edConfirmNewPass);
        tvUserID = view.findViewById(R.id.tvUserID);
        tvUserID.setText(user.getId().trim());
    }

    private boolean checkValidate() {
        if (edNewPass.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edConfirmPass.getText().toString().trim().equals(edNewPass.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Mật khẩu nhập lại không đúng!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}