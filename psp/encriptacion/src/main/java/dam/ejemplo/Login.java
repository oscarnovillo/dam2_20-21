package dam.ejemplo;

import dam.asimetrico.ClienteWebCert;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login {


    public static void main(String[] args) {

        CloseableHttpClient httpclient;
        httpclient = HttpClients.createDefault();
        //pedir clave publica
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/encriptacionServidor-1.0-SNAPSHOT/pfx");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();

            nvps.add(new BasicNameValuePair("op", "LOGIN"));

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); // Hace uso del provider BC
            keyGen.initialize(2048);  // tamano clave 512 bits
            KeyPair clavesRSA = keyGen.generateKeyPair();
            PrivateKey clavePrivada = clavesRSA.getPrivate();
            PublicKey clavePublica = clavesRSA.getPublic();

            nvps.add(new BasicNameValuePair("clavePublica", Base64.getUrlEncoder().encodeToString(clavePublica.getEncoded())));

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            HttpEntity entity = response2.getEntity();


            String base64Publica = EntityUtils.toString(entity, "UTF-8");

            //Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
            char[] password = "abc".toCharArray();
            ByteArrayInputStream input = new ByteArrayInputStream(java.util.Base64.getUrlDecoder().decode(base64Publica));
            KeyStore ksLoad = KeyStore.getInstance("PKCS12");
            ksLoad.load(input, "".toCharArray());

            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate("publica");


            System.out.println(certLoad.getSubjectX500Principal());
            System.out.println(certLoad.getIssuerX500Principal());

            String dn = certLoad.getSubjectX500Principal().getName();
            LdapName ldapDN = new LdapName(dn);
            for (Rdn rdn : ldapDN.getRdns()) {
                if (rdn.getType().equals("CN")) {
                    System.out.println(rdn.getValue());
                }
            }

            byte[] bufferPub = new byte[5000];
            FileInputStream in = new FileInputStream("dam1024.publica");
            int chars = in.read(bufferPub, 0, 5000);
            in.close();

            byte[] bufferPub2 = new byte[chars];
            System.arraycopy(bufferPub, 0, bufferPub2, 0, chars);
            KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA");
            // 4.2 Recuperar clave publica desde datos codificados en formato X509
            X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub);
            PublicKey clavePublicaServidor = keyFactoryRSA.generatePublic(clavePublicaSpec);

            certLoad.verify(clavePublicaServidor);


//
//
//            //descifrar mensaje
//            httpPost = new HttpPost("http://localhost:8080/encriptacionServidor/pfx");
//            nvps = new ArrayList<NameValuePair>();
//
//            nvps.add(new BasicNameValuePair("op", "MANDAR"));
//            //mandar certificado
//            nvps.add(new BasicNameValuePair("cert", Base64.getUrlEncoder().encodeToString(certLoad.getEncoded())));
//
//            //mandar texto
//            nvps.add(new BasicNameValuePair("texto", "firmado"));
//
//            //mandar firma
//            Signature sign = Signature.getInstance("SHA256WithRSA");
//            sign.initSign(keyLoad);
//            sign.update("firmado".getBytes());
//            byte[] firma = sign.sign();
//            nvps.add(new BasicNameValuePair("firma", Base64.getUrlEncoder().encodeToString(firma)));
//
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//            response2 = httpclient.execute(httpPost);
//            entity = response2.getEntity();




        } catch (Exception ex) {
            Logger.getLogger(ClienteWebCert.class.getName()).log(Level.SEVERE, null, ex);
        }




    }
}
