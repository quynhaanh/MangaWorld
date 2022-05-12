package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
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
 * Use the {@link OTPFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OTPFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton btnClose;
    EditText edOTP1, edOTP2, edOTP3, edOTP4;
    Button btnConfirm;
    TextView tvResend, tvMailToSend;
    ProgressBar progressBar;

    int verifyCode;
    UserModel newUser;

    public OTPFragment() {
        // Required empty public constructor
    }

    public OTPFragment(int verifyCode, UserModel user) {
        this.verifyCode = verifyCode;
        this.newUser = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OTPFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OTPFragment newInstance(String param1, String param2) {
        OTPFragment fragment = new OTPFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static OTPFragment newInstance(String param1, String param2, int verifyCode, UserModel newUser) {
        OTPFragment fragment = new OTPFragment(verifyCode, newUser);
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
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        setControl(view);
        setEvent();

        tvMailToSend.setText("Mã xác nhận đã được gửi tới\n" + newUser.getEmail());

        return view;
    }

    private void setEvent() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        edOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    edOTP2.requestFocus();
                }
            }
        });

        edOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    edOTP3.requestFocus();
                }
            }
        });

        edOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    edOTP4.requestFocus();
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edOTP1.getText().toString()
                        + edOTP2.getText().toString()
                        + edOTP3.getText().toString()
                        + edOTP4.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                btnConfirm.setVisibility(View.GONE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(String.valueOf(verifyCode).equals(code))
                        {
                            UserController controller = new UserController(LoadActivity.url, getActivity());
                            controller.insertUser(newUser, new IVolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    progressBar.setVisibility(View.GONE);
                                    btnConfirm.setVisibility(View.VISIBLE);

                                    Toast.makeText(getActivity(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).openFragment(SignInFragment.newInstance("", ""));
                                }
                            });
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            btnConfirm.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Sai mã OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 500);
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode = ((MainActivity) getActivity()).sendOTPCode(newUser.getEmail());
            }
        });
    }

    private void setControl(View view) {
        btnClose = view.findViewById(R.id.btnOTPClose);

        edOTP1 = view.findViewById(R.id.edOTP1);
        edOTP2 = view.findViewById(R.id.edOTP2);
        edOTP3 = view.findViewById(R.id.edOTP3);
        edOTP4 = view.findViewById(R.id.edOTP4);

        tvResend = view.findViewById(R.id.tvOTPResend);
        tvMailToSend = view.findViewById(R.id.tvOTPMailToSend);

        btnConfirm = view.findViewById(R.id.btnOTPConfirm);
        progressBar = view.findViewById(R.id.progressBarOTP);
    }
}