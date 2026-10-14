/**
 * Classe para gerir os estilos literarios
 * @author Dárcio Guerra e Rodrigo Pereira
 */
public class Style implements Comparable<Style>, SearchTerm{
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String aName) {
        this.name = aName;
    }

    @Override
    public int compareTo(Style aStyle) {
        return this.getName().compareTo(aStyle.getName());
    }

    @Override
    public String getSearchableAttribute() {
        return name;
    }
}
