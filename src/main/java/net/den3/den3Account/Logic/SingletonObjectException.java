package net.den3.den3Account.Logic;

public class SingletonObjectException extends RuntimeException{
    public SingletonObjectException(){
        super("シングルトンオブジェクトを含むクラスのインスタンスを生成しないでください");
    }
}
