package net.den3.den3Account.Entity;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MethodPair<GetReturn,SetVal>{
    public final Supplier<GetReturn> getter;
    public final Consumer<SetVal> setter;
    public MethodPair(Supplier<GetReturn> get, Consumer<SetVal> set){
        this.getter = get;
        this.setter = set;
    }
}
