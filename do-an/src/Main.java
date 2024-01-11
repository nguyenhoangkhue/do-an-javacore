import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        Service service=new Service();
        String file1="user.json";
        String file2="book.json";
        String file3="admin.json";
        User user=new User();
        Admin admin=new Admin();
        Book bookinfo=new Book();
        service.startProgram(sc,file1,file2,user,file3,admin,bookinfo);
    }
}
