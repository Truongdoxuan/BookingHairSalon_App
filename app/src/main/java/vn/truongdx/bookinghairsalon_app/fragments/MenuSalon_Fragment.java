package vn.truongdx.bookinghairsalon_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.truongdx.bookinghairsalon_app.MainActivity;
import vn.truongdx.bookinghairsalon_app.R;
import vn.truongdx.bookinghairsalon_app.activities.SignUp_Activity;

public class MenuSalon_Fragment extends Fragment {
    private Button booking;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_salon, container, false);
        booking = view.findViewById(R.id.btn_Book);
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveBooking(view);
            }
        });
        return view;
    }
    public void moveBooking(View view) {
        // Chuyển sang Fragment Booking_Fragment
        Fragment bookingFragment = new Booking_Fragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Thay thế fragment hiện tại bằng Booking_Fragment
        transaction.replace(R.id.fragment_container, bookingFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}