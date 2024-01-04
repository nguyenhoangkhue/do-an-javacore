import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Service extends Manager implements SignIn,SignUp,ForgotPassword,SignOut,AddNewAdmin,AddNewBooks{

        void startProgram(Scanner sc, String file1,String file2,User user,String file3,Admin admin){
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
                            signIn(sc, file1,file2,user,file3,admin);
                            break;
                        case 2:
                            signUp(sc, file1,file3);
                            break;
                        case 3:
                            forgotPassword(sc, file1,file2,user,file3,admin);
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
    public void userloginSuccess(Scanner sc, String file1, User user,String file2,String file3,Admin admin) {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Enter 1: To change your password");
            System.out.println("Enter 2: To find books");
            System.out.println("Enter 3: To borrow books");
            System.out.println("Enter 4: To show books you have red");
            System.out.println("Enter 5: To show books you are borrowing");
            System.out.println("Enter 6: To sign out");
            System.out.println("Enrer 7: To exit");
            System.out.println("Nhập lựa chọn của bạn");
            int optionMenu = sc.nextInt();
            sc.nextLine();
            switch (optionMenu) {
                case 1:
                    changePassword(sc, user, file1);
                    break;
                case 2:
                    findBooks(sc,file2);
                case 3:
                    borrowBooks(sc,file2);
                    break;
//                case 4:
//                    booksHaveBeenRed(file1);
//                    return;
//                case 5:
//                    booksAreBorrowing(file1);
//                    return;
                case 6:
                    signOut(sc, file1,file2,user,file3,admin);
                    return;
                case 7:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
                    break;
            }
        }
    }
    public void adminloginSuccess(Scanner sc, String file1, User user,String file2,String file3,Admin admin) {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Enter 1: To change your password");
            System.out.println("Enter 2: To find books");
            System.out.println("Enter 3: To show list user");
            System.out.println("Enter 4: To show list admin");
            System.out.println("Enter 5: To add new admin");
            System.out.println("Enter 6: To add new books");
            System.out.println("Enter 7: To sign out");
            System.out.println("Enrer 8: To exit");
            System.out.println("Nhập lựa chọn của bạn");
            int optionMenu = sc.nextInt();
            sc.nextLine();
            switch (optionMenu) {
                case 1:
                    adminChangePassword(sc, admin, file1);
                    break;
                case 2:
                    findBooks(sc,file2);
                case 3:
                    showListUser(file1);
                    break;
                case 4:
                    showListAdmin(file3);
                    break;
                case 5:
                    addNewAdmin(sc,file1,file3);
                    break;
                case 6:
                    addNewBooks(sc,file2);
                    break;
                case 7:
                    signOut(sc, file1,file2,user,file3,admin);
                    return;
                case 8:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
                    break;
            }
        }
    }
    @Override
    public void signIn(Scanner sc, String file1,String file2,User user,String file3,Admin admin){
        try {
            System.out.println("***************ĐĂNG NHẬP***************");
            System.out.println("Nhập username:");
            String username = sc.nextLine();
            System.out.println("Nhập password:");
            String password = sc.nextLine();
            List<User> users = getListObjectFromJsonFile(file1);
            Optional<List<User>> usersOptional = Optional.ofNullable(users);
            List<Admin> admins = getListObjectFromJsonFile2(file3);
            Optional<List<Admin>> adminsOptional = Optional.ofNullable(admins);
            if (usersOptional.isPresent()||adminsOptional.isPresent()) {
                for (User anuser : users) {
                    for (Admin anAdmin:admins){
                        if (anuser.getUserName().equals(username) && anuser.getPassword().equals(password)) {
                            System.out.println("Xin chào " + anuser.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                            userloginSuccess(sc, file1, user, file2,file3,admin);
                            return;
                        } else if (anAdmin.getUserName().equals(username)&&anAdmin.getPassword().equals(password)) {
                            System.out.println("Xin chào " + anAdmin.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                            adminloginSuccess(sc, file1, user, file2,file3,admin);
                            return;
                        }
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
    public void signUp(Scanner sc, String file1,String file3) {
        try {
            System.out.println("***************ĐĂNG KÝ***************");
            User user = new User();
            System.out.println("Nhập username:");
            while (true) {
                String username = sc.nextLine();
                if (checkLegalUsername(username)) {
                    if (!isExistsUsername(file1, username,file3)) {
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
    public void addNewAdmin(Scanner sc, String file1,String file3) {
        try {
            System.out.println("***************THÊM ADMIN***************");
            Admin admin = new Admin();
            System.out.println("Nhập username:");
            while (true) {
                String username = sc.nextLine();
                if (checkLegalUsername(username)) {
                    if (!isExistsUsername(file1, username,file3)) {
                        admin.setUserName(username);
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
            admin.setPassword(password);
            ArrayList<Admin> admins = new ArrayList<>(getListObjectFromJsonFile2(file3));
            admins.add(admin);
            convertObjectToJsonFile2("admin.json", admins);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewBooks(Scanner sc,String file2){
        try {
            System.out.println("***************THÊM SÁCH***************");
            Book book = new Book();
            System.out.println("Nhập tên sách:");
            while (true) {
                String title = sc.nextLine();
                if (!isExistsTitle(file2, title)) {
                    book.setTitle(title);
                    break;
                } else {
                    System.out.println("Sách đã tồn tại!\nVui lòng nhập tên sách khác:");
                }
            }
            while (true) {
                UUID uuid = UUID.randomUUID();
                if (!isExistsId(file2, book.getId())) {
                    book.setId(String.valueOf(uuid));
                    break;
                } else {
                    UUID anotherUuid = UUID.randomUUID();
                    if (!isExistsId(file2, book.getId())) {
                        book.setId(String.valueOf(anotherUuid));
                        break;
                    }
                }
            }
            System.out.println("Nhập thông tin sách");
            System.out.println("Nhập thể loại:");
            String[] category= new String[]{sc.nextLine()};
            book.setCategory(category);
            System.out.println("Nhập tên tác giả:");
            String author=sc.nextLine();
            book.setAuthor(author);
            System.out.println("Nhập số trang:");
            int pageNumber= sc.nextInt();
            book.setPage_number(pageNumber);
            System.out.println("Nhập năm suất bản:");
            int releaseYear=sc.nextInt();
            book.setRelease_year(releaseYear);
            String status="available";
            book.setStatus(status);
            ArrayList<Book> books = new ArrayList<>(getListObjectFromJsonFile1(file2));
            books.add(book);
            convertObjectToJsonFile1("book.json", books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void forgotPassword(Scanner sc, String file1,String file2,User user,String file3,Admin admin) {
        System.out.println("Vui lòng nhập user name của bạn:");
        String username = sc.nextLine();
        if (isExistsUsername(file1, username,file3)) {
            while (true) {
                System.out.println("Vui lòng nhập mật khẩu mới:");
                String password = checkPassword(sc);
                System.out.println("Vui lòng nhập lại mật khẩu mới:");
                String passwordAgain = checkPassword(sc);
                if (password.equals(passwordAgain)) {
                    System.out.println("Cài đặt mật khẩu thành công!");
                    System.out.println("Vui lòng đăng nhập lại!");
                    signIn(sc, file1,file2,user,file3,admin);
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
    public void adminChangePassword(Scanner sc,Admin admin, String file3) {
        ArrayList<Admin> admins = new ArrayList<>(getListObjectFromJsonFile2(file3));
        int indexOfAdmin = admins.indexOf(admin);

        while (true) {
            System.out.println("Vui lòng nhập mật khẩu mới:");
            String password = checkPassword(sc);
            System.out.println("Vui lòng nhập lại mật khẩu mới:");
            String passwordAgain = checkPassword(sc);
            if (password.equals(passwordAgain)) {
                admin.setPassword(password);
                admins.set(indexOfAdmin, admin);
                convertObjectToJsonFile2("admin.json", admins);
                System.out.println("Cài đặt mật khẩu thành công!");
                break;
            } else {
                System.out.println("Mật khẩu không trùng khớp!\nVui lòng nhập lại!");
            }
        }
    }
    @Override
    public void signOut(Scanner sc, String file1,String file2,User user,String file3,Admin admin) {
            startProgram(sc, file1,file2,user,file3,admin);
    }
    public void findBooks(Scanner sc, String file2) {
        try {
            List<Book> listBooks = getListObjectFromJsonFile1(file2);
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
                        for (Book book : listBooks) {
                            if (book.getTitle().equals(strBookName)) {
                                System.out.println("Sách bạn cần tìm là:");
                                String bookJson = gson.toJson(book);
                                System.out.println(bookJson);
                                while (true){
                                    System.out.println("Enter 1: To borrow book");
                                    System.out.println("Enter 2: To exit");
                                    System.out.println("Nhập lựa chọn của bạn");
                                    int option=sc.nextInt();
                                    sc.nextLine();
                                    switch (option){
                                        case 1:
                                            if (status(file2, strBookName)) {
                                                System.out.println("Mượn sách thành công!\nHãy trả lại sách sau 30 ngày để không bị phạt!");
                                                book.setStatus("unavailable");
                                                book.setUserBorrow(username);
                                                convertObjectToJsonFile1("book.json", listBooks);
                                                break;
                                            }else {
                                                System.out.println("Sách bạn muốn mượn không còn!\nVui lòng mượn sách khác hoặc quay lại sau!");
                                                break;
                                            }
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Nhập thể loại sách:");
                        String strBookCategory = sc.nextLine();
                        System.out.println("Các sách thuộc thể loại "+strBookCategory+" là:");
                        for (Book book : listBooks) {
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

    public void showListUser(String file1){
        List<User> users = getListObjectFromJsonFile(file1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(users);
        System.out.println(userJson);
    }
    public void showListAdmin(String file3){
        List<Admin> admins = getListObjectFromJsonFile2(file3);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String adminJson = gson.toJson(admins);
        System.out.println(adminJson);
    }

    public void borrowBooks(Scanner sc,String file2) {
        System.out.println("Nhập tên sách muốn mượn:");
        String strBookName = sc.nextLine();
        if (status(file2, strBookName)) {
            System.out.println("Mượn sách thành công!\nHãy trả lại sách sau 30 ngày để không bị phạt!");
        } else {
            System.out.println("Sách bạn muốn mượn không còn!\nVui lòng mượn sách khác hoặc quay lại sau!");
        }
    }

    public void booksAreBorrowing(Scanner sc,String file2,Book bookinfor){
        List<Book> listBooks = getListObjectFromJsonFile1(file2);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Nhập tên sách đang mượn");
        String searchBorrowing=sc.nextLine();
        for (Book book : listBooks) {
            if (book.getTitle().equals(searchBorrowing)) {
                while(true){
                    System.out.println("Sách bạn cần tìm là:");
                    String bookJson = gson.toJson(book);
                    System.out.println(bookJson);
                    System.out.println("Enter 1: To show book information");
                    System.out.println("Enter 2: To return the book");
                    System.out.println("Enter 3: To exit");
                    System.out.println("Nhập lựa chọn của bạn");
                    int optionMenu=sc.nextInt();
                    sc.nextLine();
                    switch (optionMenu){
                        case 1:
                            printBook(bookinfor);
                            break;
                        case 2:

                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Không hợp lệ!");
                            break;
                    }
                }
            }
        }
    }
    void printBook(Book book) {
        System.out.println("Thông tin sách là");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String bookJson = gson.toJson(book);
        System.out.println(bookJson);
    }
}

