package org.school.userandsecurity.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import org.openframework.commons.config.service.as.MessageResourceAS;
import org.openframework.commons.constants.CommonConstants;
import org.school.userandsecurity.constant.EncryptionConstants;
import org.school.userandsecurity.constant.UserSecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

	/** Logger that is available to subclasses in same package */
	final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

	@Inject
	Optional<MessageResourceAS> messageResourceAS;

	public String encrypt(String toBeEncrypt) {

		Properties properties = getProperties();
		return encrypt(toBeEncrypt, properties);
	}

	public String encrypt(String toBeEncrypt, Properties properties) {

		String result = null;
		try {

			Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, properties);
			if (null == cipher) {
				return null;
			}

			byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes());
			result = new String(Base64.getEncoder().encode(encrypted), CommonConstants.ENCODING_UTF8);

		} catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException ex) {
			logger.error(String.format("Error caught in encrypt(), for toBeEncrypt: %s ", toBeEncrypt), ex);
		}
		return result;
	}

	private Cipher getCipher(int mode, Properties properties) {

		Cipher cipher = null;
		try {
			String iv = properties.getProperty(EncryptionConstants.IV);
			String secretKey = properties.getProperty(EncryptionConstants.PROP_SECRET_KEY);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(CommonConstants.ENCODING_UTF8));
			SecretKeySpec secretKeySpec = new SecretKeySpec(generateKey(secretKey, properties),
					properties.getProperty(EncryptionConstants.ENCRYPTION_DECRYPTION_ALGORITHM));
			cipher = Cipher.getInstance(properties.getProperty(EncryptionConstants.CIPHER_TRANSFORMATION));

			cipher.init(mode, secretKeySpec, ivParameterSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException
				| InvalidKeyException | UnsupportedEncodingException ex) {
			logger.error(String.format("Error in getCipher(), for mode: [%s] and [%s] properties: ", mode, properties),
					ex);
		}
		return cipher;
	}

	/**
	 * This method provides sha-256 hash key.
	 * 
	 * @param key
	 *            key to Digest
	 * @return hash key
	 * @throws GeneralSecurityException
	 *             General Security
	 * @throws UnsupportedEncodingException
	 *             wrong Encoding
	 */
	public byte[] generateKey(String key, Properties properties) {
		byte[] binary = null;
		try {
			binary = key.getBytes(CommonConstants.ENCODING_UTF8);
			MessageDigest sha = MessageDigest
					.getInstance(properties.getProperty(EncryptionConstants.SHA_ALGORITHM_256));
			binary = sha.digest(binary);
			binary = Arrays.copyOf(binary, EncryptionConstants.SHA_KEY_BIT);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			logger.error(String.format("Error caught in generateKey(), for key: %s", key), e);
		}
		return binary;
	}

	public String decrypt(String encrypted, Properties properties) {

		String result = null;
		try {

			Cipher cipher = getCipher(Cipher.DECRYPT_MODE, properties);
			if (null == cipher) {
				return null;
			}

			byte[] cipherText = Base64.getDecoder().decode(encrypted.getBytes(CommonConstants.ENCODING_UTF8));

			result = new String(cipher.doFinal(cipherText), CommonConstants.ENCODING_UTF8);
		} catch (BadPaddingException | IllegalBlockSizeException | IllegalArgumentException
				| UnsupportedEncodingException ex) {
			logger.error(String.format("Error caught in encrypt(), for toBeEncrypt: %s ", encrypted), ex);
		}
		return result;
	}

	public String decrypt(String encrypted) {

		Properties properties = getProperties();
		return decrypt(encrypted, properties);
	}

	private Properties getProperties() {

		Properties properties = null;
		if(messageResourceAS.isPresent()) {
			properties = messageResourceAS.get().getAppConfigsMap().get(UserSecurityConstants.ENCRYPTION);
		} else {
			// TODO provide default properties if not found in messageResourceAS
			properties = new Properties();
		}
		return properties;
	}
}
