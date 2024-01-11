package ClassesEleicoes;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Locale;

public class Candidato {
    private int cd_cargo;
    private int cd_situacao_candidato_tot;
    private int nr_candidato;
    private String nm_urna_candidato;
    private Partido part;
    private int nr_federacao;
    private int idade;
    private int cd_sit_tot_turno;
    private int cd_genero;
    private String nm_tipo_destinacao_votos;
    private int num_votos;

    public Candidato(int cd_cargo, int cd_situacao_candidato_tot, int nr_candidato, String nm_urna_candidato,
            Partido part, int nr_federacao, String dt_nascimento_str, String dt_eleicao_str, int cd_sit_tot_turno, int cd_genero, 
            String nm_tipo_destinacao_votos) {
        this.cd_cargo = cd_cargo;
        this.cd_situacao_candidato_tot = cd_situacao_candidato_tot;
        this.nr_candidato = nr_candidato;
        this.nm_urna_candidato = nm_urna_candidato;
        this.part = part;
        part.adicionaCandidatoPartido(this);
        this.nr_federacao = nr_federacao;
        this.cd_sit_tot_turno = cd_sit_tot_turno;
        this.cd_genero = cd_genero;
        this.nm_tipo_destinacao_votos = nm_tipo_destinacao_votos;
        this.num_votos = 0;

        // CÁLCULO DA IDADE DO CANDIDATO
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dt_eleicao = null;
        try{
            dt_eleicao = LocalDate.parse(dt_eleicao_str, dtf);
        }
        catch(DateTimeParseException e){
            System.out.println("Data da eleição passada no formato incorreto.\nDeve estar no seguinte formato: dd/MM/yyyy");
            System.exit(1);
        }
        LocalDate dt_nascimento = LocalDate.parse(dt_nascimento_str, dtf);
        Period periodo = Period.between(dt_nascimento, dt_eleicao);
        idade = periodo.getYears();

    }

    public int getCd_cargo() {
        return cd_cargo;
    }

    public int getCd_situacao_candidato_tot() {
        return cd_situacao_candidato_tot;
    }

    public int getNr_candidato() {
        return nr_candidato;
    }

    public String getNm_urna_candidato() {
        return nm_urna_candidato;
    }

    public Partido getPartido() {
        return part;
    }

    public int getNr_federacao() {
        return nr_federacao;
    }

    public int getIdade() {
        return idade;
    }

    public int getCd_sit_tot_turno() {
        return cd_sit_tot_turno;
    }

    public int getCd_genero() {
        return cd_genero;
    }

    public String getNm_tipo_destinacao_votos() {
        return nm_tipo_destinacao_votos;
    }

    public int getNum_votos() {
        return num_votos;
    }

    public void acrescentaNumVotosCandidato(int qnt){
        num_votos += qnt;
    }


    // COMPARATOR
    public static Comparator<Candidato> NumVotosComparator = new Comparator<Candidato>() {
        // Comparação do numero de votos
        public int compare(Candidato c1, Candidato c2) {
            int votos_c1 = c1.getNum_votos();
            int votos_c2 = c2.getNum_votos();
            // Retorna na ordem decrescente
            return Integer.compare(votos_c2, votos_c1);
        }
    };

    public static Comparator<Candidato> IdadeComparator = new Comparator<Candidato>() {
        // Comparação da idade
        public int compare(Candidato c1, Candidato c2) {
            int idade_c1 = c1.getIdade();
            int idade_c2 = c2.getIdade();
            // Retorna na ordem decrescente
            return Integer.compare(idade_c2, idade_c1);
        }
    };

    public static Comparator<Candidato> PartidoComparator = new Comparator<Candidato>() {
        // Comparação do número partidário do candidato
        public int compare(Candidato c1, Candidato c2) {
            int nr_p_c1 = c1.getPartido().getNr();
            int nr_p_c2 = c2.getPartido().getNr();
            // Retorna na ordem crescente
            return Integer.compare(nr_p_c1, nr_p_c2);
        }
    };


    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        nf.setGroupingUsed(true);

        if(getNum_votos() < 2){
            return getNm_urna_candidato() + " (" + getPartido().getSigla() + ", " + nf.format(getNum_votos()) + " voto)";
        }
        return getNm_urna_candidato() + " (" + getPartido().getSigla() + ", " + nf.format(getNum_votos()) + " votos)";
    }
}
