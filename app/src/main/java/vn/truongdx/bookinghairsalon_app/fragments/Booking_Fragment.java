package vn.truongdx.bookinghairsalon_app.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import vn.truongdx.bookinghairsalon_app.MainActivity;
import vn.truongdx.bookinghairsalon_app.R;

public class Booking_Fragment extends Fragment {
    //khai báo biến
    private Button btnchonDV;
    private Button btnSetTime, btnConfirm;
    private Button btnToday, btnTomorrow, btnNextday, btnOrtherday;
    private TextView textTime, textDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        btnConfirm = view.findViewById(R.id.btn_confirmBooking);
        btnSetTime = view.findViewById(R.id.btnSetTime);
        btnToday = view.findViewById(R.id.btn_today);
        btnTomorrow = view.findViewById(R.id.btn_tomorrow);
        btnNextday = view.findViewById(R.id.btn_nextday);
        btnOrtherday = view.findViewById(R.id.btn_otherday);
        textTime = view.findViewById(R.id.txt_time);
        textDate = view.findViewById(R.id.txt_date);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert_Confirm();
            }
        });
        btnSetTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    getContext(),
                    (timePicker, selectedHour, selectedMinute) -> {
                        String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        textTime.setText(time);
                    },
                    12, //giá trị giờ mặc định
                    0, //giá trị phút mặc định
                    true //định dạng 24h
            );
            timePickerDialog.show(); //show
        });

        //lấy ngày hiện taị
        Calendar lich = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //sự kiện nút today
        btnToday.setOnClickListener(v -> {
            String today = sdf.format(lich.getTime());
            //hiển thị trên txt
            textDate.setText(today);
        });
        //sự kiện nút ngày mai
        btnTomorrow.setOnClickListener(v -> {
            lich.add(Calendar.DAY_OF_YEAR,1); //cộng thêm 1 so với ngày hôm nay
            String tomorrow = sdf.format(lich.getTime());
            textDate.setText(tomorrow);
            lich.add(Calendar.DAY_OF_YEAR,-1); //reset về lại hôm nay trong dialog
        });
        //sự kiện nút ngày kia
        btnNextday.setOnClickListener(v -> {
            lich.add(Calendar.DAY_OF_YEAR,2); //cộng thêm 2 để lấy ngày mốt
            String nextday = sdf.format(lich.getTime());
            textDate.setText(nextday);
            lich.add(Calendar.DAY_OF_YEAR,-2);
        });
        //sự kiện nút ngày khác
        btnOrtherday.setOnClickListener(v -> {
            //hiển thị date dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePicker,year, month, day) -> {
                        //lấy ngày được chọn từ datepicker
                        String getDay = String.format("%02d/%02d/%d", day, month +1, year);
                        textDate.setText(getDay);
                    },
                    lich.get(Calendar.YEAR),
                    lich.get(Calendar.MONTH),
                    lich.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show(); //show
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