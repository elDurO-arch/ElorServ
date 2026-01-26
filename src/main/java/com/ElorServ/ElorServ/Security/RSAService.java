package com.ElorServ.ElorServ.Security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAService {
	private KeyPair keyPair;
	
	public RSAService() {
        try {
            // Generamos las claves RSA (1024 o 2048 bits) al arrancar
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            this.keyPair = keyGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//esto genera la  Clave Pública en  para enviarla al cliente
    public String getPublicKeyAsString() {
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }
    
 //descifrar un mensaje (contraseña) usando la Clave Privada
    public String decrypt(String mensajeCifradoBase64) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

        // Convertimos el texto Base64 a bytes reales
        byte[] bytesCifrados = Base64.getDecoder().decode(mensajeCifradoBase64);
        
        // Desciframos
        byte[] bytesDescifrados = cipher.doFinal(bytesCifrados);
        
        return new String(bytesDescifrados);
    }

}
