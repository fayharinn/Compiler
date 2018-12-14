package ast;

import type.Type;
import utils.Id;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

import java.util.HashMap;
import java.util.List;

public class LetTuple extends Exp {
    public final List<Id> ids;
    final List<Type> ts;
    public final Exp e1;
    public final Exp e2;

    public LetTuple(List<Id> ids, List<Type> ts, Exp e1, Exp e2) {
        this.ids = ids;
        this.ts = ts;
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
	public Type accept(TypeCheckVisitor typeCheckVisitor, HashMap<Id, Type> env, Type exptype,
			HashMap<Type, Type> genEqs) {
		// TODO Auto-generated method stub
		return typeCheckVisitor.visit(this, env, exptype, genEqs);
	}
}
