# Exercício 9

Trabalho que implementa o *exercício 9* da segunda implementação pedida na disciplina **Paradigmas de Programação**,
que gerencia publicações (livros e revistas) associadas as suas respectivas editoras.

## Código base da main:
```java 
  public class Principal {
   public static void main(String[] args) {
     Editora oreilly = new Editora("O'Reilly", "http://oreilly.com/");
     String [] autores = {"Eric Freeman", "Elisabeth Freeman"};
     Publicacao headfirst = new Livro("Padrões de Projeto", "Programação", "2a", oreilly, autores);
     Editora tres = new Editora("Editora Três","http://editora3.terra.com.br/");
     Publicacao oracle = new Revista("Isto É", "Notícias", "2279", tres, "semanal");
     System.out.println("O'Reilly: " + oreilly.getNumPubs());
    }
  }
```
## O que é pedido:
(a) Definir a classe **Editora** e seu respectivo construtor

(b) Definir uma classe chamada **Livro** e seu respectivo construtor. 

(c) De forma similar, defina uma classe chamada **Revista**.

(d) Definir uma classe chamada **Publicacao**, e sua relação com **Livro** e **Revista**

(e) Definir o método **getnumPubs() em Editora**.

(f) Criar um objeto que represente esta publicação: http://shop.oreilly.com/product/0636920029274.do e imprimir a quantidade de publicações de cada editora criada.

(g) Criar métodos para salvar e recuperar publicações entre arquivos

### Definindo a classe Editora:

```java
public class Editora {
    private String nome;
    private String site;
    //...possui mais parâmetros que serão mencionados mais tarde

public Editora(String nome, String site) {
        this.nome = nome;
        this.site = site;
        ...
}
public String getNome() {
        return nome;
    }

    public String getSite() {
        return site;
    }

    @Override
    public String toString() {
        return nome + "(" + site + ")";
    }
...
```
- De princípio, possui apenas como parâmetros o nome e site da editora
- Após o método getnumpubs() e o salvamento e recuperação de arquivos, teremos mais parâmetros

### Definindo a classe Publicação: 
```java
public abstract class Publicacao {
    String nome;
    String assunto;
    String edicao;
    Editora editora;

    public Publicacao(String nome, String assunto, String edicao, Editora editora) {
        this.nome = nome;
        this.assunto = assunto;
        this.edicao = edicao;
        this.editora = editora;
        editora.addPub(this);
    }

    public void getPubli() {
        System.out.println("Nome: "+ nome + "\nAssunto: "+ assunto +"\nEdicao: "+ edicao +"\nEditora: "+ editora);
    }
    
    public String getNome() {
        return nome;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getEdicao() {
        return edicao;
    }

    public Editora getEditora() {
        return editora;
    }

    public abstract void getInfo();

}
```
- Definindo Publicação como abstrato, pois Livro e Revista herdarão seus métodos...
- ... e implementarão getInfo(), que apenas imprime as suas informações
- Redefinição do método equals, que será útil para evitar duplicatas no arquivo .txt
- **Incrementação:** getPubli() retorna todas as informações de uma dada publicação

### Definição de Livro e Revista: 
```java
public class Revista extends Publicacao {
    private String periodo;

    public Revista(String nome,String assunto,String edicao, Editora editora, String periodo){
        super(nome,assunto,edicao,editora);
        this.periodo = periodo;
    }

    public void getInfo() {
        System.out.println("------------------\n");
        System.out.println("Tipo: Revista");
        getPubli();
        System.out.println("Periodicidade: "+ periodo);
    }

    public String getPeriodo() {
        return periodo;
    }
}
```
```java
public class Livro extends Publicacao {
    private String [] autores;

    public Livro (String nome, String assunto, String edicao, Editora editora, String[] autores){
        super(nome,assunto,edicao,editora);
        this.autores = autores;
    }

    public void getInfo() {
        System.out.println("------------------\n");
        System.out.println("Tipo: Livro\n");
        getPubli();
        System.out.println("Autores: ");
        for(String autor : autores){
            System.out.println(autor);
        }
    }
    public String[] getAutores() {
        return autores;
    }

}
```
- **Incrementação:** getInfo() varia de acordo com o objeto referido, pois revista tem a sua periodicidade enquanto o livro tem seus autores

### Salvar e recuperar Publicações:

-  **Na classe editora:**

```java
...
private Map<String, Publicacao> publis;
public Editora(String nome, String site) {
        ...
        this.publis = new HashMap<>();
}
public int getNumPubs(){
        return publis.size();
    }
public Map<String, Publicacao> getListaPublis() {
        return publis;
    }

    public void addPub(Publicacao publi) {
        String chaveUnica = publi.getNome() + "_" + publi.getEdicao();
        publis.put(chaveUnica, publi);
    }

    // Método para salvar editoras e publicações
    public static void salvarEditoras(List<Editora> editoras, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Editora editora : editoras) {
                writer.write(editora.getNome() + ";" + editora.getSite());
                writer.newLine();
                
                // Salvar publicações
                for (Publicacao publi : editora.getListaPublis().values()) {
                    if (publi instanceof Livro) {
                        Livro livro = (Livro) publi;
                        writer.write("Livro;" + livro.getNome() + ";" + livro.getAssunto() + ";" +
                                livro.getEdicao() + ";" + livro.getEditora().getNome() + ";" +
                                String.join(",", livro.getAutores()));
                    } else if (publi instanceof Revista) {
                        Revista revista = (Revista) publi;
                        writer.write("Revista;" + revista.getNome() + ";" + revista.getAssunto() + ";" +
                                revista.getEdicao() + ";" + revista.getEditora().getNome() + ";" +
                                revista.getPeriodo());
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar editoras e publicações
    public static List<Editora> carregarEditoras(String nomeArquivo) {
        List<Editora> editoras = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            Editora currentEditora = null;

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                
                if (dados.length == 2) {
                    // Criar uma nova editora
                    String nomeEditora = dados[0];
                    String site = dados[1];
                    currentEditora = new Editora(nomeEditora, site);
                    editoras.add(currentEditora);
                } else if (dados.length == 6) {
                    // Carregar uma publicação
                    String tipo = dados[0];
                    String nomePubli = dados[1];
                    String assunto = dados[2];
                    String edicao = dados[3];
                    String nomeEditora = dados[4];
                    String detalhes = dados[5];

                    if (currentEditora != null && currentEditora.getNome().equals(nomeEditora)) {
                        if (tipo.equals("Livro")) {
                            String[] autores = detalhes.split(",");
                            Livro livro = new Livro(nomePubli, assunto, edicao, currentEditora, autores);
                            currentEditora.addPub(livro);
                        } else if (tipo.equals("Revista")) {
                            Revista revista = new Revista(nomePubli, assunto, edicao, currentEditora, detalhes);
                            currentEditora.addPub(revista);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return editoras;
    }
```
  - Manipulação de mapas para salvar e recuperar publicações; e para implementar o getNumPubs()
  - No salvamento de arquivos, é salvo o arquivo linha por linha começando pela editora, que servirá para separar quais publicações são de cada editora
  - Depois vemos qual é o tipo da Publicação, e fazemos todas as operações necessárias
  ```java
  String.join(",", livro.getAutores())
  ```
  - Essa linha de código é muito importante para separar corretamente os Autores do livro, já que as informações do arquivo .txt estão separadas por ";" e os autores estarão separadas por ","
  - Já na hora de salvar arquivos, é criado um arrayList de editoras, e é adicionado suas informações (editora e publicações) para recuperar corretamente os dados

### Main atualizada: 

```java
import java.util.*;

public class Principal {
    @SuppressWarnings("unused")
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

        // Salvar editoras e publicações no arquivo
        List<Editora> editoras = new ArrayList<>();
        editoras.add(oreilly);
        editoras.add(tres);
        Editora.salvarEditoras(editoras, "editoras.txt");

        // Limpar editoras para testar o carregamento do arquivo
        editoras.clear();

        // Carregar editoras e publicações do arquivo
        editoras = Editora.carregarEditoras("editoras.txt");

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
```
