import ClassesEleicoes.Eleicao;
import Leitura_e_Escrita.Leitura;
import Leitura_e_Escrita.Relatorio;

public class App {
    public static void main(String[] args){
        // Verificação da passagem de parâmetros 
        if((!args[0].contentEquals("--federal") && !args[0].contentEquals("--estadual")) || args.length != 4){
            System.out.println("Parâmetros passados de forma incorreta.\nEles devem estar no seguinte formato: " +
                               "<opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votação> <data>");
            System.exit(1);
        }

        // Guarda a opção de cargo passado como parâmetro
        int tipo_cargo = 0;
        if(args[0].contentEquals("--federal")){
            tipo_cargo = 6;
        }
        else{
            tipo_cargo = 7;
        }

        Eleicao eleicao = new Eleicao(tipo_cargo);

        Leitura.RealizaLeitura(eleicao, args[1], args[2], args[3]);
        Relatorio.ImprimeRelatorio(eleicao);
    }
}
