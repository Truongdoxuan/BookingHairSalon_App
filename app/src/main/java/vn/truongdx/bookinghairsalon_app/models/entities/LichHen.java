package vn.truongdx.bookinghairsalon_app.models.entities;

public class LichHen {
    private String tenkh;
    private String gioitinh;
    private String sdt;
    private String date;
    private String time;
    private String note;

    public LichHen() {
    }

    public LichHen(String tenkh, String gioitinh, String sdt, String date, String time, String note) {
        this.tenkh = tenkh;
        this.gioitinh = gioitinh;
        this.sdt = sdt;
        this.date = date;
        this.time = time;
        this.note = note;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

