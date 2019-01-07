package ast;


import type.Type;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

public class LetRec extends Exp {
    public final FunDef fd;
    public final Exp e;

    public LetRec(FunDef fd, Exp e) {
        this.fd = fd;
        this.e = e;
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
