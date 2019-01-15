package type;

public abstract class Type {
    private static int x = 0;
    public static Type gen() {
        return new TVar("?" + x++);
    }
    public abstract String toString();
    public Type getReturnType(){
        return new TUnit();
    }

    public  String TypeToString(){
        return "Type";
    }
}










