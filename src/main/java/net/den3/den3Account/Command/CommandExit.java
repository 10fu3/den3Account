package net.den3.den3Account.Command;

import spark.Spark;

public class CommandExit implements ICommand{

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public boolean run(String[] option) {
        System.out.println("Shutdown...");
        Spark.stop();
        return true;
    }
}
