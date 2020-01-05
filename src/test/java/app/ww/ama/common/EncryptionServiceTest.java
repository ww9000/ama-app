package app.ww.ama.common;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.crypto.tink.BinaryKeysetReader;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.KeysetReader;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.proto.KeyTemplate;

import app.ww.ama.context.CommonConfiguration;

@PowerMockIgnore({ "javax.crypto.*", "javax.management.*" })
@PrepareForTest({ BinaryKeysetReader.class, CleartextKeysetHandle.class })
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CommonConfiguration.class })
public class EncryptionServiceTest {

	@Autowired
	EncryptionService encryptionSvc;

	@Mock
	File fileMock;

	@Mock
	KeysetReader keysetReaderMock;

	private final KeyTemplate KEY_TEMPLATE = AeadKeyTemplates.AES128_GCM;
	private final String keyPath = "";
	private KeysetHandle keysetHandle;
	private KeysetHandle wrongKeysetHandle;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		keysetHandle = KeysetHandle.generateNew(KEY_TEMPLATE);
		wrongKeysetHandle = KeysetHandle.generateNew(KEY_TEMPLATE);
		PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(fileMock);
		PowerMockito.mockStatic(BinaryKeysetReader.class);
		PowerMockito.mockStatic(CleartextKeysetHandle.class);
	}

	@Test
	public void testEncryptDecryptSuccess() throws GeneralSecurityException, IOException {
		PowerMockito.when(BinaryKeysetReader.withFile(Matchers.any())).thenReturn(keysetReaderMock);
		PowerMockito.when(CleartextKeysetHandle.read(Matchers.any(KeysetReader.class))).thenReturn(keysetHandle);
		String plainText = "plaintextstring";
		String encrypted = encryptionSvc.encrypt(plainText, keyPath);
		String decrypted = encryptionSvc.decrypt(encrypted, keyPath);

		assertEquals(plainText, decrypted);
	}

	@Test(expected = GeneralSecurityException.class)
	public void testEncryptDecryptFailWrongKey() throws GeneralSecurityException, IOException {
		PowerMockito.when(BinaryKeysetReader.withFile(Matchers.any())).thenReturn(keysetReaderMock);
		PowerMockito.when(CleartextKeysetHandle.read(Matchers.any(KeysetReader.class))).thenReturn(keysetHandle);
		String plainText = "plaintextstring";
		String encrypted = encryptionSvc.encrypt(plainText, keyPath);
		PowerMockito.when(CleartextKeysetHandle.read(Matchers.any(KeysetReader.class))).thenReturn(wrongKeysetHandle);
		encryptionSvc.decrypt(encrypted, keyPath);
	}
}
