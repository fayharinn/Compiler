package ast;

import java.util.HashMap;

import type.Type;
import utils.Id;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

public class Bool extends Exp {
    public final boolean b;

    public Bool(boolean b) {
        this.b = b;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
    
	@Override
	public Type accept(TypeCheckVisitor typeCheckVisitor, Type expType) {
		// TODO Auto-generated method stub
		return typeCheckVisitor.visit(this, expType);
	}
}
