package model;

import util.NhapLieu;

/** Khách hàng doanh nghiệp - kế thừa KhachHang (hoTen chính là tên công ty). */
public class KhachHangDoanhNghiep extends KhachHang {
    private String maSoThue;

    public KhachHangDoanhNghiep(String ma, String tenCongTy, String sdt, String maSoThue) {
        super(ma, tenCongTy, sdt);
        this.maSoThue = maSoThue;
    }

    public String getMaSoThue() { return maSoThue; }

    /** Mã số thuế hợp lệ: 10 hoặc 13 chữ số. */
    public static boolean mstHopLe(String s) {
        if (s.length() != 10 && s.length() != 13) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String docMst(String thongBao) {
        while (true) {
            String s = NhapLieu.docChuoi(thongBao);
            if (mstHopLe(s)) {
                return s;
            }
            System.out.println("Mã số thuế gồm 10 hoặc 13 chữ số, nhập lại!");
        }
    }

    @Override
    public String getLoai() {
        return "Doanh nghiệp";
    }

    @Override
    public boolean khop(String tuKhoa) {
        return super.khop(tuKhoa) || maSoThue.contains(tuKhoa);
    }

    @Override
    public void capNhat() {
        super.capNhat();
        while (true) {
            String s = NhapLieu.docChuoi("Mã số thuế mới [" + maSoThue + "]: ");
            if (s.isEmpty()) {
                break;
            }
            if (mstHopLe(s)) {
                maSoThue = s;
                break;
            }
            System.out.println("Mã số thuế gồm 10 hoặc 13 chữ số, nhập lại hoặc Enter để giữ nguyên!");
        }
    }

    @Override
    public void hienThiChiTiet() {
        super.hienThiChiTiet();
        System.out.println("Mã số thuế: " + maSoThue);
    }
}
