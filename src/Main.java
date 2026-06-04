import java.util.Scanner;
import menu.LoginMenu;
import menu.SignUpMenu;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== SISTEM RENTAL MOBIL ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            int pilih = sc.nextInt();
           

            if (pilih == 1) {
                LoginMenu loginMenu = new LoginMenu();
                loginMenu.show();
            } 
            else if (pilih == 2) {
                SignUpMenu signUpMenu = new SignUpMenu();
                signUpMenu.show();
            } 
            else if (pilih == 0) {
                System.out.println("Program selesai.");
                break;
            } 
            else {
                System.out.println("Pilihan tidak valid.");
            }
        }

        sc.close();
    }
}