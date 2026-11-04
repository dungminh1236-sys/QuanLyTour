package model;

import util.NhapLieu;

/** Lớp trừu tượng - gốc của cây kế thừa Khách hàng. */
public abstract class KhachHang {
    protected String ma;
    protected String hoTen;
    protected String sdt;

    public KhachHang(String ma, String hoTen, String sdt) {
        this.ma = ma;
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public String getMa() { return ma; }
    public String getHoTen() { return hoTen; }
    public String getSdt() { return sdt; }

    /** Mỗi loại khách hàng tự khai báo loại của mình (đa hình). */
    public abstract String getLoai();

    /** Khách hàng có khớp từ khóa tìm kiếm không (lớp con mở rộng thêm). */
    public boolean khop(String tuKhoa) {
        String k = tuKhoa.toLowerCase();
        return ma.toLowerCase().contains(k)
                || hoTen.toLowerCase().contains(k)
                || getLoai().toLowerCase().contains(k)
                || sdt.contains(k);
    }

    /** Nhập thông tin mới (Enter để giữ nguyên giá trị cũ). Lớp con override để hỏi thêm thuộc tính riêng. */
    public void capNhat() {
        String t = NhapLieu.docChuoi("Họ tên mới [" + hoTen + "]: ");
        if (!t.isEmpty()) {
            hoTen = t;
        }
        while (true) {
            String s = NhapLieu.docChuoi("SĐT mới [" + sdt + "]: ");
            if (s.isEmpty()) {
                break;
            }
            if (NhapLieu.sdtHopLe(s)) {
                sdt = s;
                break;
            }
            System.out.println("SĐT không hợp lệ (9-11 chữ số), nhập lại hoặc Enter để giữ nguyên!");
        }
    }

    /** In thông tin chi tiết (lớp con override để in thêm thuộc tính riêng). */
    public void hienThiChiTiet() {
        System.out.println("Mã KH   : " + ma);
        System.out.println("Họ tên  : " + hoTen);
        System.out.println("Loại    : " + getLoai());
        System.out.println("SĐT     : " + sdt);
    }

    @Override
    public String toString() {
        return ma + " | " + hoTen + " | " + getLoai() + " | " + sdt;
    }
}
