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
        List<String> regiaoDados = new ArrayList<String>();
        List<String> codigo = new ArrayList<String>();
        Map<String, Integer> dados = new LinkedHashMap<String, Integer>();
        Map<String, Integer> labels = new HashMap<String, Integer>();

        try {

            BufferedReader br = new BufferedReader(new FileReader("exemplo.txt"));
            String linha = br.readLine().strip().toUpperCase();

            while(linha != null){

                if(linha.equals(".CODE")){
                    linha = br.readLine().strip().toUpperCase();
                    while(!linha.equals(".ENDCODE")){
                        codigo.add(linha);
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

        for (int pc = 0; pc < codigo.size();pc++){

            String[] input = codigo.get(pc).split(" ");
            String op = input[0];
            String param = input[1];

            if(op.contains(":")){
                op = input[1];
                param = input[2];
                labels.put(input[0], pc);
            }

            if(param.contains("#")){
                param = "" + dados.values().toArray()[Integer.parseInt(param.substring(1))-1];
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
                    System.out.println(acc);
                    int intervalo = ThreadLocalRandom.current().nextInt(0, 21);
                }
            }
            System.out.println(acc);
        }
    }
}