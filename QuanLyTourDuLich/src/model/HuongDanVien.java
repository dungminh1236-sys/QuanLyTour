package model;

/** Lớp con của NhanVien - dùng cho phần đặt tour (gán hướng dẫn viên vào tour). */
public class HuongDanVien extends NhanVien {
    public HuongDanVien(String ma, String hoTen, String sdt) {
        super(ma, hoTen, sdt);
    }

    @Override
    public String getChucVu() {
        return "Hướng dẫn viên";
    }
}