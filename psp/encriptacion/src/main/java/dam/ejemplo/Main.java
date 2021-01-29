package dam.ejemplo;


import com.google.common.io.Files;
import com.google.common.primitives.Bytes;
import com.google.gson.Gson;

import dam.Cifrado;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Main {

    public static void main(String[] args) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte[] clave = new byte[16];
        sr.nextBytes(clave);
        String claveSimetrica = Base64.getUrlEncoder().encodeToString(clave);

        Cifrado c = new Cifrado();

        String mensaje = "super confidencial";

        byte[] iv = new byte[12];
        byte[] salt = new byte[16];

        sr.nextBytes(iv);
        sr.nextBytes(salt);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

        c.setIv(Base64.getUrlEncoder().encodeToString(iv));
        c.setSalt(Base64.getUrlEncoder().encodeToString(salt));
        c.setIteraciones(65536);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // en el jdk8 esta limitado a 128 bits, desde el 9 puede ser de 256
        KeySpec spec = new PBEKeySpec(claveSimetrica.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");


        Cipher cipher = Cipher.getInstance("AES/GCM/noPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        c.setMensaje(Base64.getUrlEncoder().encodeToString(
                cipher.doFinal(mensaje.getBytes("UTF-8"))));

        String nombre = "albertoastudillo";

        // Anadir provider JCE (provider por defecto no soporta RSA)
        //Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cifrador = Cipher.getInstance("RSA");

        // Crear KeyFactory (depende del provider) usado para las transformaciones de claves*/
        KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA"); // Hace uso del provider BC
        //*** 4 Recuperar clave PUBLICA del fichero */
        // 4.1 Leer datos binarios x809
        byte[] bufferPub = new byte[5000];
        FileInputStream in = new FileInputStream(nombre + ".publica");
        DataInputStream d = new DataInputStream(in);

        int charsPub = in.read(bufferPub, 0, 5000);
        in.close();

        byte[] bufferPub2 = new byte[charsPub];
        System.arraycopy(bufferPub, 0, bufferPub2, 0, charsPub);


        // 4.2 Recuperar clave publica desde datos codificados en formato X509
        X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub2);
        PublicKey clavePublicaAlberto = keyFactoryRSA.generatePublic(clavePublicaSpec);

        cifrador.init(Cipher.ENCRYPT_MODE, clavePublicaAlberto);  // Cifra con la clave publica
        c.setClaveSimetrica(Base64.getUrlEncoder()
                .encodeToString(cifrador.doFinal(claveSimetrica.getBytes())));


        String miNombre = "juancarlos";
        byte[] bufferPriv = new byte[5000];
        in = new FileInputStream(miNombre + ".privada");
        int chars = in.read(bufferPriv, 0, 5000);
        in.close();

        byte[] bufferPriv2 = new byte[chars];
        System.arraycopy(bufferPriv, 0, bufferPriv2, 0, chars);

        // 2.2 Recuperar clave privada desde datos codificados en formato PKCS8
        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferPriv2);

        PrivateKey miClavePrivada = keyFactoryRSA.generatePrivate(clavePrivadaSpec);

        Signature sign = Signature.getInstance("SHA256WithRSA");
        sign.initSign(miClavePrivada);
        sign.update(mensaje.getBytes());
        c.setFirma(Base64.getUrlEncoder().encodeToString(sign.sign()));

        Gson g = new Gson();

//        String json  = g.toJson(c);
        BufferedWriter f = Files.newWriter(Paths.get("prueba.txt").toFile(), Charset.defaultCharset());
        g.toJson(c, f);
        f.close();

        //Files.write(json.getBytes(),Paths.get("prueba.txt").toFile());





    }
}
