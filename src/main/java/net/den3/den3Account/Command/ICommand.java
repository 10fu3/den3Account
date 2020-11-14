package net.den3.den3Account.Command;

public interface ICommand {
    String getName();
    boolean run(String[] option);
}
