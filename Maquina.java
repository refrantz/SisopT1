import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Maquina {
    
    static int acc = 0;
    static int pc = 0;

    static List<String> codigo = new ArrayList<String>();

    public static void main (String args[]){
        codigo.add(args[0]);

        for (String linha:codigo){

            Map<String, Integer> dados = new LinkedHashMap<String, Integer>();
            dados.put("cu", 1);
            dados.put("cuc", 2);
            dados.put("cucu", 3);
            String[] input = linha.split(" ");
            String op = input[0];
            String param = input[1];

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
                
            }else if(op.equals("BRPOS")){

            }else if(op.equals("BRZERO")){

            }else if(op.equals("BRNEG")){


            }else if(op.equals("SYSCALL")){

            }
            System.out.println(acc);
        }
    }
}