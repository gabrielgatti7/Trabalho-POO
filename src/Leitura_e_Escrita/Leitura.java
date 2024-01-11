package Leitura_e_Escrita;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import ClassesEleicoes.Candidato;
import ClassesEleicoes.Eleicao;
import ClassesEleicoes.Partido;

public class Leitura {
    public static void RealizaLeitura(Eleicao eleicao, String arg1, String arg2, String arg3){
        // Mapa de candidatos com NM_TIPO_DESTINACAO_VOTOS = "Válido (legenda)"
        Map<Integer, Candidato> candidatos_destinacao_legenda = new HashMap<Integer, Candidato>();

        LeArquivoCandidatos(candidatos_destinacao_legenda, arg1, arg3, eleicao);
        LeArquivoVotos(candidatos_destinacao_legenda, arg2, arg3, eleicao);      
    }


    private static void LeArquivoCandidatos(Map<Integer, Candidato> candidatos_destinacao_legenda, String arg1, String arg3, 
                                            Eleicao eleicao){
        try{
            InputStream fis       = new FileInputStream(arg1);
            InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader br     = new BufferedReader(isr);

            String linha;
            String[] campos_coluna;
            linha = br.readLine(); // Leitura da linha com nomes das colunas
            linha = br.readLine();
            while (linha != null) {
                campos_coluna = linha.split(";");

                // CD_CARGO = 13  --  CD_SITUACAO_CANDIDADO_TOT = 68  --  NR_CANDIDATO = 16  --  NM_URNA_CANDIDATO = 18
                // NR_PARTIDO = 27  --  SG_PARTIDO = 28  --  NR_FEDERACAO = 30  --  DT_NASCIMENTO = 42
                // CD_SIT_TOT_TURNO = 56  --  CD_GENERO = 45  --  NM_TIPO_DESTINACAO_VOTOS = 67
                
                int cd_cargo                    = Integer.parseInt(campos_coluna[13].replaceAll("\"", ""));
                int cd_situacao_candidato_tot   = Integer.parseInt(campos_coluna[68].replaceAll("\"", ""));
                int nr_candidato                = Integer.parseInt(campos_coluna[16].replaceAll("\"", ""));
                int nr_partido                  = Integer.parseInt(campos_coluna[27].replaceAll("\"", ""));
                int nr_federacao                = Integer.parseInt(campos_coluna[30].replaceAll("\"", ""));
                int cd_sit_tot_turno            = Integer.parseInt(campos_coluna[56].replaceAll("\"", ""));
                int cd_genero                   = Integer.parseInt(campos_coluna[45].replaceAll("\"", ""));
                String nm_urna_candidato        = campos_coluna[18].replaceAll("\"", "");
                String sg_partido               = campos_coluna[28].replaceAll("\"", "").trim();
                String dt_nascimento            = campos_coluna[42].replaceAll("\"", "").trim();
                String nm_tipo_destinacao_votos = campos_coluna[67].replaceAll("\"", "").trim();


                // CRIAÇÃO DO PARTIDO
                Partido part_candidato = null;
                // Se o partido ja foi criado
                if(eleicao.getPartidos().containsKey(nr_partido)){
                    part_candidato = eleicao.getPartidos().get(nr_partido);
                }
                // Se o partido ainda não foi criado
                else{
                    part_candidato = new Partido(nr_partido, sg_partido);
                    eleicao.adicionaPartidoEleicao(part_candidato);
                }


                // CRIAÇÃO DO CANDIDATO
                if((eleicao.getTipo_cargo() == cd_cargo)){
                    // Se os votos no candidato vão para a legenda
                    if(nm_tipo_destinacao_votos.contentEquals("Válido (legenda)")){
                        Candidato cand = new Candidato(cd_cargo, cd_situacao_candidato_tot, nr_candidato, nm_urna_candidato, 
                                                       part_candidato, nr_federacao, dt_nascimento, arg3, cd_sit_tot_turno, 
                                                       cd_genero, nm_tipo_destinacao_votos);
                        candidatos_destinacao_legenda.put(nr_candidato, cand);
                    }
                    // Se tem candidatura deferida
                    else if(cd_situacao_candidato_tot == 2 || cd_situacao_candidato_tot == 16){
                        Candidato cand = new Candidato(cd_cargo, cd_situacao_candidato_tot, nr_candidato, nm_urna_candidato, 
                                                       part_candidato, nr_federacao, dt_nascimento, arg3, cd_sit_tot_turno, 
                                                       cd_genero, nm_tipo_destinacao_votos);
                        eleicao.adicionaCandidatoEleicao(cand);

                        // Se o candidato foi eleito
                        if(cd_sit_tot_turno == 2 || cd_sit_tot_turno == 3){
                            eleicao.adicionaCandidatoEleitoEleicao(cand);
                        }
                    }
                }
                linha = br.readLine();
            }

            br.close();
            isr.close();
            fis.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo de consulta de candidatos.");
            System.exit(1);
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Esquema de codificações usado para o arquivo de consulta de candidatos não é suportado.");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Erro na leitura da linha do arquivo de consulta de candidatos");
            System.exit(1);
        }
    }


    private static void LeArquivoVotos(Map<Integer, Candidato> candidatos_destinacao_legenda, String arg2, String arg3, Eleicao eleicao){
        try{
            InputStream fis       = new FileInputStream(arg2);
            InputStreamReader isr = new InputStreamReader(fis, "ISO-8859-1");
            BufferedReader br     = new BufferedReader(isr);

            String linha;
            String[] campos_coluna;
            linha = br.readLine(); // Leitura da linha com nomes das colunas
            linha = br.readLine();
            while (linha != null) {
                campos_coluna = linha.split(";");
            
                // CD_CARGO = 17 -- NR_VOTAVEL = 19 -- QT_VOTOS = 21

                int cd_cargo   = Integer.parseInt(campos_coluna[17].replaceAll("\"", ""));
                int nr_votavel = Integer.parseInt(campos_coluna[19].replaceAll("\"", ""));
                int qt_votos   = Integer.parseInt(campos_coluna[21].replaceAll("\"", ""));


                // Se o voto condiz com a opção de cargo e não é nulo
                if((eleicao.getTipo_cargo() == cd_cargo) && (nr_votavel < 95 || nr_votavel > 98)){
                    
                    // Se eh voto no partido
                    if(nr_votavel > 9 && nr_votavel < 100){
                        if(eleicao.getPartidos().containsKey(nr_votavel)){
                            eleicao.getPartidos().get(nr_votavel).acrescentaNumVotosLegendaPartido(qt_votos);
                            eleicao.acrescentaTotalVotosLegendaEleicao(qt_votos);
                        }
                    }
                    // Se eh voto em candidato
                    else if((eleicao.getTipo_cargo() == 6 && (nr_votavel > 999 && nr_votavel < 10000)) || 
                            (eleicao.getTipo_cargo() == 7 && (nr_votavel > 9999 && nr_votavel < 100000))){
                        
                        Candidato cand = eleicao.getCandidatos().get(nr_votavel);

                        // Se o candidato votado não tem candidatura deferida e/ou se votos sao redirecionados para legenda
                        if(cand == null){
                            // Se votos vao para o partido
                            if(candidatos_destinacao_legenda.containsKey(nr_votavel)){
                                candidatos_destinacao_legenda.get(nr_votavel).getPartido().acrescentaNumVotosLegendaPartido(qt_votos);
                                eleicao.acrescentaTotalVotosLegendaEleicao(qt_votos);
                            }
                        }
                        // Se tem candidatura deferida e votos vao para o candidato
                        else{
                            cand.acrescentaNumVotosCandidato(qt_votos);
                            cand.getPartido().acrescentaNumVotosNominaisPartido(qt_votos);
                            eleicao.acrescentaTotalVotosNominaisEleicao(qt_votos);
                        }
                    
                    }
                    
                }
                linha = br.readLine();
            }
            br.close();
            isr.close();
            fis.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Erro ao abrir o arquivo de votos.");
            System.exit(1);
        }
        catch (UnsupportedEncodingException e) {
            System.out.println("Esquema de codificações usado para o arquivo de votos não é suportado.");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Erro na leitura da linha do arquivo de votos");
            System.exit(1);
        }
    }
}
