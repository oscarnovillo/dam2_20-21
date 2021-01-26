/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.asimetrico;


import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author oscar
 */
public class ClienteWebCert {

    public static void main(String[] args) {
        CloseableHttpClient httpclient;
        httpclient = HttpClients.createDefault();
        //pedir clave publica
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/encriptacionServidor/pfx");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("op", "NUEVO"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            HttpEntity entity = response2.getEntity();

           
            String base64Publica = EntityUtils.toString(entity, "UTF-8");

            //Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
            char[] password = "abc".toCharArray();
            ByteArrayInputStream input = new ByteArrayInputStream(java.util.Base64.getUrlDecoder().decode(base64Publica));
            KeyStore ksLoad = KeyStore.getInstance("PKCS12");
            ksLoad.load(input, password);

            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate("publica");
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(null);
            KeyStore.PrivateKeyEntry privateKeyEntry = 
                    (KeyStore.PrivateKeyEntry) ksLoad.getEntry("privada", pt);
            RSAPrivateKey keyLoad = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
            
 
            System.out.println(certLoad.getIssuerX500Principal());

            
            //descifrar mensaje
            httpPost = new HttpPost("http://localhost:8080/encriptacionServidor/pfx");
            nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("op", "MANDAR"));
            //mandar certificado
            nvps.add(new BasicNameValuePair("cert", Base64.getUrlEncoder().encodeToString(certLoad.getEncoded())));
            
            //mandar texto
            nvps.add(new BasicNameValuePair("texto", "firmado"));
            
            //mandar firma
            Signature sign = Signature.getInstance("SHA256WithRSA");
            sign.initSign(keyLoad);
            sign.update("firmado".getBytes());
            byte[] firma = sign.sign();
            nvps.add(new BasicNameValuePair("firma", Base64.getUrlEncoder().encodeToString(firma)));
            
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response2 = httpclient.execute(httpPost);
            entity = response2.getEntity();
            

            

        } catch (Exception ex) {
            Logger.getLogger(ClienteWebCert.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

}
