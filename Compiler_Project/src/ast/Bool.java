package ast;

import type.Type;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;
import visitor.VisitorArgs;

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
	public Type accept(TypeCheckVisitor typeCheckVisitor) {
		// TODO Auto-generated method stub
		return typeCheckVisitor.visit(this);
	}

    public void accept(VisitorArgs v, Exp e) {
        v.visit(this, e);
    }

    public String typeToString(){
        return "TBool";
    }
}
