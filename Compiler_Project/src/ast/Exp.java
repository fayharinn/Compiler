package ast;

import type.Type;
import utils.Id;
import visitor.*;

import java.util.HashMap;


public abstract class Exp {
    public abstract void accept(Visitor v);

    public abstract <E> E accept(ObjVisitor<E> v);



    public abstract void accept(VisitorArgs v, Exp e1);


    //public abstract void accept(ArmVisitor v);
	public abstract Type accept(TypeCheckVisitor typeCheckVisitor);

    /**
     *
     * @return Le type du noeud courant.
     */
    public String typeToString(){
        return "Exp";
    }

}
