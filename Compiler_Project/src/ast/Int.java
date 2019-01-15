package ast;

import type.Type;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

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

    public String toString(){
        return "TInt";
    }
    
	@Override
    public Type accept(TypeCheckVisitor typeCheckVisitor) {
        // TODO Auto-generated method stub
        return typeCheckVisitor.visit(this);
    }

    public String typeToString(){
        return "TInt";
    }
}
