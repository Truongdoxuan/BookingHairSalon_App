package vn.truongdx.bookinghairsalon_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
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

import vn.truongdx.bookinghairsalon_app.MainActivity;
import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.databinding.ActivityMainBinding;
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bmenu_home) {
                replaceFragment(new Home_Fragment());
            } else if (item.getItemId() == R.id.bmenu_menusalon) {
                replaceFragment(new MenuSalon_Fragment());
            } else if (item.getItemId() == R.id.bmenu_booking) {
                replaceFragment(new Booking_Fragment());
            } else if (item.getItemId() == R.id.bmenu_status) {
                replaceFragment(new ChairStatus_Fragment());
            } else if (item.getItemId() == R.id.bmenu_account) {
                replaceFragment(new MyAccount_Fragment());
            }
            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            replaceFragment(new Home_Fragment());
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
        } else if (item.getItemId() == R.id.nav_contact) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Contact_Fragment()).commit();
        } else if (item.getItemId() == R.id.nav_map) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Map_Fragment()).commit();
        } else if (item.getItemId() == R.id.it_logout) {
            //chuyển về trang đăng nhập
            Intent iPageLogin = new Intent(Home_Activity.this, MainActivity.class);
            startActivity(iPageLogin);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}