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
            processoPrioritario = EscalonamentoPrioridadeSemPreempcao(unidadeDeTempoAtual);
        else if(metodoDeEscalonamento.equals("PrioridadeComPreempcao"))
            processoPrioritario = EscalonamentoPrioridadeComPreempcao(unidadeDeTempoAtual);
        else
            processoPrioritario = EscalonamentoRoundRobin(unidadeDeTempoAtual);

        return processoPrioritario;
    }

    private Processo EscalonamentoPrioridadeSemPreempcao(int unidadeDeTempoAtual) {
        //TO DO
        return null;
    }

    private Processo EscalonamentoPrioridadeComPreempcao(int unidadeDeTempoAtual) {
        //TO DO
        return null;
    }

    private Processo EscalonamentoRoundRobin(int unidadeDeTempoAtual) {
        //TO DO
        return null;
    }
}
