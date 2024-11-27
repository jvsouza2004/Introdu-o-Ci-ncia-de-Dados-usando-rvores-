public class Node <E  extends Comparable<E>>{
    //atributos
    private E value;
    private Node filhoEsquerdo;
    private Node filhoDireito;
    
    //construtor
    public Node(E value){
        this.value = value;
        this.filhoDireito = null;
        this.filhoEsquerdo = null;
    }
    //métodos de acesso
    public E getValue(){
        return value;
    }
    public void setValue(E value){
        this.value = value;
    }
    public Node getFilhoEsquerdo(){
        return filhoEsquerdo;
    }
    public void setFilhoEsquerdo(Node filhoEsquerdo){
        this.filhoEsquerdo = filhoEsquerdo;
    }
    public Node getFilhoDireito(){
        return filhoDireito;
    }
    public void setFilhoDireito(Node filhoDireito){
        this.filhoDireito = filhoDireito;
    }
    public String toString(){
        return value.toString();
    }
}