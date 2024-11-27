public class RegistroObitos implements Comparable<RegistroObitos> {
    private int ano;
    private int obitosMenoresDe1Ano;

    public RegistroObitos(int ano, int obitosMenoresDe1Ano) {
        this.ano = ano;
        this.obitosMenoresDe1Ano = obitosMenoresDe1Ano;
    }

    public int getAno() {
        return ano;
    }

    public int getObitosMenoresDe1Ano() {
        return obitosMenoresDe1Ano;
    }

    public void somarObitos(int obitos) {
        this.obitosMenoresDe1Ano += obitos;
    }

    @Override
    public int compareTo(RegistroObitos outro) {
        return Integer.compare(this.ano, outro.ano);  // comparação pelo ano
    }

    @Override
    public String toString() {
        return "Ano: " + ano + ", Óbitos menores de 1 ano: " + obitosMenoresDe1Ano;
    }
}
