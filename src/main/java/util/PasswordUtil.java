package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    // ============================
    // 既存の SHA-256
    // ============================
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ============================
    // PBKDF2（推奨）
    // ============================

    private static final int ITERATIONS = 20000; // ストレッチング回数
    private static final int KEY_LENGTH = 256;   // 生成するハッシュの長さ（ビット）

    // ランダムソルト生成
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    // PBKDF2 ハッシュ生成
    public static String hashWithPBKDF2(String password, String salt) {
        try {
            KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                hexToBytes(salt),
                ITERATIONS,
                KEY_LENGTH
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return bytesToHex(hash);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ============================
    // ユーティリティ
    // ============================

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
}