package vn.truongdx.bookinghairsalon_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import vn.truongdx.bookinghairsalon_app.R;

public class Booking_Fragment extends Fragment {
    //khai báo biến
    private Button btnchonDV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        Button btnConfirm = view.findViewById(R.id.btn_confirmBooking);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert_Confirm();
            }
        });

        return view;
    }
    //hàm thực hiện chương trình
    private void showAlert_Confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Xác nhận đặt lịch ?")
                .setCancelable(false) //chặn không thoát ra ngoài
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Action();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void Action() {
        // Xử lý hành động thực hiện sau khi xác nhận

    }
}