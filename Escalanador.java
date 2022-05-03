import java.util.List;

public class Escalanador {

    List<Processo> processos;
    String metodoDeEscalonamento;
    int quantum;

    public Escalanador(List<Processo> processos, String metodoDeEscalonamento, int quantum) {
        this.processos = processos;
        this.metodoDeEscalonamento = metodoDeEscalonamento;
        this.quantum = quantum;
    }

    public Escalanador(List<Processo> processos, String metodoDeEscalonamento) {
        this.processos = processos;
        this.metodoDeEscalonamento = metodoDeEscalonamento;
        
        if(metodoDeEscalonamento.equals("RoundRobin")){
            System.out.println("RoundRobin precisa do parametro quantum!");
            System.exit(0);
        }
    }

    public Processo DecideProximoProcessoARodar(int unidadeDeTempoAtual) {

        Processo processoPrioritario;

        if(metodoDeEscalonamento.equals("PrioridadeSemPreempcao"))
            processoPrioritario = EscalonamentoPrioridadeSemPreempcao(processos, unidadeDeTempoAtual);
        else if(metodoDeEscalonamento.equals("PrioridadeComPreempcao"))
            processoPrioritario = EscalonamentoPrioridadeComPreempcao(processos, unidadeDeTempoAtual);
        else
            processoPrioritario = EscalonamentoRoundRobin(processos, unidadeDeTempoAtual, quantum);

        return processoPrioritario;
    }

    private Processo EscalonamentoPrioridadeSemPreempcao(List<Processo> processos, int unidadeDeTempoAtual) {
        //TO DO
        return null;
    }

    private Processo EscalonamentoPrioridadeComPreempcao(List<Processo> processos, int unidadeDeTempoAtual) {
        //TO DO
        return null;
    }

    private Processo EscalonamentoRoundRobin(List<Processo> processos, int unidadeDeTempoAtual, int quantum) {
        //TO DO
        return null;
    }
}
