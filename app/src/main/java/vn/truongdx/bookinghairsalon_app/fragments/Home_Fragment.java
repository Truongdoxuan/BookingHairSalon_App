package vn.truongdx.bookinghairsalon_app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.models.adapters.LichHen_Adapter;
import vn.truongdx.bookinghairsalon_app.models.entities.LichHen;

public class Home_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private LichHen_Adapter adapter;
    private List<LichHen> appointmentList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        appointmentList = new ArrayList<>();
        // Add sample data
        appointmentList.add(new LichHen("Nguyễn Văn A","Nam", "0123456789", "21/12/2024", "14:00", "Mang tài liệu"));

        adapter = new LichHen_Adapter(appointmentList, new LichHen_Adapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                // Handle edit action
                LichHen appointment = appointmentList.get(position);
                Toast.makeText(getContext(), "Sửa: " + appointment.getTenkh(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                // Handle delete action
                appointmentList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "Đã xóa lịch hẹn", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}