package vn.truongdx.bookinghairsalon_app.fragments;

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
            public void onEditClick(int position) {
                // Xử lý chỉnh sửa
                LichHen appointment = appointmentList.get(position);
                Toast.makeText(getContext(), "Sửa: " + appointment.getTenkh(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                // Xử lý xóa
                appointmentList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Đã xóa lịch hẹn", Toast.LENGTH_SHORT).show();
            }
        });

        // Đặt adapter cho RecyclerView
        recyclerView.setAdapter(adapter);

        // Lấy số điện thoại tài khoản hiện tại
        UserSession userSession = new UserSession(requireContext());
        String currentSdt = userSession.getPhone();
        Log.d("Home_Fragment", "Current Phone: '" + currentSdt + "'");
        Log.d("Home_Fragment", "Current Phone: " + currentSdt); // Log số điện thoại hiện tại

        // Kết nối đến Firebase
        DatabaseReference ref = DatabaseConnection.getDatabaseReference("lichhen");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                appointmentList.clear();
                Log.d("Home_Fragment", "Snapshot count: " + snapshot.getChildrenCount());
                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot appointmentSnapshot : dateSnapshot.getChildren()) {
                        LichHen appointment = appointmentSnapshot.getValue(LichHen.class);
                        if (appointment != null && currentSdt.equals(appointment.getSdt())) {
                            appointmentList.add(appointment);
                            Log.d("Home_Fragment", "Appointment: " + appointment.getTenkh());
                        } else {
                            Log.e("Home_Fragment", "Lỗi lấy dữ liệu từ snapshot");
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d("Home_Fragment", "Appointment list size: " + appointmentList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                Log.e("Home_Fragment", "DatabaseError: " + error.getMessage());
            }
        });
        return view;
    }
}
