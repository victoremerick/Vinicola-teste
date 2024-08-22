package br.victoremerick.gx2teste.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecomendacaoVinho {
    private String tipo_vinho;
    private String safra;
}
