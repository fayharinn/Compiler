package ast;

import visitor.*;

import java.util.List;

import type.Type;

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
	public Type accept(TypeCheckVisitor typeCheckVisitor) {
		// TODO Auto-generated method stub
		return typeCheckVisitor.visit(this);
	}




    public void accept(VisitorArgs v, Exp e) {
        v.visit(this, e);
    }

    public String typeToString(){
        return "App";

    }
}
