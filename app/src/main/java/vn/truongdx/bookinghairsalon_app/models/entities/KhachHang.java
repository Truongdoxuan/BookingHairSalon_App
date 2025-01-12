package vn.truongdx.bookinghairsalon_app.models.entities;

public class KhachHang {
    private String id;
    private String ten;
    private String sdt;
    private String mk;
    private String email;

    public KhachHang() {}

    public KhachHang(String ten, String sdt, String mk, String email) {
        this.ten = ten;
        this.sdt = sdt;
        this.mk = mk;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMk() {
        return mk;
    }

    public void setMk(String mk) {
        this.mk = mk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
