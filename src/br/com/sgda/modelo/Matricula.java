/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgda.modelo;

import java.util.Calendar;

/**
 *
 * @author ACER-NOTE
 */
public class Matricula {
    
    
    private int id;
    private String descricao;
    private Pessoa pessoa;
    private Calendar data;

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setAluno(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
   
    
}
