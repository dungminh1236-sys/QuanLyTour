package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Lop generic quan ly danh sach doi tuong (dung chung cho Tour, KhachHang, NhanVien...).
 * FileUtil can: them(), clear() va duyet duoc bang for-each (Iterable).
 */
public class DanhSach<T> implements Iterable<T> {
    private final List<T> ds = new ArrayList<>();

    public void them(T t) { ds.add(t); }

    /** Xoa doi tuong, tra ve true neu xoa duoc. */
    public boolean xoa(T t) { return ds.remove(t); }

    public void clear() { ds.clear(); }

    public int size() { return ds.size(); }

    public boolean isEmpty() { return ds.isEmpty(); }

    public T get(int viTri) { return ds.get(viTri); }

    /** Tim phan tu dau tien thoa dieu kien, khong co thi tra ve null. */
    public T tim(Predicate<T> dieuKien) {
        for (T t : ds) if (dieuKien.test(t)) return t;
        return null;
    }

    /** Loc tat ca phan tu thoa dieu kien. */
    public List<T> loc(Predicate<T> dieuKien) {
        List<T> kq = new ArrayList<>();
        for (T t : ds) if (dieuKien.test(t)) kq.add(t);
        return kq;
    }

    /** Tra ve List ben trong (de truyen cho cac lop can List). */
    public List<T> getDanhSach() { return ds; }

    @Override
    public Iterator<T> iterator() { return ds.iterator(); }
}
