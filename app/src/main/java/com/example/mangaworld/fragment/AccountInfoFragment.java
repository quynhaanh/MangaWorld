package com.example.mangaworld.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mangaworld.R;
import com.example.mangaworld.activity.AdminActivity;
import com.example.mangaworld.activity.MainActivity;
import com.example.mangaworld.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView txtName;
    TextView txtEmail;
    Button btnNav, btnLogOut;

    UserModel user;

    public AccountInfoFragment() {
        // Required empty public constructor
    }

    public AccountInfoFragment(UserModel user) {
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountInfoFragment newInstance(String param1, String param2) {
        AccountInfoFragment fragment = new AccountInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static AccountInfoFragment newInstance(String param1, String param2, UserModel user) {
        AccountInfoFragment fragment = new AccountInfoFragment(user);
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
        View view = inflater.inflate(R.layout.fragment_account_info, container, false);

        setControl(view);
        setEvent();

        if (user.getIdRole() == 1) {
            btnNav.setText("Quản trị");
        } else if (user.getIdRole() == 2) {
            btnNav.setText("Sách của bạn");
            // user here
        }

        return view;
    }

    private void setEvent() {
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getIdRole() == 1) {
                    Intent intent = new Intent(getActivity(), AdminActivity.class);
                    startActivity(intent);
                } else if (user.getIdRole() == 2) {
                    // user here - Chuyển hướng sang ACtivity của Long ở đây

                    //=======================
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thêm Dialog hỏi người dùng có muốn logout hay không tại đây

                logOut();
            }
        });
    }

    private void setControl(View view) {
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);

        btnNav = view.findViewById(R.id.btnNav);
        btnLogOut = view.findViewById(R.id.btnLogOut);
    }

    private void logOut() {
        ((MainActivity) getActivity()).setLoggedUser(null);
        ((MainActivity) getActivity()).openFragment(AccountFragment.newInstance("", ""));
    }
}