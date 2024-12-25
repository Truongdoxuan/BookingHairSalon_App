package vn.truongdx.bookinghairsalon_app.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.locks.ReentrantLock;

import vn.truongdx.bookinghairsalon_app.MainActivity;
import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.fragments.Booking_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.ChairStatus_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.Contact_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.Home_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.Map_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.MenuSalon_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.MyAccount_Fragment;


public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_nav,
                R.string.close_nav
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bmenu_home) {
                replaceFragment(new Home_Fragment());
                setTitle("Trang chủ");
            } else if (item.getItemId() == R.id.bmenu_menusalon) {
                replaceFragment(new MenuSalon_Fragment());
                setTitle("Bảng giá dịch vụ");
            } else if (item.getItemId() == R.id.bmenu_booking) {
                replaceFragment(new Booking_Fragment());
                setTitle("Thông tin khách hàng đăt lịch hẹn");
            } else if (item.getItemId() == R.id.bmenu_status) {
                replaceFragment(new ChairStatus_Fragment());
                setTitle("Tình trạng ghế");
            }
            return true;
        });

        if (savedInstanceState == null) {
            replaceFragment(new Home_Fragment());
            setTitle("Trang chủ");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
            setTitle("Tài khoản");
        } else if (item.getItemId() == R.id.nav_contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Contact_Fragment()).commit();
            setTitle("Liên hệ với cửa hàng");
        } else if (item.getItemId() == R.id.nav_map) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Map_Fragment()).commit();
            setTitle("Bản đồ");
        } else if (item.getItemId() == R.id.it_logout) {
            showAlert_Logout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return; // Không thay thế nếu fragment hiện tại giống với fragment cần thay thế
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void showAlert_Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc muốn đăng xuất")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //chuyển về trang đăng nhập
                        Intent iPageLogin = new Intent(Home_Activity.this, MainActivity.class);
                        startActivity(iPageLogin);
                        finish();
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
}