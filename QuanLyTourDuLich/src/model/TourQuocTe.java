package model;

/** Tour quốc tế - kế thừa Tour */
public class TourQuocTe extends Tour {

    public TourQuocTe() {
        super();
    }

    public TourQuocTe(String maTour, String tenTour, int soNgay, double gia, String diemKhoiHanh) {
        super(maTour, tenTour, soNgay, gia, diemKhoiHanh);
    }

    @Override
    public String getLoaiTour() {
        return "Quốc tế";
    }

    @Override
    public double tinhTongTien(int soNguoi) {
        // Hiện tại giá x số người. Muốn tính thêm phí visa thì cộng thêm ở đây.
        return gia * soNguoi;
    }
}