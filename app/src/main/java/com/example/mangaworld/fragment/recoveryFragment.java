package com.example.mangaworld.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.LoadActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.UserController;
import com.example.mangaworld.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recoveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recoveryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText edRecoveryEmail;
    Button btnConfirmEmail;
    ImageButton btnClose;

    public recoveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recoveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recoveryFragment newInstance(String param1, String param2) {
        recoveryFragment fragment = new recoveryFragment();
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

        View view = inflater.inflate(R.layout.fragment_recovery, container, false);

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

        btnConfirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = edRecoveryEmail.getText().toString().trim();

                if(!Email.isEmpty()) {
                    int code = ((MainActivity) getActivity()).sendOTPCode(Email);
                    UserController controller = new UserController(LoadActivity.url, getActivity());
                    controller.getUserByEmail(Email, new IVolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            UserModel user = controller.convertJSONUser(result);
                            if(user.getEmail() != null){
                                ((MainActivity) getActivity()).openFragment(
                                        OTPFragment.newInstance("", "", code, user, true));
                            }else
                                Toast.makeText(getActivity(), "Không tìm thấy tài khoản sử dụng email này", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void setControl(View view) {
        btnClose = view.findViewById(R.id.btnChangePassClose);
        btnConfirmEmail = view.findViewById(R.id.btnComfirmEmail);

        edRecoveryEmail = view.findViewById(R.id.edRecoveryEmail);
    }
}