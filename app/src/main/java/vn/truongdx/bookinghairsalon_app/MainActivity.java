package vn.truongdx.bookinghairsalon_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.truongdx.bookinghairsalon_app.activities.Home_Activity;
import vn.truongdx.bookinghairsalon_app.utils.DatabaseConnection;

public class MainActivity extends AppCompatActivity {
    //khai báo biến
    EditText tendn, mathkhau;
    Button dangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tendn = findViewById(R.id.txt_tendn);
        mathkhau = findViewById(R.id.txt_mk);
        dangnhap = findViewById(R.id.btn_dangnhap);

        //lắng nghe sự kiện nút đăng nhập
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(view);
            }
        });
    }

    //hàm thực hiện chương trình
    public void Login(View view) {
        //lấy giá trị nhập vào
        String tendnInput = tendn.getText().toString().trim();
        String mkInput = mathkhau.getText().toString().trim();

        //trường hợp rỗng
        if (tendnInput.isEmpty() || mkInput.isEmpty()) {
            tendn.setError("Vui lòng nhập tên đăng nhập");
            mathkhau.setError("Vui lòng nhập mật khẩu");
            return;
        }

        //gọi databse
        DatabaseReference databaseRef = DatabaseConnection.getDatabaseReference("accounts");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean loginsuccess = false;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String dbtendn = userSnapshot.child("tendn").getValue(String.class);
                    String dbmatkhau = userSnapshot.child("mk").getValue(String.class);

                    //ktra trung khớp
                    if (dbtendn.equals(tendnInput) && dbmatkhau.equals(mkInput)) {
                        try {
                            Intent iPageHome = new Intent(MainActivity.this, Home_Activity.class);
                            startActivity(iPageHome);
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Có lỗi xảy ra khi chuyển trang", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    } else {
                        Toast.makeText(MainActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.printf("Lỗi kết nối database: "+ error.getMessage());
                Toast.makeText(MainActivity.this, "Lỗi kết nối database: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}