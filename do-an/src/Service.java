import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Service extends Manager implements SignIn,SignUp,ForgotPassword,SignOut,AddNewAdmin,AddNewBooks {

    void startProgram(Scanner sc, String file1, String file2, User user, String file3, Admin admin, Book bookinfo) throws Exception {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Nhấn 1: Để đăng nhập");
            System.out.println("Nhấn 2: Để đăng ký");
            System.out.println("Nhấn 3: Nếu bạn quên password");
            System.out.println("Nhấn 4: Để thoát");
            System.out.println("Nhập lựa chọn của bạn");
            int optionMenu = sc.nextInt();
            sc.nextLine();
            switch (optionMenu) {
                case 1:
                    signIn(sc, file1, file2, user, file3, admin, bookinfo);
                    break;
                case 2:
                    signUp(sc, file1, file3);
                    break;
                case 3:
                    forgotPassword(sc, file1, file2, user, file3, admin, bookinfo);
                    break;
                case 4:
                    return;
                default:
                    throw new Exception("Không hợp lệ!\nVui lòng nhập lại!");
            }
        }

    }

     void userloginSuccess(Scanner sc, String file1, User user, String file2, String file3, Admin admin, Book bookinfo) throws Exception {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Nhấn 1: Để thay đổi password");
            System.out.println("Nhấn 2: Để tìm sách");
            System.out.println("Nhấn 3: Để mượn sách");
            System.out.println("Nhấn 4: Để xem sách đã đọc");
            System.out.println("Nhấn 5: Để xem sách đang mượn");
            System.out.println("Nhấn 6: Để đăng xuất");
            System.out.println("Nhấn 7: Để thoát");
            System.out.println("Nhập lựa chọn của bạn");
            int optionMenu = sc.nextInt();
            sc.nextLine();
            switch (optionMenu) {
                case 1:
                    changePassword(sc, user, file1);
                    break;
                case 2:
                    findBooks(sc, file2);
                case 3:
                    borrowBooks(sc, file2, file1);
                case 4:
                    booksHaveBeenRed(file1);
                    return;
                case 5:
                    booksAreBorrowing(sc, file1, file2, bookinfo);
                    return;
                case 6:
                    signOut(sc, file1, file2, user, file3, admin, bookinfo);
                    return;
                case 7:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
                    break;
            }
        }
    }

     void adminloginSuccess(Scanner sc, String file1, User user, String file2, String file3, Admin admin, Book bookinfo) throws Exception {
        while (true) {
            System.out.println("-----------------MENU-----------------");
            System.out.println("Nhấn 1: Để thay đổi password");
            System.out.println("Nhấn 2: Để tìm sách");
            System.out.println("Nhấn 3: Để xem danh sách user và thông tin của họ");
            System.out.println("Nhấn 4: Để xem danh sách admin");
            System.out.println("Nhấn 5: Để thêm admin mới");
            System.out.println("Nhấn 6: Để thêm sách mới");
            System.out.println("Nhấn 7: Để xóa người dùng");
            System.out.println("Nhấn 8: Để xóa sách cũ");
            System.out.println("Nhấn 9: Để đăng xuất");
            System.out.println("Nhấn 10: Để thoát");
            System.out.println("Nhập lựa chọn của bạn");
            int optionMenu = sc.nextInt();
            sc.nextLine();
            switch (optionMenu) {
                case 1:
                    adminChangePassword(sc, admin, file1);
                    break;
                case 2:
                    findBooks(sc, file2);
                case 3:
                    showListUser(file1);
                    break;
                case 4:
                    showListAdmin(file3);
                    break;
                case 5:
                    addNewAdmin(sc, file1, file3);
                    break;
                case 6:
                    addNewBooks(sc, file2);
                    break;
                case 7:
                    removeUser(sc,file1);
                    break;
                case 8:
                    removeBook(sc,file2);
                    break;
                case 9:
                    signOut(sc, file1, file2, user, file3, admin, bookinfo);
                    return;
                case 10:
                    return;
                default:
                    System.out.println("Không hợp lệ!");
                    break;
            }
        }
    }

    @Override
    public void signIn(Scanner sc, String file1, String file2, User user, String file3, Admin admin, Book bookinfo) {
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
            if (usersOptional.isPresent() || adminsOptional.isPresent()) {
                for (User anUser : users) {
                    for (Admin anAdmin : admins) {
                        if (anUser.getUserName().equals(username) && anUser.getPassword().equals(password)) {
                            System.out.println("Xin chào " + anUser.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                            userloginSuccess(sc, file1, user, file2, file3, admin, bookinfo);
                            return;
                        } else if (anAdmin.getUserName().equals(username) && anAdmin.getPassword().equals(password)) {
                            System.out.println("Xin chào " + anAdmin.getUserName() + "!\nBạn có thể thực hiện các hành động sau:");
                            adminloginSuccess(sc, file1, user, file2, file3, admin, bookinfo);
                            return;
                        }
                    }
                }
                System.out.println("Tài khoản hoặc mật khẩu không chính xác!\nVui lòng nhập lại");
                signIn(sc, file1, file2, user, file3, admin, bookinfo);
            } else {
                System.out.println("Tài khoản hoặc mật khẩu không chính xác!\nVui lòng nhập lại");
                signIn(sc, file1, file2, user, file3, admin, bookinfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signUp(Scanner sc, String file1, String file3) {
        try {
            System.out.println("***************ĐĂNG KÝ***************");
            User user = new User();
            System.out.println("Nhập username:");
            while (true) {
                String username = sc.nextLine();
                if (checkLegalUsername(username)) {
                    if (!isExistsUsername(file1, username, file3)) {
                        user.setUserName(username);
                        break;
                    } else {
                        System.out.println("Tài khoản đã tồn tại!\nVui lòng nhập lại username khác:");
                        signUp(sc, file1, file3);
                    }
                } else {
                    System.out.println("Tài khoản không hợp lệ!\nVui lòng nhập lại username khác:");
                    signUp(sc, file1, file3);
                }
            }

            System.out.println("Nhập password:");
            String password = checkPassword(sc);
            user.setPassword(password);
            user.setBooksHaveBeenRedId(null);
            user.setBookAreBorrowingId(null);
            ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile1(file1));
            users.add(user);
            convertObjectToJsonFile1("user.json", users);
            System.out.println("Chúc mừng bạn đã đăng ký thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewAdmin(Scanner sc, String file1, String file3) {
        try {
            System.out.println("***************THÊM ADMIN***************");
            Admin admin = new Admin();
            System.out.println("Nhập username:");
            while (true) {
                String username = sc.nextLine();
                if (checkLegalUsername(username)) {
                    if (!isExistsUsername(file1, username, file3)) {
                        admin.setUserName(username);
                        break;
                    } else {
                        System.out.println("Tài khoản đã tồn tại!\nVui lòng nhập lại username khác:");
                        addNewAdmin(sc, file1, file3);
                    }
                } else {
                    System.out.println("Tài khoản không hợp lệ!\nVui lòng nhập lại username khác:");
                    addNewAdmin(sc, file1, file3);
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
    public void addNewBooks(Scanner sc, String file2) {
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
                    addNewBooks(sc, file2);
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
            System.out.println("Nhập số lượng thể loại của sách");
            int n = sc.nextInt();
            sc.nextLine();
            System.out.println("Nhập thể loại:");
            String[] category = new String[n];
            book.setCategory(category);
            for (int i = 0; i < n; i++) {
                category[i] = sc.nextLine();
            }
            System.out.println("Nhập tên tác giả:");
            String author = sc.nextLine();
            book.setAuthor(author);
            System.out.println("Nhập số trang:");
            int pageNumber = sc.nextInt();
            book.setPage_number(pageNumber);
            System.out.println("Nhập năm suất bản:");
            int releaseYear = sc.nextInt();
            book.setRelease_year(releaseYear);
            String status = "available";
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
    public void forgotPassword(Scanner sc, String file1, String file2, User user, String file3, Admin admin, Book bookinfo) {
        List<User> users = getListObjectFromJsonFile1(file1);
        System.out.println("Vui lòng nhập user name của bạn:");
        String username = sc.nextLine();
        for (User anUser:users){
            if (username.equals(anUser.getUserName())) {
                while (true) {
                    System.out.println("Vui lòng nhập mật khẩu mới:");
                    String password = checkPassword(sc);
                    System.out.println("Vui lòng nhập lại mật khẩu mới:");
                    String passwordAgain = checkPassword(sc);
                    if (password.equals(passwordAgain)) {
                        anUser.setPassword(password);
                        convertObjectToJsonFile1("user.json",users);
                        System.out.println("Cài đặt mật khẩu thành công!");
                        System.out.println("Vui lòng đăng nhập lại!");
                        signIn(sc, file1, file2, user, file3, admin, bookinfo);
                        return;
                    } else {
                        System.out.println("Mật khẩu không trùng khớp!\nVui lòng nhập lại!");
                    }
                }
            } else {
                System.out.println("Tài khoản không tồn tại!");
            }
        }
    }

    @Override
    public void changePassword(Scanner sc, User user, String file1) {
        ArrayList<User> users = new ArrayList<>(getListObjectFromJsonFile1(file1));
        int indexOfUser = users.indexOf(user);
        while (true) {
            System.out.println("Vui lòng nhập mật khẩu mới:");
            String password = checkPassword(sc);
            System.out.println("Vui lòng nhập lại mật khẩu mới:");
            String passwordAgain = checkPassword(sc);
            if (password.equals(passwordAgain)) {
                user.setPassword(password);
                users.set(indexOfUser, user);
                convertObjectToJsonFile1("user.json", users);
                System.out.println("Thay đổi mật khẩu thành công!");
                break;
            } else {
                System.out.println("Mật khẩu không trùng khớp!\nVui lòng nhập lại!");
                changePassword(sc,user,file1);
            }
        }
    }

    @Override
    public void adminChangePassword(Scanner sc, Admin admin, String file3) {
        ArrayList<Admin> admins = new ArrayList<>(getListObjectFromJsonFile3(file3));
        int indexOfAdmin = admins.indexOf(admin);
        while (true) {
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
                adminChangePassword(sc,admin,file3);
            }
        }
    }

    @Override
    public void signOut(Scanner sc, String file1, String file2, User user, String file3, Admin admin, Book bookinfo) throws Exception {
        startProgram(sc, file1, file2, user, file3, admin, bookinfo);
    }

    public void findBooks(Scanner sc, String file2) {
        try {
            List<Book> listBooks = getListObjectFromJsonFile2(file2);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            while (true) {
                System.out.println("Nhập 1: Để tìm sách theo tên");
                System.out.println("Nhập 2: Để tìm sách theo thể loại");
                System.out.println("Nhập 3: Để tìm sách theo id");
                System.out.println("Nhập 4: Để thoát");
                System.out.println("Nhập lựa chọn của bạn");
                int optionMenu = sc.nextInt();
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
                            }else{
                                System.out.println("Sách bạn muốn tìm không tồn tại\nVui lòng nhập tên sách khác");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("Nhập thể loại sách:");
                        String strBookCategory = sc.nextLine();
                        System.out.println("Các sách thuộc thể loại " + strBookCategory + " là:");
                        for (Book book : listBooks) {
                            boolean contains = Arrays.asList(book.getCategory()).contains(strBookCategory);
                            if (contains) {
                                String bookJson = gson.toJson(book);
                                System.out.println(bookJson);
                            } else {
                                System.out.println("Thể loại bạn muốn tìm không tồn tại!\nVui lòng nhập thể loại khác!");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Nhập id sách");
                        String id=sc.nextLine();
                        for (Book book: listBooks){
                            if (book.getId().equals(id)) {
                                System.out.println("Sách bạn cần tìm là:");
                                String bookJson = gson.toJson(book);
                                System.out.println(bookJson);
                            }else{
                                System.out.println("Sách bạn muốn tìm không tồn tại\nVui lòng nhập tên sách khác");
                            }
                        }
                        break;
                    case 4:
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

     void showListUser(String file1) {
        List<User> users = getListObjectFromJsonFile1(file1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userJson = gson.toJson(users);
        System.out.println(userJson);
    }

     void showListAdmin(String file3) {
        List<Admin> admins = getListObjectFromJsonFile3(file3);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String adminJson = gson.toJson(admins);
        System.out.println(adminJson);
    }

     void booksAreBorrowing(Scanner sc, String file1, String file2, Book bookinfo) {
        List<Book> listBooks = getListObjectFromJsonFile2(file2);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<User> user = getListObjectFromJsonFile1(file1);
        for (User anUser : user) {
            if (anUser.getBookAreBorrowingId() != null) {
                System.out.println(anUser.getBookAreBorrowingId());
            }
            System.out.println("Nhập id sách bạn đang mượn");
            String id = sc.nextLine();
            for (Book book : listBooks) {
                boolean contains = Arrays.asList(anUser.getBookAreBorrowingId()).contains(id);
                if (contains) {
                    while (true) {
                        System.out.println("Sách bạn cần tìm là:" + book.getTitle());
                        System.out.println("Nhập 1: Để xem thông tin sách");
                        System.out.println("Nhập 2: Để trả sách");
                        System.out.println("Nhập 3: Để thoát");
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
                                anUser.setBookAreBorrowingId(null);
                                convertObjectToJsonFile1("user.json", user);
                                boolean contain = Arrays.asList(anUser.getBooksHaveBeenRedId()).contains(id);
                                if (!contain) {
                                    anUser.setBooksHaveBeenRedId(new String[]{book.getId()});
                                    convertObjectToJsonFile1("user.json", user);
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
        System.out.println("Không có kết quả phù hợp!\nVui lòng nhập lại id sách!");
    }

    void printBook(Book book) {
        System.out.println("Thông tin sách:");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String bookJson = gson.toJson(book);
        System.out.println(bookJson);
    }

    void booksHaveBeenRed(String file1) {
        List<User> users = getListObjectFromJsonFile1(file1);
        for (User user : users) {
            System.out.println(user.getBooksHaveBeenRedId());
        }
    }

    void borrowBooks(Scanner sc, String file2, String file1) {
        List<User> users = getListObjectFromJsonFile1(file1);
        Optional<List<User>> usersOptional = Optional.ofNullable(users);
        if (usersOptional.isPresent()) {
            for (User anUser : users) {
                List<Book> listBooks = getListObjectFromJsonFile2(file2);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                System.out.println("Nhập id sách bạn muốn mượn:");
                String id = sc.nextLine();
                for (Book book : listBooks) {
                    if (book.getId().equals(id)) {
                        System.out.println("Sách bạn cần tìm là:");
                        String bookJson = gson.toJson(book);
                        System.out.println(bookJson);
                        if (status(file2, id)) {
                            if (isBorrowing(file1)) {
                                boolean contain = Arrays.asList(anUser.getBooksHaveBeenRedId()).contains(id);
                                if (!contain) {
                                    System.out.println("Mượn sách thành công!\nHãy trả lại sách sau 30 ngày để không bị phạt!");
                                    book.setStatus("unavailable");
                                    book.setUserBorrow(anUser.getUserName());
                                    convertObjectToJsonFile2("book.json", listBooks);
                                    anUser.setBookAreBorrowingId(book.getId());
                                    convertObjectToJsonFile1("user.json", users);
                                    break;
                                } else {
                                    System.out.println("Bạn đã đọc sách này rồi!\nVui lòng mượn sách khác!");
                                    borrowBooks(sc,file2,file1);
                                }
                            } else {
                                System.out.println("Bạn đang mượn sách!\nVui trả sách đang mượn trước khi mượn sách khác!");
                                break;
                            }
                        } else {
                            System.out.println("Sách bạn muốn mượn không còn!\nVui lòng mượn sách khác hoặc thử lại sau!");
                            borrowBooks(sc,file2,file1);
                        }
                    }
                }
            }
        }
    }
    void removeUser(Scanner sc,String file1){
        List<User> users = getListObjectFromJsonFile1(file1);
        System.out.println("Nhập username người dùng muốn xóa:");
        String userName=sc.nextLine();
        List<User> newListUser = new ArrayList<>();
        for (User user:users){
            if(!userName.equals(user.getUserName())){
                newListUser.add(user);
                convertObjectToJsonFile1("user.json",newListUser);
                System.out.println("Đã xóa "+userName+" khỏi danh sách người dùng!");
                break;
            }
        }
    }
    void removeBook(Scanner sc,String file2){
        List<Book> listBook = getListObjectFromJsonFile2(file2);
        System.out.println("Nhập id sách muốn xóa:");
        String removeBookId=sc.nextLine();
        List<Book> newListBook = new ArrayList<>();
        for (Book book:listBook){
            if(!removeBookId.equals(book.getId())){
                newListBook.add(book);
                convertObjectToJsonFile2("book.json",newListBook);
                System.out.println("Đã xóa sách có id "+removeBookId+" khỏi thư viện!");
                break;
            }
        }
    }
}
