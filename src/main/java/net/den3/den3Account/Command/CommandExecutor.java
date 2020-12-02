package net.den3.den3Account.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandExecutor {
    public final static CommandExecutor SINGLE = new CommandExecutor();
    private Map<String,ICommand> commandStore = new HashMap<>();
    public final Scanner scan;

    public CommandExecutor(){
        scan = new Scanner(System.in);

        this.registerCommand(new CommandExit());
        this.registerCommand(new CommandAccountCURD());
        this.registerCommand(new CommandConfig());

    }

    public void registerCommand(ICommand command){
        this.commandStore.put(command.getName(),command);
    }

    public void standbyCommand(){
        new Thread(()->{
            String beforeCommand = "";
            String[] lines;
            String line;
            while (!(beforeCommand.length() == 4 && beforeCommand.equalsIgnoreCase("exit"))){
                line = scan.nextLine();
                lines = line.split(" ");
                if(lines.length < 1){
                    continue;
                }
                if(!runCommand(lines)){
                    System.out.println("Command execution failed.");
                    System.out.println("Command: "+line);
                }
                beforeCommand = line;
            }
        }).start();


    }

    public boolean runCommand(String[] arg){
        if(arg.length < 1){
            return false;
        }
        if(!commandStore.containsKey(arg[0])){
            return false;
        }
        String[] args = new String[arg.length-1];
        for (int i = 0; i < arg.length - 1; i++) {
            args[i] = arg[i+1];
        }
        return commandStore.get(arg[0]).run(args);
    }
}
