import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Service implements SignIn,SignUp,ForgotPassword{

        void startProgram(Scanner sc, String fileName){
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
                            signIn(sc, fileName);
                            break;
                        case 2:
                            signUp(sc, fileName);
                            break;
                        case 3:
                            forgotPassword(sc, fileName);
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
    void loginSuccess(Scanner sc, String fileName, User user) {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Enter 1: To change your password");
            System.out.println("Enter 2: To show books you have red");
            System.out.println("Enter 3: To show books you are borrowing");
            System.out.println("Enter 4: To sign out");
            System.out.println("Enrer 5: To exit");
            System.out.println("Nhập lựa chọn của bạn");
            int optionMenu = sc.nextInt();
            sc.nextLine();
            switch (optionMenu) {
                case 1:
                    changePassword(sc, user, fileName);
                    break;
                case 2:
                    booksHaveBeenRed(fileName);
                    return;
                case 3:
                    booksAreBorrowing(fileName);
                    return;
                case 4:
                    signOut(sc, fileName);
                    return;
                case 5:
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
    public void signIn(Scanner sc, String fileName){
        try {
            System.out.println("***************ĐĂNG NHẬP***************");
            System.out.println("Nhập username:");
            String username = sc.nextLine();
            System.out.println("Nhập password:");
            String password = sc.nextLine();
            List<User> users = getListObjectFromJsonFile(fileName);
            Optional<List<User>> usersOptional = Optional.ofNullable(users);
            if (usersOptional.isPresent()) {
                for (User user : users) {
                    if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                        System.out.println("Xin chao " + user.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                        loginSuccess(sc, fileName, user);
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
    public void signUp(Scanner sc, String fileName) {
        try {
            System.out.println("***************ĐĂNG KÝ***************");
            User user = new User();
            System.out.println("Nhập username:");
            while (true) {
                String username = sc.nextLine();
                if (checkUsername(username)) {
                    if (!isExistsUsername(fileName, username)) {
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
            String password = checkPassword(scanner);
            user.setPassword(password);
            ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile(fileName));
            users.add(user);
            convertObjectToJsonFile("user.json", users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void forgotPassword(Scanner sc, String fileName) {
        System.out.println("Vui lòng nhập user name của bạn:");
        String username = sc.nextLine();
        if (isExistsUsername(fileName, username)) {
            while (true) {
                System.out.println("Vui lòng nhập mật khẩu mới:");
                String password = checkPassword(sc);
                System.out.println("Vui lòng nhập lại mật khẩu mới:");
                String passwordAgain = checkPassword(sc);
                if (password.equals(passwordAgain)) {
                    System.out.println("Cài đặt mật khẩu thành công!");
                    System.out.println("Vui lòng đăng nhập lại!");
                    signIn(sc, fileName);
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
    void changePassword(Scanner sc, User user, String fileName) {
        ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile(fileName));
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
    public void signOut(Scanner sc, String fileName) {
        startProgram(sc, fileName);
    }
}
