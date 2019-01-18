package type;

public class TVar extends Type {
    public String v;
    TVar(String v) {
        this.v = v;
    }
    @Override
    public String toString() {
        return v;
    }

    public  String TypeToString(){
        return "TVar";
    }
}
