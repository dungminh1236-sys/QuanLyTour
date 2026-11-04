package service;

import model.MayBay;
import model.PhuongTien;
import model.XeDuLich;
import util.DanhSach;
import util.FileUtil;
import util.MaTuDong;
import util.NhapLieu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/** Menu 5 - Quan ly Phuong tien. */
public class QuanLyPhuongTien {
    private static final String FILE = "phuongtien.txt";
    private static final int TOI_DA_CHO = 1000;

    private final DanhSach<PhuongTien> ds = new DanhSach<>();

    // Hook kiem tra phuong tien co dang nam trong don dat tour hay khong (QuanLyDatTour gan vao)
    private Predicate<String> kiemTraDangDung = ma -> false;

    public void setKiemTraDangDung(Predicate<String> kiemTra) {
        this.kiemTraDangDung = kiemTra;
    }

    public List<PhuongTien> getDanhSach() {
        return ds.getDanhSach();
    }

    public PhuongTien timTheoMa(String ma) {
        return ds.tim(pt -> pt.getMaPT().equalsIgnoreCase(ma));
    }

    public int demXe() {
        int n = 0;
        for (PhuongTien pt : ds) if (pt instanceof XeDuLich) n++;
        return n;
    }

    public int demMayBay() {
        int n = 0;
        for (PhuongTien pt : ds) if (pt instanceof MayBay) n++;
        return n;
    }

    // ================= MENU =================
    public void menu() {
        int chon;
        do {
            System.out.println("\n========== QUAN LY PHUONG TIEN ==========");
            System.out.println("1. Xem danh sach phuong tien");
            System.out.println("2. Them phuong tien");
            System.out.println("3. Sua phuong tien");
            System.out.println("4. Xoa phuong tien");
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
        System.out.println("Tong so phuong tien: " + ds.size());
    }

    private void hienThiDanhSach(List<PhuongTien> list) {
        if (list.isEmpty()) {
            System.out.println("(Khong co phuong tien nao)");
            return;
        }
        System.out.printf("%-8s %-30s %-12s%n", "Ma", "Ten phuong tien", "Loai");
        for (PhuongTien pt : list) pt.hienThi();   // da hinh
    }

    public void them() {
        System.out.println("Chon loai:");
        System.out.println("1. Xe du lich");
        System.out.println("2. May bay");
        int loai = NhapLieu.docSo("Nhap lua chon: ", 1, 2);

        String ten = NhapLieu.docChuoiBatBuoc("Ten: ");
        PhuongTien pt;
        if (loai == 1) {
            int soCho = NhapLieu.docSo("So cho: ", 1, TOI_DA_CHO);
            pt = new XeDuLich(MaTuDong.taoMa(MaTuDong.PHUONG_TIEN), ten, soCho);
        } else {
            int soGhe = NhapLieu.docSo("So ghe: ", 1, TOI_DA_CHO);
            pt = new MayBay(MaTuDong.taoMa(MaTuDong.PHUONG_TIEN), ten, soGhe);
        }
        ds.them(pt);
        luuFile();
        System.out.println("Them thanh cong! Ma phuong tien: " + pt.getMaPT());
    }

    public void sua() {
        PhuongTien pt = timTheoMa(NhapLieu.docChuoi("Nhap ma phuong tien can sua: "));
        if (pt == null) {
            System.out.println("Khong tim thay phuong tien!");
            return;
        }
        System.out.println("Thong tin hien tai:");
        pt.hienThiChiTiet();
        System.out.println("(De trong de giu nguyen)");

        String ten = NhapLieu.docChuoi("Ten moi [" + pt.getTenPT() + "]: ");
        if (!ten.isEmpty()) pt.setTenPT(ten);

        String s = NhapLieu.docChuoi("So cho/ghe moi [" + pt.getSucChua() + "]: ");
        if (!s.isEmpty()) {
            try {
                int sl = Integer.parseInt(s);
                if (sl < 1 || sl > TOI_DA_CHO) {
                    System.out.println("Gia tri phai tu 1 den " + TOI_DA_CHO + ", giu nguyen gia tri cu.");
                } else if (pt instanceof XeDuLich) {
                    ((XeDuLich) pt).setSoCho(sl);
                } else if (pt instanceof MayBay) {
                    ((MayBay) pt).setSoGhe(sl);
                }
            } catch (NumberFormatException e) {
                System.out.println("Gia tri khong hop le, giu nguyen gia tri cu.");
            }
        }
        luuFile();
        System.out.println("Sua phuong tien thanh cong!");
    }

    public void xoa() {
        PhuongTien pt = timTheoMa(NhapLieu.docChuoi("Nhap ma phuong tien can xoa: "));
        if (pt == null) {
            System.out.println("Khong tim thay phuong tien!");
            return;
        }
        if (kiemTraDangDung.test(pt.getMaPT())) {
            System.out.println("Khong the xoa: phuong tien dang duoc dung trong don dat tour!");
            return;
        }
        if (NhapLieu.xacNhan("Ban co chac muon xoa?")) {
            ds.xoa(pt);
            luuFile();
            System.out.println("Xoa phuong tien thanh cong!");
        } else {
            System.out.println("Da huy xoa.");
        }
    }

    public void timKiem() {
        System.out.println("1. Tim theo ma");
        System.out.println("2. Tim theo ten");
        System.out.println("3. Tim theo loai");
        int chon = NhapLieu.docSo("Nhap lua chon: ", 1, 3);

        List<PhuongTien> kq = new ArrayList<>();
        if (chon == 3) {
            System.out.println("1. Xe du lich   2. May bay");
            int loai = NhapLieu.docSo("Chon loai: ", 1, 2);
            for (PhuongTien pt : ds) {
                if ((loai == 1 && pt instanceof XeDuLich) || (loai == 2 && pt instanceof MayBay)) kq.add(pt);
            }
        } else {
            String k = NhapLieu.docChuoi(chon == 1 ? "Nhap ma can tim: " : "Nhap ten can tim: ").toLowerCase();
            for (PhuongTien pt : ds) {
                String truong = chon == 1 ? pt.getMaPT() : pt.getTenPT();
                if (truong.toLowerCase().contains(k)) kq.add(pt);
            }
        }
        System.out.println("Ket qua:");
        hienThiDanhSach(kq);
    }

    // ================= FILE =================
    public void docTuFile() {
        ds.clear();
        for (String dong : FileUtil.docFile(FILE)) {
            try {
                String[] p = dong.split("\\|");
                PhuongTien pt;
                if (p[0].equals("XE")) pt = new XeDuLich(p[1], p[2], Integer.parseInt(p[3]));
                else pt = new MayBay(p[1], p[2], Integer.parseInt(p[3]));
                ds.them(pt);
                MaTuDong.capNhat(p[1]);   // dong bo bo dem ma tu dong
            } catch (Exception e) {
                System.out.println("Bo qua dong loi trong " + FILE + ": " + dong);
            }
        }
    }

    public void luuFile() {
        List<String> dong = new ArrayList<>();
        for (PhuongTien pt : ds) dong.add(pt.toFileString());
        FileUtil.ghiFile(FILE, dong);
    }
}
