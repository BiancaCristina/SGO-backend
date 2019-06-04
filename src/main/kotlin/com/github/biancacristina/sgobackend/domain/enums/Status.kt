package com.github.biancacristina.sgobackend.domain.enums

enum class Status constructor(val cod: Int, val status: String) {
    ACIONADO(1, "ACIONADO"),
    EM_ELABORACAO(2, "EM ELABORAÇÃO"),
    EM_ANALISE(3, "EM ANÁLISE"),
    ELABORADO(4, "ELABORADO"),
    EM_CONSTRUCAO(5, "EM CONSTRUÇÃO"),
    TESTE_LIBERADO(6, "LIBERADO PARA TESTES"),
    PRONTO(7, "PRONTO PARA ENTREGA"),
    ACEITACAO(8, "ACEITADO"),
    ENTREGUE(9, "ENTREGUE"),
    ANALISE_MEDICAO(10, "ANÁLISE DE MEDIÇÃO"),
    PAGAMENTO_LIBERADO(11, "PAGAMENTO LIBERADO"),
    CONCLUIDO(12, "CONCLUÍDO");

    companion object {
        fun toEnum(cod: Int?): Status? {
            // Convert a cod to a Status

            if (cod == null) return null

            for (i in Status.values()) {
                if (cod == i.cod) return i
            }

            throw IllegalArgumentException("Id invalido: $cod")
        }
    }
}