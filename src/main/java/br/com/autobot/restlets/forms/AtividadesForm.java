package br.com.autobot.restlets.forms;

import javax.ws.rs.FormParam;

import lombok.Getter;

@Getter
public class AtividadesForm {

    @FormParam("idplano")
    private Long idplano;
    @FormParam("idscript")
    private Long idscript;
    @FormParam("idusuario")
    private Long idusuario;
    @FormParam("atividade")
    private String atividade;
}