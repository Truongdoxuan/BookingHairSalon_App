package vn.truongdx.bookinghairsalon_app.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import vn.truongdx.bookinghairsalon_app.R;

public class Booking_Fragment extends Fragment {
    //khai báo biến
    private CheckBox cbCat, cbUon, cbNhuom, cbDuoi, cbPhuchoi;
    private CheckBox cbGoidauthg, cbGoidauds;
    private CheckBox cbMakeup, cbNoimi;
    private CheckBox cbLaymun, cbNail;
    private Button btnSelectAll, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        return view;
    }
}