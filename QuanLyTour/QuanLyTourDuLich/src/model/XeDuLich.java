package model;


public class XeDuLich extends PhuongTien {
    private int soCho;

    public XeDuLich(String maPT, String tenPT, int soCho) {
        super(maPT, tenPT);
        this.soCho = soCho;
    }

    public int getSoCho() { return soCho; }
    public void setSoCho(int soCho) { this.soCho = soCho; }

    @Override
    public String getLoai() { return "Xe du lich"; }

    @Override
    public int getSucChua() { return soCho; }

    @Override
    public String moTaChiTiet() { return "So cho: " + soCho; }

    @Override
    public String toFileString() {
        return "XE|" + maPT + "|" + tenPT + "|" + soCho;
    }
}
