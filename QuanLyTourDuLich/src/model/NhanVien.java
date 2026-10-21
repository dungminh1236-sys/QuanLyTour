package model;

import util.NhapLieu;

/** Lớp trừu tượng - gốc của cây kế thừa Nhân viên. */
public abstract class NhanVien {
    protected String ma;
    protected String hoTen;
    protected String sdt;

    public NhanVien(String ma, String hoTen, String sdt) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public String getMa() { return ma; }
    public String getHoTen() { return hoTen; }
    public String getSdt() { return sdt; }

    /** Mỗi loại nhân viên tự khai báo chức vụ của mình (đa hình). */
    public abstract String getChucVu();

    /** Nhân viên có khớp từ khóa tìm kiếm không. */
    public boolean khop(String tuKhoa) {
        String k = tuKhoa.toLowerCase();
        return ma.toLowerCase().contains(k)
                || hoTen.toLowerCase().contains(k)
                || getChucVu().toLowerCase().contains(k)
                || sdt.contains(k);
    }

    /** Nhập thông tin mới (Enter để giữ nguyên giá trị cũ). */
    public void capNhat() {
        String t = NhapLieu.docChuoi("Họ tên mới [" + hoTen + "]: ");
        if (!t.isEmpty()) {
            hoTen = t;
        }
        while (true) {
            String s = NhapLieu.docChuoi("SĐT mới [" + sdt + "]: ");
            if (s.isEmpty()) {
                break;
            }
            if (NhapLieu.sdtHopLe(s)) {
                sdt = s;
                break;
            }
            System.out.println("SĐT không hợp lệ (9-11 chữ số), nhập lại hoặc Enter để giữ nguyên!");
        }
    }

    @Override
    public String toString() {
        return ma + " | " + hoTen + " | " + getChucVu() + " | " + sdt;
    }
}