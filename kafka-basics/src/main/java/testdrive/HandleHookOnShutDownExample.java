package testdrive;

public class HandleHookOnShutDownExample {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("I'm stopped")));


        while (true){
            try {
                Thread.sleep(1000);
            } catch (Exception e){
//                NOPE
            }
        }
    }
}
