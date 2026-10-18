package service;

import model.Tour;
import model.TourNoiDia;
import model.TourQuocTe;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Quản lý Tour: xem, thêm, sửa, xóa, tìm kiếm.
 * Dữ liệu lưu ở data/tour.txt (tự đọc khi mở, tự ghi sau mỗi thay đổi).
 */
public class QuanLyTour {
    private static final String FILE_PATH = "data/tour.txt";

    private ArrayList<Tour> dsTour = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public QuanLyTour() {
        docFile();
    }

    // Người 4 (Đặt tour) sẽ dùng 2 hàm này
    public ArrayList<Tour> getDsTour() {
        return dsTour;
    }

    public Tour timTheoMa(String ma) {
        for (Tour t : dsTour) {
            if (t.getMaTour().equalsIgnoreCase(ma)) {
                return t;
            }
        }
        return null;
    }

    //  MENU 1
    public void menu() {
        int chon;
        do {
            System.out.println("\n========== QUẢN LÝ TOUR ==========");
            System.out.println("1. Xem danh sách tour");
            System.out.println("2. Thêm tour");
            System.out.println("3. Sửa tour");
            System.out.println("4. Xóa tour");
            System.out.println("5. Tìm kiếm tour");
            System.out.println("0. Quay lại");
            chon = docSo("Nhập lựa chọn: ");

            switch (chon) {
                case 1: xemDanhSach(); break;
                case 2: themTour(); break;
                case 3: suaTour(); break;
                case 4: xoaTour(); break;
                case 5: timKiem(); break;
                case 0: break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }

    // 1. XEM 
    public void xemDanhSach() {
        if (dsTour.isEmpty()) {
            System.out.println("Chưa có tour nào!");
            return;
        }
        System.out.printf("%-8s %-30s %-10s %-8s %12s%n", "Mã", "Tên tour", "Loại", "Số ngày", "Giá");
        for (Tour t : dsTour) {
            t.hienThiDong(); // đa hình: mỗi tour tự in loại của nó
        }
        System.out.println("Tổng số tour: " + dsTour.size());
    }

    // 2. THÊM 
    public void themTour() {
        System.out.println("Chọn loại tour:");
        System.out.println("1. Tour nội địa");
        System.out.println("2. Tour quốc tế");
        int loai = docSo("Nhập lựa chọn: ");
        if (loai != 1 && loai != 2) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }

        System.out.print("Tên tour: ");
        String ten = sc.nextLine().trim();
        int soNgay = docSoDuong("Số ngày: ");
        double gia = docGia("Giá tour: ");
        System.out.print("Điểm khởi hành: ");
        String diemKH = sc.nextLine().trim();

        String ma = taoMaTour();
        Tour t;
        if (loai == 1) {
            t = new TourNoiDia(ma, ten, soNgay, gia, diemKH);
        } else {
            t = new TourQuocTe(ma, ten, soNgay, gia, diemKH);
        }

        dsTour.add(t);
        ghiFile();
        System.out.println("Thêm thành công!");
        System.out.println("Mã tour: " + ma);
    }

    // 3. SỬA 
    public void suaTour() {
        System.out.print("Nhập mã tour cần sửa: ");
        String ma = sc.nextLine().trim();
        Tour t = timTheoMa(ma);
        if (t == null) {
            System.out.println("Không tìm thấy tour có mã " + ma);
            return;
        }

        System.out.println("Thông tin hiện tại:");
        System.out.println(t);

        System.out.print("Tên mới: ");
        t.setTenTour(sc.nextLine().trim());
        t.setSoNgay(docSoDuong("Số ngày mới: "));
        t.setGia(docGia("Giá mới: "));

        ghiFile();
        System.out.println("Sửa tour thành công!");
    }

    // 4. XÓA 
    public void xoaTour() {
        System.out.print("Nhập mã tour cần xóa: ");
        String ma = sc.nextLine().trim();
        Tour t = timTheoMa(ma);
        if (t == null) {
            System.out.println("Không tìm thấy tour có mã " + ma);
            return;
        }

        System.out.print("Bạn có chắc muốn xóa? (Y/N): ");
        String xacNhan = sc.nextLine().trim();
        if (xacNhan.equalsIgnoreCase("Y")) {
            dsTour.remove(t);
            ghiFile();
            System.out.println("Xóa tour thành công!");
        } else {
            System.out.println("Đã hủy xóa.");
        }
    }

    // 5. TÌM KIẾM
    public void timKiem() {
        System.out.println("1. Tìm theo mã");
        System.out.println("2. Tìm theo tên");
        System.out.println("3. Tìm theo loại tour");
        int chon = docSo("Nhập lựa chọn: ");

        List<Tour> ketQua = new ArrayList<>();

        switch (chon) {
            case 1:
                System.out.print("Nhập mã cần tìm: ");
                String ma = sc.nextLine().trim();
                for (Tour t : dsTour) {
                    if (t.getMaTour().equalsIgnoreCase(ma)) {
                        ketQua.add(t);
                    }
                }
                break;
            case 2:
                System.out.print("Nhập tên cần tìm: ");
                String ten = sc.nextLine().trim().toLowerCase();
                for (Tour t : dsTour) {
                    if (t.getTenTour().toLowerCase().contains(ten)) {
                        ketQua.add(t);
                    }
                }
                break;
            case 3:
                System.out.println("1. Tour nội địa");
                System.out.println("2. Tour quốc tế");
                int loai = docSo("Nhập lựa chọn: ");
                String tenLoai = (loai == 1) ? "Nội địa" : "Quốc tế";
                for (Tour t : dsTour) {
                    if (t.getLoaiTour().equals(tenLoai)) {
                        ketQua.add(t);
                    }
                }
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy kết quả nào!");
        } else {
            System.out.println("Kết quả:");
            for (Tour t : ketQua) {
                System.out.println(t.getMaTour() + " | " + t.getTenTour() + " | " + t.getLoaiTour());
            }
        }
    }

    // MÃ TỰ ĐỘNG
    // Tạm thời tự sinh mã T001, T002... Khi Người 5 xong MaTuDong
    // thì thay thân hàm này bằng: return MaTuDong.taoMa("T");
    private String taoMaTour() {
        int max = 0;
        for (Tour t : dsTour) {
            try {
                int so = Integer.parseInt(t.getMaTour().substring(1));
                if (so > max) max = so;
            } catch (Exception e) {
                // bỏ qua mã sai định dạng
            }
        }
        return String.format("T%03d", max + 1);
    }

    // ĐỌC / GHI FILE 
    private void docFile() {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            return; // chưa có file thì bắt đầu với danh sách rỗng
        }
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split("\\|");
                // p: loại | mã | tên | số ngày | giá | điểm khởi hành
                if (p.length < 6) continue;
                int soNgay = Integer.parseInt(p[3]);
                double gia = Double.parseDouble(p[4]);
                if (p[0].equals("Nội địa")) {
                    dsTour.add(new TourNoiDia(p[1], p[2], soNgay, gia, p[5]));
                } else {
                    dsTour.add(new TourQuocTe(p[1], p[2], soNgay, gia, p[5]));
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc file tour.txt: " + e.getMessage());
        }
    }

    private void ghiFile() {
        try {
            Files.createDirectories(Paths.get("data"));
            List<String> lines = new ArrayList<>();
            for (Tour t : dsTour) {
                lines.add(t.toFileString());
            }
            Files.write(Paths.get(FILE_PATH), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file tour.txt: " + e.getMessage());
        }
    }

    // HÀM NHẬP AN TOÀN 
    private int docSo(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    private int docSoDuong(String thongBao) {
        while (true) {
            int so = docSo(thongBao);
            if (so > 0) return so;
            System.out.println("Giá trị phải lớn hơn 0!");
        }
    }

    private double docGia(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            try {
                double gia = Double.parseDouble(sc.nextLine().trim());
                if (gia > 0) return gia;
                System.out.println("Giá phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
    }
}
