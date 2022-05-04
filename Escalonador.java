import java.util.ArrayList;
import java.util.List;

public class Escalonador {

    List<Processo> processos;
    String metodoDeEscalonamento;
    int quantum;

    public Escalonador(List<Processo> processos, String metodoDeEscalonamento) {
        this.processos = processos;
        this.metodoDeEscalonamento = metodoDeEscalonamento;
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

        List<Processo> processosCopia = new ArrayList<>(this.processos);

        //Se tiver em bloqueado, não temos que executar nenhuma instrução, então to enviando null (posso enviar o projeto, mas vamos ter que validar na "máquina"
        if(processosCopia.stream().anyMatch(p -> p.estado.equals(Processo.Estado.BLOQUEADO))) {
            return null;
        }

        if(processosCopia.stream().anyMatch(p -> p.estado.equals(Processo.Estado.RODANDO))) {
            return processos.stream().filter(p -> p.estado.equals(Processo.Estado.RODANDO)).findFirst().orElse(null);
        }

        List<Processo> processosDisponiveis = processosCopia.stream().filter(p -> p.arrivalTime <= unidadeDeTempoAtual).filter(p -> p.estado.equals(Processo.Estado.PRONTO)).toList();

        if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.ALTA))) {
            return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.ALTA)).findFirst().orElse(null);
        } else if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.MEDIA))) {
            return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.MEDIA)).findFirst().orElse(null);
        } else if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.BAIXA))) {
            return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.BAIXA)).findFirst().orElse(null);
        }

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
