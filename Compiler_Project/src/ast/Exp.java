package ast;

import type.Type;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

public abstract class Exp {
    public abstract void accept(Visitor v);

    public abstract <E> E accept(ObjVisitor<E> v);

	public abstract Type accept(TypeCheckVisitor typeCheckVisitor);

    /**
     *
     * @return Le type du noeud courant.
     */
    public String typeToString(){
        return "Exp";
    }

}
