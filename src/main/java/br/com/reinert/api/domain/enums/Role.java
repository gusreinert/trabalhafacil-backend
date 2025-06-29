package br.com.reinert.api.domain.enums;

public enum Role {
    USER("USER"),
    WORKER("WORKER");

    private final String roleValue;

    Role(String role) {
        this.roleValue = role;
    }

    public String getValue() {
        return roleValue;
    }

    @Override
    public String toString() {
        return this.roleValue;
    }
}
