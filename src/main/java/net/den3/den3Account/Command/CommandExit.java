package net.den3.den3Account.Command;

import net.den3.den3Account.Router.URLTask;
import net.den3.den3Account.Store.AccountStore;

public class CommandExit implements ICommand{

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public boolean run(String[] option) {
        System.out.println("Shutdown...");
        URLTask.webApp.stop();
        AccountStore.getInstance().closeStore();
        return true;
    }
}
