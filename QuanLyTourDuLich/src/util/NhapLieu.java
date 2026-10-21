package util;

import java.util.Scanner;

/** Tiện ích nhập liệu từ bàn phím (dùng chung cho các menu). */
public class NhapLieu {
    private static Scanner sc = new Scanner(System.in, "UTF-8");

    public static String docChuoi(String thongBao) {
        System.out.print(thongBao);
        return sc.nextLine().trim();
    }

    /** Nhập chuỗi không được rỗng. */
    public static String docChuoiBatBuoc(String thongBao) {
        while (true) {
            String s = docChuoi(thongBao);
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("Không được để trống, vui lòng nhập lại!");
        }
    }

    /** Nhập số nguyên trong khoảng [min, max]. */
    public static int docSo(String thongBao, int min, int max) {
        while (true) {
            String s = docChuoi(thongBao);
            try {
                int n = Integer.parseInt(s);
                if (n >= min && n <= max) {
                    return n;
                }
            } catch (NumberFormatException e) {
                // nhập không phải số, bỏ qua và nhập lại
            }
            System.out.println("Giá trị không hợp lệ (" + min + " - " + max + "), nhập lại!");
        }
    }

    /** Số điện thoại hợp lệ: 9-11 chữ số. */
    public static boolean sdtHopLe(String s) {
        if (s.length() < 9 || s.length() > 11) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String docSdt(String thongBao) {
        while (true) {
            String s = docChuoi(thongBao);
            if (sdtHopLe(s)) {
                return s;
            }
            System.out.println("SĐT không hợp lệ (9-11 chữ số), nhập lại!");
        }
    }

    public static boolean xacNhan(String thongBao) {
        String s = docChuoi(thongBao + " (y/n): ");
        return s.equalsIgnoreCase("y");
    }
}