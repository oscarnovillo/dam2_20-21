/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encriptacionservidor;

import org.apache.commons.codec.binary.Base64;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar
 */
@WebServlet(name = "PruebaPFX", urlPatterns = {"/pfx"})
public class PruebaPFX extends HttpServlet {

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
            throws ServletException, IOException {
        String op = request.getParameter("op");

        switch (op) {
            case "NUEVO":
                try {
                    //mandar clave publica.
                    //Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
                    CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
                    // generate it with 2048 bits
                    certGen.generate(2048);
                    // prepare the validity of the certificate
                    long validSecs = (long) 365 * 24 * 60 * 60; // valid for one year
                    // add the certificate information, currently only valid for one year.

                    //firmar x509 por servidor
                    PKCS8EncodedKeySpec clavePrivadaSpec = null;
                    byte[] bufferPriv = new byte[5000];
                    InputStream in = request.getServletContext().getResourceAsStream("/WEB-INF/dam1024.privada");
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

                    PrivateKey clavePrivadaServidor;
                    // Anadir provider JCE (provider por defecto no soporta RSA)
                   // Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
                    //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

                    KeyFactory keyFactoryRSA = null; // Hace uso del provider BC

                    keyFactoryRSA = KeyFactory.getInstance("RSA");

                    clavePrivadaServidor = keyFactoryRSA.generatePrivate(clavePrivadaSpec);

                    X509Certificate cert = certGen.getSelfCertificate(
                            // enter your details according to your application
                            new X500Name("CN=Juan Fernandez,O=My Organisation,L=My City,C=DE"), validSecs);

                    byte[] inCertBytes = cert.getTBSCertificate();
                    X509CertInfo info = new X509CertInfo(inCertBytes);

                    info.set(X509CertInfo.ISSUER, new X500Name("CN=SERVIDOR,O=My Organisation,L=My City,C=DE"));
                    X509CertImpl certificadoCliente = new X509CertImpl(info);

                    certificadoCliente.sign(clavePrivadaServidor, cert.getSigAlgName());

                    PrivateKey clavePrivadaCliente = certGen.getPrivateKey();

                    KeyStore ks = KeyStore.getInstance("PKCS12");
                    ks.load(null, null);
                    ks.setCertificateEntry("publica", certificadoCliente);
                    ks.setKeyEntry("privada", clavePrivadaCliente, null,
                            new Certificate[]{certificadoCliente});
                    ByteArrayOutputStream fos = new ByteArrayOutputStream();

//                    String webInfPath = request.getServletContext().getRealPath("WEB-INF");
//                    FileOutputStream fo = new FileOutputStream(webInfPath+"//keystore.pfx");
//                    ks.store(fo,password);
                    char[] password = "abc".toCharArray();
                    ks.store(fos, password);
                    //String respuesta = new String(Base64.encodeBase64(fos.toByteArray()));
                    response.getOutputStream().write(Base64.encodeBase64(fos.toByteArray()));
                    fos.close();

                } catch (Exception e) {
                    Logger.getLogger(PruebaRSA.class.getName()).log(Level.SEVERE, null, e);
                }
                break;
            case "MANDAR":
                try {

                    String publica = request.getParameter("cert");
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    X509Certificate cert2 = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(Base64.decodeBase64(publica)));

                    //cargar clave public de servidor
                    KeyFactory keyFactoryRSA = null; // Hace uso del provider BC

                    keyFactoryRSA = KeyFactory.getInstance("RSA");
                    byte[] bufferPub = new byte[5000];
                    InputStream in = request.getServletContext().getResourceAsStream("/WEB-INF/dam1024.publica");
                    int charsPub;

                    charsPub = in.read(bufferPub, 0, 5000);
                    in.close();

                    byte[] bufferPub2 = new byte[charsPub];
                    System.arraycopy(bufferPub, 0, bufferPub2, 0, charsPub);
                    //d.readFully(bufferPub, 0, 162);
                    //in.read(bufferPub, 0, 5000);
                    in.close();

                    X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub2);
                    PublicKey clavePublica2 = keyFactoryRSA.generatePublic(clavePublicaSpec);

                    cert2.verify(clavePublica2);

                    String texto = request.getParameter("texto");
                    byte[] firma = Base64.decodeBase64(request.getParameter("firma"));

                    Signature sign = Signature.getInstance("SHA256WithRSA");
                    sign.initVerify(cert2.getPublicKey());
                    sign.update(texto.getBytes());

                    System.out.println(cert2.getIssuerX500Principal());
                    System.out.println(cert2.getSubjectDN());
                    LdapName ldapDN = new LdapName(cert2.getSubjectDN().getName());
                    for (Rdn rdn : ldapDN.getRdns()) {
                        if (rdn.getType().equals("CN")) {
                            System.out.println(rdn.getValue());
                        }
                    }
                    System.out.println("FIRMADO " + sign.verify(firma));

                } catch (Exception e) {
                    Logger.getLogger(PruebaRSA.class.getName()).log(Level.SEVERE, null, e);
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

    /*
    public class CertificateChainGeneration {

    public static void CertificateChainGeneration(){
        try{
            //Generate ROOT certificate
            CertAndKeyGen keyGen=new CertAndKeyGen("RSA","SHA1WithRSA",null);
            keyGen.generate(1024);
            PrivateKey privateKey=keyGen.getPrivateKey();

            X509Certificate myCertificate = keyGen.getSelfCertificate(new X500Name("CN=MYCERTIFICATE"), (long) 365 * 24 * 60 * 60);

            myCertificate   = createSignedCertificate(myCertificate,privateKey);
            FileOutputStream publicKeyOut = new FileOutputStream("PublicKey.pem");
            BASE64Encoder encoder = new BASE64Encoder();
            publicKeyOut.write(X509Factory.BEGIN_CERT.getBytes());
            publicKeyOut.write('\n');
            encoder.encodeBuffer(myCertificate.getEncoded(), publicKeyOut);
            publicKeyOut.write(X509Factory.END_CERT.getBytes());
            publicKeyOut.close();

            byte[] privateKeyBytes = privateKey.getEncoded();
            FileOutputStream privateKeyOut = new FileOutputStream("PrivateKey.der");
            privateKeyOut.write(privateKeyBytes);
            privateKeyOut.close(); 
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static X509Certificate createSignedCertificate(X509Certificate issuerCertificate,PrivateKey issuerPrivateKey){
        try{
            Principal issuer = issuerCertificate.getSubjectDN();
            String issuerSigAlg = issuerCertificate.getSigAlgName();

            byte[] inCertBytes = issuerCertificate.getTBSCertificate();
            X509CertInfo info = new X509CertInfo(inCertBytes);
            info.set(X509CertInfo.ISSUER, new CertificateIssuerName((X500Name) issuer));

                        CertificateExtensions exts=new CertificateExtensions();
                        BasicConstraintsExtension bce = new BasicConstraintsExtension(true, -1);
                        exts.set(BasicConstraintsExtension.NAME,new BasicConstraintsExtension(false, bce.getExtensionValue()));
                        info.set(X509CertInfo.EXTENSIONS, exts);

            X509CertImpl outCert = new X509CertImpl(info);
            outCert.sign(issuerPrivateKey, issuerSigAlg);

            return outCert;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
     */
}
