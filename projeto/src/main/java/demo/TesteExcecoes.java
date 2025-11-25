package demo;

import exception.DadosInvalidosException;
import model.Musica;

public class TesteExcecoes {

    public static void main(String[] args) {
        System.out.println("Teste 1: Criando música com título vazio...");
        try {
            Musica m1 = new Musica("", "Pink Floyd", "Dark Side", "Rock", 300);
            System.out.println("ERRO: A música foi criada e não devia!");
        } catch (DadosInvalidosException e) {
            System.out.println("SUCESSO! Exceção capturada: " + e.getMessage());
        }

        System.out.println("Teste 2: Criando música com duração -10...");
        try {
            Musica m2 = new Musica("Money", "Pink Floyd", "Dark Side", "Rock", -10);
            System.out.println("ERRO: A música foi criada e não devia!");
        } catch (DadosInvalidosException e) {
            System.out.println("SUCESSO! Exceção capturada: " + e.getMessage());
        }

        System.out.println("Teste 3: Criando música válida...");
        try {
            Musica m3 = new Musica("Time", "Pink Floyd", "Dark Side", "Rock", 400);
            System.out.println("SUCESSO! Música criada: " + m3);
        } catch (DadosInvalidosException e) {
            System.out.println("ERRO: Deu erro numa música válida: " + e.getMessage());
        }
    }
}