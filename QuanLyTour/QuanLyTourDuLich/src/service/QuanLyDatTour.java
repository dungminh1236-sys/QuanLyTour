package service;

import model.DatTour;
import model.HuongDanVien;
import model.KhachHang;
import model.PhuongTien;
import model.Tour;
import util.DanhSach;
import util.FileUtil;
import util.MaTuDong;
import util.NhapLieu;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu 6 - Quan ly Dat tour.
 * Nhan danh sach cua cac nhom khac qua constructor (truyen tham chieu nen luon dong bo du lieu).
 */
public class QuanLyDatTour {
    private static final String FILE = "dattour.txt";
    private static final int TOI_DA_NGUOI = 1000;

    private final DanhSach<DatTour> ds = new DanhSach<>();

    private final List<KhachHang> dsKhachHang;
    private final List<Tour> dsTour;
    private final List<HuongDanVien> dsHuongDanVien;
    private final QuanLyPhuongTien qlPhuongTien;

    public QuanLyDatTour(List<KhachHang> dsKhachHang,
                         List<Tour> dsTour,
                         List<HuongDanVien> dsHuongDanVien,
                         QuanLyPhuongTien qlPhuongTien) {
        this.dsKhachHang = dsKhachHang;
        this.dsTour = dsTour;
        this.dsHuongDanVien = dsHuongDanVien;
        this.qlPhuongTien = qlPhuongTien;
        // Khong cho xoa phuong tien dang duoc dat
        this.qlPhuongTien.setKiemTraDangDung(this::phuongTienDangDuocDung);
    }

    public List<DatTour> getDanhSach() {
        return ds.getDanhSach();
    }

    public boolean phuongTienDangDuocDung(String maPT) {
        for (DatTour dt : ds)
            if (dt.getPhuongTien().getMaPT().equalsIgnoreCase(maPT)) return true;
        return false;
    }

    public DatTour timTheoMa(String ma) {
        return ds.tim(dt -> dt.getMaDT().equalsIgnoreCase(ma));
    }

    // ===== Thong ke (Nguoi 5 co the goi) =====
    public int tongSoLuotDat() { return ds.size(); }

    public int tongSoKhachDaDat() {
        int n = 0;
        for (DatTour dt : ds) n += dt.getSoNguoi();
        return n;
    }

    // ================= MENU =================
    public void menu() {
        int chon;
        do {
            System.out.println("\n========== QUAN LY DAT TOUR ==========");
            System.out.println("1. Xem danh sach dat tour");
            System.out.println("2. Them dat tour");
            System.out.println("3. Sua dat tour");
            System.out.println("4. Xoa dat tour");
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
        System.out.println("Tong so luot dat: " + ds.size());
    }

    private void hienThiDanhSach(List<DatTour> list) {
        if (list.isEmpty()) {
            System.out.println("(Khong co don dat tour nao)");
            return;
        }
        System.out.printf("%-8s %-22s %-24s %-9s %s%n", "Ma DT", "Khach hang", "Tour", "So nguoi", "Tong tien");
        for (DatTour dt : list) dt.hienThi();
    }

    public void them() {
        KhachHang kh = timKhachHang(NhapLieu.docChuoi("Ma khach hang: "));
        if (kh == null) { System.out.println("Khong tim thay khach hang!"); return; }

        Tour tour = timTour(NhapLieu.docChuoi("Ma tour: "));
        if (tour == null) { System.out.println("Khong tim thay tour!"); return; }

        int soNguoi = NhapLieu.docSo("So nguoi: ", 1, TOI_DA_NGUOI);

        HuongDanVien hdv = chonHuongDanVien();
        if (hdv == null) return;

        PhuongTien pt = chonPhuongTien(soNguoi);
        if (pt == null) return;

        DatTour dt = new DatTour(MaTuDong.taoMa(MaTuDong.DAT_TOUR), kh, tour, hdv, pt, soNguoi);
        ds.them(dt);
        luuFile();
        System.out.println("Tong tien: " + Tour.dinhDangTien(dt.getTongTien()) + " VND");
        System.out.println("Dat tour thanh cong! Ma: " + dt.getMaDT());
    }

    public void sua() {
        DatTour dt = timTheoMa(NhapLieu.docChuoi("Nhap ma dat tour can sua: "));
        if (dt == null) { System.out.println("Khong tim thay don dat tour!"); return; }

        System.out.println("Thong tin hien tai:");
        dt.hienThiChiTiet();
        System.out.println("(De trong de giu nguyen)");

        // So nguoi
        int soNguoiMoi = dt.getSoNguoi();
        String s = NhapLieu.docChuoi("So nguoi moi [" + dt.getSoNguoi() + "]: ");
        if (!s.isEmpty()) {
            try {
                int n = Integer.parseInt(s);
                if (n >= 1 && n <= TOI_DA_NGUOI) soNguoiMoi = n;
                else System.out.println("So nguoi phai tu 1 den " + TOI_DA_NGUOI + ", giu nguyen.");
            } catch (NumberFormatException e) {
                System.out.println("Gia tri khong hop le, giu nguyen.");
            }
        }

        // Huong dan vien
        s = NhapLieu.docChuoi("Ma huong dan vien moi [" + dt.getHuongDanVien().getMa() + "]: ");
        if (!s.isEmpty()) {
            HuongDanVien h = timHuongDanVien(s);
            if (h != null) dt.setHuongDanVien(h);
            else System.out.println("Khong tim thay huong dan vien, giu nguyen.");
        }

        // Phuong tien
        PhuongTien ptMoi = dt.getPhuongTien();
        s = NhapLieu.docChuoi("Ma phuong tien moi [" + ptMoi.getMaPT() + "]: ");
        if (!s.isEmpty()) {
            PhuongTien p = qlPhuongTien.timTheoMa(s);
            if (p != null) ptMoi = p;
            else System.out.println("Khong tim thay phuong tien, giu nguyen.");
        }

        // Kiem tra suc chua truoc khi ap dung
        boolean duCho = ptMoi.getSucChua() >= soNguoiMoi;
        if (duCho) {
            dt.setPhuongTien(ptMoi);
            dt.setSoNguoi(soNguoiMoi);   // tu tinh lai tong tien
        } else {
            System.out.println("Phuong tien " + ptMoi.getMaPT() + " chi chua "
                    + ptMoi.getSucChua() + " nguoi, khong du cho " + soNguoiMoi
                    + " nguoi. Khong luu thay doi so nguoi/phuong tien.");
        }
        luuFile();
        if (duCho) {
            System.out.println("Sua dat tour thanh cong! Tong tien moi: "
                    + Tour.dinhDangTien(dt.getTongTien()) + " VND");
        }
    }

    public void xoa() {
        DatTour dt = timTheoMa(NhapLieu.docChuoi("Nhap ma dat tour can xoa: "));
        if (dt == null) { System.out.println("Khong tim thay don dat tour!"); return; }
        if (NhapLieu.xacNhan("Ban co chac muon xoa?")) {
            ds.xoa(dt);
            luuFile();
            System.out.println("Xoa dat tour thanh cong!");
        } else {
            System.out.println("Da huy xoa.");
        }
    }

    public void timKiem() {
        System.out.println("1. Tim theo ma dat tour");
        System.out.println("2. Tim theo ma khach hang");
        System.out.println("3. Tim theo ma tour");
        int chon = NhapLieu.docSo("Nhap lua chon: ", 1, 3);
        String k = NhapLieu.docChuoi("Nhap tu khoa: ").toLowerCase();

        List<DatTour> kq = new ArrayList<>();
        for (DatTour dt : ds) {
            String truong = chon == 1 ? dt.getMaDT()
                          : chon == 2 ? dt.getKhachHang().getMa()
                          : dt.getTour().getMaTour();
            if (truong.toLowerCase().contains(k)) kq.add(dt);
        }
        System.out.println("Ket qua:");
        hienThiDanhSach(kq);
        for (DatTour dt : kq) {
            System.out.println();
            dt.hienThiChiTiet();
        }
    }

    // ================= CHON DOI TUONG =================
    private HuongDanVien chonHuongDanVien() {
        if (dsHuongDanVien.isEmpty()) {
            System.out.println("Chua co huong dan vien nao! Hay them o Menu 3 truoc.");
            return null;
        }
        System.out.println("Chon huong dan vien:");
        for (int i = 0; i < dsHuongDanVien.size(); i++) {
            HuongDanVien h = dsHuongDanVien.get(i);
            System.out.println((i + 1) + ". " + h.getMa() + " - " + h.getHoTen());
        }
        int c = NhapLieu.docSo("Chon: ", 1, dsHuongDanVien.size());
        return dsHuongDanVien.get(c - 1);
    }

    private PhuongTien chonPhuongTien(int soNguoi) {
        List<PhuongTien> phuHop = new ArrayList<>();
        for (PhuongTien p : qlPhuongTien.getDanhSach())
            if (p.getSucChua() >= soNguoi) phuHop.add(p);

        if (phuHop.isEmpty()) {
            System.out.println("Khong co phuong tien nao du suc chua cho " + soNguoi + " nguoi!");
            return null;
        }
        System.out.println("Chon phuong tien:");
        for (int i = 0; i < phuHop.size(); i++) {
            PhuongTien p = phuHop.get(i);
            System.out.println((i + 1) + ". " + p.getMaPT() + " - " + p.getTenPT()
                    + " (" + p.moTaChiTiet() + ")");
        }
        int c = NhapLieu.docSo("Chon: ", 1, phuHop.size());
        return phuHop.get(c - 1);
    }

    private KhachHang timKhachHang(String ma) {
        for (KhachHang k : dsKhachHang) if (k.getMa().equalsIgnoreCase(ma)) return k;
        return null;
    }

    private Tour timTour(String ma) {
        for (Tour t : dsTour) if (t.getMaTour().equalsIgnoreCase(ma)) return t;
        return null;
    }

    private HuongDanVien timHuongDanVien(String ma) {
        for (HuongDanVien h : dsHuongDanVien) if (h.getMa().equalsIgnoreCase(ma)) return h;
        return null;
    }

    // ================= FILE =================
    /** Phai goi SAU KHI da doc tour, khach hang, nhan vien, phuong tien. */
    public void docTuFile() {
        ds.clear();
        for (String dong : FileUtil.docFile(FILE)) {
            try {
                String[] p = dong.split("\\|");
                KhachHang kh = timKhachHang(p[1]);
                Tour tour = timTour(p[2]);
                HuongDanVien hdv = timHuongDanVien(p[3]);
                PhuongTien pt = qlPhuongTien.timTheoMa(p[4]);
                int soNguoi = Integer.parseInt(p[5]);
                if (kh == null || tour == null || hdv == null || pt == null) {
                    System.out.println("Bo qua don dat tour " + p[0] + ": thieu du lieu lien ket.");
                    continue;
                }
                ds.them(new DatTour(p[0], kh, tour, hdv, pt, soNguoi));
                MaTuDong.capNhat(p[0]);
            } catch (Exception e) {
                System.out.println("Bo qua dong loi trong " + FILE + ": " + dong);
            }
        }
    }

    public void luuFile() {
        List<String> dong = new ArrayList<>();
        for (DatTour dt : ds) dong.add(dt.toFileString());
        FileUtil.ghiFile(FILE, dong);
    }
}
