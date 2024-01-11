package ClassesEleicoes;
import java.util.HashMap;
import java.util.Map;

public class Eleicao {
    private int tipo_cargo; // 6 = Federal --- 7 = Estadual 
    private Map<Integer, Candidato> candidatos = new HashMap<Integer, Candidato>(); // Candidatos com candidatura deferida
    private Map<Integer, Candidato> candidatos_eleitos = new HashMap<Integer, Candidato>();
    private Map<Integer, Partido> partidos = new HashMap<Integer, Partido>();
    private int total_votos_nominais;
    private int total_votos_legenda;

    public Eleicao(int tipo_cargo){
        this.tipo_cargo = tipo_cargo;
        total_votos_nominais = 0;
        total_votos_legenda = 0;
    }

    public void adicionaCandidatoEleicao(Candidato cand){
        candidatos.put(cand.getNr_candidato(), cand);
    }

    public void adicionaPartidoEleicao(Partido part){
        partidos.put(part.getNr(), part);
    }

    public void adicionaCandidatoEleitoEleicao(Candidato cand){
        candidatos_eleitos.put(cand.getNr_candidato(), cand);
    }

    public void acrescentaTotalVotosNominaisEleicao(int qnt){
        total_votos_nominais += qnt;
    }

    public void acrescentaTotalVotosLegendaEleicao(int qnt){
        total_votos_legenda += qnt;
    }

    public Map<Integer, Candidato> getCandidatos() {
        return candidatos;
    }

    public Map<Integer, Candidato> getCandidatosEleitos() {
        return candidatos_eleitos;
    }

    public Map<Integer, Partido> getPartidos() {
        return partidos;
    }

    public int getTotal_votos_nominais() {
        return total_votos_nominais;
    }

    public int getTotal_votos_legenda() {
        return total_votos_legenda;
    }

    public int getTotalVotosEleicao(){
        return total_votos_legenda + total_votos_nominais;
    }

    public int getTipo_cargo() {
        return tipo_cargo;
    }

}
