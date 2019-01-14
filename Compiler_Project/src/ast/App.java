package ast;

import visitor.*;

import java.util.HashMap;
import java.util.List;

import type.Type;
import utils.Id;

public class App extends Exp {
    public final Exp e;
    public final List<Exp> es;

    public App(Exp e, List<Exp> es) {
        this.e = e;
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

    @Override
    public void accept(ArmVisitorArgs v, Exp e1) {
        v.visit(this, e);
    }

    public void accept(VisitorArgs v, Exp e){
        v.visit(this, e);
    }
}
