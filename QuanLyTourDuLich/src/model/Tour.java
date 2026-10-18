package model;

import java.util.Locale;

/**
 * Lớp trừu tượng Tour - chứa thông tin chung của mọi loại tour.
 * Không thể tạo trực tiếp "new Tour()", phải tạo TourNoiDia hoặc TourQuocTe.
 */
public abstract class Tour {
    protected String maTour;
    protected String tenTour;
    protected int soNgay;
    protected double gia;
    protected String diemKhoiHanh;

    public Tour() {
    }

    public Tour(String maTour, String tenTour, int soNgay, double gia, String diemKhoiHanh) {
        this.maTour = maTour;
        this.tenTour = tenTour;
        this.soNgay = soNgay;
        this.gia = gia;
        this.diemKhoiHanh = diemKhoiHanh;
    }

    // Phương thức trừu tượng: lớp con BẮT BUỘC phải viết lại (override) 
    public abstract String getLoaiTour();               // "Nội địa" hoặc "Quốc tế"
    public abstract double tinhTongTien(int soNguoi);   // dùng cho phần Đặt tour

    // Getter / Setter 
    public String getMaTour() { return maTour; }
    public void setMaTour(String maTour) { this.maTour = maTour; }

    public String getTenTour() { return tenTour; }
    public void setTenTour(String tenTour) { this.tenTour = tenTour; }

    public int getSoNgay() { return soNgay; }
    public void setSoNgay(int soNgay) { this.soNgay = soNgay; }

    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }

    public String getDiemKhoiHanh() { return diemKhoiHanh; }
    public void setDiemKhoiHanh(String diemKhoiHanh) { this.diemKhoiHanh = diemKhoiHanh; }

    // Tiện ích 

    /** Định dạng tiền kiểu Việt Nam: 3500000 -> 3.500.000 */
    public static String dinhDangTien(double so) {
        return String.format(Locale.GERMANY, "%,.0f", so);
    }

    /** Chuỗi để ghi vào file tour.txt (các trường ngăn cách bằng dấu |) */
    public String toFileString() {
        return getLoaiTour() + "|" + maTour + "|" + tenTour + "|" + soNgay
                + "|" + (long) gia + "|" + diemKhoiHanh;
    }

    /** Một dòng trong bảng danh sách */
    public void hienThiDong() {
        System.out.printf("%-8s %-30s %-10s %-8d %12s%n",
                maTour, tenTour, getLoaiTour(), soNgay, dinhDangTien(gia));
    }

    @Override
    public String toString() {
        return tenTour + " | " + soNgay + " ngày | " + dinhDangTien(gia);
    }
}