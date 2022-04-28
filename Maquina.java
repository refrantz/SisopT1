import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Maquina {

    public static void main (String args[]){

        int acc = 0;
        List<String> codigo = new ArrayList<String>();
        Map<String, Integer> dados = new LinkedHashMap<String, Integer>();
        Map<String, Integer> labels = new HashMap<String, Integer>();

        dados.put("cu", 1);
        dados.put("cuc", 2);
        dados.put("cucu", 3);
        codigo.add(args[0]);

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
                param = "" + dados.values().toArray()[Integer.parseInt(param.substring(1))];
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
                acc = Integer.parseInt(param);
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
                }else{
                    int intervalo = ThreadLocalRandom.current().nextInt(0, 21);
                }
            }
            System.out.println(acc);
        }
    }
}