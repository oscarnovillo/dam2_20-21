/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dam.asimetrico;

import com.nimbusds.jose.util.X509CertUtils;
import dam.encriptacion.PasswordHash;
//import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
//import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
//import sun.security.tools.keytool.CertAndKeyGen;
//import sun.security.x509.X500Name;
//import sun.security.x509.X500Name;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

//import sun.security.tools.keytool.CertAndKeyGen;
//import sun.security.x509.X500Name;

/**
 *
 * @author oscar
 */
public class Cert {

    public static void main(String[] args) throws Exception{
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); // Hace uso del provider BC
        keyGen.initialize(2048);  // tamano clave 512 bits
        KeyPair clavesRSA = keyGen.generateKeyPair();
        PrivateKey clavePrivada = clavesRSA.getPrivate();
        PublicKey clavePublica = clavesRSA.getPublic();

        X509V3CertificateGenerator cert1 = new X509V3CertificateGenerator();
        cert1.setSerialNumber(BigInteger.valueOf(1));   //or generate a random number
        cert1.setSubjectDN(new X509Principal("CN=Oscar"));  //see examples to add O,OU etc
        cert1.setIssuerDN(new X509Principal("CN=Oscar")); //same since it is self-signed
        cert1.setPublicKey(clavesRSA.getPublic());
        cert1.setNotBefore(
                Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC)));
        cert1.setNotAfter(new Date());
        cert1.setSignatureAlgorithm("SHA1WithRSAEncryption");
        PrivateKey signingKey = clavesRSA.getPrivate();


        X509Certificate cert =  cert1.generate(signingKey);

        String certificado = Base64.getUrlEncoder().encodeToString(cert.getEncoded());



        X509Certificate certBase64 = X509CertUtils.parse(Base64.getUrlDecoder().decode(certificado));

        System.out.println("FUNCIONA" +certBase64.getIssuerX500Principal());






//        cert.verify(clavePublica);
//        X500Name x500Name = new X500Name("CN=***.com, OU=Security&Defense, O=*** Crypto., L=Ottawa, ST=Ontario, C=CA");
//        SubjectPublicKeyInfo pubKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
//        final Date start = new Date();
//        final Date until = Date.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC));
//        final X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(x500Name,
//                new BigInteger(10, new SecureRandom()), start,   until,  x500Name,  pubKeyInfo
//        );
//        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
//
//        Certificate certificate = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider()).getCertificate(certificateBuilder.build(contentSigner));
//
//        System.out.println("x.509 certificate is successfully generated!");

//        X509Certificate caCert = X509CertificateBuilder.fromKeyPair(ca)
//                .subject(new X500Principal("CN=ca"))
//                .validity(Instant.now(), Instant.now().plus(Period.ofDays(3650)))
//                .addExtension("BasicConstraints", true, "")
//                .signatureAlgorithm("SHA256withRSA")
//                .selfSign();
        try {
            // Anadir provider JCE (provider por defecto no soporta RSA)
//            Security.addProvider(new BouncyCastleProvider());  // Cargar el provider BC
//
//                    final X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(x500Name,
//                new BigInteger(10, new SecureRandom()), start,   until,  x500Name,  pubKeyInfo
//        );
//            CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
//            // generate it with 2048 bits
//            certGen.generate(2048);
//            // prepare the validity of the certificate
//            long validSecs = (long) 365 * 24 * 60 * 60; // valid for one year
//            // add the certificate information, currently only valid for one year.
//
//            X509Certificate cert = certGen.getSelfCertificate(
//                    // enter your details according to your application
//                    new X500Name("CN=Pedro Salazar,O=My Organisation,L=My City,C=DE"), validSecs);



            PrivateKey pk = clavesRSA.getPrivate();
            PublicKey publicKey = clavesRSA.getPublic();
            System.out.println(cert.getIssuerX500Principal());

            String dn = cert.getSubjectX500Principal().getName();
            LdapName ldapDN = new LdapName(dn);
            for (Rdn rdn : ldapDN.getRdns()) {
                if (rdn.getType().equals("CN")) {
                    System.out.println(rdn.getValue());
                }
            }

            //KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA", "BC"); // Hace uso del provider BC
            //generadorRSA.initialize(1024);
//            KeyPair clavesRSA = null;
//            PrivateKey clavePrivada = null;
//            PublicKey clavePublica = null;

            KeyStore ks = KeyStore.getInstance("PKCS12");
            char[] password = PasswordHash.createHash("abc").toCharArray();
            ks.load(null, null);
            ks.setCertificateEntry("publica", cert);
            ks.setKeyEntry("privada", pk, password, new Certificate[]{cert});
            FileOutputStream fos = new FileOutputStream("keystore.pfx");
            ks.store(fos, password);
            fos.close();

            //leer fichero
            KeyStore ksLoad = KeyStore.getInstance("PKCS12");
            ksLoad.load(new FileInputStream("keystore.pfx"), password);

            X509Certificate certLoad = (X509Certificate) ksLoad.getCertificate("publica");
            KeyStore.PasswordProtection pt = new KeyStore.PasswordProtection(password);
            PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) ksLoad.getEntry("privada", pt);
            PrivateKey keyLoad = (PrivateKey) privateKeyEntry.getPrivateKey();

            System.out.println(certLoad.getIssuerX500Principal());
            System.out.println(certLoad.getSubjectX500Principal());
            //certLoad.verify(clavePublica);

            dn = certLoad.getSubjectX500Principal().getName();
            ldapDN = new LdapName(dn);
            for (Rdn rdn : ldapDN.getRdns()) {
                if (rdn.getType().equals("CN")) {
                    System.out.println(rdn.getValue());
                }
            }

            clavesRSA = new KeyPair(certLoad.getPublicKey(), keyLoad);

            Cipher cifrador = Cipher.getInstance("RSA");
            cifrador.init(Cipher.ENCRYPT_MODE, clavesRSA.getPrivate());
            cifrador.doFinal("hola".getBytes());

            Signature sign = Signature.getInstance("SHA256WithRSA");

            sign.initSign(clavesRSA.getPrivate());

            MessageDigest hash = MessageDigest.getInstance("SHA-512");

            sign.update(hash.digest("hola".getBytes()));
            byte[] firma = sign.sign();

            sign.initVerify(certLoad.getPublicKey());
            sign.update(hash.digest("hola".getBytes()));
            System.out.println(sign.verify(firma));

            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                Certificate cert2 = cf.generateCertificate(new ByteArrayInputStream(certLoad.getEncoded()));

                System.out.println(cert2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }



        } catch (Exception ex) {
            Logger.getLogger(Cert.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
