package service;

import model.DiaDiem;
import model.DiaDiemDuLich;
import util.NhapLieu;

/** Menu 4. Quản lý Địa điểm. */
public class QuanLyDiaDiem {
    private DiaDiem[] danhSach = new DiaDiem[100];
    private int soLuong = 0;

    public QuanLyDiaDiem() {
        // Dữ liệu mẫu
        danhSach[soLuong++] = new DiaDiemDuLich("DD001", "Hồ Xuân Hương", "Đà Lạt", "Thiên nhiên");
        danhSach[soLuong++] = new DiaDiemDuLich("DD002", "Bà Nà Hills", "Đà Nẵng", "Khu vui chơi");
        danhSach[soLuong++] = new DiaDiemDuLich("DD003", "Phố cổ Hội An", "Quảng Nam", "Di sản");
        danhSach[soLuong++] = new DiaDiemDuLich("DD004", "Tháp Eiffel", "Paris", "Danh lam");
    }

    private static final String TIEU_DE = "Mã | Tên địa điểm | Tỉnh/Thành phố | Loại hình";

    /** Sinh mã tự động: DD001, DD002, ... */
    private String taoMa() {
        int max = 0;
        for (int i = 0; i < soLuong; i++) {
            try {
                int so = Integer.parseInt(danhSach[i].getMa().substring(2));
                if (so > max) {
                    max = so;
                }
            } catch (NumberFormatException e) {
                // bỏ qua mã không đúng dạng DDxxx
            }
        }
        String s = "" + (max + 1);
        while (s.length() < 3) {
            s = "0" + s;
        }
        return "DD" + s;
    }

    public DiaDiem timTheoMa(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMa().equalsIgnoreCase(ma)) {
                return danhSach[i];
            }
        }
        return null;
    }

    public void chayMenu() {
        int chon;
        do {
            System.out.println();
            System.out.println("========== QUẢN LÝ ĐỊA ĐIỂM ==========");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm địa điểm");
            System.out.println("3. Sửa địa điểm");
            System.out.println("4. Xóa địa điểm");
            System.out.println("5. Tìm kiếm");
            System.out.println("0. Quay lại");
            chon = NhapLieu.docSo("Nhập lựa chọn: ", 0, 5);

            if (chon == 1) {
                hienThiTatCa();
            } else if (chon == 2) {
                them();
            } else if (chon == 3) {
                sua();
            } else if (chon == 4) {
                xoa();
            } else if (chon == 5) {
                timKiem();
            }
        } while (chon != 0);
    }

    public void hienThiTatCa() {
        if (soLuong == 0) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println();
        System.out.println(TIEU_DE);
        for (int i = 0; i < soLuong; i++) {
            System.out.println(danhSach[i]);
        }
    }

    public void them() {
        if (soLuong == danhSach.length) {
            System.out.println("Danh sách đã đầy, không thể thêm!");
            return;
        }
        String maMoi = taoMa();
        String ten = NhapLieu.docChuoiBatBuoc("Tên địa điểm: ");
        String tp = NhapLieu.docChuoiBatBuoc("Tỉnh/Thành phố: ");
        String loai = NhapLieu.docChuoiBatBuoc("Loại hình (biển, núi, di sản...): ");

        // Đa hình: biến kiểu cha, đối tượng kiểu con
        DiaDiem dd = new DiaDiemDuLich(maMoi, ten, tp, loai);
        danhSach[soLuong++] = dd;
        System.out.println("Thêm thành công! Mã: " + maMoi);
    }

    public void sua() {
        String ma = NhapLieu.docChuoiBatBuoc("Nhập mã cần sửa: ");
        DiaDiem dd = timTheoMa(ma);
        if (dd == null) {
            System.out.println("Không tìm thấy mã " + ma + "!");
            return;
        }
        System.out.println("(Nhấn Enter để giữ nguyên giá trị cũ)");
        dd.capNhat();
        System.out.println("Sửa thành công!");
    }

    public void xoa() {
        String ma = NhapLieu.docChuoiBatBuoc("Nhập mã cần xóa: ");
        DiaDiem dd = timTheoMa(ma);
        if (dd == null) {
            System.out.println("Không tìm thấy mã " + ma + "!");
            return;
        }
        if (!NhapLieu.xacNhan("Bạn chắc chắn muốn xóa " + dd.getMa() + "?")) {
            System.out.println("Đã hủy xóa.");
            return;
        }
        int viTri = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] == dd) {
                viTri = i;
                break;
            }
        }
        for (int i = viTri; i < soLuong - 1; i++) {
            danhSach[i] = danhSach[i + 1];
        }
        danhSach[soLuong - 1] = null;
        soLuong--;
        System.out.println("Xóa thành công!");
    }

    public void timKiem() {
        String tuKhoa = NhapLieu.docChuoiBatBuoc("Nhập từ khóa: ");
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].khop(tuKhoa)) {
                if (dem == 0) {
                    System.out.println();
                    System.out.println(TIEU_DE);
                }
                System.out.println(danhSach[i]);
                dem++;
            }
        }
        if (dem == 0) {
            System.out.println("Không tìm thấy kết quả phù hợp!");
        } else {
            System.out.println("Tìm thấy " + dem + " kết quả.");
        }
    }
}