package menu;

import java.util.Scanner;

import menu.member.MemberMenu;
import menu.manager.ManagerMenu;
import menu.pegawai.PegawaiMenu;
import model.*;
import service.AuthService;

public class LoginMenu {

    private Scanner sc = new Scanner(System.in);
    private AuthService authService = new AuthService();

    public void show() {

        while(true){

            System.out.println("\n===== LOGIN =====");

            System.out.print("Email    : ");
            String email = sc.nextLine();

            System.out.print("Password : ");
            String password = sc.nextLine();

            AuthResponse response =
                    authService.login(email, password);

            switch(response.getStatus()){

                case SUCCESS:

                    User user = response.getUser();

                    System.out.println("Login berhasil!");

                    switch(user.getRole()){

                        case MANAGER:
                            new ManagerMenu(user).show();
                            break;

                        case MEMBER:
                            new MemberMenu(user).show();
                            break;

                        case PEGAWAI:
                            new PegawaiMenu(user).show();
                            break;
                    }

                    break;

                case WRONG_PASSWORD:
                    System.out.println("Password salah!");
                    break;

                case ACCOUNT_NOT_FOUND:

                    System.out.println("Akun tidak ditemukan!");

                    System.out.print(
                            "Mau daftar? (y/n): "
                    );

                    String pilih = sc.nextLine();

                    if(pilih.equalsIgnoreCase("y")){

                        new SignUpMenu().show();
                    }

                    break;
            }
        }
    }
}