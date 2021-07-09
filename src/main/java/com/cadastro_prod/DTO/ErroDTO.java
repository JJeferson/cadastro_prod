package com.cadastro_prod.DTO;

public class ErroDTO {
    private String Erro;
    private Integer HttpStatus;

    public String getErro() {
        return this.Erro;
    }

    public void setErro(String Erro) {
        this.Erro = Erro;
    }

    public Integer getHttpStatus() {
        return this.HttpStatus;
    }

    public void setHttpStatus(Integer HttpStatus) {
        this.HttpStatus = HttpStatus;
    }


}