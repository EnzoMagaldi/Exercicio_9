public class Revista extends Publicacao{
    private String periodo;

    public Revista(String nome,String assunto,String edicao, Editora editora, String periodo){
        super(nome,assunto,edicao,editora);
        this.periodo = periodo;
    }

    public void getInfo(){
        System.out.println("------------------\n");
        System.out.println("Tipo: Revista");
        getPubli();
        System.out.println("Periodicidade: "+ periodo);
    }

    public String getPeriodo() {
        return periodo;
    }
}
