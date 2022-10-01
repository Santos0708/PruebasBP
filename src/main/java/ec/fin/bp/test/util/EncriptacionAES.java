package ec.fin.bp.test.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class EncriptacionAES {
	private static final byte[] iv = new byte[16];
	private static final IvParameterSpec ips = new IvParameterSpec(iv);

	private static SecretKeySpec generaClave(String secretKey)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] claveEncriptacion = secretKey.getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		claveEncriptacion = sha.digest(claveEncriptacion);
		claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
		SecretKeySpec rersulSecretKey = new SecretKeySpec(claveEncriptacion, "AES");
		return rersulSecretKey;
	}

	public String encriptar(String datos, String claveSecreta)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		SecretKeySpec secretKey = null;
		if (claveSecreta != null) {
			secretKey = generaClave(claveSecreta);
		}
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(1, secretKey, ips);
		byte[] datosEncriptar = datos.getBytes("UTF-8");
		byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
		String encriptado = Base64.encodeBase64String(bytesEncriptados);
		return encriptado;
	}
}
