import java.util.List;

public class Escalanador {

    public Processo DecideProximoProcessoARodar(List<Processo> processos, int unidadeDeTempoAtual, String metodoDeEscalonamento, int quantum) {

        Processo processoPrioritario;

        if(metodoDeEscalonamento == "PrioridadeSemPreempcao")
            processoPrioritario = EscalonamentoPrioridadeSemPreempcao(processos, unidadeDeTempoAtual);
        else if(metodoDeEscalonamento == "PrioridadeComPreempcao")
            processoPrioritario = EscalonamentoPrioridadeComPreempcao(processos, unidadeDeTempoAtual);
        else
            processoPrioritario = EscalonamentoRoundRobin(processos, unidadeDeTempoAtual, quantum);

        return processoPrioritario;
    }

    public Processo DecideProximoProcessoARodar(List<Processo> processos, int unidadeDeTempoAtual, String metodoDeEscalonamento) {
        return DecideProximoProcessoARodar(processos, unidadeDeTempoAtual, metodoDeEscalonamento, 0);
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
