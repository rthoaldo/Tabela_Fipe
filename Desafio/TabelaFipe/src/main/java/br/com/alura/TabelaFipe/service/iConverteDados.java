package br.com.alura.TabelaFipe.service;
import java.util.List;

public interface iConverteDados {
    <T> T obterDados(String json, Class <T> Class);
    <T> List<T> obterLista (String json, Class<T> classe);
}