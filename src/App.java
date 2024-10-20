import service.RAMService;

public class App {
    static RAMService service = new RAMService();
    public static void main(String[] args) {
        RAMService console = new RAMService();
        //System.out.println("\u001B[0m" + "hello" + "\u001B[0m");
        console.showMenu();
    }
}
