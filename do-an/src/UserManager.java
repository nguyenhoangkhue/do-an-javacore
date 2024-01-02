import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public abstract class UserManager {
    abstract void changePassword(Scanner sc, User user, String file1);

    abstract void signOut(Scanner sc, String file1,String file2,User user);

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
            if (gson.fromJson(reader, User[].class) == null) {
                return Collections.emptyList();
            } else {
                List<User> users = Arrays.asList(gson.fromJson(reader, User[].class));
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
            if (gson.fromJson(reader, User[].class) == null) {
                return Collections.emptyList();
            } else {
                List<Book> books = Arrays.asList(gson.fromJson(reader, Book[].class));
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
            if (gson.fromJson(reader, User[].class) == null) {
                return Collections.emptyList();
            } else {
                List<Admin> admins = Arrays.asList(gson.fromJson(reader, Admin[].class));
                reader.close();
                return admins;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    String checkPassword(Scanner scanner) {
        while (true) {
            String password = scanner.nextLine();
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
                " ^[a-zA-Z0-9._-]{3,15}$";
        return Pattern.matches(USERNAME_PATTERN, username);
    }
    boolean isExistsUsername(String file1, String username) {
        List<User> users = getListObjectFromJsonFile(file1);
        Optional<List<User>> usersOptional = Optional.ofNullable(users);
        if (usersOptional.isPresent()) {
            for (User user : users) {
                if (user.getUserName().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }
}
