public class AVL<E extends Comparable<E>> {

    // Raiz da árvore
    private Node raiz;

    // Construtor
    public AVL() {
        raiz = null; // Inicialmente a árvore está vazia
    }

    // Método de verificação se a árvore está vazia
    public boolean isEmpty() {
        return (raiz == null);
    }

    // Método de inserção
    public E inserir(E valor) {
        try {
            Node novo = new Node(valor);
            this.raiz = inserir(novo, raiz);
        } catch (Exception exMemoria) {
            return null;
        }
        return valor;
    }

    // Método de inserção com balanceamento
    private Node inserir(Node novo, Node anterior) {
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

        // Atualiza a altura do nó
        anterior.setAltura(1 + Math.max(obterAltura(anterior.getFilhoEsquerdo()), obterAltura(anterior.getFilhoDireito())));

        // Verifica o fator de balanceamento e realiza as rotações necessárias
        return balancear(anterior);
    }

    // Método de balanceamento da árvore
    private Node balancear(Node no) {
        int fatorBalanceamento = no.getFatorDeBalanceamento();

        // Caso 1: Desbalanceamento à esquerda
        if (fatorBalanceamento > 1) {
            if (no.getFilhoEsquerdo().getFatorDeBalanceamento() < 0) {
                // Rotação esquerda-direita
                no.setFilhoEsquerdo(rotacionarEsquerda(no.getFilhoEsquerdo()));
            }
            // Rotação à direita
            return rotacionarDireita(no);
        }

        // Caso 2: Desbalanceamento à direita
        if (fatorBalanceamento < -1) {
            if (no.getFilhoDireito().getFatorDeBalanceamento() > 0) {
                // Rotação direita-esquerda
                no.setFilhoDireito(rotacionarDireita(no.getFilhoDireito()));
            }
            // Rotação à esquerda
            return rotacionarEsquerda(no);
        }

        // Caso 3: A árvore já está balanceada
        return no;
    }

    // Método de rotação à direita
    private Node rotacionarDireita(Node no) {
        Node novoRaiz = no.getFilhoEsquerdo();
        no.setFilhoEsquerdo(novoRaiz.getFilhoDireito());
        novoRaiz.setFilhoDireito(no);
        no.setAltura(Math.max(obterAltura(no.getFilhoEsquerdo()), obterAltura(no.getFilhoDireito())) + 1);
        novoRaiz.setAltura(Math.max(obterAltura(novoRaiz.getFilhoEsquerdo()), obterAltura(novoRaiz.getFilhoDireito())) + 1);
        return novoRaiz;
    }

    // Método de rotação à esquerda
    private Node rotacionarEsquerda(Node no) {
        Node novoRaiz = no.getFilhoDireito();
        no.setFilhoDireito(novoRaiz.getFilhoEsquerdo());
        novoRaiz.setFilhoEsquerdo(no);
        no.setAltura(Math.max(obterAltura(no.getFilhoEsquerdo()), obterAltura(no.getFilhoDireito())) + 1);
        novoRaiz.setAltura(Math.max(obterAltura(novoRaiz.getFilhoEsquerdo()), obterAltura(novoRaiz.getFilhoDireito())) + 1);
        return novoRaiz;
    }

    // Método auxiliar para obter a altura de um nó
    private int obterAltura(Node no) {
        if (no == null) {
            return 0;
        }
        return no.getAltura();
    }

    // Método de busca de um registro de óbitos por ano
    public RegistroObitos buscar(int ano) {
        return buscar(raiz, ano);
    }

    // Método recursivo de busca
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

    // Método de exclusão de um registro de óbitos por ano
    public boolean excluir(int ano) {
        if (raiz == null) {
            return false; // Árvore vazia
        }

        raiz = excluir(raiz, ano);
        return raiz != null;
    }

    // Método recursivo de exclusão
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

        // Atualiza a altura e balanceia o nó
        no.setAltura(1 + Math.max(obterAltura(no.getFilhoEsquerdo()), obterAltura(no.getFilhoDireito())));
        return balancear(no);
    }

    // Método para encontrar o nó com o menor valor (para exclusão)
    private Node encontrarMinimo(Node no) {
        while (no.getFilhoEsquerdo() != null) {
            no = no.getFilhoEsquerdo();
        }
        return no;
    }

    // Método de percurso em ordem (in-order)
    public void emOrdem() {
        emOrdem(raiz);
    }

    private void emOrdem(Node no) {
        if (no != null) {
            emOrdem(no.getFilhoEsquerdo());
            System.out.println(no.getValue());
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
