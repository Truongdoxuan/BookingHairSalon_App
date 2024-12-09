package vn.truongdx.bookinghairsalon_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

        btnchonDV = view.findViewById(R.id.btn_select_dv);

        //sự kiện khi onclick
        btnchonDV.setOnClickListener(v -> showCheckbox());

        return view;
    }
    //hàm thực hiện chương trình
    private void showCheckbox() {
//        //gọi mảng danh sách dịch vụ trong string.xml
//        final String[] listDv = getResources().getStringArray(R.array.sp_list);
//        //tạo mảng lưu trạng trái checkbox
//        final boolean[] selectedSP = new boolean[listDv.length];
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMultiChoiceItems(listDv, selectedSP, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                //lưu trạng thái checkbox khi đc thay đổi
//                selectedSP[which] = isChecked;
//            }
//        })
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        StringBuilder selected = new StringBuilder();
//                        for (int i = 0; i < listDv.length; i++) {
//                            if (selectedSP[i]) {
//                                selected.append(listDv[i]).append("\n");
//                            }
//                        }
//                        if (selected.length() > 0) {
//                            // Hiển thị các dịch vụ đã chọn
//                            Toast.makeText(getActivity(), "Dịch vụ đã chọn: \n" + selected.toString(), Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getActivity(), "Chưa chọn dịch vụ nào", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//                .setNegativeButton("Hủy",null);
//        builder.create().show();
    }
}