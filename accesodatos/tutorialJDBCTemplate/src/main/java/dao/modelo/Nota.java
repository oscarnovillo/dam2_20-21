/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.modelo;

/**
 *
 * @author daw
 */
public class Nota {

    private int nota;
    private int al;
    private int asig;

    public Nota(int al, int asig) {
        this.al = al;
        this.asig = asig;
    }

    public Nota(int nota, int al, int asig) {
        this.nota = nota;
        this.al = al;
        this.asig = asig;
    }

    public Nota() {

    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getAl() {
        return al;
    }

    public void setAl(int al) {
        this.al = al;
    }

    public int getAsig() {
        return asig;
    }

    public void setAsig(int asig) {
        this.asig = asig;
    }

}
