package ast;

import java.util.HashMap;

import type.Type;
import utils.Id;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

public class Float extends Exp {
    public float f;

    public Float(float f) {
        this.f = f;
    }

    public <E> E accept(ObjVisitor<E> v) {
        return v.visit(this);
    }
    public void accept(Visitor v) {
        v.visit(this);
    }
    
	@Override
	public Type accept(TypeCheckVisitor typeCheckVisitor, HashMap<Id, Type> env, Type exptype,
			HashMap<Type, Type> genEqs) {
		// TODO Auto-generated method stub
		return typeCheckVisitor.visit(this, env, exptype, genEqs);
	}
}
