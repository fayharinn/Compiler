package ast;

import java.util.HashMap;

import type.Type;
import utils.Id;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;
import visitor.VisitorArgs;

public class Int extends Exp {
    public final int i;

    public Int(int i) {
        this.i = i;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    public void accept(Visitor v) {
        v.visit(this);
    }
    
	@Override
	public Type accept(TypeCheckVisitor typeCheckVisitor, HashMap<String, Type> env, Type exptype,
			HashMap<Type, Type> genEqs) {
		// TODO Auto-generated method stub
		return typeCheckVisitor.visit(this, env, exptype, genEqs);
	}

    public void accept(VisitorArgs v, Exp e){
        v.visit(this, e);
    }
}
