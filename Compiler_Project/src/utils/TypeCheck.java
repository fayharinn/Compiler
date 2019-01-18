package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ast.Exp;
import visitor.TypeCheckVisitor;
import type.*;

public class TypeCheck {

	
	public static HashMap<String,Type> check(Exp expression) {
		
		// check 1
		TypeCheckVisitor tcv = new TypeCheckVisitor();
		expression.accept(tcv,new HashMap<String,Type>(),new TUnit(), new HashMap<Type,Type>());
        System.out.println();
        System.out.println(tcv.allEnv);
        Iterator it = tcv.getEq().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getValue().getClass().toString().equals("class type.TVar")) {
            	System.out.println(pair.getKey() + " = " + ((TVar) pair.getValue()).v);
            }
            else {
            	System.out.println(pair.getKey() + " = " + pair.getValue().getClass());	
            }
            it.remove(); // avoids a ConcurrentModificationException
            
        }
        
        //check 2
        return tcv.env;
        
	}
}
