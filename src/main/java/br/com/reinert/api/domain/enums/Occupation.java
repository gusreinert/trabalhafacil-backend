package br.com.reinert.api.domain.enums;

public enum Occupation {
    PEDREIRO("Pedreiro"),
    ELETRICISTA("Eletricista"),
    ENCANADOR("Encanador"),
    CARPINTEIRO("Carpinteiro"),
    PINTOR("Pintor"),
    JARDINEIRO("Jardineiro"),
    MARCENEIRO("Marceneiro"),
    VIDRACEIRO("Vidraceiro"),
    REFRIGERISTA("Refrigerista"),
    LIMPEZA("Serviço de Limpeza");

    private final String occupationValue;

    Occupation(String occupation) {
        this.occupationValue = occupation;
    }

    public String getValue() {
        return occupationValue;
    }

    @Override
    public String toString() {
        return this.occupationValue;
    }
}
