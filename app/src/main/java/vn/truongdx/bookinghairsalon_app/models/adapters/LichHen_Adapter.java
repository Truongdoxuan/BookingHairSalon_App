package vn.truongdx.bookinghairsalon_app.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.models.entities.LichHen;

public class LichHen_Adapter extends RecyclerView.Adapter<LichHen_Adapter.AppointmentViewHolder> {
    private List<LichHen> lichHenList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public LichHen_Adapter(List<LichHen> lichHenList, OnItemClickListener listener) {
        this.lichHenList = lichHenList != null ? lichHenList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichhen, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        LichHen lichHen = lichHenList.get(position);

        // Kiểm tra nếu giá trị không null để tránh lỗi NullPointerException
        holder.tvName.setText(lichHen.getTenkh() != null ? "Tên: " + lichHen.getTenkh() : "Tên: N/A");
        holder.tvPhone.setText(lichHen.getSdt() != null ? "Số điện thoại: " + lichHen.getSdt() : "Số điện thoại: N/A");
        holder.tvDate.setText(lichHen.getDate() != null ? "Ngày: " + lichHen.getDate() : "Ngày: N/A");
        holder.tvTime.setText(lichHen.getTime() != null ? "Thời gian: " + lichHen.getTime() : "Thời gian: N/A");
        holder.tvNotes.setText(lichHen.getNote() != null ? "Ghi chú: " + lichHen.getNote() : "Ghi chú: N/A");

        // Xử lý sự kiện click cho các nút
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return lichHenList != null ? lichHenList.size() : 0;
    }

    // Constructor, dữ liệu, và các phương thức khác

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các View bên trong item
        TextView tvName, tvPhone, tvDate, tvTime, tvNotes;
        Button btnDelete;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}


