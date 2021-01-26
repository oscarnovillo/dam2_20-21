/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.asimetrico;


import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
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
public class ClienteWeb {

    public static void main(String[] args) {
        CloseableHttpClient httpclient;
        httpclient = HttpClients.createDefault();
        //pedir clave publica
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8084/encriptacionServidor/rsa");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("op", "PUBLICA"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            HttpEntity entity = response2.getEntity();

            String base64Publica = EntityUtils.toString(entity, "UTF-8");

            // Anadir provider JCE (provider por defecto no soporta RSA)
            //Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
            //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cifrador = Cipher.getInstance("RSA/ECB/NoPadding");

            // Crear KeyFactory (depende del provider) usado para las transformaciones de claves*/
            KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA"); // Hace uso del provider BC

            // 4.2 Recuperar clave publica desde datos codificados en formato X509
            X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(Base64.getUrlDecoder().decode(base64Publica));
            PublicKey clavePublica2 = keyFactoryRSA.generatePublic(clavePublicaSpec);

            
            //descifrar mensaje
            httpPost = new HttpPost("http://localhost:8084/encriptacionServidor/rsa");
            nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("op", "CIFRADO"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            response2 = httpclient.execute(httpPost);
            entity = response2.getEntity();

            base64Publica = EntityUtils.toString(entity, "UTF-8");
            
            cifrador.init(Cipher.DECRYPT_MODE, clavePublica2);  // Cifra con la clave publica
             
            System.out.println(new String(cifrador.doFinal(Base64.getUrlDecoder().decode(base64Publica))));

        } catch (Exception ex) {
            Logger.getLogger(ClienteWeb.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }

}
