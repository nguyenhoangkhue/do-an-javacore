import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Service service=new Service();
        String file1="user.json";
        String file2="book.json";
        service.startProgram(sc,file1);
    }
}
