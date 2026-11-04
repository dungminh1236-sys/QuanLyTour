package model;


public abstract class PhuongTien {
    protected String maPT;
    protected String tenPT;

    public PhuongTien(String maPT, String tenPT) {
        this.maPT = maPT;
        this.tenPT = tenPT;
    }

    // ===== Phuong thuc abstract (da hinh) =====
    public abstract String getLoai();          // "Xe du lich" / "May bay"
    public abstract int getSucChua();          // so cho / so ghe
    public abstract String moTaChiTiet();      // mo ta rieng cua tung loai
    public abstract String toFileString();     // dong luu vao file txt

    // ===== Getter / Setter =====
    public String getMaPT() { return maPT; }
    public String getTenPT() { return tenPT; }
    public void setTenPT(String tenPT) { this.tenPT = tenPT; }

    /** Hien thi 1 dong trong danh sach: Ma | Ten | Loai */
    public void hienThi() {
        System.out.printf("%-8s %-30s %-12s%n", maPT, tenPT, getLoai());
    }

    /** Hien thi day du thong tin (dung polymorphism: moTaChiTiet cua lop con). */
    public void hienThiChiTiet() {
        System.out.println(maPT + " | " + tenPT + " | " + getLoai() + " | " + moTaChiTiet());
    }

    @Override
    public String toString() {
        return maPT + " - " + tenPT;
    }
}
