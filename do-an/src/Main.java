import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Service service=new Service();
        String fileName="services";
        service.startProgram(sc,fileName);
    }
}
