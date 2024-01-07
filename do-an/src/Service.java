import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Service extends Manager implements SignIn,SignUp,ForgotPassword,SignOut,AddNewAdmin,AddNewBooks{

        void startProgram(Scanner sc, String file1,String file2,User user,String file3,Admin admin,Book bookinfo){
            try {
                while (true) {
                    System.out.println("-----------------MENU-----------------");
                    System.out.println("Enter 1: To Sign in");
                    System.out.println("Enter 2: To Sign up");
                    System.out.println("Enter 3: If you forgot your password");
                    System.out.println("Enter 4: To borrow books");
                    System.out.println("Enter 5: To exit");
                    System.out.println("Nhập lựa chọn của bạn");
                    int optionMenu=sc.nextInt();
                    sc.nextLine();
                    switch (optionMenu) {
                        case 1:
                            signIn(sc, file1,file2,user,file3,admin,bookinfo);
                            break;
                        case 2:
                            signUp(sc, file1,file3);
                            break;
                        case 3:
                            forgotPassword(sc, file1,file2,user,file3,admin,bookinfo);
                            break;
                        case 4:
                            borrowBooks(sc,file2,file1);
                        case 5:
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
    public void userloginSuccess(Scanner sc, String file1, User user,String file2,String file3,Admin admin,Book bookinfo) {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Enter 1: To change your password");
            System.out.println("Enter 2: To find books");
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
                case 3:
                    booksHaveBeenRed(file1);
                    return;
                case 4:
                    booksAreBorrowing(sc,file1,file2,bookinfo);
                    return;
                case 5:
                    signOut(sc, file1,file2,user,file3,admin,bookinfo);
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
                    break;
            }
        }
    }
    public void adminloginSuccess(Scanner sc, String file1, User user,String file2,String file3,Admin admin,Book bookinfo) {
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
                    signOut(sc, file1,file2,user,file3,admin,bookinfo);
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
    public void signIn(Scanner sc, String file1,String file2,User user,String file3,Admin admin,Book bookinfo){
        try {
            System.out.println("***************ĐĂNG NHẬP***************");
            System.out.println("Nhập username:");
            String username = sc.nextLine();
            System.out.println("Nhập password:");
            String password = sc.nextLine();
            List<User> users = getListObjectFromJsonFile1(file1);
            Optional<List<User>> usersOptional = Optional.ofNullable(users);
            List<Admin> admins = getListObjectFromJsonFile3(file3);
            Optional<List<Admin>> adminsOptional = Optional.ofNullable(admins);
            if (usersOptional.isPresent()||adminsOptional.isPresent()) {
                for (User anUser : users) {
                    for (Admin anAdmin:admins){
                        if (anUser.getUserName().equals(username) && anUser.getPassword().equals(password)) {
                            System.out.println("Xin chào " + anUser.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                            userloginSuccess(sc, file1, user, file2,file3,admin,bookinfo);
                            return;
                        } else if (anAdmin.getUserName().equals(username)&&anAdmin.getPassword().equals(password)) {
                            System.out.println("Xin chào " + anAdmin.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                            adminloginSuccess(sc, file1, user, file2,file3,admin,bookinfo);
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
            ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile1(file1));
            users.add(user);
            convertObjectToJsonFile1("user.json", users);
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
            ArrayList<Admin> admins = new ArrayList<>(getListObjectFromJsonFile3(file3));
            admins.add(admin);
            convertObjectToJsonFile3("admin.json", admins);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewBooks(Scanner sc,String file2){
        try {
            System.out.println("***************THÊM SÁCH***************");
            Book book = new Book();
            System.out.println("Nhập thông tin sách");
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
            System.out.println("Nhập số thể loại của sách");
            int n=sc.nextInt();
            sc.nextLine();
            System.out.println("Nhập thể loại:");
            String[] category= new String[n];
            book.setCategory(category);
            for (int i=0;i<n;i++){
                category[i]=sc.nextLine();
            }
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
            ArrayList<Book> books = new ArrayList<>(getListObjectFromJsonFile2(file2));
            books.add(book);
            convertObjectToJsonFile2("book.json", books);
            System.out.println("Thêm sách thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void forgotPassword(Scanner sc, String file1,String file2,User user,String file3,Admin admin,Book bookinfo) {
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
                    signIn(sc, file1,file2,user,file3,admin,bookinfo);
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
        ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile1(file1));
        int indexOfUser = users.indexOf(user);

        System.out.println("Vui lòng nhập mật khẩu mới:");
        String password = checkPassword(sc);
        System.out.println("Vui lòng nhập lại mật khẩu mới:");
        String passwordAgain = checkPassword(sc);
        if (password.equals(passwordAgain)) {
            user.setPassword(password);
            users.set(indexOfUser, user);
            System.out.println("Cài đặt mật khẩu thành công!");
            convertObjectToJsonFile1("user.json", users);
        } else {
            System.out.println("Mật khẩu không trùng khớp!\nVui lòng nhập lại!");
        }
    }
    @Override
    public void adminChangePassword(Scanner sc,Admin admin, String file3) {
        ArrayList<Admin> admins = new ArrayList<>(getListObjectFromJsonFile3(file3));
        int indexOfAdmin = admins.indexOf(admin);


        System.out.println("Vui lòng nhập mật khẩu mới:");
        String password = checkPassword(sc);
        System.out.println("Vui lòng nhập lại mật khẩu mới:");
        String passwordAgain = checkPassword(sc);
        if (password.equals(passwordAgain)) {
            admin.setPassword(password);
            admins.set(indexOfAdmin, admin);
            convertObjectToJsonFile3("admin.json", admins);
            System.out.println("Cài đặt mật khẩu thành công!");
        } else {
            System.out.println("Mật khẩu không trùng khớp!\nVui lòng nhập lại!");
        }
    }
    @Override
    public void signOut(Scanner sc, String file1,String file2,User user,String file3,Admin admin,Book bookinfo) {
            startProgram(sc, file1,file2,user,file3,admin,bookinfo);
    }
    public void findBooks(Scanner sc, String file2) {
        try {
            List<Book> listBooks = getListObjectFromJsonFile2(file2);
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
                            }else {
                                System.out.println("Thể loại bạn muốn tìm không tồn tại!\nVui lòng nhập thể loại khác!");
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
        List<User> users = getListObjectFromJsonFile1(file1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(users);
        System.out.println(userJson);
    }
    public void showListAdmin(String file3){
        List<Admin> admins = getListObjectFromJsonFile3(file3);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String adminJson = gson.toJson(admins);
        System.out.println(adminJson);
    }

    public void booksAreBorrowing(Scanner sc,String file1,String file2,Book bookinfo){
        List<Book> listBooks = getListObjectFromJsonFile2(file2);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println("Nhập id sách bạn đang mượn");
        String id=sc.nextLine();
        List<User> user = getListObjectFromJsonFile1(file1);
        for (Book book : listBooks) {
            for (User anUser:user){
                boolean contains = Arrays.asList(anUser.getBooksAreBorrowing()).contains(id);
            if (contains) {
                while (true) {
                    System.out.println("Sách bạn cần tìm là:");
                    String bookJson = gson.toJson(book);
                    System.out.println(bookJson);
                    System.out.println("Enter 1: To show book information");
                    System.out.println("Enter 2: To return the book");
                    System.out.println("Enter 3: To exit");
                    System.out.println("Nhập lựa chọn của bạn");
                    int optionMenu = sc.nextInt();
                    sc.nextLine();
                    switch (optionMenu) {
                        case 1:
                            printBook(bookinfo);
                            break;
                        case 2:
                            System.out.println("Trả sách thành công!");
                            book.setStatus("available");
                            book.setUserBorrow(null);
                            convertObjectToJsonFile2("book.json", listBooks);
                            anUser.setBooksAreBorrowing(null);
                            convertObjectToJsonFile1("user.json", user);
                            boolean contain = Arrays.asList(anUser.getBooksHaveBeenRed()).contains(id);
                            if (!contain){
                                anUser.setBooksHaveBeenRed(new String[]{book.getId()});
                                convertObjectToJsonFile1("user.json",user);
                            }
                            break;
                        case 3:
                            return;
                        default:
                            System.out.println("Không hợp lệ!");
                            break;
                    }
                }
            }
            }
        }
        System.out.println("Không có kết quả phù hợp!\nVui lòng nhập lại tên sách!");
    }
    void printBook(Book book) {
        System.out.println("Thông tin sách là");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String bookJson = gson.toJson(book);
        System.out.println(bookJson);
    }
    void booksHaveBeenRed(String file1){
        List<User> users = getListObjectFromJsonFile1(file1);
        for (User user:users ){
            System.out.println(user.getBooksHaveBeenRed());
        }
    }
    void borrowBooks(Scanner sc,String file2,String file1){
        System.out.println("Vui lòng đăng nhập trước khi mượn sách");
        System.out.println("Nhập username:");
        String username = sc.nextLine();
        System.out.println("Nhập password:");
        String password = sc.nextLine();
        List<User> users = getListObjectFromJsonFile1(file1);
        Optional<List<User>> usersOptional = Optional.ofNullable(users);
        if (usersOptional.isPresent()) {
            for (User anUser : users) {
                if (anUser.getUserName().equals(username) && anUser.getPassword().equals(password)) {
                    while (true){
                        List<Book> listBooks = getListObjectFromJsonFile2(file2);
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        System.out.println("Enter 1: To borrow book");
                        System.out.println("Enter 2: To exit");
                        System.out.println("Nhập lựa chọn của bạn");
                        int option=sc.nextInt();
                        sc.nextLine();
                        switch (option){
                            case 1:
                                System.out.println("Nhập id sách bạn muốn mượn:");
                                String id = sc.nextLine();
                                for (Book book : listBooks) {
                                    if (book.getId().equals(id)) {
                                        System.out.println("Sách bạn cần tìm là:");
                                        String bookJson = gson.toJson(book);
                                        System.out.println(bookJson);
                                        if (status(file2, id)) {
                                            if (!isBorrowing(file1)){
                                                System.out.println("Mượn sách thành công!\nHãy trả lại sách sau 30 ngày để không bị phạt!");
                                                book.setStatus("unavailable");
                                                book.setUserBorrow(username);
                                                convertObjectToJsonFile2("book.json", listBooks);
                                                anUser.setBooksAreBorrowing(new String[]{book.getId()});
                                                convertObjectToJsonFile1("user.json",users);
                                                break;
                                            }else {
                                                System.out.println("Vui trả sách đang mượn trước khi mượn sách khác!");
                                                break;
                                            }
                                        }else {
                                            System.out.println("Sách bạn muốn mượn không còn!\nVui lòng mượn sách khác hoặc quay lại sau!");
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 2:
                                return;
                            default:
                                System.out.println("Không hợp lệ!");
                                break;
                        }
                    }
                }else {
                    System.out.println("Tài khoản hoặc mật khẩu không chính xác!\nVui lòng nhập lại");
                    borrowBooks(sc, file2, file1);
                    return;
                }
            }
        }else {
            System.out.println("Tài khoản hoặc mật khẩu không chính xác!\nVui lòng nhập lại");
            borrowBooks(sc, file2, file1);
        }
    }
}

