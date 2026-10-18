package service;

import model.HuongDanVien;
import model.NhanVien;
import model.NhanVienDieuHanh;
import util.NhapLieu;

/** Menu 3. Quản lý Nhân viên. */
public class QuanLyNhanVien {
    private NhanVien[] danhSach = new NhanVien[100];
    private int soLuong = 0;

    public QuanLyNhanVien() {
        // Dữ liệu mẫu
        danhSach[soLuong++] = new HuongDanVien("NV001", "Nguyễn Minh A", "0901111111");
        danhSach[soLuong++] = new HuongDanVien("NV002", "Trần Văn B", "0902222222");
        danhSach[soLuong++] = new NhanVienDieuHanh("NV003", "Lê Văn C", "0903333333");
    }

    private static final String TIEU_DE = "Mã NV | Họ tên | Chức vụ | SĐT";

    /** Sinh mã tự động: NV001, NV002, ... */
    private String taoMa() {
        int max = 0;
        for (int i = 0; i < soLuong; i++) {
            try {
                int so = Integer.parseInt(danhSach[i].getMa().substring(2));
                if (so > max) {
                    max = so;
                }
            } catch (NumberFormatException e) {
                // bỏ qua mã không đúng dạng NVxxx
            }
        }
        String s = "" + (max + 1);
        while (s.length() < 3) {
            s = "0" + s;
        }
        return "NV" + s;
    }

    public NhanVien timTheoMa(String ma) {
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
            System.out.println("========== QUẢN LÝ NHÂN VIÊN ==========");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm nhân viên");
            System.out.println("3. Sửa nhân viên");
            System.out.println("4. Xóa nhân viên");
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
        System.out.println("Chọn loại nhân viên:");
        System.out.println("1. Hướng dẫn viên");
        System.out.println("2. Nhân viên điều hành");
        int loai = NhapLieu.docSo("Nhập lựa chọn: ", 1, 2);
        String hoTen = NhapLieu.docChuoiBatBuoc("Họ tên: ");
        String sdt = NhapLieu.docSdt("SĐT: ");

        // Đa hình: biến kiểu cha, đối tượng kiểu con
        NhanVien nv;
        if (loai == 1) {
            nv = new HuongDanVien(maMoi, hoTen, sdt);
        } else {
            nv = new NhanVienDieuHanh(maMoi, hoTen, sdt);
        }
        danhSach[soLuong++] = nv;
        System.out.println("Thêm thành công! Mã: " + maMoi);
    }

    public void sua() {
        String ma = NhapLieu.docChuoiBatBuoc("Nhập mã cần sửa: ");
        NhanVien nv = timTheoMa(ma);
        if (nv == null) {
            System.out.println("Không tìm thấy mã " + ma + "!");
            return;
        }
        System.out.println("(Nhấn Enter để giữ nguyên giá trị cũ)");
        nv.capNhat();
        System.out.println("Sửa thành công!");
    }

    public void xoa() {
        String ma = NhapLieu.docChuoiBatBuoc("Nhập mã cần xóa: ");
        NhanVien nv = timTheoMa(ma);
        if (nv == null) {
            System.out.println("Không tìm thấy mã " + ma + "!");
            return;
        }
        if (!NhapLieu.xacNhan("Bạn chắc chắn muốn xóa " + nv.getMa() + "?")) {
            System.out.println("Đã hủy xóa.");
            return;
        }
        int viTri = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] == nv) {
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

    // ---------- Chuẩn bị cho phần ĐẶT TOUR ----------

    /** Mảng hướng dẫn viên để phần đặt tour chọn gán vào tour. */
    public HuongDanVien[] getDanhSachHuongDanVien() {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] instanceof HuongDanVien) {
                dem++;
            }
        }
        HuongDanVien[] kq = new HuongDanVien[dem];
        int viTri = 0;
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i] instanceof HuongDanVien) {
                kq[viTri++] = (HuongDanVien) danhSach[i];
            }
        }
        return kq;
    }

    /** Tìm hướng dẫn viên theo mã (null nếu không có hoặc không phải HDV). */
    public HuongDanVien timHuongDanVienTheoMa(String ma) {
        NhanVien nv = timTheoMa(ma);
        if (nv instanceof HuongDanVien) {
            return (HuongDanVien) nv;
        }
        return null;
    }

    /** In danh sách hướng dẫn viên (cho người dùng chọn khi đặt tour). */
    public void hienThiDanhSachHuongDanVien() {
        HuongDanVien[] ds = getDanhSachHuongDanVien();
        if (ds.length == 0) {
            System.out.println("Chưa có hướng dẫn viên nào!");
            return;
        }
        System.out.println(TIEU_DE);
        for (int i = 0; i < ds.length; i++) {
            System.out.println(ds[i]);
        }
    }
}