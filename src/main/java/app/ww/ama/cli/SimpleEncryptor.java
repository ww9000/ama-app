package app.ww.ama.cli;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import java.util.Scanner;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.BinaryKeysetReader;
import com.google.crypto.tink.BinaryKeysetWriter;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadFactory;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.proto.KeyTemplate;

public class SimpleEncryptor {

	private static final KeyTemplate KEY_TEAMPLATE = AeadKeyTemplates.AES128_GCM;

	private static final String EG_FILEPATH = "eg. /home/myfolder/secret.key";

	private static Scanner scanner;
	
	private static Scanner getScanner() {
		if(scanner == null)
			return new Scanner(System.in);
		else
			return scanner;
	}
	
	private enum Cmd {
		CREATE("1", "Create a key file with given file path"),
		ENCRYPT("2", "Encrypt an input string using a given key file"),
		DECRYPT("3", "Decrypt an input string using a given key file");
		
		public String value;
		public String description;
		
		private Cmd(String value, String description) {
			this.value = value;
			this.description = description;
		}
	}

	public static void main(String[] args) throws GeneralSecurityException, IOException {
		enterMenu(args);
	}

	public static void enterMenu(String[] args) throws GeneralSecurityException, IOException {
		AeadConfig.register();

		System.out.println("Choose one of the following options:");
		for (Cmd cmd : Cmd.values())
			System.out.println(String.format("%1$-3s", String.valueOf(cmd.value) + ".") + String.format("%1$-12s", cmd) + cmd.description);
		
		String input = getScanner().nextLine();
		if(input.equals(Cmd.CREATE.value)) {
			createKey();
		} else if(input.equals(Cmd.ENCRYPT.value)) {
			encrypt();
		} else if(input.equals(Cmd.DECRYPT.value)) {
			decrypt();
		} else {
			System.out.println("Invalid input, exiting.");
		}
		getScanner().close();
	}
	
	private static void createKey() throws GeneralSecurityException, IOException {
		System.out.println("Enter file path to save generated key: (" + EG_FILEPATH + ")");
		String filePath = scanner.nextLine();
		getScanner().close();
		
		System.out.println("Creating key: " + filePath);
		KeysetHandle keysetHandle = KeysetHandle.generateNew(KEY_TEAMPLATE);
		filePath = filePath.replace("\\", "\\\\");
		CleartextKeysetHandle.write(keysetHandle, BinaryKeysetWriter.withFile(new File(filePath)));
		System.out.println("Key created: " + filePath);
	}

	private static void encrypt() throws GeneralSecurityException, IOException {
		// Load key
		System.out.println("Enter file path to load key: (" + EG_FILEPATH + ")");
		String filePath = getScanner().nextLine();
		System.out.println("Loading key...");
		KeysetHandle keysetHandle = CleartextKeysetHandle.read(BinaryKeysetReader.withFile(new File(filePath)));
		System.out.println("Key loaded.");

		// Encrypt
		System.out.println("Enter string to encrypt.");
		String input = getScanner().nextLine();
		System.out.println("Encrypting text...");
		Aead aead = AeadFactory.getPrimitive(keysetHandle);
		byte[] cipherText = aead.encrypt(input.getBytes(), null);
		String encrypted = Base64.getEncoder().encodeToString(cipherText);
		System.out.println("Encrypted ciphertext:" + encrypted);
	}

	private static void decrypt() throws GeneralSecurityException, IOException {
		// Load key
		System.out.println("Enter file path to load key: (" + EG_FILEPATH + ")");
		String filePath = getScanner().nextLine();
		System.out.println("Loading key...");
		KeysetHandle keysetHandle = CleartextKeysetHandle.read(BinaryKeysetReader.withFile(new File(filePath)));
		System.out.println("Key loaded.");
		
		// Decrypt
		System.out.println("Enter string to decrypt.");
		String encrypted = getScanner().nextLine();
		System.out.println("Encrypting text...");
		Aead aead = AeadFactory.getPrimitive(keysetHandle);
		byte[] cipherText = aead.decrypt(Base64.getDecoder().decode(encrypted), null);
		String output = new String(cipherText);
		System.out.println("Decrypted ciphertext:" + output);
	}
}
