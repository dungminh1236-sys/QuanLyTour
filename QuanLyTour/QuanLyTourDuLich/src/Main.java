
package src;

import model.*;
import service.*;
import util.*;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo các service quản lý
        QuanLyTour quanLyTour = new QuanLyTour();
        QuanLyKhachHang quanLyKhachHang = new QuanLyKhachHang();
        QuanLyNhanVien quanLyNhanVien = new QuanLyNhanVien();
        QuanLyDiaDiem quanLyDiaDiem = new QuanLyDiaDiem();
        QuanLyPhuongTien quanLyPhuongTien = new QuanLyPhuongTien();

        // Tải dữ liệu phương tiện từ file
        quanLyPhuongTien.docTuFile();

        // Chuẩn bị danh sách tham chiếu để đồng bộ cho module Đặt tour
        List<KhachHang> dsKhachHang = Arrays.asList(quanLyKhachHang.getDanhSach());
        List<Tour> dsTour = quanLyTour.getDsTour();
        List<HuongDanVien> dsHuongDanVien = Arrays.asList(quanLyNhanVien.getDanhSachHuongDanVien());

        QuanLyDatTour quanLyDatTour = new QuanLyDatTour(dsKhachHang, dsTour, dsHuongDanVien, quanLyPhuongTien);
        
        // Tải dữ liệu đặt tour từ file sau khi đã có đầy đủ các danh sách liên kết
        quanLyDatTour.docTuFile();

        int choice;
        do {
            System.out.println("\n0.MENU CHÍNH");
            System.out.println("CHƯƠNG TRÌNH QUẢN LÝ TOUR");
            System.out.println("1. Quản lý Tour");
            System.out.println("2. Quản lý Khách hàng");
            System.out.println("3. Quản lý Nhân viên");
            System.out.println("4. Quản lý Địa điểm");
            System.out.println("5. Quản lý Phương tiện");
            System.out.println("6. Quản lý Đặt tour");
            System.out.println("7. Thống kê");
            System.out.println("0. Thoát");
            choice = NhapLieu.docSo("Nhập lựa chọn: ", 0, 7);

            switch (choice) {
                case 1:
                    quanLyTour.menu();
                    break;
                case 2:
                    quanLyKhachHang.chayMenu();
                    break;
                case 3:
                    quanLyNhanVien.chayMenu();
                    break;
                case 4:
                    quanLyDiaDiem.chayMenu();
                    break;
                case 5:
                    quanLyPhuongTien.menu();
                    break;
                case 6:
                    quanLyDatTour.menu();
                    break;
                case 7:
                    hienThiThongKe(quanLyTour, quanLyKhachHang, quanLyNhanVien, quanLyDiaDiem, quanLyPhuongTien, quanLyDatTour);
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình quản lý tour du lịch!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 7.");
            }
        } while (choice != 0);
    }

    /**
     * Chức năng 7: Thống kê toàn bộ hệ thống đúng chuẩn theo yêu cầu đề bài
     */
    private static void hienThiThongKe(QuanLyTour qlTour, QuanLyKhachHang qlKH,
                                      QuanLyNhanVien qlNV, QuanLyDiaDiem qlDD,
                                      QuanLyPhuongTien qlPT, QuanLyDatTour qlDT) {
        System.out.println("\nTHỐNG KÊ");
        
        // 1. Thống kê Tour
        List<Tour> dsTour = qlTour.getDsTour();
        int tongTour = dsTour.size();
        int tourNoiDia = 0;
        int tourQuocTe = 0;
        for (Tour t : dsTour) {
            if (t instanceof TourNoiDia || t.getLoaiTour().equalsIgnoreCase("Nội địa")) {
                tourNoiDia++;
            } else {
                tourQuocTe++;
            }
        }
        System.out.println("Tổng số tour: " + tongTour);
        System.out.println("Tour nội địa: " + tourNoiDia);
        System.out.println("Tour quốc tế: " + tourQuocTe);

        // 2. Thống kê Khách hàng
        int tongKH = qlKH.getSoLuong();
        System.out.println("Tổng số khách hàng: " + tongKH);

        // 3. Thống kê Nhân viên
        HuongDanVien[] dsHDV = qlNV.getDanhSachHuongDanVien();
        int soHDV = dsHDV.length;
        int tongNV = 0;
        int soNVDieuHanh = 0;
        for (int i = 1; i <= 200; i++) {
            String ma = String.format("NV%03d", i);
            NhanVien nv = qlNV.timTheoMa(ma);
            if (nv != null) {
                tongNV++;
                if (nv instanceof NhanVienDieuHanh) {
                    soNVDieuHanh++;
                }
            }
        }
        System.out.println("Tổng số nhân viên: " + tongNV);
        System.out.println("- Hướng dẫn viên: " + soHDV);
        System.out.println("- Nhân viên điều hành: " + soNVDieuHanh);

        // 4. Thống kê Địa điểm
        int tongDD = 0;
        for (int i = 1; i <= 200; i++) {
            String ma = String.format("DD%03d", i);
            DiaDiem dd = qlDD.timTheoMa(ma);
            if (dd != null) {
                tongDD++;
            }
        }
        System.out.println("Tổng số địa điểm: " + tongDD);

        // 5. Thống kê Phương tiện
        List<PhuongTien> dsPT = qlPT.getDanhSach();
        int tongPT = dsPT.size();
        int soXe = qlPT.demXe();
        int soMayBay = qlPT.demMayBay();
        System.out.println("Tổng số phương tiện: " + tongPT);
        System.out.println("- Xe du lịch: " + soXe);
        System.out.println("- Máy bay: " + soMayBay);

        // 6. Thống kê Đặt tour & Doanh thu
        int tongLuotDat = qlDT.tongSoLuotDat();
        int tongKhachDat = qlDT.tongSoKhachDaDat();
        System.out.println("Tổng số lượt đặt tour: " + tongLuotDat);
        System.out.println("Tổng số khách đã đặt: " + tongKhachDat);

        double doanhThuNoiDia = 0;
        double doanhThuQuocTe = 0;
        for (DatTour dt : qlDT.getDanhSach()) {
            Tour t = dt.getTour();
            if (t != null) {
                if (t instanceof TourNoiDia || t.getLoaiTour().equalsIgnoreCase("Nội địa")) {
                    doanhThuNoiDia += dt.getTongTien();
                } else {
                    doanhThuQuocTe += dt.getTongTien();
                }
            }
        }
        double tongDoanhThu = doanhThuNoiDia + doanhThuQuocTe;
        System.out.println("Doanh thu:");
        System.out.println("Tour nội địa: " + Tour.dinhDangTien(doanhThuNoiDia) + " VNĐ");
        System.out.println("Tour quốc tế: " + Tour.dinhDangTien(doanhThuQuocTe) + " VNĐ");
        System.out.println("Tổng doanh thu: " + Tour.dinhDangTien(tongDoanhThu) + " VNĐ");
        System.out.println("0. Quay lại");
        System.out.print("Nhập lựa chọn: 0. Quay lại\n");
    }
}
```[cite: 52, 60]