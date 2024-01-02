import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        String file3="admin.json";
        ArrayList<Admin>arrAdmin=new ArrayList<>();
        Admin admin=new Admin("khue123","vadcac");
        arrAdmin.add(admin);
        convertObjectToJsonFile2(file3, arrAdmin);
        Scanner sc=new Scanner(System.in);
        Service service=new Service();
        String file1="user.json";
        String file2="book.json";
        User user=new User();
        service.startProgram(sc,file1,file2,user);
    }

    private static void convertObjectToJsonFile2(String file3, ArrayList<Admin> arrAdmin) {
    }
}
