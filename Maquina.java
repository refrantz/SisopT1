import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Maquina {

    public static void main (String args[]){

        int acc = 0;
        List<Processo> processos = new ArrayList<Processo>();
        List<String> regiaoDados = new ArrayList<String>();
        //List<String> codigo = new ArrayList<String>(); talvez seja melhor tratar as linhas de codigo individualmente por processo
        Map<String, Integer> dados = new LinkedHashMap<String, Integer>();
        Map<String, Integer> labels = new HashMap<String, Integer>();

        // ainda precisa mudar a estrutura para trabalhar com multiplos programas, faz parte da interface ( precisamos decidir como fazer isso ).
        try {

            BufferedReader br = new BufferedReader(new FileReader("programas/prog1.txt"));

            //Criando o processo e adicionando ele na lista de processos - importante ter uma lista de processos pro escalonador conseguir decidir qual rodar depois
            Processo processo1 = new Processo(0);
            processos.add(processo1);

            String linha = br.readLine().strip().toUpperCase();

            while(linha != null){

                if(linha.equals(".CODE")){
                    linha = br.readLine().strip().toUpperCase();
                    while(!linha.equals(".ENDCODE")){
                        processo1.codigo.add(linha);
                        linha = br.readLine().strip().toUpperCase();
                    }
                }

                if(linha.equals(".DATA")){
                    linha = br.readLine().strip().toUpperCase();
                    while(!linha.equals(".ENDDATA")){
                        regiaoDados.add(linha);
                        linha = br.readLine().strip().toUpperCase();
                    }
                }

                linha = br.readLine();
                if(linha != null){
                    linha = linha.strip().toUpperCase();
                }

            }

            br.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        for (String linha : regiaoDados){
            String[] input = linha.split(" ");
            dados.put(input[0], Integer.parseInt(input[1]));
        }

        //mudar para ser feito pra todos os processos, se um programa perde processador e volta depois ele deve voltar na mesma linha de antes, entao o PC pode ficar guardado na classe Processo.
        for (int pc = 0; pc < processos.get(0).codigo.size(); pc++){

            String[] input = processos.get(0).codigo.get(pc).split(" ");
            String op = input[0];
            String param = input[1];

            if(op.contains(":")){
                labels.put(op.substring(0, op.length()-1), pc-1);
                op = input[1];
                param = input[2];
            }

            if(param.contains("#")){
                param = "" + Integer.parseInt(param.substring(1));
            }
            
            if (op.equals("ADD")){
                if(dados.containsKey(param)){ //esse IF ELSE tem que estar para todas as operações aritmeticas - talvez exista um jeito mais elegante de fazer isso
                    acc += dados.get(param);
                }else{
                    acc += Integer.parseInt(param);
                }
            }else if(op.equals("SUB")){
                acc -= Integer.parseInt(param);
            }else if(op.equals("MULT")){
                acc *= Integer.parseInt(param);
            }else if(op.equals("DIV")){
                acc /= Integer.parseInt(param);

            }else if(op.equals("LOAD")){
                if(dados.containsKey(param)){
                    acc = dados.get(param);
                }else{
                    acc = Integer.parseInt(param);
                }
            }else if(op.equals("STORE")){
                dados.replace(param, acc);

            }else if(op.equals("BRANY")){
                pc = labels.get(param);
            }else if(op.equals("BRPOS") && acc > 0){
                pc = labels.get(param);
            }else if(op.equals("BRZERO") && acc == 0){
                pc = labels.get(param);
            }else if(op.equals("BRNEG") && acc < 0){
                pc = labels.get(param);

            }else if(op.equals("SYSCALL")){
                int paramC = Integer.parseInt(param);

                if(paramC == 0){
                    System.exit(0);
                }else if(paramC == 1){
                    //System.out.println(acc);
                    int intervalo = ThreadLocalRandom.current().nextInt(0, 21);
                }
            }
            System.out.println(acc);
        }
    }
}