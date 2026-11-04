package model;

import util.NhapLieu;

/** Khách hàng cá nhân - kế thừa KhachHang. */
public class KhachHangCaNhan extends KhachHang {
    private String cccd;

    public KhachHangCaNhan(String ma, String hoTen, String sdt, String cccd) {
        super(ma, hoTen, sdt);
        this.cccd = cccd;
    }

    public String getCccd() { return cccd; }

    /** CCCD hợp lệ: đúng 12 chữ số. */
    public static boolean cccdHopLe(String s) {
        if (s.length() != 12) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String docCccd(String thongBao) {
        while (true) {
            String s = NhapLieu.docChuoi(thongBao);
            if (cccdHopLe(s)) {
                return s;
            }
            System.out.println("CCCD phải gồm đúng 12 chữ số, nhập lại!");
        }
    }

    @Override
    public String getLoai() {
        return "Cá nhân";
    }

    @Override
    public boolean khop(String tuKhoa) {
        return super.khop(tuKhoa) || cccd.contains(tuKhoa);
    }

    @Override
    public void capNhat() {
        super.capNhat();
        while (true) {
            String s = NhapLieu.docChuoi("CCCD mới [" + cccd + "]: ");
            if (s.isEmpty()) {
                break;
            }
            if (cccdHopLe(s)) {
                cccd = s;
                break;
            }
            System.out.println("CCCD phải gồm đúng 12 chữ số, nhập lại hoặc Enter để giữ nguyên!");
        }
    }

    @Override
    public void hienThiChiTiet() {
        super.hienThiChiTiet();
        System.out.println("CCCD    : " + cccd);
    }
}
