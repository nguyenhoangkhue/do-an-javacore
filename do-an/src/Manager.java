import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public abstract class Manager {
    abstract void changePassword(Scanner sc, User user, String file1);
    abstract void adminChangePassword(Scanner sc, Admin admin, String file3);
    abstract void signOut(Scanner sc, String file1,String file2,User user,String file3,Admin admin);

    public void convertObjectToJsonFile(String file1, Object obj) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(file1));
            gson.toJson(obj, writer);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<User> getListObjectFromJsonFile(String file1) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(file1));
            User[] usersFile = gson.fromJson(reader, User[].class);
            if (usersFile == null) {
                return Collections.emptyList();
            } else {
                List<User> users = Arrays.asList(usersFile);
                reader.close();
                return users;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public void convertObjectToJsonFile1(String file2, Object obj) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(file2));
            gson.toJson(obj, writer);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Book> getListObjectFromJsonFile1(String file2) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(file2));
            Book[] booksFile = gson.fromJson(reader, Book[].class);
            if (booksFile == null) {
                return Collections.emptyList();
            } else {
                List<Book> books = Arrays.asList(booksFile);
                reader.close();
                return books;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public void convertObjectToJsonFile2(String file3, Object obj) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = Files.newBufferedWriter(Paths.get(file3));
            gson.toJson(obj, writer);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Admin> getListObjectFromJsonFile2(String file3) {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(file3));
            Admin[] adminsFile = gson.fromJson(reader, Admin[].class);
            if (adminsFile == null) {
                return Collections.emptyList();
            } else {
                List<Admin> admins = Arrays.asList(adminsFile);
                reader.close();
                return admins;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    String checkPassword(Scanner sc) {
        while (true) {
            String password = sc.nextLine();
            Pattern pattern = Pattern.compile("\\S{7,15}");
            boolean isPassword = pattern.matcher(password).matches();
            if (!isPassword) {
                System.out.println("Mật khẩu không hợp lệ!\nVui lòng nhập !");
            } else {
                return password;
            }
        }
    }
    boolean checkLegalUsername(String username) {
        String USERNAME_PATTERN =
                "^[a-zA-Z0-9._-]{3,15}$";
        return Pattern.matches(USERNAME_PATTERN, username);
    }
    boolean isExistsUsername(String file1, String username,String file3) {
        List<User> users = getListObjectFromJsonFile(file1);
        List<Admin> admins = getListObjectFromJsonFile2(file3);
        Optional<List<User>> usersOptional = Optional.ofNullable(users);
        Optional<List<Admin>> adminsOptional = Optional.ofNullable(admins);
        if (usersOptional.isPresent()||adminsOptional.isPresent()) {
            for (User user : users) {
                for (User admin : admins){
                    if (user.getUserName().equals(username)||admin.getUserName().equals(username)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean isExistsTitle(String file2, String title) {
        List<Book> books = getListObjectFromJsonFile1(file2);
        Optional<List<Book>> booksOptional = Optional.ofNullable(books);
        if (booksOptional.isPresent()) {
            for (Book book : books){
                if (book.getTitle().equals(title)) {
                    return true;
                }
            }
        }
        return false;
    }
    boolean isExistsId(String file2, String uuid) {
        List<Book> books = getListObjectFromJsonFile1(file2);
        Optional<List<Book>> idOptional = Optional.ofNullable(books);
        if (idOptional.isPresent()) {
            for (Book book : books){
                if (book.getId().equals(uuid)) {
                    return true;
                }
            }
        }
        return false;
    }
    boolean status(String file2,String strBookName){
        List<Book> listBooks = getListObjectFromJsonFile1(file2);
        for (Book book : listBooks) {
            if (book.getTitle().equals(strBookName)||book.getStatus().equals("active")) {
                return true;
            }
        }
        return false;
    }

}
