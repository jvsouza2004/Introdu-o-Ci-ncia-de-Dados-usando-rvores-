public class Node<E extends Comparable<E>> {
    // Atributos
    private E value;
    private Node filhoEsquerdo;
    private Node filhoDireito;
    private int altura; // Adicionado para manter a altura do nó

    // Construtor
    public Node(E value) {
        this.value = value;
        this.filhoDireito = null;
        this.filhoEsquerdo = null;
        this.altura = 1; // Inicializando a altura como 1 para o nó recém-criado
    }

    // Métodos de acesso
    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Node getFilhoEsquerdo() {
        return filhoEsquerdo;
    }

    public void setFilhoEsquerdo(Node filhoEsquerdo) {
        this.filhoEsquerdo = filhoEsquerdo;
    }

    public Node getFilhoDireito() {
        return filhoDireito;
    }

    public void setFilhoDireito(Node filhoDireito) {
        this.filhoDireito = filhoDireito;
    }

    // Método para obter a altura do nó
    public int getAltura() {
        return altura;
    }

    // Método para atualizar a altura do nó
    public void setAltura(int altura) {
        this.altura = altura;
    }

    // Método para calcular o fator de balanceamento do nó
    public int getFatorDeBalanceamento() {
        int alturaEsquerda = (filhoEsquerdo != null) ? filhoEsquerdo.getAltura() : 0;
        int alturaDireita = (filhoDireito != null) ? filhoDireito.getAltura() : 0;
        return alturaEsquerda - alturaDireita;
    }

    // Método toString para exibir o valor do nó
    public String toString() {
        return value.toString();
    }
}
