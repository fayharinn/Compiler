package ast;

import type.Type;
import utils.Id;
import visitor.*;

import java.util.HashMap;

public class Save extends Exp {

    public final Id id; // registre qu'on veut sauvegarder
    public final int stackOffset; //[fp, #stackoffset]
    public final Exp e;//suite

    public Save(Id id, int stackOffset, Exp e) {
        this.id = id;
        this.stackOffset = stackOffset;
        this.e = e;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }


    @Override
    public void accept(VisitorArgs v, Exp e1) {
        v.visit(this,e1);
    }

    @Override
    public Type accept(TypeCheckVisitor typeCheckVisitor) {
        return null;
    }


    public void accept(Visitor v) {v.visit(this);}
}
