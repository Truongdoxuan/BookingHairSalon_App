package vn.truongdx.bookinghairsalon_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.truongdx.bookinghairsalon_app.MainActivity;
import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.activities.Home_Activity;
import vn.truongdx.bookinghairsalon_app.utils.UserSession;

public class Account_Fragment extends Fragment {
    //khai báo biến
    private TextView textViewName, textViewPhoneNumber, textViewEmail;
    private Button buttonLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        // Ánh xạ các view
        textViewName = view.findViewById(R.id.textViewName);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        UserSession userSession = new UserSession(requireContext());
        String name = userSession.getName();
        String phone = userSession.getPhone();
        String email = userSession.getEmail();

        textViewName.setText(name != null ? name : "Chưa cập nhật");
        textViewPhoneNumber.setText(phone != null ? phone : "Chưa cập nhật");
        textViewEmail.setText(email != null ? email : "Chưa cập nhật");

        buttonLogout.setOnClickListener(v -> {
            Logout();
        });

        return view;
    }
    private void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Bạn có chắc muốn đăng xuất")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Xóa thông tin người dùng đã lưu
                        UserSession userSession = new UserSession(requireContext());
                        userSession.clearSession();

                        // Chuyển về trang đăng nhập
                        Intent iPageLogin = new Intent(requireActivity(), MainActivity.class);
                        startActivity(iPageLogin);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertLogOut = builder.create();
        alertLogOut.show();
    }
}