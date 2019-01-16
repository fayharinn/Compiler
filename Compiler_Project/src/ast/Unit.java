package ast;

import type.Type;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;
import visitor.VisitorArgs;

public class Unit extends Exp {
    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
    

    public void accept(VisitorArgs v, Exp e){
        v.visit(this, e);
    }


    public Type accept(TypeCheckVisitor typeCheckVisitor) {
        // TODO Auto-generated method stub
        return typeCheckVisitor.visit(this);
    }

    public String typeToString(){
        return "Null";
    }

}
