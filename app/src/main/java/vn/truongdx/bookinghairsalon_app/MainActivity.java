package vn.truongdx.bookinghairsalon_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
                NextPage_Home(view);
            }
        });
    }

    //hàm thực hiện chương trình
    public void NextPage_Home(View view) {

    }
}