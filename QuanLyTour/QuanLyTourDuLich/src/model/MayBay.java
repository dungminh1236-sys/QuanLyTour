package model;


public class MayBay extends PhuongTien {
    private int soGhe;

    public MayBay(String maPT, String tenPT, int soGhe) {
        super(maPT, tenPT);
        this.soGhe = soGhe;
    }

    public int getSoGhe() { return soGhe; }
    public void setSoGhe(int soGhe) { this.soGhe = soGhe; }

    @Override
    public String getLoai() { return "May bay"; }

    @Override
    public int getSucChua() { return soGhe; }

    @Override
    public String moTaChiTiet() { return "So ghe: " + soGhe; }

    @Override
    public String toFileString() {
        return "MAYBAY|" + maPT + "|" + tenPT + "|" + soGhe;
    }
}
