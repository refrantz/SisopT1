import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Processo implements Comparable<Processo>{
    public String pid; //aka nome, poder ser só um numero identificador como podemos colocar Processo #N, checar isso depois
    public static int nupid;
    public List<String> codigo = new ArrayList<String>();
    public Map<String, Integer> dados = new LinkedHashMap<String, Integer>();
    public Map<String, Integer> labels = new HashMap<String, Integer>();
    public Estado estado = Estado.PRONTO; // Inicializa como PRONTO
    public Prioridade prioridade = Prioridade.BAIXA; // BAIXA como padrão
    public Integer arrivalTime;
    public Integer waitingTime;
    public Integer endTime;
    public Integer pc;
    public Integer quantum;
    public Integer quantumExecutado;


    public Processo(Integer arrivalTime){
        this.pid = "Processo " +nupid;
        nupid++;

        this.arrivalTime = arrivalTime;
        this.pc = 0;
    }

    public Processo(Integer arrivalTime, int prioridade, int quantum){
        this.pid = "Processo " +nupid;
        nupid++;

        this.arrivalTime = arrivalTime;
        
        if(prioridade == 0){
            this.prioridade = Prioridade.ALTA;
        }else if(prioridade == 1){
            this.prioridade = Prioridade.MEDIA;
        }else{
            this.prioridade = Prioridade.BAIXA;
        }

        this.pc = 0;
        this.quantum = quantum;
    }

    public Processo(Integer arrivalTime, int prioridade){
        new Processo(arrivalTime, prioridade, 0);
    }


    @Override public int compareTo(Processo outroProcesso) { //implementação }
        if (this.arrivalTime < outroProcesso.arrivalTime) {
            return -1;
        } if (this.arrivalTime > outroProcesso.arrivalTime) {
            return 1;
        }
        return 0;
    }

    public enum Estado{PRONTO,RODANDO,BLOQUEADO,FINALIZADO}
    public enum Prioridade{BAIXA,MEDIA,ALTA}

}
