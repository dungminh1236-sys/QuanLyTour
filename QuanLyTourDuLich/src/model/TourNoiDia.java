package model;

/** Tour nội địa - kế thừa Tour */
public class TourNoiDia extends Tour {

    public TourNoiDia() {
        super();
    }

    public TourNoiDia(String maTour, String tenTour, int soNgay, double gia, String diemKhoiHanh) {
        super(maTour, tenTour, soNgay, gia, diemKhoiHanh);
    }

    @Override
    public String getLoaiTour() {
        return "Nội địa";
    }

    @Override
    public double tinhTongTien(int soNguoi) {
        return gia * soNguoi; // tour nội địa: giá x số người
    }
}