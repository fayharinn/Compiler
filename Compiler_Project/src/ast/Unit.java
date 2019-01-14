package ast;

import type.Type;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

public class Unit extends Exp {
    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
    
	@Override
    public Type accept(TypeCheckVisitor typeCheckVisitor) {
        // TODO Auto-generated method stub
        return typeCheckVisitor.visit(this);
    }
}
