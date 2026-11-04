package model;

public class DatTour {
    private String maDT;
    private KhachHang khachHang;
    private Tour tour;
    private HuongDanVien huongDanVien;
    private PhuongTien phuongTien;
    private int soNguoi;
    private double tongTien;

    public DatTour(String maDT, KhachHang khachHang, Tour tour,
                   HuongDanVien huongDanVien, PhuongTien phuongTien, int soNguoi) {
        this.maDT = maDT;
        this.khachHang = khachHang;
        this.tour = tour;
        this.huongDanVien = huongDanVien;
        this.phuongTien = phuongTien;
        this.soNguoi = soNguoi;
        this.tongTien = tinhTongTien();
    }

    /** Tong tien: giao cho Tour tinh (da hinh: tour noi dia / quoc te co the tinh khac nhau). */
    public double tinhTongTien() {
        return tour.tinhTongTien(soNguoi);
    }

    // ===== Getter =====
    public String getMaDT() { return maDT; }
    public KhachHang getKhachHang() { return khachHang; }
    public Tour getTour() { return tour; }
    public HuongDanVien getHuongDanVien() { return huongDanVien; }
    public PhuongTien getPhuongTien() { return phuongTien; }
    public int getSoNguoi() { return soNguoi; }
    public double getTongTien() { return tongTien; }

    // ===== Setter (dung khi sua dat tour) =====
    public void setHuongDanVien(HuongDanVien huongDanVien) { this.huongDanVien = huongDanVien; }
    public void setPhuongTien(PhuongTien phuongTien) { this.phuongTien = phuongTien; }
    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
        this.tongTien = tinhTongTien();   // tinh lai tien
    }

    /** 1 dong trong danh sach: Ma DT | Khach hang | Tour | So nguoi | Tong tien */
    public void hienThi() {
        System.out.printf("%-8s %-22s %-24s %-9d %s%n",
                maDT, khachHang.getHoTen(), tour.getTenTour(), soNguoi, Tour.dinhDangTien(tongTien));
    }

    /** Hien thi chi tiet dang cay nhu mo ta de bai. */
    public void hienThiChiTiet() {
        System.out.println(maDT);
        System.out.println(" |-- Khach hang    : " + khachHang.getMa() + " - " + khachHang.getHoTen());
        System.out.println(" |-- Tour          : " + tour.getMaTour() + " - " + tour.getTenTour());
        System.out.println(" |-- Huong dan vien: " + huongDanVien.getMa() + " - " + huongDanVien.getHoTen());
        System.out.println(" |-- Phuong tien   : " + phuongTien.getMaPT() + " - " + phuongTien.getTenPT());
        System.out.println(" |-- So nguoi      : " + soNguoi);
        System.out.println(" `-- Tong tien     : " + Tour.dinhDangTien(tongTien) + " VND");
    }

    /** Luu file chi luu MA cua cac doi tuong lien ket. */
    public String toFileString() {
        return maDT + "|" + khachHang.getMa() + "|" + tour.getMaTour() + "|"
                + huongDanVien.getMa() + "|" + phuongTien.getMaPT() + "|" + soNguoi;
    }
}
