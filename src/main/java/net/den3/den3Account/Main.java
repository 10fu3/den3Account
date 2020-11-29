package net.den3.den3Account;

import net.den3.den3Account.Command.CommandExecutor;
import net.den3.den3Account.Router.URLTask;
public class Main {
    public static void main(String[] args) {
        new Thread(CommandExecutor.SINGLE::standbyCommand).start();
        URLTask.setupRouting();
    }
}
