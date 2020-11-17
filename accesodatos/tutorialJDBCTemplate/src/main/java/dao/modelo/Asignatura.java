/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 *
 * @author daw
 */

@Data @AllArgsConstructor @NoArgsConstructor
public class Asignatura {

    private int id;
    private String nombre;
    private String curso;
    private String ciclo;

}
