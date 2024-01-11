package ClassesEleicoes;
import java.util.ArrayList;
import java.util.Comparator;

public class Partido {
    private int nr;
    private String sigla;
    private ArrayList<Candidato> candidatos_partido = new ArrayList<Candidato>();
    private int votos_nominais;
    private int votos_legenda;

    public Partido(int nr, String sigla) {
        this.nr = nr;
        this.sigla = sigla;
        votos_nominais = 0;
        votos_legenda = 0;
    }

    public void adicionaCandidatoPartido(Candidato cand){
        this.candidatos_partido.add(cand);
    }

    public ArrayList<Candidato> getCandidatos_partido() {
        return candidatos_partido;
    }

    public int getNr() {
        return nr;
    }

    public String getSigla() {
        return sigla;
    }

    public int getVotos_nominais() {
        return votos_nominais;
    }

    public int getVotos_legenda() {
        return votos_legenda;
    }

    public int getTotalVotosPartido(){
        return votos_nominais + votos_legenda;
    }

    public void acrescentaNumVotosNominaisPartido(int qnt){
        votos_nominais += qnt;
    }

    public void acrescentaNumVotosLegendaPartido(int qnt){
        votos_legenda += qnt;
    }

    // Comparator
    public static Comparator<Partido> NumPartidoComparator = new Comparator<Partido>() {
        // Comparação do numero dos partidos
        public int compare(Partido p1, Partido p2) {
            int nr_p1 = p1.getNr();
            int nr_p2 = p2.getNr();
            // Retorna na ordem crescente
            return Integer.compare(nr_p1, nr_p2);
        }
    };

    public static Comparator<Partido> TotalVotosComparator = new Comparator<Partido>() {
        // Comparação do total de votos dos partidos
        public int compare(Partido p1, Partido p2) {
            int votos_p1 = p1.getTotalVotosPartido();
            int votos_p2 = p2.getTotalVotosPartido();
            // Retorna na ordem decrescente
            return Integer.compare(votos_p2, votos_p1);
        }
    };

    @Override
    public String toString() {
        return getSigla() + " - " + getNr();
    }


}
