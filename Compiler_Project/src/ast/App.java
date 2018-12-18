package ast;

import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

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
	public Type accept(TypeCheckVisitor typeCheckVisitor, Type expType) {
		// TODO Auto-generated method stub
		return typeCheckVisitor.visit(this, expType);
	}
}
