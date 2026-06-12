package br.com.vestaplan.api.exceptions;

public class EntidadeNaoEncontradaException extends RuntimeException{
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
