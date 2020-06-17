import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class main {

    public static void main(String[] args) {
        CustomHeap heap = new CustomHeap();
        Scanner scanner = new Scanner(System.in);
        try {
            int M = Integer.parseInt(scanner.nextLine());
            for(int i = 0; i < M; i++){
                String[] line = scanner.nextLine().split(" ");
                runCommand(heap, line);
            }

        } catch (NumberFormatException e){
            System.out.println("Number of commands should be an integer");
        }
    }

    private static void runCommand(CustomHeap heap, String[] line) {

        String command = line[0];
        // check if command exists
        if (!PossibleCommands.getCommands().contains(command)){
            System.out.println("Unknown command");
            return;
        }
        // check how many args should have
        if (PossibleCommands.getCommandsWithArg(0).contains(command)){
            try {
                String methodName = PossibleCommands.valueOf(command).command;
                Method method = CustomHeap.class.getDeclaredMethod(methodName);
                method.invoke(heap);
            } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Unknown command");
                e.printStackTrace();
            }
        }
        else if (PossibleCommands.getCommandsWithArg(2).contains(command)){
            try {
                int value = Integer.parseInt(line[1]);
                int priority = Integer.parseInt(line[2]);

                String methodName = PossibleCommands.valueOf(command).command;
                Method method = CustomHeap.class.getDeclaredMethod(methodName, int.class, int.class);
                method.invoke(heap, value, priority);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong arguments for command");
            } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Unknown command");
                e.printStackTrace();
            }
        }
    }

}
