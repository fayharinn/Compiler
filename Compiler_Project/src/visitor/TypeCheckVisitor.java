package visitor;

import ast.*;
import ast.Float;
import utils.*;
import type.*;
import java.util.*;


public class TypeCheckVisitor implements TypeVisitor<Type> {
	private Environement environement;
	private HashMap<Type,Type> equations;
	private AllEnvironements gho;

    public TypeCheckVisitor() {
		try {
			// il faudrait supprimer l'attribut environement et ses affectations
			//-----------------------------------------------------------------
			this.environement = new Environement();
			ArrayList<Type> args = new ArrayList<>();
			args.add(new TInt());
			environement.ajouterVar("print_int",new TFun(args,new TUnit()));
			//------------------------------------------------------------------
			gho = new AllEnvironements();
			gho.ajouterVar("print_int",new TFun(args,new TUnit()));
			this.equations =  new HashMap<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public AllEnvironements getEnvironement() {
        return gho;
    }

    public HashMap<Type,Type> getEq(){
        return equations;
    }


	public void printAllEnvironements(){
    	for (Environement t : gho.getLesEnvironements()){
    		printEnvironement(t);
		}
	}

   public void printEnvironement(Environement p){
		   try {
			   for (String keys: p.getGho().keySet()){
				   String id =keys;
				   Type value = p.getTypeofVar(id);
				   System.out.println(id + " " + value.toString());
			   }
		   } catch (Exception e) {
			   e.printStackTrace();
		   }

   }

	public Type visit(Unit e,Type expType) {
        return new TUnit();
    }

    public Type visit(Bool e,Type expType) {
        return new TBool();
    }

    public Type visit(Int e,Type expType) {
        return new TInt();
    }

    public Type visit(Float e,Type expType) {
        return new TFloat();
    }


    public Type visit(Not e,Type expType) {
        e.e.accept(this, new TBool());
        equations.put(new TBool(), expType);
        return new TBool();
    }
    public Type visit(Neg e,Type expType) {
    	e.e.accept(this,new TInt());
    	equations.put(new TInt(), expType);
    	return new TInt();
    }

    public Type visit(Add e,Type expType) {
    	Type t1= e.e1.accept(this,new TInt());
    	Type t2= e.e2.accept(this,new TInt());
        equations.put(new TInt(), expType);

		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in addition with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in addition with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

    	return new TInt();
    }

    public Type visit(Sub e,Type expType) {
    	Type t1 = e.e1.accept(this,new TInt());
    	Type t2 = e.e2.accept(this,new TInt());
    	equations.put(new TInt(), expType);
		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in Substraction with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in Substraction with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	return new TInt();
    }

    public Type visit(FNeg e,Type expType){
        e.e.accept(this,new TFloat());
        return new TFloat();
    }

    public Type visit(FAdd e,Type expType){
    	Type t1 = e.e1.accept(this,new TFloat());
    	Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);

		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in addition with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in addition with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

    	return new TFloat();
    }

    public Type visit(FSub e,Type expType){
    	Type t1 = e.e1.accept(this,new TFloat());
    	Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);
		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Substraction with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Substraction with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	return new TFloat();
    }

    public Type visit(FMul e,Type expType) {
        Type t1 = e.e1.accept(this,new TFloat());
        Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);
		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Multiplication with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Multiplication with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	return new TFloat();
    }

    public Type visit(FDiv e,Type expType){
        Type t1 = e.e1.accept(this,new TFloat());
        Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);
		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Division with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Division with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	return new TFloat();
    }

    public Type visit(Eq e,Type expType){
    	Type t1 = e.e1.accept(this,new TInt());
    	Type t2 = e.e2.accept(this,new TInt());
    	equations.put(new TBool(), expType);
		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in the condition with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in condition with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	return new TBool();
    }

    public Type visit(LE e,Type expType){
    	Type t1 = e.e1.accept(this,new TInt());
    	Type t2 = e.e2.accept(this,new TInt());
    	equations.put(new TBool(), expType);

		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in the condition of LESS OR EQUAL with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in condition of LESS OR EQUAL with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	return new TBool();
    }

    public Type visit(If e,Type expType){
    	Type t1 = e.e1.accept(this,new TBool());

		if (t1.getClass() != TBool.class){
			if (t1.getReturnType().getClass()!=TBool.class){
				try {
					throw new Exception("Error in If construction, in the condition");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	Type t2 = e.e2.accept(this,new TInt());
		if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in If construction, check the then");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

    	Type t3 = e.e3.accept(this,new TInt());
		if (t3.getClass() != TInt.class){
			if (t3.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in If construction, check the else");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
    	equations.put(new TInt(), expType);
        return new TInt();
    }

    public Type visit(Let e,Type expType) {
    	Type t1 = e.e1.accept(this,e.t);
    	if(gho.containsKey(e.id.id)) {
    		try {
				throw new Exception("Error in Let declaration : "+e.id.id+" already defined");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
		try {
			gho.ajouterVar(e.id.id, t1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		equations.put(t1, expType);
    	Type t2 = e.e2.accept(this,expType);
    	return t2;
    }
    

    public Type visit(Var e,Type expType) {
		if(gho.containsKey(e.id.id)){
			Type t = null;
			try {
				t = gho.getTypeOfVar(e.id.id);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			equations.put(t, expType);
			return t;
		} else
			try {
				throw new Exception("Undefined Variable "+e.id.id);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		return null;

	}


	public Type visit(LetRec e,Type expType){
		gho.créerEnvironementLocale();
		FunDef f = e.fd;
		ArrayList<Type> argsType = new ArrayList<Type>();
		try {
			for (Id vars : f.args){
				gho.ajouterVar(vars.id,new TInt());
				argsType.add(new TInt());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		TFun functionType = new TFun(argsType,new TInt());
		try {
			this.gho.ajouterVar(f.id.id,functionType);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Type FundefType = f.e.accept(this,expType);
		functionType.setReturnType(FundefType);

		e.e.accept(this,expType);
		gho.supprimerEnvironementLocale();
		return functionType;
	}

	public Type visit(App e,Type expType){
		TFun functionType = null;
		try {
			if (e.e.getClass()!= Var.class) throw new Exception(e.e.toString() +" is not a function call");
			Var calledFunction = (Var) e.e;
			if (!gho.containsKey(calledFunction.id.id)) throw  new Exception(calledFunction.id.id +" is not defined");
			// Type check of function paramétres
			functionType = (TFun) gho.getTypeOfVar(calledFunction.id.id);
			if (e.es.equals(functionType.getargsType())) throw new Exception("Not the same arguments Types");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return functionType;
	}

	public Type visit(Tuple e,Type expType){
        return null;
    }

    public Type visit(LetTuple e,Type expType){
        return null;
    }

    public Type visit(Array e,Type expType){
        return null;
    }

    public Type visit(Get e,Type expType){
        return null;
    }

    public Type visit(Put e,Type expType){
        return null;
    }


}


