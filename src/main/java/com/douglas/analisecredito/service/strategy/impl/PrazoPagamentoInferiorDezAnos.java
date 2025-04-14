package com.douglas.analisecredito.service.strategy.impl;

import com.douglas.analisecredito.domain.Proposta;
import com.douglas.analisecredito.service.strategy.CalculoPonto;
import org.springframework.stereotype.Component;

@Component
public class PrazoPagamentoInferiorDezAnos implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return proposta.getPrazoPagamento() < 120 ? 100: 0;
    }


}
