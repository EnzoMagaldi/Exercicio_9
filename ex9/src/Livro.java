public class Livro extends Publicacao{
    private String [] autores;

    public Livro (String nome, String assunto, String edicao, Editora editora, String[] autores){
        super(nome,assunto,edicao,editora);
        this.autores = autores;
    }

    public void getInfo(){
        System.out.println("------------------\n");
        System.out.println("Tipo: Livro\n");
        getPubli();
        System.out.println("Autores: ");
        for(String autor : autores){
            System.out.println(autor);
        }
    }
    public String[] getAutores(){
        return autores;
    }

}
