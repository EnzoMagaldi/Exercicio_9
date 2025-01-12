import java.io.*;
import java.util.*;

public class Editora {
    private String nome;
    private String site;
    private Map<String, Publicacao> publis; // Mapa de publicações da editora

    public Editora(String nome, String site) {
        this.nome = nome;
        this.site = site;
        this.publis = new HashMap<>();
    }

    public int getNumPubs() {
        return publis.size();
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

    public Map<String, Publicacao> getListaPublis() {
        return publis;
    }

    public void addPub(Publicacao publi) {
        publis.put(publi.getNome(), publi);
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
}
