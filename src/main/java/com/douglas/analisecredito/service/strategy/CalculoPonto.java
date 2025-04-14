package com.douglas.analisecredito.service.strategy;

import com.douglas.analisecredito.domain.Proposta;

public interface CalculoPonto {
    int calcular(Proposta proposta);
}
