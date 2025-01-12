package vn.truongdx.bookinghairsalon_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.security.identity.CipherSuiteNotSupportedException;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import vn.truongdx.bookinghairsalon_app.MainActivity;
import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.models.entities.KhachHang;
import vn.truongdx.bookinghairsalon_app.utils.DatabaseConnection;

public class SignUp_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

    }
    //hàm thực hiện đăng ký và up lên firebase
    public void SignUp(View view) {
        //lấy dữ liệu từ EditText
        String ten = ((EditText) findViewById(R.id.txt_ten)).getText().toString().trim();
        String sdt = ((EditText) findViewById(R.id.txt_sdt)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.txt_email)).getText().toString().trim();
        String mk = ((EditText) findViewById(R.id.txt_mk)).getText().toString().trim();
        String remk = ((EditText) findViewById(R.id.txt_remk)).getText().toString().trim();

        //kiểm tra hợp lệ
        if (ten.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sdt.isEmpty() || sdt.length() != 10) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mk.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mk.equals(remk)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        //kết nối firebase
        DatabaseReference ref = DatabaseConnection.getDatabaseReference("taikhoan/khachhang");

        //tạo id mới cho tài khoản
        ref.orderByKey().limitToLast(1).get().addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
               int nextId = 1; //mặc định id tiếp theo là 1

               //lấy id cuối cùng
               if (task.getResult().exists()) {
                   String lastId = task.getResult().getChildren().iterator().next().getKey();
                   if (lastId != null && lastId.startsWith("KH")) {
                       //lấy id cuối tăng 1
                       String lastIdStr = lastId.substring(2); //lấy phần số của id
                       nextId = Integer.parseInt(lastIdStr) +1;
                   }
               }

               //tạo id mới theo định dạng "KH001"
               String userId = String.format("KH%03d", nextId);

               //tạo đối tượng tài khoảng mới
               KhachHang khachHang = new KhachHang(ten,sdt,mk,email);
               khachHang.setId(userId);

               //gửi dữ liệu lên firebase
               ref.child(userId).setValue(khachHang).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()) {
                           Toast.makeText(SignUp_Activity.this,"Đăng ký thành công, quay về trang đăng nhập", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(SignUp_Activity.this, MainActivity.class);
                           startActivity(intent);
                           finish();
                       }
                   }
               });
           }
        });

    }
    public void moveLogin(View view) {
        Intent intent = new Intent(SignUp_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}