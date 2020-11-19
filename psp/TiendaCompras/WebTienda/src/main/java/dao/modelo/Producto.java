package dao.modelo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Producto {
    private long id_producto;
    @NotEmpty(message = "El nombre del prodcuto no puede estar vacio")
    private final String nombreProducto;
    @NotEmpty(message = "El precio del prodcuto no puede estar vacio")
    private final double precioProducto;
}
