package vn.truongdx.bookinghairsalon_app.activities;

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

import com.google.android.material.navigation.NavigationView;

import vn.truongdx.bookinghairsalon_app.MainActivity;
import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.fragments.Contact_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.Home_Fragment;
import vn.truongdx.bookinghairsalon_app.fragments.Map_Fragment;

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_Fragment()).commit();
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
}