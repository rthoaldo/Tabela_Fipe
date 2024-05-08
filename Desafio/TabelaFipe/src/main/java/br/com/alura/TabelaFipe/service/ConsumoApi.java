package br.com.alura.TabelaFipe.service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
    public Object obterDados(String endereco) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create(endereco))
                    .build();
            HttpResponse<String> response = null;
            try {
                response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String json = response.body();
            return json;
    }
}
