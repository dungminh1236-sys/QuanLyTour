package service;

import model.DiaDiem;
import model.DiaDiemDuLich;
import util.DanhSach;
import util.FileUtil;
import util.MaTuDong;
import util.NhapLieu;

import java.util.ArrayList;
import java.util.List;

/** Menu 4 - Quan ly Dia diem. */
public class QuanLyDiaDiem {
    private static final String FILE = "diadiem.txt";
    private static final String TIEU_DE = "Ma | Ten dia diem | Tinh/Thanh pho | Loai hinh";

    private final DanhSach<DiaDiem> ds = new DanhSach<>();

    public List<DiaDiem> getDanhSach() {
        return ds.getDanhSach();
    }

    public DiaDiem timTheoMa(String ma) {
        return ds.tim(dd -> dd.getMa().equalsIgnoreCase(ma));
    }

    // ================= MENU =================
    public void menu() {
        int chon;
        do {
            System.out.println("\n========== QUAN LY DIA DIEM ==========");
            System.out.println("1. Xem danh sach");
            System.out.println("2. Them dia diem");
            System.out.println("3. Sua dia diem");
            System.out.println("4. Xoa dia diem");
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
        System.out.println("Tong so dia diem: " + ds.size());
    }

    private void hienThiDanhSach(List<DiaDiem> list) {
        if (list.isEmpty()) {
            System.out.println("(Khong co dia diem nao)");
            return;
        }
        System.out.println(TIEU_DE);
        for (DiaDiem dd : list) System.out.println(dd);
    }

    public void them() {
        String ten = NhapLieu.docChuoiBatBuoc("Ten dia diem: ");
        String tp = NhapLieu.docChuoiBatBuoc("Tinh/Thanh pho: ");
        String loai = NhapLieu.docChuoiBatBuoc("Loai hinh (bien, nui, di san...): ");

        // Da hinh: bien kieu cha, doi tuong kieu con
        DiaDiem dd = new DiaDiemDuLich(MaTuDong.taoMa(MaTuDong.DIA_DIEM), ten, tp, loai);
        ds.them(dd);
        luuFile();
        System.out.println("Them thanh cong! Ma: " + dd.getMa());
    }

    public void sua() {
        DiaDiem dd = timTheoMa(NhapLieu.docChuoi("Nhap ma dia diem can sua: "));
        if (dd == null) { System.out.println("Khong tim thay dia diem!"); return; }

        System.out.println("Thong tin hien tai:");
        System.out.println(TIEU_DE);
        System.out.println(dd);
        System.out.println("(De trong de giu nguyen)");

        dd.capNhat();
        luuFile();
        System.out.println("Sua dia diem thanh cong!");
    }

    public void xoa() {
        DiaDiem dd = timTheoMa(NhapLieu.docChuoi("Nhap ma dia diem can xoa: "));
        if (dd == null) { System.out.println("Khong tim thay dia diem!"); return; }
        if (NhapLieu.xacNhan("Ban co chac muon xoa?")) {
            ds.xoa(dd);
            luuFile();
            System.out.println("Xoa dia diem thanh cong!");
        } else {
            System.out.println("Da huy xoa.");
        }
    }

    public void timKiem() {
        String k = NhapLieu.docChuoi("Nhap tu khoa: ");

        List<DiaDiem> kq = new ArrayList<>();
        for (DiaDiem dd : ds) if (dd.khop(k)) kq.add(dd);

        System.out.println("Ket qua:");
        hienThiDanhSach(kq);
        if (!kq.isEmpty()) System.out.println("Tim thay " + kq.size() + " ket qua.");
    }

    // ================= FILE =================
    /** Dong trong file: ma|ten|tinhThanh|loaiHinh */
    public void docTuFile() {
        ds.clear();
        for (String dong : FileUtil.docFile(FILE)) {
            try {
                String[] p = dong.split("\\|");
                ds.them(new DiaDiemDuLich(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim()));
                MaTuDong.capNhat(p[0].trim());
            } catch (Exception e) {
                System.out.println("Bo qua dong loi trong " + FILE + ": " + dong);
            }
        }
    }

    public void luuFile() {
        List<String> dong = new ArrayList<>();
        for (DiaDiem dd : ds) {
            DiaDiemDuLich d = (DiaDiemDuLich) dd;
            dong.add(d.getMa() + "|" + d.getTen() + "|" + d.getTinhThanh() + "|" + d.getLoaiHinh());
        }
        FileUtil.ghiFile(FILE, dong);
    }
}
