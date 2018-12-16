package visitor;

import java.util.HashMap;

import ast.*;
import ast.Float;
import type.Type;
import utils.Id;

public interface TypeVisitor<E>  {
    E visit(Unit e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Bool e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Int e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Float e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Not e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Neg e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Add e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Sub e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FNeg e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FAdd e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FSub e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FMul e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(FDiv e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Eq e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(LE e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(If e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Let e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Var e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(LetRec e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(App e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Tuple e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(LetTuple e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Array e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Get e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
    E visit(Put e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs);
}
