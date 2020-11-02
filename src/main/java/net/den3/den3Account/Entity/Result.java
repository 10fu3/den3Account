//package net.den3.den3Account.Entity;
//
//import java.util.function.Consumer;
//
//enum State{
//    INITIALIZED,
//    SUCCEEDED,
//    FAILED
//}
//
//public class Result<T> {
//    private T value = null;
//    private State state = State.INITIALIZED;
//    private Consumer<T> onSucceededFunction = null;
//    private Runnable onFailedFunction = null;
//
//    public Result<T> setValue(T v){
//        if(v == null){
//            state = State.FAILED;
//        }else{
//            this.value = v;
//            state = State.SUCCEEDED;
//        }
//        return this;
//    }
//
//    public Result<T> setOnSucceed(Consumer<T> onSucceeded){
//        this.onSucceededFunction = onSucceeded;
//        return this;
//    }
//
//    public Result<T> setOnFailed(Runnable onFailed){
//        this.onFailedFunction = onFailed;
//        return this;
//    }
//
//    public void run(){
//        if(this.onFailedFunction == null && this.state != State.SUCCEEDED){
//            throw new NullPointerException();
//        }else{
//            if(state == State.SUCCEEDED){
//                onSucceededFunction.accept(this.value);
//            }else{
//                onFailedFunction.run();
//            }
//        }
//    }
//
//    public Result setState(State changed){
//        this.state = changed;
//        return this;
//    }
//
//    public Result setFailed(){
//        this.state = State.FAILED;
//        return this;
//    }
//}
