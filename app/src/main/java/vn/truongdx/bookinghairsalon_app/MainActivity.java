package vn.truongdx.bookinghairsalon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.locks.ReentrantLock;

import vn.truongdx.bookinghairsalon_app.activities.Home_Activity;
import vn.truongdx.bookinghairsalon_app.activities.SignUp_Activity;
import vn.truongdx.bookinghairsalon_app.utils.DatabaseConnection;

public class MainActivity extends AppCompatActivity {

    // Khai báo biến
    private EditText sdt, mathkhau;
    private Button dangnhap;
//    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdt = findViewById(R.id.txt_sdt);
        mathkhau = findViewById(R.id.txt_mk);
        dangnhap = findViewById(R.id.btn_dangnhap);
//        progressBar = findViewById(R.id.progress_bar);

        // Lắng nghe sự kiện nút đăng nhập
        dangnhap.setOnClickListener(view -> Login());
    }

    // Hàm thực hiện đăng nhập
    private void Login() {
        // Lấy giá trị nhập vào
        String sdtInput = sdt.getText().toString().trim();
        String mkInput = mathkhau.getText().toString().trim();

        // Trường hợp rỗng
        if (sdtInput.isEmpty() || mkInput.isEmpty()) {
            Toast.makeText(MainActivity.this, "Số điện thoại hoặc mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị ProgressBar
//        progressBar.setVisibility(View.VISIBLE);

        // Gọi database
        DatabaseReference databaseRef = DatabaseConnection.getDatabaseReference("taikhoan");

// Truy vấn vào nhánh nhanvien hoặc khachhang
        databaseRef.child("nhanvien").orderByChild("sdt").equalTo(sdtInput).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Kiểm tra mật khẩu ở nhánh nhanvien
                    boolean loginSuccess = false;
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String dbmatkhau = userSnapshot.child("mk").getValue(String.class);
                        if (dbmatkhau != null && dbmatkhau.equals(mkInput)) {
                            // Đăng nhập thành công
                            Intent iPageHome = new Intent(MainActivity.this, Home_Activity.class);
                            startActivity(iPageHome);
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            finish();
                            loginSuccess = true;
                            break;
                        }
                    }
                    if (!loginSuccess) {
                        Toast.makeText(MainActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu không có trong nhánh nhanvien, kiểm tra trong nhánh khachhang
                    databaseRef.child("khachhang").orderByChild("sdt").equalTo(sdtInput).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                boolean loginSuccess = false;
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    String dbmatkhau = userSnapshot.child("mk").getValue(String.class);
                                    if (dbmatkhau != null && dbmatkhau.equals(mkInput)) {
                                        // Đăng nhập thành công
                                        Intent iPageHome = new Intent(MainActivity.this, Home_Activity.class);
                                        startActivity(iPageHome);
                                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                        loginSuccess = true;
                                        break;
                                    }
                                }
                                if (!loginSuccess) {
                                    Toast.makeText(MainActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Sai tên đăng nhập
                                Toast.makeText(MainActivity.this, "Tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Lỗi kết nối database: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối database: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void moveSignUp(View view) {
        Intent intent = new Intent(MainActivity.this, SignUp_Activity.class);
        startActivity(intent);
        finish();
    }
}
