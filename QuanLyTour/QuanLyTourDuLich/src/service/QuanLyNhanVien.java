package service;

import model.HuongDanVien;
import model.NhanVien;
import model.NhanVienDieuHanh;
import util.DanhSach;
import util.FileUtil;
import util.MaTuDong;
import util.NhapLieu;

import java.util.ArrayList;
import java.util.List;

/** Menu 3 - Quản lý Nhân viên. */
public class QuanLyNhanVien {
    private static final String FILE = "nhanvien.txt";
    private static final String TIEU_DE = "Mã NV | Họ tên | Chức vụ | SĐT";

    private final DanhSach<NhanVien> ds = new DanhSach<>();

    // Danh sách HDV riêng, luôn đồng bộ với ds (QuanLyDatTour giữ tham chiếu tới list này)
    private final List<HuongDanVien> dsHuongDanVien = new ArrayList<>();

    public List<NhanVien> getDanhSach() {
        return ds.getDanhSach();
    }

    public List<HuongDanVien> getDanhSachHuongDanVien() {
        return dsHuongDanVien;
    }

    public NhanVien timTheoMa(String ma) {
        return ds.tim(nv -> nv.getMa().equalsIgnoreCase(ma));
    }

    /** Tìm hướng dẫn viên theo mã (null nếu không có hoặc không phải HDV). */
    public HuongDanVien timHuongDanVienTheoMa(String ma) {
        NhanVien nv = timTheoMa(ma);
        if (nv instanceof HuongDanVien) return (HuongDanVien) nv;
        return null;
    }

    // ================= MENU =================
    public void menu() {
        int chon;
        do {
            System.out.println("\n========== QUẢN LÝ NHÂN VIÊN ==========");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm nhân viên");
            System.out.println("3. Sửa nhân viên");
            System.out.println("4. Xóa nhân viên");
            System.out.println("5. Tìm kiếm");
            System.out.println("0. Quay lại");
            chon = NhapLieu.docSo("Nhập lựa chọn: ", 0, 5);
            switch (chon) {
                case 1: xemDanhSach(); break;
                case 2: them(); break;
                case 3: sua(); break;
                case 4: xoa(); break;
                case 5: timKiem(); break;
                default: break;
            }
        } while (chon != 0);
    }

    // ================= CHỨC NĂNG =================
    public void xemDanhSach() {
        hienThiDanhSach(ds.getDanhSach());
        System.out.println("Tổng số nhân viên: " + ds.size());
    }

    private void hienThiDanhSach(List<NhanVien> list) {
        if (list.isEmpty()) {
            System.out.println("(Không có nhân viên nào)");
            return;
        }
        System.out.println(TIEU_DE);
        for (NhanVien nv : list) System.out.println(nv);
    }

    /** In danh sách hướng dẫn viên (cho người dùng chọn khi đặt tour). */
    public void hienThiDanhSachHuongDanVien() {
        if (dsHuongDanVien.isEmpty()) {
            System.out.println("Chưa có hướng dẫn viên nào!");
            return;
        }
        System.out.println(TIEU_DE);
        for (HuongDanVien h : dsHuongDanVien) System.out.println(h);
    }

    public void them() {
        System.out.println("Chọn loại nhân viên:");
        System.out.println("1. Hướng dẫn viên");
        System.out.println("2. Nhân viên điều hành");
        int loai = NhapLieu.docSo("Nhập lựa chọn: ", 1, 2);
        String hoTen = NhapLieu.docChuoiBatBuoc("Họ tên: ");
        String sdt = NhapLieu.docSdt("SĐT: ");
        String ma = MaTuDong.taoMa(MaTuDong.NHAN_VIEN);

        // Đa hình: biến kiểu cha, đối tượng kiểu con
        NhanVien nv;
        if (loai == 1) {
            HuongDanVien h = new HuongDanVien(ma, hoTen, sdt);
            dsHuongDanVien.add(h);
            nv = h;
        } else {
            nv = new NhanVienDieuHanh(ma, hoTen, sdt);
        }
        ds.them(nv);
        luuFile();
        System.out.println("Thêm thành công! Mã: " + nv.getMa());
    }

    public void sua() {
        NhanVien nv = timTheoMa(NhapLieu.docChuoi("Nhập mã nhân viên cần sửa: "));
        if (nv == null) { System.out.println("Không tìm thấy nhân viên!"); return; }

        System.out.println("Thông tin hiện tại:");
        System.out.println(TIEU_DE);
        System.out.println(nv);
        System.out.println("(Để trống để giữ nguyên)");

        nv.capNhat();
        luuFile();
        System.out.println("Sửa nhân viên thành công!");
    }

    public void xoa() {
        NhanVien nv = timTheoMa(NhapLieu.docChuoi("Nhập mã nhân viên cần xóa: "));
        if (nv == null) { System.out.println("Không tìm thấy nhân viên!"); return; }
        if (NhapLieu.xacNhan("Bạn có chắc muốn xóa?")) {
            ds.xoa(nv);
            if (nv instanceof HuongDanVien) dsHuongDanVien.remove(nv);
            luuFile();
            System.out.println("Xóa nhân viên thành công!");
        } else {
            System.out.println("Đã hủy xóa.");
        }
    }

    public void timKiem() {
        String k = NhapLieu.docChuoi("Nhập từ khóa: ");

        List<NhanVien> kq = new ArrayList<>();
        for (NhanVien nv : ds) if (nv.khop(k)) kq.add(nv);

        System.out.println("Kết quả:");
        hienThiDanhSach(kq);
        if (!kq.isEmpty()) System.out.println("Tìm thấy " + kq.size() + " kết quả.");
    }

    // ================= FILE =================
    /** Dòng trong file: ma|loai|hoTen|sdt (loai = HDV hoặc DH) */
    public void docTuFile() {
        ds.clear();
        dsHuongDanVien.clear();
        for (String dong : FileUtil.docFile(FILE)) {
            try {
                String[] p = dong.split("\\|");
                String ma = p[0].trim();
                String loai = p[1].trim();
                String hoTen = p[2].trim();
                String sdt = p[3].trim();

                NhanVien nv;
                if (loai.equalsIgnoreCase("HDV")) {
                    HuongDanVien h = new HuongDanVien(ma, hoTen, sdt);
                    dsHuongDanVien.add(h);
                    nv = h;
                } else {
                    nv = new NhanVienDieuHanh(ma, hoTen, sdt);
                }
                ds.them(nv);
                MaTuDong.capNhat(ma);
            } catch (Exception e) {
                System.out.println("Bỏ qua dòng lỗi trong " + FILE + ": " + dong);
            }
        }
    }

    public void luuFile() {
        List<String> dong = new ArrayList<>();
        for (NhanVien nv : ds) {
            String loai = (nv instanceof HuongDanVien) ? "HDV" : "DH";
            dong.add(nv.getMa() + "|" + loai + "|" + nv.getHoTen() + "|" + nv.getSdt());
        }
        FileUtil.ghiFile(FILE, dong);
    }
}
