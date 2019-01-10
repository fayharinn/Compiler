package ast;

import java.util.HashMap;

import type.Type;
import utils.Id;
import visitor.*;

public abstract class Exp {
    public abstract void accept(Visitor v);

    public abstract <E> E accept(ObjVisitor<E> v);

	public abstract Type accept(TypeCheckVisitor typeCheckVisitor,HashMap<String, Type> env,Type exptype,HashMap<Type,Type> genEqs);

    public void accept(ArmVisitorArgs armVisitorArgs, Exp e1) {

    }


    //public abstract void accept(ArmVisitor v);
}
