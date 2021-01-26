/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encriptacionservidor;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author oscar
 */
@WebServlet(name = "PruebaRSA", urlPatterns = {"/rsa"})
public class PruebaRSA extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String op = request.getParameter("op");

        switch (op) {
            case "PUBLICA":
                //mandar clave publica.
                byte[] bufferPub = new byte[5000];
                InputStream in = request.getServletContext().getResourceAsStream("/WEB-INF/dam1024.publica");
                int charsPub;
                try {
                    charsPub = in.read(bufferPub, 0, 5000);
                    in.close();

                    byte[] bufferPub2 = new byte[charsPub];
                    System.arraycopy(bufferPub, 0, bufferPub2, 0, charsPub);
                    //d.readFully(bufferPub, 0, 162);
                    //in.read(bufferPub, 0, 5000);
                    in.close();
                    byte[] bufferCode64 = Base64.getEncoder().encode(bufferPub2);
                    response.getOutputStream().write(bufferCode64);
                } catch (IOException ex) {
                    Logger.getLogger(PruebaRSA.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            case "CIFRADO":
                // Anadir provider JCE (provider por defecto no soporta RSA)
                //Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
                //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                Cipher cifrador = null;
                KeyFactory keyFactoryRSA = null; // Hace uso del provider BC
                try {
                    cifrador = Cipher.getInstance("RSA/ECB/NoPadding");

                    keyFactoryRSA = KeyFactory.getInstance("RSA");
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(PruebaRSA.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(PruebaRSA.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Crear KeyFactory (depende del provider) usado para las transformaciones de claves*/
                //cifrar un mensaje
                String mensaje = "HOLA Q TAL";
                // PASO 3b: Poner cifrador en modo DESCIFRADO
                // 2 Recuperar clave Privada del fichero */
                // 2.1 Leer datos binarios PKCS8
                PKCS8EncodedKeySpec clavePrivadaSpec = null;
                byte[] bufferPriv = new byte[5000];
                in = request.getServletContext().getResourceAsStream("/WEB-INF/dam1024.privada");
                int chars;
                try {
                    chars = in.read(bufferPriv, 0, 5000);
                    in.close();

                    byte[] bufferPriv2 = new byte[chars];
                    System.arraycopy(bufferPriv, 0, bufferPriv2, 0, chars);

                    // 2.2 Recuperar clave privada desde datos codificados en formato PKCS8
                    clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferPriv2);

                } catch (IOException ex) {
                    Logger.getLogger(PruebaRSA.class.getName()).log(Level.SEVERE, null, ex);
                }

                PrivateKey clavePrivada2;
                try {
                    clavePrivada2 = keyFactoryRSA.generatePrivate(clavePrivadaSpec);
                    cifrador.init(Cipher.ENCRYPT_MODE, clavePrivada2); // Descrifra con la clave privada

                    System.out.println("3b. Cifrar con clave privada");
                    byte[] bufferPlano2 = cifrador.doFinal(mensaje.getBytes("UTF-8"));
                    response.getOutputStream().write(Base64.getEncoder().encode(bufferPlano2));

                } catch (Exception ex) {
                    Logger.getLogger(PruebaRSA.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
