package Leitura_e_Escrita;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ClassesEleicoes.Candidato;
import ClassesEleicoes.Eleicao;
import ClassesEleicoes.Partido;

public class Relatorio {
    public static void ImprimeRelatorio(Eleicao eleicao){
        numVagasRelatorio(eleicao);
        EleitosRelatorio(eleicao);
        MaisVotadosRelatorio(eleicao);
        TerianSidoEleitosRelatorio(eleicao);
        EleitosQueSeBeneficiaramRelatorio(eleicao);
        VotacaoPartidosRelatorio(eleicao);
        PrimeiroEUltimoDoPartidoRelatorio(eleicao);
        EleitosFaixaEtariaRelatorio(eleicao);
        EleitosGeneroRelatorio(eleicao);
        QuantidadeVotosRelatorio(eleicao);
    }


    private static void numVagasRelatorio(Eleicao eleicao){
        System.out.println("Número de vagas: " + eleicao.getCandidatosEleitos().size() + "\n");
    }


    private static void EleitosRelatorio(Eleicao eleicao){
        if(eleicao.getTipo_cargo() == 6){
            System.out.println("Deputados federais eleitos:");
        }
        else{
            System.out.println("Deputados estaduais eleitos:");
        }

        List<Candidato> lista_cand_eleitos = new ArrayList<Candidato>(eleicao.getCandidatosEleitos().values());
        // ORDENAÇÃO DA LISTA DE ELEITOS
        Collections.sort(lista_cand_eleitos, Candidato.IdadeComparator); // Ordem decrescente de idade
        Collections.sort(lista_cand_eleitos, Candidato.NumVotosComparator); // Ordem decrescente de votos

        int i = 1;
        for(Candidato c: lista_cand_eleitos){
            if(c.getNr_federacao() == -1)
                System.out.println(i + " - " + c);
            else
                System.out.println(i + " - *" + c);
            i++;
        }
    }


    private static void MaisVotadosRelatorio(Eleicao eleicao){
        System.out.println("\n" + "Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        List<Candidato> lista_cand = new ArrayList<Candidato>(eleicao.getCandidatos().values());
        // ORDENAÇÃO DA LISTA DE CANDIDATOS DEFERIDOS
        Collections.sort(lista_cand, Candidato.IdadeComparator); // Ordem decrescente de idade
        Collections.sort(lista_cand, Candidato.NumVotosComparator); // Ordem decrescente de votos
        
        int i = 1;
        int num_vagas = eleicao.getCandidatosEleitos().size();
        for(Candidato c: lista_cand){
            if(c.getNr_federacao() == -1)
                System.out.println(i + " - " + c);
            else
                System.out.println(i + " - *" + c);
            if(i == num_vagas)
                break;
            i++;
        }
    }


    private static void TerianSidoEleitosRelatorio(Eleicao eleicao){
        System.out.println("\n" + "Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");

        List<Candidato> lista_cand = new ArrayList<Candidato>(eleicao.getCandidatos().values());
        // ORDENAÇÃO DA LISTA DE CANDIDATOS DEFERIDOS
        Collections.sort(lista_cand, Candidato.IdadeComparator); // Ordem decrescente de idade
        Collections.sort(lista_cand, Candidato.NumVotosComparator); // Ordem decrescente de votos
        
        List<Candidato> lista_cand_eleitos = new ArrayList<Candidato>(eleicao.getCandidatosEleitos().values());

        int i = 1;
        int num_vagas = eleicao.getCandidatosEleitos().size();
        for(Candidato c: lista_cand){
            // Se está entre os X mais votados (X = num_vagas), porém não está na lista de eleitos
            if(!lista_cand_eleitos.contains(c)){
                if(c.getNr_federacao() == -1)
                    System.out.println(i + " - " + c);
                else
                    System.out.println(i + " - *" + c);
            }
            if(i == num_vagas)
                break;
            i++;
        }
    }


    private static void EleitosQueSeBeneficiaramRelatorio(Eleicao eleicao){
        System.out.println("\n" + "Eleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");

        List<Candidato> lista_cand = new ArrayList<Candidato>(eleicao.getCandidatos().values());
        // ORDENAÇÃO DA LISTA DE CANDIDATOS DEFERIDOS
        Collections.sort(lista_cand, Candidato.IdadeComparator); // Ordem decrescente de idade
        Collections.sort(lista_cand, Candidato.NumVotosComparator); // Ordem decrescente de votos
        
        List<Candidato> lista_cand_eleitos = new ArrayList<Candidato>(eleicao.getCandidatosEleitos().values());
        Collections.sort(lista_cand_eleitos, Candidato.IdadeComparator);
        Collections.sort(lista_cand_eleitos, Candidato.NumVotosComparator);

        int num_vagas = eleicao.getCandidatosEleitos().size();
        for(Candidato c: lista_cand_eleitos){
            // Se não está entre os X mais votados (X = num_vagas)
            if(lista_cand.indexOf(c) >= num_vagas){
                if(c.getNr_federacao() == -1)
                    System.out.println(lista_cand.indexOf(c)+1 + " - " + c);
                else
                    System.out.println(lista_cand.indexOf(c)+1 + " - *" + c);
            }
        }
    }


    private static void VotacaoPartidosRelatorio(Eleicao eleicao){
        System.out.println("\n" + "Votação dos partidos e número de candidatos eleitos:");

        List<Partido> partidos = new ArrayList<Partido>(eleicao.getPartidos().values());
        // ORDENAÇÃO DA LISTA DE PARTIDOS
        Collections.sort(partidos, Partido.NumPartidoComparator); // Ordem crescente de número do partido
        Collections.sort(partidos, Partido.TotalVotosComparator); // Ordem decrescente de total de votos

        // Formatação do número com agrupamento de três digitos usando o .
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        nf.setGroupingUsed(true);

        int i = 1;
        for(Partido p: partidos){
            // Obtenção do número de eleitos no partido
            int qnt_eleitos = 0;
            for(Candidato c: p.getCandidatos_partido()){
                if(eleicao.getCandidatosEleitos().containsKey(c.getNr_candidato()))
                    qnt_eleitos++;
            }

            String votos;
            if(p.getTotalVotosPartido() < 2)
                votos = " voto (";
            else
                votos = " votos (";

            String nominais;
            if(p.getVotos_nominais() < 2)
                nominais = " nominal e ";
            else
                nominais = " nominais e ";

            String cand_eleitos;
            if(qnt_eleitos < 2)
                cand_eleitos = " candidato eleito";
            else
                cand_eleitos = " candidatos eleitos";

            System.out.println(i + " - " + p + ", " + nf.format(p.getTotalVotosPartido()) + votos + nf.format(p.getVotos_nominais()) +
                                nominais + nf.format(p.getVotos_legenda()) + " de legenda), " + qnt_eleitos + cand_eleitos);
            i++;
        }
    }


    private static void PrimeiroEUltimoDoPartidoRelatorio(Eleicao eleicao){
        System.out.println("\n" + "Primeiro e último colocados de cada partido:");

        List<Candidato> lista_cand = new ArrayList<Candidato>(eleicao.getCandidatos().values());
        // ORDENAÇÃO DA LISTA DE CANDIDADOS DEFERIDOS
        Collections.sort(lista_cand, Candidato.IdadeComparator); // Ordem decrescente de idade
        Collections.sort(lista_cand, Candidato.PartidoComparator); // Ordem crescente de número do partido do candidato
        Collections.sort(lista_cand, Candidato.NumVotosComparator); // Ordem decrescente de número de votos
        
        // Lista auxiliar para guardar os partidos já printados
        List<Partido> lista_partidos_aux = new ArrayList<Partido>();

        int i = 1;
        for(Candidato c: lista_cand){
            // Se o partido do candidato ainda não foi printado
            if(!lista_partidos_aux.contains(c.getPartido())){
                List<Candidato> lista_cand_partido = c.getPartido().getCandidatos_partido();
                // ORDENAÇÃO DA LISTA DE CANDIDATOS DO PARTIDO
                Collections.sort(lista_cand_partido, Candidato.IdadeComparator); // Ordem decrescente de idade
                Collections.sort(lista_cand_partido, Candidato.NumVotosComparator); // Ordem crescente de número de votos

                // Se o primeiro candidato da lista tem número de votos diferente de 0
                if(lista_cand_partido.get(0).getNum_votos() != 0){
                    int a = 1;
                    Candidato ultimo = lista_cand_partido.get(lista_cand_partido.size()-1);

                    // Obtenção do candidato menor colocado na lista que seja deferido 
                    while(true){
                        ultimo = lista_cand_partido.get(lista_cand_partido.size()-a);
                        if(ultimo.getCd_situacao_candidato_tot() == 2 || ultimo.getCd_situacao_candidato_tot() == 16)
                            break;
                        a++;
                    }

                    // Formatação do número com agrupamento de três digitos usando o .
                    NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
                    nf.setGroupingUsed(true);

                    System.out.print(i + " - " + c.getPartido() + ", " + c.getNm_urna_candidato() + " (" + 
                                        c.getNr_candidato() + ", " + nf.format(c.getNum_votos()) + " votos) / ");

                    if(ultimo.getNum_votos() < 2){
                        System.out.println(ultimo.getNm_urna_candidato() + " (" + ultimo.getNr_candidato() + ", " + 
                                            nf.format(ultimo.getNum_votos()) + " voto)");
                    }
                    else{
                        System.out.println(ultimo.getNm_urna_candidato() + " (" + ultimo.getNr_candidato() + ", " + 
                                            nf.format(ultimo.getNum_votos()) + " votos)");
                    }
                }

                lista_partidos_aux.add(c.getPartido());
                i++;
            }
        }
    }


    private static void EleitosFaixaEtariaRelatorio(Eleicao eleicao){
        System.out.println("\n" + "Eleitos, por faixa etária (na data da eleição):");
        
        List<Candidato> lista_cand_eleitos = new ArrayList<Candidato>(eleicao.getCandidatosEleitos().values());

        // Obtenção da quantidade de candidatos eleitos em cada faixa etária
        int menor30 = 0, entre30_40 = 0, entre40_50 = 0, entre50_60 = 0, maior60 = 0;
        for(Candidato c: lista_cand_eleitos){
            int idade = c.getIdade();
            
            if(idade < 30)
                menor30++;
            else if(idade >= 30 && idade < 40)
                entre30_40++;
            else if(idade >= 40 && idade < 50)
                entre40_50++;
            else if(idade >= 50 && idade < 60)
                entre50_60++;
            else
                maior60++;
        }

        int qnt_vagas = eleicao.getCandidatosEleitos().size();

        // Formatação do número para 2 algarismos fixos após a vírgula
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        System.out.println("      Idade < 30: " + menor30 + " (" + nf.format((menor30/(float)qnt_vagas)*100) + 
                            "%)\n30 <= Idade < 40: " + entre30_40 + " (" + nf.format((entre30_40/(float)qnt_vagas)*100) + 
                            "%)\n40 <= Idade < 50: " + entre40_50 + " (" + nf.format((entre40_50/(float)qnt_vagas)*100) + 
                            "%)\n50 <= Idade < 60: " + entre50_60 + " (" + nf.format((entre50_60/(float)qnt_vagas)*100) +
                            "%)\n60 <= Idade     : " + maior60 + " (" + nf.format((maior60/(float)qnt_vagas)*100) + "%)");
    }


    private static void EleitosGeneroRelatorio(Eleicao eleicao){
        System.out.println("\n" + "Eleitos, por gênero:");
        
        List<Candidato> lista_cand_eleitos = new ArrayList<Candidato>(eleicao.getCandidatosEleitos().values());

        // Obtenção da quantidade de candidatos eleitos de cada gênero
        int feminino = 0, masculino = 0;
        for(Candidato c: lista_cand_eleitos){
            int genero = c.getCd_genero();

            if(genero == 2)
                masculino++;
            else if(genero == 4)
                feminino++;
        }

        int qnt_vagas = eleicao.getCandidatosEleitos().size();

        // Formatação do número para 2 algarismos fixos após a vírgula
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);

        System.out.println("Feminino:  " + feminino + " (" + nf.format((feminino/(float)qnt_vagas)*100) + "%)\nMasculino: " +
                            masculino + " (" + nf.format((masculino/(float)qnt_vagas)*100) + "%)");
    }


    private static void QuantidadeVotosRelatorio(Eleicao eleicao){
        // Formatação do número com agrupamento de três digitos usando o .
        NumberFormat nf_votos = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        nf_votos.setGroupingUsed(true);

        // Formatação do número para 2 algarismos fixos após a vírgula
        NumberFormat nf_porcentagem = NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
        nf_porcentagem.setMinimumFractionDigits(2);
        nf_porcentagem.setMaximumFractionDigits(2);

        int total_votos = eleicao.getTotalVotosEleicao();
        int nominais = eleicao.getTotal_votos_nominais();
        int legenda = eleicao.getTotal_votos_legenda();

        System.out.println("\nTotal de votos válidos:    " + nf_votos.format(total_votos) + "\nTotal de votos nominais:   " 
                            + nf_votos.format(nominais) + " (" + nf_porcentagem.format((nominais/(float)total_votos)*100) +
                            "%)\nTotal de votos de legenda: " + nf_votos.format(legenda) + " (" +
                            nf_porcentagem.format((legenda/(float)total_votos)*100) + "%)\n");
    }
}
