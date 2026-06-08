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

            String input = sc.nextLine();
            
            if (input.equals("1")) {
                new LoginMenu().show();
            } 
            else if (input.equals("2")) {
                new SignUpMenu().show();
            } 
            else if (input.equals("0")) {
                System.out.println("Terima kasih");
                break;
            } 
            else {
                System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        sc.close();
    }
}