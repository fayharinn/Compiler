package ast;

import type.Type;
import utils.Id;
import visitor.*;

import java.util.HashMap;

public class Load extends Exp {

    public final Id id;
    public final int stackOffset;
    public final Exp e;

    public Load(Id id, int stackOffset, Exp e) {
        this.id = id;
        this.stackOffset = stackOffset;
        this.e = e;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    @Override
    public Type accept(TypeCheckVisitor typeCheckVisitor, HashMap<String, Type> env, Type exptype, HashMap<Type, Type> genEqs) {
        return null;
    }

    public void accept(Visitor v) {v.visit(this);}
    public void accept(VisitorArgs v, Exp e1) {
        v.visit(this, e);
    }
}