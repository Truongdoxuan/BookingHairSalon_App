package vn.truongdx.bookinghairsalon_app.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.models.adapters.LichHen_Adapter;
import vn.truongdx.bookinghairsalon_app.models.entities.LichHen;
import vn.truongdx.bookinghairsalon_app.utils.DatabaseConnection;
import vn.truongdx.bookinghairsalon_app.utils.UserSession;

public class Home_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private LichHen_Adapter adapter;
    private List<LichHen> appointmentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo adapter với danh sách trống
        appointmentList = new ArrayList<>();
        adapter = new LichHen_Adapter(appointmentList, new LichHen_Adapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                LichHen appointmentToDelete = appointmentList.get(position);
                String phoneToDelete = appointmentToDelete.getSdt();
                Log.d("Home_Fragment", "phoneToDelete: " + phoneToDelete);
                DatabaseReference ref = DatabaseConnection.getDatabaseReference("lichhen");

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có chắc muốn hủy lịch hẹn này không?")
                        .setCancelable(false)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        boolean isDeleted = false;

                                        // Lặp qua tất cả các ngày
                                        for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                                            // Lặp qua tất cả các lịch hẹn trong ngày
                                            for (DataSnapshot appointmentSnapshot : dateSnapshot.getChildren()) {
                                                LichHen appointment = appointmentSnapshot.getValue(LichHen.class);
                                                if (appointment != null && phoneToDelete.equals(appointment.getSdt())) {
                                                    // Lấy key của lịch hẹn
                                                    String appointmentKey = appointmentSnapshot.getKey();

                                                    // Xóa lịch hẹn theo key
                                                    ref.child(dateSnapshot.getKey()).child(appointmentKey).removeValue()
                                                            .addOnSuccessListener(avoid -> {
                                                                // Sử dụng position để xóa item khỏi danh sách
                                                                appointmentList.remove(position);
                                                                adapter.notifyItemRemoved(position);
                                                                Toast.makeText(getContext(), "Đã xóa lịch hẹn thành công", Toast.LENGTH_SHORT).show();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Toast.makeText(getContext(), "Lỗi khi xóa lịch hẹn", Toast.LENGTH_SHORT).show();
                                                                Log.e("Home_Fragment", "Error deleting: " + e.getMessage());
                                                            });
                                                    isDeleted = true;
                                                    break;
                                                }
                                            }
                                            if (isDeleted) break;
                                        }

                                        if (!isDeleted) {
                                            Toast.makeText(getContext(), "Không tìm thấy lịch hẹn cần xóa", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                                        Log.e("Home_Fragment", "DatabaseError: " + error.getMessage());
                                    }
                                });
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
        });

        // Đặt adapter cho RecyclerView
        recyclerView.setAdapter(adapter);

        // Lấy số điện thoại tài khoản hiện tại
        UserSession userSession = new UserSession(requireContext());
        String currentSdt = userSession.getPhone();
        Log.d("Home_Fragment", "Current Phone: " + currentSdt);

        // Kết nối đến Firebase và tải dữ liệu lịch hẹn
        DatabaseReference ref = DatabaseConnection.getDatabaseReference("lichhen");
        // Tải lại lịch hẹn từ Firebase khi tài khoản thay đổi
        loadAppointments(ref, currentSdt);
        return view;
    }

    private void loadAppointments(DatabaseReference ref, String currentSdt) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentList.clear();  // Xóa danh sách cũ trước khi thêm mới
                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot appointmentSnapshot : dateSnapshot.getChildren()) {
                        LichHen appointment = appointmentSnapshot.getValue(LichHen.class);
                        // Kiểm tra nếu appointment không null và currentSdt cũng không null
                        if (appointment != null && currentSdt != null && currentSdt.equals(appointment.getSdt())) {
                            appointmentList.add(appointment);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("Home_Fragment", "DatabaseError: " + error.getMessage());
            }
        });
    }
}
