import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AVL<RegistroObitos> arvore = new AVL<>();
        LeitorCSV.carregarCSV("obitosinfantis_periodo_original.csv", arvore);

        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        while (true) {
            // Exibindo o menu
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Inserir um registro");
            System.out.println("2 - Excluir um registro");
            System.out.println("3 - Buscar um registro");
            System.out.println("4 - Exibir dados em ordem");
            System.out.println("5 - Somar óbitos de um período");
            System.out.println("6 - Sair");
            System.out.print("Opção: ");
            
            opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1: // Inserir um registro
                    scanner.nextLine(); // Consome a linha de quebra
                    System.out.print("Digite o ano: ");
                    int anoInserir = scanner.nextInt();
                    System.out.print("Digite os óbitos menores de 1 ano: ");
                    int obitosInserir = scanner.nextInt();
                    RegistroObitos novoRegistro = new RegistroObitos(anoInserir, obitosInserir);
                    arvore.inserir(novoRegistro);
                    System.out.println("Registro inserido com sucesso!");
                    break;

                case 2: // Excluir um registro
                    scanner.nextLine(); // Consome a linha de quebra
                    System.out.print("Digite o ano a ser excluído: ");
                    int anoExcluir = scanner.nextInt();
                    boolean excluido = arvore.excluir(anoExcluir);
                    if (excluido) {
                        System.out.println("Registro excluído com sucesso!");
                    } else {
                        System.out.println("Não foi possível excluir o registro. Ano não encontrado.");
                    }
                    break;

                case 3: // Buscar um registro
                    scanner.nextLine(); // Consome a linha de quebra
                    System.out.print("Digite o ano a ser buscado: ");
                    int anoBuscar = scanner.nextInt();
                    RegistroObitos registroEncontrado = arvore.buscar(anoBuscar);
                    if (registroEncontrado != null) {
                        System.out.println("Registro encontrado: " + registroEncontrado);
                    } else {
                        System.out.println("Registro não encontrado.");
                    }
                    break;

                case 4: // Exibir dados em ordem
                    System.out.println("Dados na árvore (em ordem):");
                    arvore.emOrdem();
                    break;

                case 6: // Sair
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                case 5: // Somar óbitos de um período
                    scanner.nextLine(); // Consome a linha de quebra
                    System.out.print("Digite o ano de início: ");
                    int anoInicio = scanner.nextInt();
                    System.out.print("Digite o ano de fim: ");
                    int anoFim = scanner.nextInt();
                    
                    int totalObitos = arvore.somarObitosPeriodo(anoInicio, anoFim);
                    System.out.println("Total de óbitos entre " + anoInicio + " e " + anoFim + ": " + totalObitos);
                    break;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
