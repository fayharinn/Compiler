package ast;

import java.util.HashMap;

import type.Type;
import utils.Id;
import visitor.*;

public class Sub extends Exp {
    public final Exp e1;
    public final Exp e2;

    public Sub(Exp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
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
