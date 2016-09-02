/**
 * 
 */
package com.nisum.employee.ref.util;

/**
 * @author NISUM
 *
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nisum.employee.ref.exception.ServiceException;

@Component
public class EnDecryptUtil {

	private final String CYPHER_TRANSFORMATION = "DES/CBC/PKCS5Padding";
	private final String ALGORITHM = "DES";
	private final String ENCODING_TYPE = "ISO-8859-1";
	private Cipher deCipher;
	private Cipher enCipher;

	@Value("${enc.key}")
	private String key;

	@Value("${enc.algParamSpec}")
	private String algParamSpec;

	@PostConstruct
	public void init() throws ServiceException {
		try {
			deCipher = Cipher.getInstance(CYPHER_TRANSFORMATION);
			enCipher = Cipher.getInstance(CYPHER_TRANSFORMATION);
			SecretKeySpec secKey = new SecretKeySpec(new DESKeySpec(
					key.getBytes()).getKey(), ALGORITHM);
			IvParameterSpec ivSpec = new IvParameterSpec(
					algParamSpec.getBytes());
			enCipher.init(Cipher.ENCRYPT_MODE, secKey, ivSpec);
			deCipher.init(Cipher.DECRYPT_MODE, secKey, ivSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new ServiceException(e);
		}
	}

	public String encrypt(String value) throws ServiceException {
		String returnBytes = null;
		try {
			if (SystemUtils.IS_OS_LINUX) {
				returnBytes = new String(
						enCipher.doFinal(convertToByteArray(value)),
						ENCODING_TYPE);
			} else {
				returnBytes = new String(
						enCipher.doFinal(convertToByteArray(value)));
			}

		} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
			throw new ServiceException(e);
		}
		return returnBytes;
	}

	public String decrypt(String encrypted) throws ServiceException {
		String returnObj = null;
		try {
			if (SystemUtils.IS_OS_LINUX) {
				returnObj = (String) convertFromByteArray(deCipher
						.doFinal(encrypted.getBytes(ENCODING_TYPE)));
			} else {
				returnObj = (String) convertFromByteArray(deCipher
						.doFinal(encrypted.getBytes()));
			}
		} catch (ClassNotFoundException | IllegalBlockSizeException
				| BadPaddingException | IOException e) {
			throw new ServiceException(e);
		}
		return returnObj;
	}

	private Object convertFromByteArray(byte[] byteObject) throws IOException,
			ClassNotFoundException {
		Object o = null;
		try (ObjectInputStream in = new ObjectInputStream(
				new ByteArrayInputStream(byteObject))) {
			o = in.readObject();
		} catch (Exception exp) {
			throw exp;
		}
		return o;
	}

	private byte[] convertToByteArray(Object complexObject) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
			out.writeObject(complexObject);
		} catch (IOException exp) {
			throw exp;
		}
		return baos.toByteArray();
	}
}
