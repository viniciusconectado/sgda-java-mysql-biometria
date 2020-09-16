/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgda.modelo;

import com.sun.jna.platform.win32.Sspi;
import com.sun.jna.platform.win32.Sspi.TimeStamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ACER-NOTE
 */
public class Pessoa {

    
    
    private int id;
    private String nome;
    private String cpf;

   private Calendar datanascimento;
    private String endereco;
    private String bairro;
    private int numero;
    private int cep;
    private int sexo;
    private String cidade;
    private String estado;

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

   
    public Calendar getDataNascimento() {
        return datanascimento;
    }

    public void setDataNascimento(Calendar dataNascimento) {
        this.datanascimento = dataNascimento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }
    
    
    
}
