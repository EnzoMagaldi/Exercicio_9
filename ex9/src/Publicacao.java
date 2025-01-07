public abstract class Publicacao {
    String nome;
    String assunto;
    String edicao;
    Editora editora;

    public Publicacao(String nome, String assunto, String edicao, Editora editora){
        this.nome = nome;
        this.assunto = assunto;
        this.edicao = edicao;
        this.editora = editora;
        editora.addPub(this);
    }

    public void getPubli(){
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        if (obj == null || getClass() != obj.getClass()) 
            return false;
        Publicacao that = (Publicacao) obj;
        return nome.equals(that.nome) && edicao.equals(that.edicao) && editora.equals(that.editora);
    } 
}
