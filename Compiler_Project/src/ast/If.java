package ast;

import java.util.HashMap;

import type.Type;
import utils.Id;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

public class If extends Exp {
    public final Exp e1;
    public final Exp e2;
    public final Exp e3;

    public If(Exp e1, Exp e2, Exp e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
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
}
