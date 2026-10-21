package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Đọc/ghi file text (UTF-8) trong thư mục data/.
 * Quy ước: mỗi đối tượng 1 dòng, các trường ngăn cách bằng dấu '|'.
 */
public class FileUtil {
    public static final String THU_MUC = "data";
    public static final String SEPARATOR = "|";

    private FileUtil() { }

    private static Path duongDan(String tenFile) {
        return Paths.get(THU_MUC, tenFile);
    }

    /** Đọc tất cả dòng (bỏ dòng trống). File chưa có -> trả về danh sách rỗng. */
    public static List<String> docFile(String tenFile) {
        List<String> kq = new ArrayList<>();
        Path p = duongDan(tenFile);
        if (!Files.exists(p)) return kq;
        try {
            for (String dong : Files.readAllLines(p, StandardCharsets.UTF_8)) {
                if (!dong.trim().isEmpty()) kq.add(dong);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file " + tenFile + ": " + e.getMessage());
        }
        return kq;
    }

    /** Ghi đè toàn bộ file; tự tạo thư mục data/ nếu chưa có. */
    public static void ghiFile(String tenFile, List<String> cacDong) {
        Path p = duongDan(tenFile);
        try {
            Files.createDirectories(p.getParent());
            Files.write(p, cacDong, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Lỗi ghi file " + tenFile + ": " + e.getMessage());
        }
    }

    /**
     * Đọc file vào DanhSach. parser: chuyển 1 dòng -> đối tượng (ném exception nếu dòng sai,
     * dòng đó sẽ bị bỏ qua và báo lỗi).
     */
    public static <T> void docDanhSach(String tenFile, DanhSach<T> ds, Function<String, T> parser) {
        ds.clear();
        int soDong = 0;
        for (String dong : docFile(tenFile)) {
            soDong++;
            try {
                ds.them(parser.apply(dong));
            } catch (Exception e) {
                System.out.println("Bỏ qua dòng " + soDong + " trong " + tenFile + " (sai định dạng).");
            }
        }
    }

    /** Ghi DanhSach ra file. toLine: chuyển đối tượng -> 1 dòng text. */
    public static <T> void ghiDanhSach(String tenFile, DanhSach<T> ds, Function<T, String> toLine) {
        List<String> cacDong = new ArrayList<>();
        for (T t : ds) cacDong.add(toLine.apply(t));
        ghiFile(tenFile, cacDong);
    }
}