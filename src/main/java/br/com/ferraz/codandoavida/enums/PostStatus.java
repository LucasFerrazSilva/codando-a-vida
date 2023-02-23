package br.com.ferraz.codandoavida.enums;

import org.springframework.util.StringUtils;

public enum PostStatus {
    DRAFT, PUBLISHED, INACTIVE;

    public String getCapitalized() {
        String thisStr = this.toString();
        return thisStr.substring(0, 1).toUpperCase() + thisStr.substring(1).toLowerCase();
    }

}
