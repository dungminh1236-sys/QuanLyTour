package service;

import model.KhachHang;
import model.KhachHangCaNhan;
import model.KhachHangDoanhNghiep;
import util.NhapLieu;

/** Menu 2. Quản lý Khách hàng. */
public class QuanLyKhachHang {
    private KhachHang[] danhSach = new KhachHang[100];
    private int soLuong = 0;

    private static final String TIEU_DE = "Mã KH | Họ tên | Loại | SĐT";

    public QuanLyKhachHang() {
        // Dữ liệu mẫu
        danhSach[soLuong++] = new KhachHangCaNhan("KH001", "Nguyễn Văn An", "0901234567", "079123456781");
        danhSach[soLuong++] = new KhachHangCaNhan("KH002", "Trần Thị Mai", "0912345678", "079123456782");
        danhSach[soLuong++] = new KhachHangDoanhNghiep("KH003", "Công ty ABC", "0281234567", "0312345678");
    }

    /** Sinh mã tự động: KH001, KH002, ... */
    private String taoMa() {
        int max = 0;
        for (int i = 0; i < soLuong; i++) {
            try {
                int so = Integer.parseInt(danhSach[i].getMa().substring(2));
                if (so > max) {
                    max = so;
                }
            } catch (NumberFormatException e) {
                // bỏ qua mã không đúng dạng KHxxx
            }
        }
        String s = "" + (max + 1);
        while (s.length() < 3) {
            s = "0" + s;
        }
        return "KH" + s;
    }

    public KhachHang timTheoMa(String ma) {
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
            System.out.println("========== QUẢN LÝ KHÁCH HÀNG ==========");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Sửa khách hàng");
            System.out.println("4. Xóa khách hàng");
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
        System.out.println("Tổng số khách hàng: " + soLuong);
    }

    public void them() {
        if (soLuong == danhSach.length) {
            System.out.println("Danh sách đã đầy, không thể thêm!");
            return;
        }
        String maMoi = taoMa();
        System.out.println("Chọn loại:");
        System.out.println("1. Khách cá nhân");
        System.out.println("2. Khách doanh nghiệp");
        int loai = NhapLieu.docSo("Nhập lựa chọn: ", 1, 2);

        // Đa hình: biến kiểu cha, đối tượng kiểu con
        KhachHang kh;
        if (loai == 1) {
            String hoTen = NhapLieu.docChuoiBatBuoc("Họ tên: ");
            String cccd = KhachHangCaNhan.docCccd("CCCD: ");
            String sdt = NhapLieu.docSdt("SĐT: ");
            kh = new KhachHangCaNhan(maMoi, hoTen, sdt, cccd);
        } else {
            String ten = NhapLieu.docChuoiBatBuoc("Tên công ty: ");
            String mst = KhachHangDoanhNghiep.docMst("Mã số thuế: ");
            String sdt = NhapLieu.docSdt("SĐT: ");
            kh = new KhachHangDoanhNghiep(maMoi, ten, sdt, mst);
        }
        danhSach[soLuong++] = kh;
        System.out.println("Thêm thành công! Mã khách hàng: " + maMoi);
    }

    public void sua() {
        String ma = NhapLieu.docChuoiBatBuoc("Nhập mã cần sửa: ");
        KhachHang kh = timTheoMa(ma);
        if (kh == null) {
            System.out.println("Không tìm thấy mã " + ma + "!");
            return;
        }
        System.out.println("Thông tin hiện tại:");
        kh.hienThiChiTiet();
        System.out.println("(Nhấn Enter để giữ nguyên giá trị cũ)");
        kh.capNhat(); // đa hình: mỗi loại hỏi thêm thuộc tính riêng
        System.out.println("Sửa thành công!");
    }

    public void xoa() {
        String ma = NhapLieu.docChuoiBatBuoc("Nhập mã cần xóa: ");
        KhachHang kh = timTheoMa(ma);
        if (kh == null) {
            System.out.println("Không tìm thấy mã " + ma + "!");
            return;
        }
        if (!NhapLieu.xacNhan("Bạn chắc chắn muốn xóa " + kh.getMa() + "?")) {
            System.out.println("Đã hủy xóa.");
            return;
        }
        int viTri = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] == kh) {
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
        String tuKhoa = NhapLieu.docChuoiBatBuoc("Nhập từ khóa (mã, tên, loại, SĐT...): ");
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

    // ---------- Chuẩn bị cho phần ĐẶT TOUR (Người 4) ----------

    /** Số lượng khách hàng hiện có. */
    public int getSoLuong() {
        return soLuong;
    }

    /** Mảng khách hàng hiện có (cho thống kê, chọn khách khi đặt tour). */
    public KhachHang[] getDanhSach() {
        KhachHang[] kq = new KhachHang[soLuong];
        for (int i = 0; i < soLuong; i++) {
            kq[i] = danhSach[i];
        }
        return kq;
    }
}
