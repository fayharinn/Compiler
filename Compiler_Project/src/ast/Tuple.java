package ast;

import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

import java.util.HashMap;
import java.util.List;

import type.Type;
import utils.Id;
import visitor.VisitorArgs;

public class Tuple extends Exp {
    public final List<Exp> es;

    public Tuple(List<Exp> es) {
        this.es = es;
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
