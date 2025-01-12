package vn.truongdx.bookinghairsalon_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private static final String PREF_NAME = "USER_INFO";
    private static final String KEY_NAME = "name"; // Tên khách hàng
    private static final String KEY_PHONE = "phone"; // Số điện thoại
    private static final String KEY_EMAIL = "email"; // Email
    private static final String KEY_PASSWORD = "password"; // Mật khẩu
    private static final String KEY_ROLE = "role"; // Vai trò

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public UserSession(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu thông tin người dùng
    public void saveUserInfo(String name, String phone, String email, String password, String role) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    // Lấy tên khách hàng
    public String getName() {
        return sharedPreferences.getString(KEY_NAME, null);
    }

    // Lấy số điện thoại
    public String getPhone() {
        return sharedPreferences.getString(KEY_PHONE, null);
    }

    // Lấy email
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    // Lấy mật khẩu
    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    // Lấy vai trò
    public String getRole() {
        return sharedPreferences.getString(KEY_ROLE, null);
    }

    // Xóa thông tin người dùng (Đăng xuất)
    public void clearUserInfo() {
        editor.clear();
        editor.apply();
    }
}
