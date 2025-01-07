import java.util.*;

public class Principal {
    public static void main(String[] args) {
        
        String [] autores = {"Eric Freeman", "Elisabeth Freeman"};
        String[] autores2 = {"Jennifer Robbins"};
        // Criar novas editoras
        Editora oreilly = new Editora("O'Reilly", "http://oreilly.com/");
        Editora tres = new Editora("Editora Três", "http://editora3.terra.com.br/");

        // Criar publicações
        Publicacao headFirst = new Livro("Padrões de Projeto", "Programação", "2a", oreilly, autores);
        Publicacao oracle = new Revista("Isto É", "Notícias", "2279", tres, "semanal");
        Publicacao dev = new Livro("HTML5 P.E.","Programação", "5a",oreilly,autores2);

        // Adicionar publicações às editoras
        oreilly.addPub(headFirst);
        oreilly.addPub(dev);
        tres.addPub(oracle);

        // Salvar editoras e publicações no arquivo
        List<Editora> editoras = new ArrayList<>();
        editoras.add(oreilly);
        editoras.add(tres);
        Editora.salvarEditoras(editoras, "editoras.txt");

        // Limpar editoras para testar o carregamento do arquivo
        editoras.clear();

        // Carregar editoras e publicações do arquivo
        //editoras = Editora.carregarEditoras("editoras.txt");

        // Exibir editoras e publicações carregadas
        for (Editora editora : editoras) {
            System.out.println("------------------");
            System.out.println(""+ editora.getNumPubs()+" Publicações da Editora: " + editora.getNome());
            for (Publicacao publi : editora.getListaPublis().values()){
                publi.getInfo();
            }
        }
    }
}
