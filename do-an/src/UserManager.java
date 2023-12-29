import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public abstract class UserManager {
    abstract void changePassword(Scanner sc, User user, String fileName);

    abstract void signOut(Scanner sc, String fileName);

    // Ghi Object JSON file (Object là 1 đối tượng bất kỳ : Có thể là Single Object hoặc List Object)
    public void convertObjectToJsonFile(String fileName, Object obj) {
        try {
            // Tạo đối tượng gson
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Tạo đối tượng Writer để ghi nội dung vào file
            Writer writer = Files.newBufferedWriter(Paths.get(fileName));

            // Ghi object vào file
            gson.toJson(obj, writer);

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<User> getListObjectFromJsonFile(String fileName) {
        try {
            // Khởi tạo đối tượng gson
            Gson gson = new Gson();

            // Tạo đối tượng reader để đọc file
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            // Đọc thông tin từ file và binding và class

            //check file khong co data => list rong
            if (gson.fromJson(reader, User[].class) == null) {
                return Collections.emptyList(); //tuong duong List<User> users = new ArrayList<>();
            } else {
                List<User> users = Arrays.asList(gson.fromJson(reader, User[].class));
                // Đọc file xong thì đóng lại
                // Và trả về kết quả
                reader.close();
                return users;
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
    boolean isExistsUsername(String fileName, String username) {
        List<User> users = getListObjectFromJsonFile(fileName);
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
