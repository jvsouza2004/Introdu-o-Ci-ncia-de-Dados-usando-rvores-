public class ABB<E extends Comparable<E>> {

    // raiz
    private Node raiz;

    // construtor
    public ABB() {
        raiz = null; // cria a árvore inicialmente vazia
    }

    // método verifica se a árvore está vazia
    public boolean isEmpty() {
        return (raiz == null);
    }

    // operação de inserção
    public E inserir(E valor) {
        try {
            Node novo = new Node(valor);
            this.raiz = inserir(novo, raiz);
        } catch (Exception exMemoria) {
            return null;
        }
        return valor;
    }

    // Método de inserção que soma óbitos quando os anos são iguais
    public Node inserir(Node novo, Node anterior) {
        if (anterior == null) {
            return novo; // Insere o novo nó se não existir
        }

        RegistroObitos novoRegistro = (RegistroObitos) novo.getValue();
        RegistroObitos registroAnterior = (RegistroObitos) anterior.getValue();

        if (novoRegistro.getAno() == registroAnterior.getAno()) {
            // Se o ano for igual, soma os óbitos ao nó existente
            registroAnterior.somarObitos(novoRegistro.getObitosMenoresDe1Ano());
        } else if (novoRegistro.compareTo(registroAnterior) < 0) {
            anterior.setFilhoEsquerdo(inserir(novo, anterior.getFilhoEsquerdo())); // Insere à esquerda
        } else {
            anterior.setFilhoDireito(inserir(novo, anterior.getFilhoDireito())); // Insere à direita
        }

        return anterior;
    }

    // Busca de um registro de óbitos por ano
    public RegistroObitos buscar(int ano) {
        return buscar(raiz, ano);
    }

    private RegistroObitos buscar(Node no, int ano) {
        if (no == null) {
            return null; // Não encontrou o nó
        }

        RegistroObitos registro = (RegistroObitos) no.getValue();
        if (ano == registro.getAno()) {
            return registro; // Encontrou o registro
        } else if (ano < registro.getAno()) {
            return buscar(no.getFilhoEsquerdo(), ano); // Busca à esquerda
        } else {
            return buscar(no.getFilhoDireito(), ano); // Busca à direita
        }
    }

    // Método para excluir um nó por ano
    public boolean excluir(int ano) {
        if (raiz == null) {
            return false; // Árvore vazia
        }

        raiz = excluir(raiz, ano);
        return raiz != null;
    }

    private Node excluir(Node no, int ano) {
        if (no == null) {
            return null; // Não encontrou o nó
        }

        RegistroObitos registro = (RegistroObitos) no.getValue();
        
        if (ano < registro.getAno()) {
            no.setFilhoEsquerdo(excluir(no.getFilhoEsquerdo(), ano)); // Exclui à esquerda
        } else if (ano > registro.getAno()) {
            no.setFilhoDireito(excluir(no.getFilhoDireito(), ano)); // Exclui à direita
        } else {
            // Nó encontrado
            if (no.getFilhoEsquerdo() == null && no.getFilhoDireito() == null) {
                return null; // Nó sem filhos
            } else if (no.getFilhoEsquerdo() == null) {
                return no.getFilhoDireito(); // Nó com um filho à direita
            } else if (no.getFilhoDireito() == null) {
                return no.getFilhoEsquerdo(); // Nó com um filho à esquerda
            } else {
                // Nó com dois filhos, encontrar o menor da subárvore direita
                Node sucessor = encontrarMinimo(no.getFilhoDireito());
                no.setValue(sucessor.getValue()); // Substitui o valor
                no.setFilhoDireito(excluir(no.getFilhoDireito(), ((RegistroObitos) sucessor.getValue()).getAno())); // Remove o sucessor
            }
        }

        return no;
    }

    private Node encontrarMinimo(Node no) {
        while (no.getFilhoEsquerdo() != null) {
            no = no.getFilhoEsquerdo();
        }
        return no;
    }

    // Percurso em ordem
    public void emOrdem() {
        emOrdem(raiz);
    }

    // Método que percorre a árvore - em ordem
    private void emOrdem(Node no) {
        if (no != null) {
            emOrdem(no.getFilhoEsquerdo());
            System.out.println(no.getValue()); // Alterado para println para garantir que cada valor seja impresso em uma nova linha
            emOrdem(no.getFilhoDireito());
        }
    }
    public int somarObitosPeriodo(int anoInicio, int anoFim) {
        return somarObitosPeriodo(raiz, anoInicio, anoFim);
    }
    
    private int somarObitosPeriodo(Node no, int anoInicio, int anoFim) {
        if (no == null) {
            return 0; // Árvore vazia ou chegou no final da subárvore
        }
    
        RegistroObitos registro = (RegistroObitos) no.getValue();
        int soma = 0;
    
        // Se o ano do registro está dentro do intervalo, soma os óbitos
        if (registro.getAno() >= anoInicio && registro.getAno() <= anoFim) {
            soma += registro.getObitosMenoresDe1Ano();
        }
    
        // Percorre a subárvore esquerda se o ano de busca for maior que o ano do registro
        if (anoInicio < registro.getAno()) {
            soma += somarObitosPeriodo(no.getFilhoEsquerdo(), anoInicio, anoFim);
        }
    
        // Percorre a subárvore direita se o ano de busca for menor que o ano do registro
        if (anoFim > registro.getAno()) {
            soma += somarObitosPeriodo(no.getFilhoDireito(), anoInicio, anoFim);
        }
    
        return soma;
    }
}
