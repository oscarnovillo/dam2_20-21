package dam;

import lombok.Data;

@Data
public class Cifrado {

    // todos los campos van codificados en Base64

    private String iv;
    private String salt;
    private int iteraciones;
    private String claveSimetrica;
    private String mensaje;
    private String firma;
}
