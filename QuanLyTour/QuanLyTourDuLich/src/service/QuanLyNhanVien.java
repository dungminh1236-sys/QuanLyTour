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

/** Menu 3 - Quan ly Nhan vien. */
public class QuanLyNhanVien {
    private static final String FILE = "nhanvien.txt";
    private static final String TIEU_DE = "Ma NV | Ho ten | Chuc vu | SDT";

    private final DanhSach<NhanVien> ds = new DanhSach<>();

    // Danh sach HDV rieng, luon dong bo voi ds (QuanLyDatTour giu tham chieu toi list nay)
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

    /** Tim huong dan vien theo ma (null neu khong co hoac khong phai HDV). */
    public HuongDanVien timHuongDanVienTheoMa(String ma) {
        NhanVien nv = timTheoMa(ma);
        if (nv instanceof HuongDanVien) return (HuongDanVien) nv;
        return null;
    }

    // ================= MENU =================
    public void menu() {
        int chon;
        do {
            System.out.println("\n========== QUAN LY NHAN VIEN ==========");
            System.out.println("1. Xem danh sach");
            System.out.println("2. Them nhan vien");
            System.out.println("3. Sua nhan vien");
            System.out.println("4. Xoa nhan vien");
            System.out.println("5. Tim kiem");
            System.out.println("0. Quay lai");
            chon = NhapLieu.docSo("Nhap lua chon: ", 0, 5);
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

    // ================= CHUC NANG =================
    public void xemDanhSach() {
        hienThiDanhSach(ds.getDanhSach());
        System.out.println("Tong so nhan vien: " + ds.size());
    }

    private void hienThiDanhSach(List<NhanVien> list) {
        if (list.isEmpty()) {
            System.out.println("(Khong co nhan vien nao)");
            return;
        }
        System.out.println(TIEU_DE);
        for (NhanVien nv : list) System.out.println(nv);
    }

    /** In danh sach huong dan vien (cho nguoi dung chon khi dat tour). */
    public void hienThiDanhSachHuongDanVien() {
        if (dsHuongDanVien.isEmpty()) {
            System.out.println("Chua co huong dan vien nao!");
            return;
        }
        System.out.println(TIEU_DE);
        for (HuongDanVien h : dsHuongDanVien) System.out.println(h);
    }

    public void them() {
        System.out.println("Chon loai nhan vien:");
        System.out.println("1. Huong dan vien");
        System.out.println("2. Nhan vien dieu hanh");
        int loai = NhapLieu.docSo("Nhap lua chon: ", 1, 2);
        String hoTen = NhapLieu.docChuoiBatBuoc("Ho ten: ");
        String sdt = NhapLieu.docSdt("SDT: ");
        String ma = MaTuDong.taoMa(MaTuDong.NHAN_VIEN);

        // Da hinh: bien kieu cha, doi tuong kieu con
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
        System.out.println("Them thanh cong! Ma: " + nv.getMa());
    }

    public void sua() {
        NhanVien nv = timTheoMa(NhapLieu.docChuoi("Nhap ma nhan vien can sua: "));
        if (nv == null) { System.out.println("Khong tim thay nhan vien!"); return; }

        System.out.println("Thong tin hien tai:");
        System.out.println(TIEU_DE);
        System.out.println(nv);
        System.out.println("(De trong de giu nguyen)");

        nv.capNhat();
        luuFile();
        System.out.println("Sua nhan vien thanh cong!");
    }

    public void xoa() {
        NhanVien nv = timTheoMa(NhapLieu.docChuoi("Nhap ma nhan vien can xoa: "));
        if (nv == null) { System.out.println("Khong tim thay nhan vien!"); return; }
        if (NhapLieu.xacNhan("Ban co chac muon xoa?")) {
            ds.xoa(nv);
            if (nv instanceof HuongDanVien) dsHuongDanVien.remove(nv);
            luuFile();
            System.out.println("Xoa nhan vien thanh cong!");
        } else {
            System.out.println("Da huy xoa.");
        }
    }

    public void timKiem() {
        String k = NhapLieu.docChuoi("Nhap tu khoa: ");

        List<NhanVien> kq = new ArrayList<>();
        for (NhanVien nv : ds) if (nv.khop(k)) kq.add(nv);

        System.out.println("Ket qua:");
        hienThiDanhSach(kq);
        if (!kq.isEmpty()) System.out.println("Tim thay " + kq.size() + " ket qua.");
    }

    // ================= FILE =================
    /** Dong trong file: ma|loai|hoTen|sdt (loai = HDV hoac DH) */
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
                System.out.println("Bo qua dong loi trong " + FILE + ": " + dong);
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
