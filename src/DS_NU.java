import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DS_NU implements LoaiDanhSach{
    private int n;
    private NU[] dssp;
    Scanner sc = new Scanner(System.in);

    public DS_NU() {
        n = 0;
        dssp = null;
    }

    public NU[] getDssp() {
        return dssp;
    }

    public void setDssp(NU[] dssp) {
        this.dssp = dssp;
    }

    public static void printline() {
        System.out.println();
        for (int i = 0; i < 130; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    // ------------------------------------------------------------------------------------- //
    // dem so luong nuoc uong
    public int demNU() {
        int dem = 0;
        try {
            FileInputStream file = new FileInputStream("./database/DSNU.txt");
            InputStreamReader reader = new InputStreamReader(file, StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(reader);
            String line = null;
            try {

                while ((line = buffer.readLine()) != null) {
                    dem++;
                }
                n = dem;
            } catch (IOException ex) {
                Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dem;
    }

    // ------------------------------------------------------------------------------------- //
    // doc danh sach nuoc uong
    public void docDSNU() {
        try {
            FileInputStream file = new FileInputStream("./database/DSNU.txt");
            InputStreamReader reader = new InputStreamReader(file, StandardCharsets.UTF_8);
            BufferedReader buffer = new BufferedReader(reader);

            String line = null;

            try {

                n = demNU();
                dssp = new NU[n];
                int i = 0;
                while ((line = buffer.readLine()) != null) {
                    dssp[i] = new NU();
                    dssp[i].xuly(line);
                    i++;
                }

            } catch (IOException ex) {
                Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ------------------------------------------------------------------------------------- //
    // cap nhat danh sach nuoc uong
    public void capNhatDSNU() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("./database/DSNU.txt");
            for (int i = 0; i < n; i++) {
                String line = dssp[i].xylyLuu();
                try {
                    byte[] infor = line.getBytes("utf8");
                    try {
                        fos.write(infor);
                    } catch (IOException ex) {
                        Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Cap nhat du lieu thanh cong !!!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(DS_NU.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // ------------------------------------------------------------------------------------- //
    // them sp nuoc uong vao tron danh sach nuoc uong
    public void addNU(NU sp) {
        dssp = Arrays.copyOf(dssp, n + 1);
        for (int i = 0; i < n + 1; i++) {
            if (i == n) {
                dssp[i] = sp;
            }
        }
        n++;
        capNhatDSNU();
    }

    // 5 chuc nang chinh co ban cua quan li danh sach
    // ------------------------------------------------------------------------------------- //
    // them san pham
    public void Them() {
        NU nuocuong = new NU();
        nuocuong.Nhap();
        addNU(nuocuong);
    }

    // ------------------------------------------------------------------------------------- //
    // xoa san pham
    public void Xoa() {
        Matcher c;
        String a;
        do {
            System.out.print("Nhap ma san pham can xoa: ");
            a = sc.nextLine();
            String check = "^D[0-9]{2}$";
            Pattern b = Pattern.compile(check);
            c = b.matcher(a);
        } while (!c.find());

        boolean checking = false;
        for (int i = 0; i < n; i++) {
            String key = dssp[i].getId();
            if (key.contentEquals(a)) {
                checking = true;
                for (int j = i; j < n - 1; j++) {
                    dssp[j] = dssp[j + 1];
                }
                n--;
                dssp = Arrays.copyOf(dssp, n);
            }
        }

        if (checking) {
            capNhatDSNU();
        } else {
            System.out.println("Khong tim thay ma san pham !");
        }
    }

    // ------------------------------------------------------------------------------------- //
    // sua thong tin san pham
    public void Sua() {
        Matcher c;
        String a;
        do {
            System.out.print("Nhap ma san pham can sua: ");
            a = sc.nextLine();
            String check = "^D[0-9]{2}$";
            Pattern b = Pattern.compile(check);
            c = b.matcher(a);
        } while (!c.find());

        boolean checking = false;
        for (int i = 0; i < n; i++) {
            String key = dssp[i].getId();
            if (key.contentEquals(a)) {
                checking = true;
                NU sp = new NU();
                System.out.println("Nhap thong tin san pham");
                sp.Nhap();
                dssp[i] = sp;
            }
        }
        if (checking) {
            capNhatDSNU();
        } else {
            System.out.println("Khong tim thay ma san pham !");
        }
    }

    // ------------------------------------------------------------------------------------- //
    // tim kiem thon tin san pham
    public void TimKiem() {
        Matcher check;
        String temp;
        String selectTemp;
        int select;

        do {
            System.out.println("");
            System.out.println("+---------------------------------------------+");
            System.out.println("|     Tim kiem trong danh sach nuoc uong      |");
            System.out.println("| -------------------=====--------------------|");
            System.out.println("| 1. Tim kiem theo ma nuoc uong               |");
            System.out.println("| 2. Tim kiem theo ten nuoc uong              |");
            System.out.println("| 0. Tro ve                                   |");
            System.out.println("+---------------------------------------------+");
            // Regex
            do {
                System.out.print("Nhap vao lua chon: ");
                selectTemp = new Scanner(System.in).nextLine();
                String c = "^[0-9]{1}";
                Pattern b = Pattern.compile(c);
                check = b.matcher(selectTemp);
            } while (!check.find());
            select = Integer.parseInt(selectTemp);

            switch (select) {
                case 1:
                    System.out.println("\nBan da chon tim kiem theo ma nuoc uong");
                    do {
                        System.out.print("Nhap ma nuoc uong can tim: ");
                        temp = new Scanner(System.in).nextLine();
                        String c = "^D[0-9]{2}$";
                        Pattern b = Pattern.compile(c);
                        check = b.matcher(temp);
                    } while (!check.find());

                    printline();
                    System.out.printf("| %-20s %-25s %-50s %-28s |",
                            "Ma san pham", "Ten san pham", "Chi tiet SP", "Gia");
                    for (int i = 0; i < n; i++) {
                        String key = dssp[i].getId();
                        if (key.contentEquals(temp)) {
                            dssp[i].Xuat();
                            break;
                        }
                    }
                    printline();
                    break;

                case 2:
                    System.out.println("\nBan da chon tim kiem theo ten nuoc uong");
                    do {
                        System.out.print("Nhap ten nuoc uong can tim: ");
                        temp = new Scanner(System.in).nextLine();
                        String c = "[^0-9]";
                        Pattern b = Pattern.compile(c);
                        check = b.matcher(temp);
                    } while (!check.find());

                    printline();
                    System.out.printf("| %-20s %-25s %-50s %-28s |",
                            "Ma san pham", "Ten san pham", "Chi tiet SP", "Gia");
                    for (int i = 0; i < n; i++) {
                        String key = dssp[i].getTenNU().toLowerCase();
                        if (key.contains(temp.toLowerCase())) {
                            dssp[i].Xuat();
                        }
                    }
                    printline();
                    break;
                case 0:
                    System.out.println("\nTro ve");
                    break;

                default:
                    System.out.println("Khong co lua chon nao nhu nay !\nVui long nhap lai lua chon.");
                    break;
            }
        } while (select != 0);
    }

    // ------------------------------------------------------------------------------------- //
    // hien thi thong tin san pham
    public void HienThi() {
        printline();
        System.out.printf("| %-20s %-25s %-50s %-28s |",
                "Ma san pham", "Ten san pham", "Chi tiet SP", "Gia");
        for (int i = 0; i < n; i++) {
            dssp[i].Xuat();
        }
        printline();

    }
}
