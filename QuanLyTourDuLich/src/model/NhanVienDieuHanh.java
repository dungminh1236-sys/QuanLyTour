package model;

/** Lớp con của NhanVien. */
public class NhanVienDieuHanh extends NhanVien {
    public NhanVienDieuHanh(String ma, String hoTen, String sdt) {
        super(ma, hoTen, sdt);
    }

    @Override
    public String getChucVu() {
        return "Nhân viên điều hành";
    }
}