/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgda.modelo;

import java.sql.Timestamp;

/**
 *
 * @author ACER-NOTE
 */
public class Funcionario extends Pessoa{
    
    public Funcionario (){
        super();
    }
    
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    private Timestamp datadeadimisao;
    private String tipo;

    public Timestamp getDatadeadimisao() {
        return datadeadimisao;
    }

    public void setDatadeadimisao(Timestamp datadeadimisao) {
        this.datadeadimisao = datadeadimisao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
