package br.com.alura.TabelaFipe.principal;
import java.util.Scanner;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;
import br.com.alura.TabelaFipe.model.Dados;
import java.util.Comparator;
import br.com.alura.TabelaFipe.model.Modelos;
import java.util.stream.Collectors;
import java.util.List;
import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Veiculo;

public class Principal {

    public Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        var menu = """
                **** OPÇÕES ****
                Carro
                Moto
                Caminhão
                                
                Digite uma opção:
                """;
        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endereco;

        if (opcao.toLowerCase().contains("car")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        System.out.println(json);
        var marcas = conversor.obterLista((String) json, Dados.class);
                marcas.stream()
                        .sorted(Comparator.comparing(Dados::codigo))
                        .forEach(System.out::println);
        System.out.println("Informe a marca para consulta: ");
        var codigomarca = leitura.nextLine();

        endereco = endereco + "/" + codigomarca + "/modelos";
        json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados((String) json, Modelos.class);

        System.out.println("\nModelos desta Marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome para pesquisa: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelofiltrado = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos Filtrados: ");
        modelofiltrado.forEach(System.out::println);

        System.out.println("\nDigite o código para pesquisa do preço: ");
        var codigoModelo = leitura.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista((String) json, Dados.class);
        List<Veiculo> veiculos = new java.util.ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json  = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados((String) json, Veiculo.class);
            veiculos.add(veiculo);

        }
        System.out.println("\nVeículos filtrado por ano: ");
        veiculos.forEach(System.out::println);


    }

}


