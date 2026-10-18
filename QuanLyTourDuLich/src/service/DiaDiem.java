package model;

import util.NhapLieu;

/** Lớp gốc của cây kế thừa Địa điểm. */
public class DiaDiem {
    protected String ma;
    protected String ten;
    protected String tinhThanh;

    public DiaDiem(String ma, String ten, String tinhThanh) {
        this.ma = ma;
        this.ten = ten;
        this.tinhThanh = tinhThanh;
    }

    public String getMa() { return ma; }
    public String getTen() { return ten; }
    public String getTinhThanh() { return tinhThanh; }

    /** Địa điểm có khớp từ khóa tìm kiếm không. */
    public boolean khop(String tuKhoa) {
        String k = tuKhoa.toLowerCase();
        return ma.toLowerCase().contains(k)
                || ten.toLowerCase().contains(k)
                || tinhThanh.toLowerCase().contains(k);
    }

    /** Nhập thông tin mới (Enter để giữ nguyên giá trị cũ). */
    public void capNhat() {
        String t = NhapLieu.docChuoi("Tên địa điểm mới [" + ten + "]: ");
        if (!t.isEmpty()) {
            ten = t;
        }
        String tp = NhapLieu.docChuoi("Tỉnh/Thành phố mới [" + tinhThanh + "]: ");
        if (!tp.isEmpty()) {
            tinhThanh = tp;
        }
    }

    @Override
    public String toString() {
        return ma + " | " + ten + " | " + tinhThanh;
    }
}
