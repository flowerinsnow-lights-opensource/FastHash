package online.flowerinsnow.fasthash.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    // 16进制依次字符
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static String getFileHash(String algorithm, Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        try (FileChannel channel = FileChannel.open(path)) {
            ByteBuffer bb = ByteBuffer.allocate(1024);
            while (channel.read(bb) != -1) {
                bb.flip();
                md.update(bb);
                bb.clear();
            }
        }
        byte[] result = md.digest();
        return byteArrayToHEXString(result);
    }

    public static String byteArrayToHEXString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            // 单个byte为8个字节
            int left = (((int) b) >>> 4) & 0xF; // 取左边4位
            int right = ((int) b) & 0xF; // 取右边4位
            sb.append(HEX[left]).append(HEX[right]); // 转换为对应16进制字符
        }
        return sb.toString();
    }
}
