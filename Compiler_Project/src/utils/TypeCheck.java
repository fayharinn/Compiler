package utils;

import java.util.HashMap;

import ast.Exp;
import visitor.TypeCheckVisitor;
import type.*;

public class TypeCheck {

	
	public static void check(Exp expression) {
		TypeCheckVisitor tcv = new TypeCheckVisitor();
		expression.accept(tcv,new HashMap<String,Type>(),new TInt(), new HashMap<Type,Type>());
        System.out.println();
	}
}
