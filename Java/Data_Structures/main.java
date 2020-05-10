import enums.PossibleCommands;
import enums.PossibleTypes;
import sets.DataSet;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.lang.reflect.Method;

public class main {

    public static void main(String[] args) {
        // choose dataset based on argument --type
        DataSet dataSet = chooseType(args);
        if(dataSet == null){
            System.out.println("Wrong starting parameters");
            System.exit(0);
        }
        // read commands from stdin
        Scanner scanner = new Scanner(System.in);
        try {
            int n = Integer.parseInt(scanner.nextLine());

            for(int i = 0; i < n; i++) {
                String[] line = scanner.nextLine().split(" ");
                String command = line[0];
                String arg = (line.length > 1 ? line[1] : "");
                runCommand(dataSet, command, arg);
            }
        } catch (NumberFormatException e){
            System.out.println("First you should specify number of commands");
            System.exit(0);
        }

        // show data on stderr
        dataSet.printStats();
    }


    private static DataSet chooseType(String[] args) {
        int i = Arrays.asList(args).indexOf("--type");
        if (i + 1 < args.length && PossibleTypes.getValues().contains(args[i + 1])){
            try{
                String name = PossibleTypes.valueOf(args[i+1]).set;
                Class className = Class.forName(name);
                Object obj = className.newInstance();
                return (DataSet) obj;
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e ){
                System.out.println("Unknown type");
            }
        } else {
            System.out.println("Unknown type");
        }
        return null;
    }

    private static void runCommand(DataSet dataSet, String command, String arg){
        // check if command exists
        if (!PossibleCommands.getCommands().contains(command)){
            System.out.println("Unknown command here");
        }
        // check if should contain argument, or only command
        else if (PossibleCommands.getCommandsWithArgs().contains(command) && arg.equals("")){
            System.out.println("You should specify argument for " + command);
        }
        // execute command
        else if (PossibleCommands.getCommandsWithArgs().contains(command)){
            try {
                String methodName = PossibleCommands.valueOf(command).command;
                Method method = sets.DataSet.class.getDeclaredMethod(methodName, String.class);
                method.invoke(dataSet, arg);
            } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Unknown command err 1");
                e.printStackTrace();
            }
        }
        else if (PossibleCommands.getCommandsWithoutArgs().contains(command)){
            try {
                String methodName = PossibleCommands.valueOf(command).command;
                Method method = sets.DataSet.class.getDeclaredMethod(methodName);
                method.invoke(dataSet);
            } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Unknown command err 2");
                e.printStackTrace();
            }
        }
    }
}
