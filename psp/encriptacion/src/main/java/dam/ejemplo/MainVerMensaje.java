package dam.ejemplo;

import com.google.common.io.Files;
import com.google.gson.Gson;
import dam.Cifrado;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class MainVerMensaje {

    public static void main(String[] args) throws Exception{

        //soy alberto astudillo

        Gson g = new Gson();

        Cifrado c = g.fromJson(Files.newReader(Paths.get("prueba.txt").toFile(),
                Charset.defaultCharset()), Cifrado.class);

        //sacar clave simetrica la mia
        KeyFactory keyFactoryRSA = KeyFactory.getInstance("RSA");
        String miNombre = "albertoastudillo";
        byte[] bufferPriv = new byte[5000];
        FileInputStream in = new FileInputStream(miNombre + ".privada");
        int chars = in.read(bufferPriv, 0, 5000);
        in.close();

        byte[] bufferPriv2 = new byte[chars];
        System.arraycopy(bufferPriv, 0, bufferPriv2, 0, chars);

        // 2.2 Recuperar clave privada desde datos codificados en formato PKCS8
        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(bufferPriv2);

        PrivateKey miClavePrivada = keyFactoryRSA.generatePrivate(clavePrivadaSpec);

        Cipher cifrador = Cipher.getInstance("RSA");
        cifrador.init(Cipher.DECRYPT_MODE, miClavePrivada);

        String claveSimetrica = new String(cifrador.doFinal(
                Base64.getUrlDecoder().decode(c.getClaveSimetrica())));

        System.out.println(claveSimetrica);

        //desencriptar mensaje
        byte[] iv = Base64.getUrlDecoder().decode(c.getIv());
        byte []salt = Base64.getUrlDecoder().decode(c.getSalt());
        int numeroIteraciones = c.getIteraciones();

        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(claveSimetrica.toCharArray(), salt, numeroIteraciones,256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/GCM/noPADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
        String mensaje = new String(cipher.doFinal(
                Base64.getUrlDecoder().decode(c.getMensaje())));

        System.out.println(mensaje);

        //Probar Firma
        byte[] bufferPub = new byte[5000];
        in = new FileInputStream("juancarlos.publica");
        DataInputStream d = new DataInputStream(in);

        int charsPub = in.read(bufferPub, 0, 5000);
        in.close();

        byte[] bufferPub2 = new byte[charsPub];
        System.arraycopy(bufferPub, 0, bufferPub2, 0, charsPub);


        // 4.2 Recuperar clave publica desde datos codificados en formato X509
        X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(bufferPub2);
        PublicKey clavePublicaJuanCarlos = keyFactoryRSA.generatePublic(clavePublicaSpec);


        Signature sign = Signature.getInstance("SHA256WithRSA");


        sign.initVerify(clavePublicaJuanCarlos);
        sign.update(mensaje.getBytes());
        System.out.println(sign.verify(Base64.getUrlDecoder().decode(c.getFirma())));



    }
}
