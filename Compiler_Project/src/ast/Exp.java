package ast;

import java.util.HashMap;

import type.Type;
import utils.Id;
import visitor.ObjVisitor;
import visitor.TypeCheckVisitor;
import visitor.Visitor;

public abstract class Exp {
    public abstract void accept(Visitor v);

    public abstract <E> E accept(ObjVisitor<E> v);

	public abstract Type accept(TypeCheckVisitor typeCheckVisitor,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    
	
}
