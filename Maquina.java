import java.util.ArrayList;

public class Maquina {
    
    static int acc = 0;
    static int pc = 0;

    static ArrayList<String> codigo = new ArrayList<String>();

    public static void main (String args[]){
        for (String linha:codigo){
            String[] input = linha.split(" ");
            String op = input[0];
            String param = input[1];

            if (op.equals("ADD")){
                acc += Integer.parseInt(param);
            }else if(op.equals("SUB")){
                acc -= Integer.parseInt(param);
            }else if(op.equals("MULT")){
                acc *= Integer.parseInt(param);
            }else if(op.equals("DIV")){
                acc /= Integer.parseInt(param);

            }else if(op.equals("LOAD")){

            }else if(op.equals("STORE")){


            }else if(op.equals("BRANY")){
                
            }else if(op.equals("BRPOS")){

            }else if(op.equals("BRZERO")){

            }else if(op.equals("BRNEG")){


            }else if(op.equals("SYSCALL")){
                
            }
        }
    }
}