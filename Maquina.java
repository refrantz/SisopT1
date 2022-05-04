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
    static int acc;
    static int tempo;
    static int arrival;
    static List<Processo> processos = new ArrayList<Processo>();
    static Scanner teclado;
    static boolean continua = true;

    public static void main (String args[]){

        acc = 0;
        tempo = 0;
        arrival = 0;
        teclado = new Scanner(System.in);
        leProcessos();
        Escalonador escalonador = new Escalonador(processos, "PrioridadeSemPreempcao");

        while(continua){

            Processo processo = escalonador.DecideProximoProcessoARodar(tempo);

            if(processo != null){
                executaProcessoIntrucao(processo);
                System.out.println("Processo executado: " + processo.pid +" | no seu pc: " + processo.pc + "| No tempo: " + tempo);
                processo.pc++;
            }else{
                System.out.println("Nenhum processo ready | No tempo: " + tempo);
            }

            for(Processo aux : processos){
                if(aux.waitingTime > 0){
                    aux.waitingTime--;
                }
                if(aux.waitingTime == 0){
                    aux.estado = Processo.Estado.PRONTO;
                }
                
                if(aux.estado == Processo.Estado.FINALIZADO){
                    continua = false;
                }else{
                    continua = true;
                }
            }

            tempo++;
        }

        teclado.close();
        System.exit(0);
    }


    //executa uma instrução do processo
    public static void executaProcessoIntrucao(Processo processo){
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
                processo.estado = Processo.Estado.FINALIZADO;
                //devemos mudar para finalizar apenas o processo e nao o algoritmo inteiro
            }else if(paramC == 1){
                System.out.println(acc);
                processo.estado = Processo.Estado.BLOQUEADO;
                processo.waitingTime = ThreadLocalRandom.current().nextInt(10, 21);
            }else if(paramC == 2){
                System.out.print("Insira um numero: ");
                acc = teclado.nextInt();
                processo.estado = Processo.Estado.BLOQUEADO;
                processo.waitingTime = ThreadLocalRandom.current().nextInt(10, 21);
            }
        }
        //System.out.println(acc); 
    }

    //faz a leitura inicial dos processos - deve ser executada apenas no inicio
    public static void leProcessos() {
        try {

            File pasta = new File("programas");

            for(File txt : pasta.listFiles()){

                BufferedReader br = new BufferedReader(new FileReader(txt));

                Processo processo;

                System.out.println("Carregando processo: " + txt + " | Pid: " + Processo.nupid);
                System.out.println("Deseja definir sua prioridade? Digite NAO caso nao queira, ou um numero entre 2 (baixa prioridade) e 0 (alta prioridade)");

                String entrada = teclado.next().toUpperCase().strip();

                if(entrada.equals("NAO")){
                    processo = new Processo(arrival);
                }else{
                    processo = new Processo(arrival, Integer.parseInt(entrada));
                }

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
                arrival++;
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
                    processo.labels.put(op.substring(0, op.length()-1), processo.pc);
                }
            }
        }
    }
}