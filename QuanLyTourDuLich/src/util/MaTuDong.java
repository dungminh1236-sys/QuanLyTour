package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Sinh mã tự động (T001, KH001, NV001, DT001...).
 * Dùng static: bộ đếm dùng chung cho toàn chương trình.
 */
public class MaTuDong {
    public static final String TOUR = "T";
    public static final String KHACH_HANG = "KH";
    public static final String NHAN_VIEN = "NV";
    public static final String DIA_DIEM = "DD";
    public static final String PHUONG_TIEN = "PT";
    public static final String DAT_TOUR = "DT";

    // tiền tố -> số thứ tự lớn nhất đã dùng
    private static final Map<String, Integer> boDem = new HashMap<>();

    private MaTuDong() { }

    /** Sinh mã mới, ví dụ taoMa(MaTuDong.TOUR) -> "T001", "T002"... */
    public static String taoMa(String tienTo) {
        int so = boDem.merge(tienTo, 1, Integer::sum);
        return tienTo + String.format("%03d", so);
    }

    /**
     * Gọi mỗi khi đọc 1 đối tượng từ file lên để bộ đếm không sinh trùng mã.
     * Ví dụ capNhat("KH007") -> lần sau taoMa("KH") ra "KH008".
     */
    public static void capNhat(String ma) {
        if (ma == null) return;
        int i = 0;
        while (i < ma.length() && !Character.isDigit(ma.charAt(i))) i++;
        if (i == 0 || i == ma.length()) return;
        try {
            int so = Integer.parseInt(ma.substring(i));
            boDem.merge(ma.substring(0, i), so, Math::max);
        } catch (NumberFormatException e) {
            // mã không đúng định dạng -> bỏ qua
        }
    }

    public static void datLai() {
        boDem.clear();
    }
}