import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Service extends UserManager implements SignIn,SignUp,ForgotPassword,SignOut{

        void startProgram(Scanner sc, String file1,String file2,User user){
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
                            signIn(sc, file1,file2,user);
                            break;
                        case 2:
                            signUp(sc, file1);
                            break;
                        case 3:
                            forgotPassword(sc, file1,file2,user);
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
    public void loginSuccess(Scanner sc, String file1, User user,String file2) {
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
                    findBooks(sc,file2);
//                case 3:
//                    booksHaveBeenRed(file1);
//                    return;
//                case 4:
//                    booksAreBorrowing(file1);
//                    return;
                case 5:
                    signOut(sc, file1,file2,user);
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
    public void signIn(Scanner sc, String file1,String file2,User user){
        try {
            System.out.println("***************ĐĂNG NHẬP***************");
            System.out.println("Nhập username:");
            String username = sc.nextLine();
            System.out.println("Nhập password:");
            String password = sc.nextLine();
            List<User> users = getListObjectFromJsonFile(file1);
            Optional<List<User>> usersOptional = Optional.ofNullable(users);
            if (usersOptional.isPresent()) {
                for (User anuser : users) {
                    if (anuser.getUserName().equals(username) && anuser.getPassword().equals(password)) {
                        System.out.println("Xin chào " + anuser.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                        loginSuccess(sc, file1, user, file2);
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

            System.out.println("Nhập password:");
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
    public void forgotPassword(Scanner sc, String file1,String file2,User user) {
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
                    signIn(sc, file1,file2,user);
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
    public void signOut(Scanner sc, String file1,String file2,User user) {
            startProgram(sc, file1,file2,user);
    }
    public void findBooks(Scanner sc, String file2) {
        try {
            List<Book> lstBooks = getListObjectFromJsonFile1(file2);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            while (true) {
                System.out.println("Enter 1: To find books according name");
                System.out.println("Enter 2: To find books according category");
                System.out.println("Enter 3: To exit");
                System.out.println("Nhập lựa chọn của bạn");
                int optionMenu=sc.nextInt();
                sc.nextLine();
                switch (optionMenu) {
                    case 1:
                        System.out.println("Nhập tên sách:");
                        String strBookName = sc.nextLine();
                        for (Book book : lstBooks) {
                            if (book.getTitle().equals(strBookName)) {
                                System.out.println("Sách bạn cần tìm là:");
                                String bookJson = gson.toJson(book);
                                System.out.println(bookJson);
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Nhập thể loại sách:");
                        String strBookCategory = sc.nextLine();
                        System.out.println("Các sách thuộc thể loại "+strBookCategory+" là:");
                        for (Book book : lstBooks) {
                            boolean contains = Arrays.asList(book.getCategory()).contains(strBookCategory);
                            if (contains) {
                                String bookJson = gson.toJson(book);
                                System.out.println(bookJson);
                            }
                        }
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("Không hợp lệ!");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void booksHaveBeenRed(String file1){
//
//    }

}

