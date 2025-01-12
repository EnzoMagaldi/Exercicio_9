# Exercicio 9

Trabalho que implementa o *exercício 9* da segunda implementação pedida na disciplina **Paradigmas de Programação**

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

