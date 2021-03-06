package net.den3.den3Account.Command;

import net.den3.den3Account.Router.URLTask;
import net.den3.den3Account.Store.Store;

public class CommandExit implements ICommand{

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public boolean run(String[] option) {
        System.out.println("Shutdown...");
        Store.getInstance().closeStore();
        if(URLTask.webApp != null){
            URLTask.webApp.stop();
        }
        return true;
    }
}
