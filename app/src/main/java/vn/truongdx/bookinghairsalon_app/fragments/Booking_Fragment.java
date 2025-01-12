package vn.truongdx.bookinghairsalon_app.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.models.entities.LichHen;
import vn.truongdx.bookinghairsalon_app.utils.DatabaseConnection;

public class Booking_Fragment extends Fragment {
    //khai báo biến
    private TextView tenKH, sdtKH, textTime, textDate, ghichu;
    private RadioButton gioiTinh;
    private Button btnSetTime, btnConfirm;
    private Button btnToday, btnTomorrow, btnNextday, btnOrtherday;

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
        tenKH = view.findViewById(R.id.txt_tenKH);
        sdtKH = view.findViewById(R.id.txt_sdtKH);
        gioiTinh = view.findViewById(R.id.rd_women);
        ghichu = view.findViewById(R.id.txt_noteKH);
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

        // Lấy ngày hiện tại
        Calendar lich = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Sự kiện nút hôm nay
        btnToday.setOnClickListener(v -> {
            String today = sdf.format(lich.getTime());
            textDate.setText(today);
        });

        // Sự kiện nút ngày mai
        btnTomorrow.setOnClickListener(v -> {
            Calendar tomorrowCalendar = (Calendar) lich.clone();  // Tạo bản sao của lich
            tomorrowCalendar.add(Calendar.DAY_OF_YEAR, 1); // Cộng thêm 1 ngày
            String tomorrow = sdf.format(tomorrowCalendar.getTime());
            textDate.setText(tomorrow);
        });

        // Sự kiện nút ngày kia
        btnNextday.setOnClickListener(v -> {
            Calendar nextDayCalendar = (Calendar) lich.clone(); // Tạo bản sao của lich
            nextDayCalendar.add(Calendar.DAY_OF_YEAR, 2); // Cộng thêm 2 ngày
            String nextday = sdf.format(nextDayCalendar.getTime());
            textDate.setText(nextday);
        });

        // Sự kiện nút ngày khác
        btnOrtherday.setOnClickListener(v -> {
            // Hiển thị date dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePicker, year, month, day) -> {
                        // Lấy ngày được chọn từ datepicker
                        String getDay = String.format("%02d/%02d/%d", day, month + 1, year);  // Tháng là từ 0, nên phải cộng thêm 1
                        textDate.setText(getDay);
                    },
                    lich.get(Calendar.YEAR),
                    lich.get(Calendar.MONTH),
                    lich.get(Calendar.DAY_OF_MONTH)
            );

            // Đặt ngày tối thiểu cho datepicker
            datePickerDialog.getDatePicker().setMinDate(lich.getTimeInMillis());

            // Đặt ngày tối đa cho datepicker
            Calendar maxDateCalendar = (Calendar) lich.clone(); // Tạo bản sao của lich
            maxDateCalendar.add(Calendar.MONTH, 2); // Set ngày tối đa là 2 tháng sau
            long maxDate = maxDateCalendar.getTimeInMillis();
            datePickerDialog.getDatePicker().setMaxDate(maxDate);

            datePickerDialog.show(); // Hiển thị dialog
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

                        sendDataToFirebase();
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
    private void sendDataToFirebase() {
        // Xử lý hành động thực hiện sau khi xác nhận
        String tenkh = tenKH.getText().toString();
        String sdt = sdtKH.getText().toString();
        String date = textDate.getText().toString();
        String time = textTime.getText().toString();
        String note = ghichu.getText().toString().isEmpty() ? "Không" : ghichu.getText().toString();
        String gioitinh = gioiTinh.isChecked() ? "Nữ" : "Nam";
        //kiểm tra trống dữ liệu
        if (tenkh.isEmpty() || sdt.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        //tạo lichhen mới để gửi dữ liệu lên firebase
        LichHen lichHen = new LichHen(tenkh,gioitinh,sdt,date,time,note);

        //lấy instance của firebase
        DatabaseReference ref = DatabaseConnection.getDatabaseReference("lichhen");

        //Tạo ID lịch hẹn theo ngày hiện tại
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
            Date parsedDate = sdf.parse(date);

            String lichhenDate = new SimpleDateFormat("dd_MM_yyyy", Locale.getDefault()).format(parsedDate);
            DatabaseReference dataRef = ref.child(lichhenDate);

            //lấy số lượng lịch heẹn hiện có trong ngày
            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int nextId = (int) snapshot.getChildrenCount() + 1;
                    String lichhenId = "LH" +String.format("%03d",nextId);
                    lichHen.setId(lichhenId);
                    dataRef.child(lichhenId).setValue(lichHen)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "Đặt lịch hẹn thành công", Toast.LENGTH_SHORT).show();
                                tenKH.setText("");
                                sdtKH.setText("");
                                gioiTinh.setChecked(false);
                                textDate.setText("");
                                textTime.setText("");
                                ghichu.setText("");

                                //chuyển về fragment trang chủ
                                Fragment homeFragment = new Home_Fragment();
                                FragmentManager fragmentManager = getParentFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container,homeFragment)
                                        .addToBackStack(null)
                                        .commit(); //thực hiện chuyển đổi

                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Lỗi khi đặt lịch hẹn" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Lỗi kết nối đến Firebase: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Lỗi khi tạo ID lịch hẹn",Toast.LENGTH_SHORT).show();
        }
    }
}