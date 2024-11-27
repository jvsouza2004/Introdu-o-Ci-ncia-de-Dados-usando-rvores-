import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorCSV {

    public static void carregarCSV(String caminhoArquivo, AVL<RegistroObitos> arvore) {
        String linha;
        String divisor = ";";  // o separador no arquivo CSV

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();  // Ignora o cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(divisor);
                int ano = Integer.parseInt(dados[0]);
                int obitosMenoresDe1Ano = Integer.parseInt(dados[1]);  // Considerando que essa é a coluna de interesse
                RegistroObitos registro = new RegistroObitos(ano, obitosMenoresDe1Ano);
                arvore.inserir(registro);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
