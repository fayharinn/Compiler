package visitor;

import ast.*;
import ast.Float;
import utils.Id;
import utils.findType;
import type.*;
import java.util.*;


public class TypeCheckVisitor implements TypeVisitor<Type>, Visitor {
	HashMap<String, Type> env ;
	HashMap<Type,Type> equations;

    public TypeCheckVisitor() {
		this.env = new HashMap<>();
		this.equations =  new HashMap<>();
	}

	public Type visit(Unit e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TUnit();
    }

    public Type visit(Bool e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TBool();
    }

    public Type visit(Int e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TInt();
    }

    public Type visit(Float e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TFloat();
    }

    public Type visit(Not e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e.accept(this,env,new TBool(), genEqs);
    	genEqs.put(new TBool(), exptype);
    	return new TBool();
    }

    public Type visit(Neg e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
    	return new TInt();
    }

    public Type visit(Add e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
    	return new TInt();
    }

    public Type visit(Sub e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
    	return new TInt();
    }

    public Type visit(FNeg e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        e.e.accept(this,env,new TFloat(),genEqs);
        return new TFloat();
    }

    public Type visit(FAdd e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
    	return new TFloat();
    }

    public Type visit(FSub e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
    	return new TFloat();
    }

    public Type visit(FMul e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
    	return new TFloat();
    }

    public Type visit(FDiv e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
    	return new TFloat();
    }

    public Type visit(Eq e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TBool(), exptype);
    	return new TBool();
    }

    public Type visit(LE e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TBool(), exptype);
    	return new TBool();
    }

    public Type visit(If e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TBool(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	e.e3.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
        return new TInt();
    }

    public Type visit(Let e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	Type t1 = e.e1.accept(this,env,e.t, genEqs);
    	env.put(e.id.id, t1);
    	Type t2 = e.e2.accept(this,env,exptype, genEqs);
    	genEqs.put(new TInt(), exptype);
    	return t2;
    }

    public Type visit(Var e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	System.out.println(env);
    	System.out.println(e.id.id);
    	if(env.containsKey(e.id.id)){
    		Type t = new TInt();
			genEqs.put(new TInt(), exptype);
			return t;
    	} else
			try {
				throw new Exception("Undefined Variable");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	return null;
    		
    }

    public Type visit(LetRec e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        return null;
    }

    public Type visit(App e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Tuple e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(LetTuple e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Array e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Get e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Put e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        return null;
    }

	@Override
	public void visit(Unit e) {
		System.out.println("eee");
		
	}

	@Override
	public void visit(Bool e) {
		
		
	}

	@Override
	public void visit(Int e) {
	
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Float e) {
	
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Not e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Neg e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Add e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Sub e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FNeg e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FAdd e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FSub e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FMul e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FDiv e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Eq e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LE e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(If e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Let e) {
    	Type t1 = e.e1.accept(this,env,e.t, equations);
    	equations.put(new TInt(), e.t);
    	env.put(e.id.id, t1);
    	Type t2 = e.e2.accept(this,env,e.t, equations);
    	
		
	}

	@Override
	public void visit(Var e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LetRec e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(App e) {
		System.out.print("tesrtrtrrettret");
		e.e.accept(this,env,null, equations);
		
	}

	@Override
	public void visit(Tuple e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LetTuple e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Array e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Get e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Put e) {
		// TODO Auto-generated method stub
		
	}
}


