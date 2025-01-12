package vn.truongdx.bookinghairsalon_app.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.utils.UserSession;

public class Account_Fragment extends Fragment {
    //khai báo biến
    private TextView textViewName, textViewPhoneNumber, textViewEmail;
    private ImageView imageViewAvatar;
    private Button buttonEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        // Ánh xạ các view
        textViewName = view.findViewById(R.id.textViewName);
        textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        buttonEdit = view.findViewById(R.id.buttonEdit);

        // Lấy thông tin từ UserSession
        UserSession userSession = new UserSession(requireContext());
        String name = userSession.getName();
        String phone = userSession.getPhone();
        String email = userSession.getEmail();

        // Hiển thị thông tin lên giao diện
        textViewName.setText(name != null ? name : "Chưa cập nhật");
        textViewPhoneNumber.setText(phone != null ? phone : "Chưa cập nhật");
        textViewEmail.setText(email != null ? email : "Chưa cập nhật");

        // Xử lý nút "Sửa thông tin"
        buttonEdit.setOnClickListener(v -> {
            // Mở một màn hình khác hoặc dialog để chỉnh sửa thông tin
            // Bạn có thể viết logic mở EditProfileActivity ở đây
        });

        return view;
    }
}