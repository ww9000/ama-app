package app.ww.ama.common;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.google.crypto.tink.BinaryKeysetReader;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadFactory;

import app.ww.ama.service.AbstractService;

@Service
public class EncryptionService extends AbstractService implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		AeadConfig.register();
	}

	public String encrypt(String plainText, String keyPath) throws GeneralSecurityException, IOException {
		logger.debug("Encrypting..");
		KeysetHandle keysetHandle = getKeysetHandle(keyPath);
		byte[] cipherText = AeadFactory.getPrimitive(keysetHandle).encrypt(plainText.getBytes(), null);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decrypt(String encrypted, String keyPath) throws GeneralSecurityException, IOException {
		logger.debug("Decrypting..");
		KeysetHandle keysetHandle = getKeysetHandle(keyPath);
		byte[] cipherText = AeadFactory.getPrimitive(keysetHandle).decrypt(Base64.getDecoder().decode(encrypted), null);
		return new String(cipherText);
	}

	private KeysetHandle getKeysetHandle(String keyPath) throws GeneralSecurityException, IOException {
		return CleartextKeysetHandle.read(BinaryKeysetReader.withFile(new File(keyPath)));
	}

}
