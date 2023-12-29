import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Service extends UserManager implements SignIn,SignUp,ForgotPassword{

        void startProgram(Scanner sc, String file1){
            try {
                while (true) {
                    System.out.println("-----------------MENU-----------------");
                    System.out.println("Enter 1: To Sign in");
                    System.out.println("Enter 2: To Sign up");
                    System.out.println("Enter 3: If you forgot your password");
                    System.out.println("Enter 4: To exit");
                    System.out.println("Nhập lựa chọn của bạn");
                    int optionMenu=sc.nextInt();
                    sc.nextLine();
                    switch (optionMenu) {
                        case 1:
                            signIn(sc, file1);
                            break;
                        case 2:
                            signUp(sc, file1);
                            break;
                        case 3:
                            forgotPassword(sc, file1);
                            break;
                        case 4:
                            return;
                        default:
                            System.out.println("Không hợp lệ!");
                            break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    void loginSuccess(Scanner sc, String file1, User user) {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Enter 1: To change your password");
            System.out.println("Enter 2: To search find books");
            System.out.println("Enter 3: To show books you have red");
            System.out.println("Enter 4: To show books you are borrowing");
            System.out.println("Enter 5: To sign out");
            System.out.println("Enrer 6: To exit");
            System.out.println("Nhập lựa chọn của bạn");
            int optionMenu = sc.nextInt();
            sc.nextLine();
            switch (optionMenu) {
                case 1:
                    changePassword(sc, user, file1);
                    break;
                case 2:
                    findBooks(sc, );
                    return;
                case 3:
                    booksHaveBeenRed(file1);
                    return;
                case 4:
                    booksAreBorrowing(file1);
                    return;
                case 5:
                    signOut(sc, file1);
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
                    break;
            }
        }
    }
    void printUser(User user) {
        System.out.println("Thông tin user sau khi cập nhật!");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(user);
        System.out.println(userJson);
    }

    void printUser(List<User> users) {
        System.out.println("Thông tin user sau khi cập nhật!");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(users);
        System.out.println(userJson);
    }
    @Override
    public void signIn(Scanner sc, String file1){
        try {
            System.out.println("***************ĐĂNG NHẬP***************");
            System.out.println("Nhập username:");
            String username = sc.nextLine();
            System.out.println("Nhập password:");
            String password = sc.nextLine();
            List<User> users = getListObjectFromJsonFile(file1);
            Optional<List<User>> usersOptional = Optional.ofNullable(users);
            if (usersOptional.isPresent()) {
                for (User user : users) {
                    if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                        System.out.println("Xin chao " + user.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                        loginSuccess(sc, file1, user);
                        return;
                    }
                }
                System.out.println("Tài khoản hoặc mật khẩu không chính xác!\nVui lòng nhập lại");
            } else {
                System.out.println("Tài khoản hoặc mật khẩu không chính xác!\nVui lòng nhập lại");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void signUp(Scanner sc, String file1) {
        try {
            System.out.println("***************ĐĂNG KÝ***************");
            User user = new User();
            System.out.println("Nhập username:");
            while (true) {
                String username = sc.nextLine();
                if (checkLegalUsername(username)) {
                    if (!isExistsUsername(file1, username)) {
                        user.setUserName(username);
                        break;
                    } else {
                        System.out.println("Tài khoản đã tồn tại!\nVui lòng nhập lại username khác:");
                    }
                } else {
                    System.out.println("Tài khoản không hợp lệ!\nVui lòng nhập lại username khác:");
                }
            }

            System.out.println("Nhap password:");
            String password = checkPassword(sc);
            user.setPassword(password);
            ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile(file1));
            users.add(user);
            convertObjectToJsonFile("user.json", users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void forgotPassword(Scanner sc, String file1) {
        System.out.println("Vui lòng nhập user name của bạn:");
        String username = sc.nextLine();
        if (isExistsUsername(file1, username)) {
            while (true) {
                System.out.println("Vui lòng nhập mật khẩu mới:");
                String password = checkPassword(sc);
                System.out.println("Vui lòng nhập lại mật khẩu mới:");
                String passwordAgain = checkPassword(sc);
                if (password.equals(passwordAgain)) {
                    System.out.println("Cài đặt mật khẩu thành công!");
                    System.out.println("Vui lòng đăng nhập lại!");
                    signIn(sc, file1);
                    return;
                } else {
                    System.out.println("Mật khẩu không trùng khớp!\nVui lòng nhập lại!");
                }
            }
        } else {
            System.out.println("Tài khoản không tồn tại!");
        }
    }
    @Override
    public void changePassword(Scanner sc, User user, String file1) {
        ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile(file1));
        int indexOfUser = users.indexOf(user);

        while (true) {
            System.out.println("Vui lòng nhập mật khẩu mới:");
            String password = checkPassword(sc);
            System.out.println("Vui lòng nhập lại mật khẩu mới:");
            String passwordAgain = checkPassword(sc);
            if (password.equals(passwordAgain)) {
                user.setPassword(password);
                users.set(indexOfUser, user);
                convertObjectToJsonFile("user.json", users);
                System.out.println("Cài đặt mật khẩu thành công!");
                break;
            } else {
                System.out.println("Mật khẩu không trùng khớp!\nVui lòng nhập lại!");
            }
        }
    }
    @Override
    public void signOut(Scanner sc, String file1) {
            startProgram(sc, file1);
    }
}
