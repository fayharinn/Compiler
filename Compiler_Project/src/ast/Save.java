package ast;

import utils.Id;
import visitor.ObjVisitor;
import visitor.Visitor;

public class Save extends Exp {

    public final Id id;
    public final int stackOffset;
    public final Exp e;

    public Save(Id id, int stackOffset, Exp e) {
        this.id = id;
        this.stackOffset = stackOffset;
        this.e = e;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    public void accept(Visitor v) {v.visit(this);}
}
