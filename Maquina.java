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

        // ainda precisa mudar a estrutura para trabalhar com multiplos programas, faz parte da interface ( precisamos decidir como fazer isso ).
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
                        System.out.println(linha);
                        while(!linha.equals(".ENDDATA")){
                            String[] input = linha.split(" ");
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
            for (; processo.pc < processo.codigo.size(); processo.pc++){

                String[] input = processo.codigo.get(processo.pc).split(" ");
                String op = input[0].toUpperCase();
                String param = input[1].toUpperCase();
    
                if(op.contains(":")){
                    processo.labels.put(op.substring(0, op.length()-1), processo.pc-1);
                    op = param;
                    param = input[2].toUpperCase();
                }
    
                if(param.contains("#")){
                    param = "" + Integer.parseInt(param.substring(1));
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
                        //System.out.println(acc);
                        int intervalo = ThreadLocalRandom.current().nextInt(0, 21);
                    }
                }
                System.out.println(acc);
            }
        }
        
    }
}