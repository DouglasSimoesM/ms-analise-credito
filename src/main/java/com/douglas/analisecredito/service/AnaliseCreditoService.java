package com.douglas.analisecredito.service;

import com.douglas.analisecredito.domain.Proposta;
import com.douglas.analisecredito.exceptions.StrategyException;
import com.douglas.analisecredito.service.strategy.CalculoPonto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    @Autowired
    private List<CalculoPonto> calculoPontoList;

    @Autowired
    private NotificacaoRabbitService notificacaoRabbitService;

    @Value("${rabbitmq.propostaconcluida.exchange}")
    private String exchangePropostaConcluida;

    public void analisar(Proposta proposta) {
        try {
            int pontos = calculoPontoList.stream()
                    .mapToInt(impl -> impl.calcular(proposta))
                    .sum();

            proposta.setAprovada(pontos > 350);

        } catch (StrategyException ex) {
            proposta.setAprovada(false);
            proposta.setObservacao(ex.getMessage());
        }

        notificacaoRabbitService.notificar(exchangePropostaConcluida, proposta);
    }

}