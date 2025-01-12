package vn.truongdx.bookinghairsalon_app.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.models.entities.LichHen;

public class LichHen_Adapter extends RecyclerView.Adapter<LichHen_Adapter.AppointmentViewHolder> {
    private List<LichHen> lichHenList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public LichHen_Adapter(List<LichHen> lichHenList, OnItemClickListener listener) {
        this.lichHenList = lichHenList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichhen,parent,false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        LichHen lichHen = lichHenList.get(position);
        holder.tvName.setText("Tên: " + lichHen.getTenkh());
        holder.tvPhone.setText("Số điện thoại: " + lichHen.getSdt());
        holder.tvDate.setText("Ngày: " + lichHen.getDate());
        holder.tvTime.setText("Thời gian: " + lichHen.getTime());
        holder.tvNotes.setText("Ghi chú: " + lichHen.getNote());

        holder.btnEdit.setOnClickListener(v -> listener.onEditClick(position));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return lichHenList.size();
    }

    // Constructor, dữ liệu, và các phương thức khác

    public static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các View bên trong item
        TextView tvName, tvPhone, tvDate, tvTime, tvNotes;
        Button btnEdit, btnDelete;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
