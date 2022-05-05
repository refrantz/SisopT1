import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Escalonador {

    List<Processo> processos;
    String metodoDeEscalonamento;
    int quantum;

    public Escalonador(List<Processo> processos, String metodoDeEscalonamento) {
        this.processos = processos;
        this.metodoDeEscalonamento = metodoDeEscalonamento;
        Collections.sort(this.processos);
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

        return AcharProximoEmProcessosDisponiveis(processosDisponiveis);
    }

    private Processo EscalonamentoPrioridadeComPreempcao(int unidadeDeTempoAtual) {

        List<Processo> processosCopia = new ArrayList<>(this.processos);
        List<Processo> processosDisponiveis = processosCopia.stream().filter(p -> p.arrivalTime <= unidadeDeTempoAtual).filter(p -> p.estado.equals(Processo.Estado.PRONTO)).toList();

        if(processosCopia.stream().anyMatch(p -> p.estado.equals(Processo.Estado.RODANDO))) {
            Processo processoAtual = processos.stream().filter(p -> p.estado.equals(Processo.Estado.RODANDO)).findFirst().orElse(null);
            if(processoAtual.prioridade.equals(Processo.Prioridade.ALTA))
                return processoAtual;
            else if(processoAtual.prioridade.equals(Processo.Prioridade.MEDIA)) {
                if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.ALTA))) {
                    return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.ALTA)).findFirst().orElse(null);
                }
            } else if(processoAtual.prioridade.equals(Processo.Prioridade.BAIXA)) {
                if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.ALTA))) {
                    return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.ALTA)).findFirst().orElse(null);
                } else if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.MEDIA))) {
                    return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.MEDIA)).findFirst().orElse(null);
                }
            }
            return processoAtual;
        }

        return AcharProximoEmProcessosDisponiveis(processosDisponiveis);
    }

    private Processo EscalonamentoRoundRobin(int unidadeDeTempoAtual) {

        List<Processo> processosCopia = new ArrayList<>(this.processos);
        List<Processo> processosFinalizados = processosCopia.stream().filter(p -> p.estado.equals(Processo.Estado.FINALIZADO)).toList();

        List<Processo> processosDisponiveis = processosCopia.stream().filter(p -> p.arrivalTime <= unidadeDeTempoAtual)
                                                                     .filter(p -> p.quantumExecutado != 0)
                                                                     .filter(p -> p.estado != Processo.Estado.FINALIZADO).toList();

        if(processosDisponiveis.stream().count() == 0 && processosFinalizados.stream().count() < this.processos.stream().count()){
            this.processos.stream().map(p -> p.quantumExecutado = p.quantum);
        }

        if(processosDisponiveis.stream().anyMatch(p -> p.estado.equals(Processo.Estado.RODANDO))) {
            Processo processoAtual = processos.stream().filter(p -> p.estado.equals(Processo.Estado.RODANDO)).findFirst().orElse(null);
            processoAtual.quantumExecutado--;
            return processoAtual;
        }

        if(processosDisponiveis.stream().anyMatch(p -> p.estado.equals(Processo.Estado.BLOQUEADO))) {
            Processo processoAtual = processosDisponiveis.stream().filter(p -> p.estado.equals(Processo.Estado.BLOQUEADO)).findFirst().orElse(null);
            processoAtual.quantumExecutado--;
            return null;
        }

        Processo proximoDisponivel = AcharProximoEmProcessosDisponiveis(processosDisponiveis);
        if(proximoDisponivel != null){
            proximoDisponivel.quantumExecutado--;
        }
        return proximoDisponivel;
    }

    private Processo AcharProximoEmProcessosDisponiveis(List<Processo> processosDisponiveis) {
        if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.ALTA))) {
            return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.ALTA)).findFirst().orElse(null);
        } else if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.MEDIA))) {
            return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.MEDIA)).findFirst().orElse(null);
        } else if(processosDisponiveis.stream().anyMatch(p -> p.prioridade.equals(Processo.Prioridade.BAIXA))) {
            return processosDisponiveis.stream().filter(p -> p.prioridade.equals(Processo.Prioridade.BAIXA)).findFirst().orElse(null);
        }
        return null;
    }
}
