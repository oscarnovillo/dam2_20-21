package dao.modelo;

public class Miembro {

  private int id_miembro;
  private String nombre_miembro;
  private boolean es_papa;

  public Miembro() {
  }

  public int getId_miembro() {
    return id_miembro;
  }

  public void setId_miembro(int id_miembro) {
    this.id_miembro = id_miembro;
  }

  public String getNombre_miembro() {
    return nombre_miembro;
  }

  public void setNombre_miembro(String nombre_miembro) {
    this.nombre_miembro = nombre_miembro;
  }

  public boolean isEs_papa() {
    return es_papa;
  }

  public void setEs_papa(boolean es_papa) {
    this.es_papa = es_papa;
  }

  @Override
  public String toString() {
    return "Miembro{" +
        "id_miembro=" + id_miembro +
        ", nombre_miembro='" + nombre_miembro + '\'' +
        ", es_papa=" + es_papa +
        '}';
  }
}
