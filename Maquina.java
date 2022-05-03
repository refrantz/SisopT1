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
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Maquina {
    static List<Processo> processos = new ArrayList<Processo>();

    public static void main (String args[]){

        int acc = 0;
        int tempo = 0;
        int arrival = 0;
        lerProcessos();
        Processo processo = processos.get(1); //só por enquanto - isso é pra mudar quando o escalonador nascer

        //deve fazer parte do escalonador -------------
        for (; processo.pc < processo.codigo.size(); processo.pc++){

                String[] input = processo.codigo.get(processo.pc).split("\\s+");
                String op = input[0].toUpperCase();

                if (input.length <= 1){
                    processo.pc++;
                    input = processo.codigo.get(processo.pc).split("\\s+");
                }

                op = input[0].toUpperCase();
                String param = input[1].toUpperCase();
    
                if(op.contains(":")){
                    op = param;
                    param = input[2].toUpperCase();
                }
    
                if(param.contains("#")){
                    param = param.substring(1);
                }else if(processo.dados.containsKey(param)){
                    param = "" + processo.dados.get(param);
                }

                if (op.equals("ADD")){
                    acc += Integer.parseInt(param);
                }else if(op.equals("SUB")){
                    acc -= Integer.parseInt(param);
                }else if(op.equals("MULT")){
                    acc *= Integer.parseInt(param);
                }else if(op.equals("DIV")){
                    acc /= Integer.parseInt(param);
    
                }else if(op.equals("LOAD")){
                    if(processo.dados.containsKey(param)){
                        acc = processo.dados.get(param);
                    }else{
                        acc = Integer.parseInt(param);
                    }
                }else if(op.equals("STORE")){
                    processo.dados.replace(param, acc);
    
                }else if(op.equals("BRANY")){
                    processo.pc = processo.labels.get(param);
                }else if(op.equals("BRPOS") && acc > 0){
                    processo.pc = processo.labels.get(param);
                }else if(op.equals("BRZERO") && acc == 0){
                    processo.pc = processo.labels.get(param);
                }else if(op.equals("BRNEG") && acc < 0){
                    processo.pc = processo.labels.get(param);
    
                }else if(op.equals("SYSCALL")){
                    int paramC = Integer.parseInt(param);
    
                    if(paramC == 0){
                        System.exit(0);
                    }else if(paramC == 1){
                        System.out.println(acc);
                        int intervalo = ThreadLocalRandom.current().nextInt(0, 21);
                    }else if(paramC == 2){
                        Scanner sc= new Scanner(System.in);
                        System.out.print("Insira um numero: ");
                        acc = sc.nextInt();
                        int intervalo = ThreadLocalRandom.current().nextInt(0, 21);
                    }
                }
                tempo++;
                //System.out.println(acc);
            } //deve fazer parte do escalonador ------------- fim
    }

    public static void lerProcessos() {
        try {

            File pasta = new File("programas");

            for(File txt : pasta.listFiles()){

                BufferedReader br = new BufferedReader(new FileReader(txt));

                //Criando o processo e adicionando ele na lista de processos - importante ter uma lista de processos pro escalonador conseguir decidir qual rodar depois
                Processo processo = new Processo(0);
                processos.add(processo);

                String linha = br.readLine().strip().toUpperCase();

                while(linha != null){

                    if(linha.equals(".CODE")){
                        linha = br.readLine().strip().toUpperCase();
                        while(!linha.equals(".ENDCODE")){
                            processo.codigo.add(linha);
                            linha = br.readLine().strip().toUpperCase();
                        }
                    }

                    if(linha.equals(".DATA")){
                        linha = br.readLine().strip().toUpperCase();
                        while(!linha.equals(".ENDDATA")){
                            String[] input = linha.split("\\s+");
                            processo.dados.put(input[0], Integer.parseInt(input[1]));
                            linha = br.readLine().strip().toUpperCase();
                        }
                    }

                    linha = br.readLine();
                    if(linha != null){
                        linha = linha.strip().toUpperCase();
                    }

                }
                br.close();
            }

        }catch (Exception e) {

                e.printStackTrace();

        }

        for(Processo processo : processos){
            for (int pc = 0; pc < processo.codigo.size(); pc++){
                String[] input = processo.codigo.get(pc).split("\\s+");
                String op = input[0].toUpperCase();
    
                if (input.length <= 1){
                    processo.labels.put(op.substring(0, op.length()-1), pc);
                    pc++;
                    input = processo.codigo.get(pc).split("\\s+");
                }
    
                op = input[0].toUpperCase();
    
                if(op.contains(":")){
                    processo.labels.put(op.substring(0, op.length()-1), processo.pc-1);
                }
            }
        }
    }
}