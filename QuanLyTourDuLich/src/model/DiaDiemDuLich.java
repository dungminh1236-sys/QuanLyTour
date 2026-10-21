package model;

import util.NhapLieu;

/** Địa điểm du lịch, có thêm loại hình (biển, núi, di sản...). */
public class DiaDiemDuLich extends DiaDiem {
    private String loaiHinh;

    public DiaDiemDuLich(String ma, String ten, String tinhThanh, String loaiHinh) {
        super(ma, ten, tinhThanh);
        this.loaiHinh = loaiHinh;
    }

    public String getLoaiHinh() { return loaiHinh; }

    @Override
    public boolean khop(String tuKhoa) {
        return super.khop(tuKhoa) || loaiHinh.toLowerCase().contains(tuKhoa.toLowerCase());
    }

    @Override
    public void capNhat() {
        super.capNhat();
        String l = NhapLieu.docChuoi("Loại hình mới [" + loaiHinh + "]: ");
        if (!l.isEmpty()) {
            loaiHinh = l;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | " + loaiHinh;
    }
}