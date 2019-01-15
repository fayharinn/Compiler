package visitor;

import ast.*;
import ast.Float;
import utils.*;
import type.*;
import java.util.*;


public class TypeCheckVisitor implements TypeVisitor<Type> {
	private AllEnvironements gho;

    public TypeCheckVisitor() {
		try {
			ArrayList<Type> args = new ArrayList<>();
			args.add(new TInt());
			gho = new AllEnvironements();
			gho.ajouterVar("print_int",new TFun(args,new TUnit()));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

    public AllEnvironements getEnvironement() {
        return gho;
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
			   System.exit(1);
		   }

   }

	public Type visit(Unit e) {
        return new TUnit();
    }

    public Type visit(Bool e) {
        return new TBool();
    }

    public Type visit(Int e) {
        return new TInt();
    }

    public Type visit(Float e) {
        return new TFloat();
    }


    public Type visit(Not e) {
        e.e.accept(this);
        return new TBool();
    }
    public Type visit(Neg e) {
    	e.e.accept(this);
    	return new TInt();
    }

    public Type visit(Add e) {
    	Type t1= e.e1.accept(this);
    	Type t2= e.e2.accept(this);

		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in addition with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in addition with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}

    	return new TInt();
    }

    public Type visit(Sub e) {
    	Type t1 = e.e1.accept(this);
    	Type t2 = e.e2.accept(this);
		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in Substraction with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in Substraction with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
    	return new TInt();
    }

    public Type visit(FNeg e){
        e.e.accept(this);
        return new TFloat();
    }

    public Type visit(FAdd e){
    	Type t1 = e.e1.accept(this);
    	Type t2 = e.e2.accept(this);

		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in addition with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in addition with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}

    	return new TFloat();
    }

    public Type visit(FSub e){
    	Type t1 = e.e1.accept(this);
    	Type t2 = e.e2.accept(this);
		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Substraction with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Substraction with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
    	return new TFloat();
    }

    public Type visit(FMul e) {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Multiplication with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Multiplication with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
    	return new TFloat();
    }

    public Type visit(FDiv e){
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
		if (t1.getClass() != TFloat.class){
			if (t1.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Division with "+t1.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TFloat.class){
			if (t2.getReturnType().getClass()!=TFloat.class){
				try {
					throw new Exception("Error in Division with "+t2.getClass()+": Float expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
    	return new TFloat();
    }

    public Type visit(Eq e){
    	Type t1 = e.e1.accept(this);
    	Type t2 = e.e2.accept(this);
		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in the condition with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in condition with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
    	return new TBool();
    }

    public Type visit(LE e){
    	Type t1 = e.e1.accept(this);
    	Type t2 = e.e2.accept(this);

		if (t1.getClass() != TInt.class){
			if (t1.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in the condition of LESS OR EQUAL with "+t1.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
		else if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in condition of LESS OR EQUAL with "+t2.getClass()+": Int expected");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
    	return new TBool();
    }

    public Type visit(If e){
    	Type t1 = e.e1.accept(this);

		if (t1.getClass() != TBool.class){
			if (t1.getReturnType().getClass()!=TBool.class){
				try {
					throw new Exception("Error in If construction, in the condition");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
    	Type t2 = e.e2.accept(this);
		if (t2.getClass() != TInt.class){
			if (t2.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in If construction, check the then");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}

    	Type t3 = e.e3.accept(this);
		if (t3.getClass() != TInt.class){
			if (t3.getReturnType().getClass()!=TInt.class){
				try {
					throw new Exception("Error in If construction, check the else");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(1);
				}
			}
		}
        return new TInt();
    }

    public Type visit(Let e) {
    	Type t1 = e.e1.accept(this);
    	if(gho.containsKey(e.id.id)) {
    		try {
				throw new Exception("Error in Let declaration : "+e.id.id+" already defined");
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}
    	}
		try {
			gho.ajouterVar(e.id.id, t1);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
    	Type t2 = e.e2.accept(this);
    	return t2;
    }


    public Type visit(Var e) {
		if(gho.containsKey(e.id.id)){
			Type t = null;
			try {
				t = gho.getTypeOfVar(e.id.id);
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			return t;
		} else
			try {
				throw new Exception("Undefined Variable "+e.id.id);
			} catch (Exception e1) {
				e1.printStackTrace();
				System.exit(1);
			}
		return null;
	}


	public Type visit(LetRec e){
		gho.creerEnvironementLocale();
		FunDef f = e.fd;
		ArrayList<Type> argsType = new ArrayList<Type>();
		try {
			for (Id vars : f.args){
				gho.ajouterVar(vars.id,new TInt());
				argsType.add(new TInt());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}

		TFun functionType = new TFun(argsType,new TInt());
		try {
			this.gho.ajouterVar(f.id.id,functionType);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		Type FundefType = f.e.accept(this);
		functionType.setReturnType(FundefType);

		e.e.accept(this);
		gho.supprimerEnvironementLocale();
		return functionType;
	}

	public Type visit(App e){
		TFun functionType = null;
		try {
			if (e.e.getClass()!= Var.class) throw new Exception(e.e.toString() +" is not a function call");
			Var calledFunction = (Var) e.e;
			if (!gho.containsKey(calledFunction.id.id)) throw  new Exception(calledFunction.id.id +" is not defined");
			// Type check of function paramétres
			functionType = (TFun) gho.getTypeOfVar(calledFunction.id.id);
			for (int i=0; i<functionType.getargsType().size(); i++){
				String argsTab = functionType.getargsType().get(i).toString();
				String ourArgs = e.es.get(i).toString();
				if (!argsTab.equals(ourArgs)){
					String calledfunctionType = functionType.getReturnType().toString();
					String argsFunction = this.gho.getTypeOfVar(((Var) e.e).id.id).getReturnType().toString();
					if (!calledfunctionType.equals(argsFunction)){
						throw new Exception("Not the same arguments Types");
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		return functionType;
	}

	public Type visit(Tuple e){
        return null;
    }

    public Type visit(LetTuple e){
        return null;
    }

    public Type visit(Array e){
        return null;
    }

    public Type visit(Get e){
        return null;
    }

    public Type visit(Put e){
        return null;
    }


}
