package util;

import java.util.List;

/**
 * Interface tìm kiếm chung. Các lớp QuanLyXxx có thể implements TimKiem<Xxx>.
 */
public interface TimKiem<T> {
    /** Tìm chính xác theo mã, không thấy trả về null. */
    T timTheoMa(String ma);

    /** Tìm gần đúng theo tên (không phân biệt hoa/thường). */
    List<T> timTheoTen(String ten);
}