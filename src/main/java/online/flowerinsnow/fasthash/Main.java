package online.flowerinsnow.fasthash;

import online.flowerinsnow.fasthash.util.HashUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Params: <Algorithm> <File1> [File2] [File3]...");
            System.out.println("e.g: fasthash SHA-1 abc.jar def.zip");
            System.exit(1);
            return;
        }

        long start = System.currentTimeMillis();

        for (int i = 1; i < args.length; i++) {
            // args[0]: 哈希方法
            // args[i]: 文件名
            // 生成文件：拿SHA-1举例，去除其横线，并转换为小写
            try {
                String hash = HashUtils.getFileHash(args[0], new File(args[i])); // 解析的哈希值
                String newFile = args[i] + "\\" + args[0].replace("-", "").toLowerCase(); // 创建的文件
                File file = new File(newFile); // 创建的文件
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException ignored) {
                    }
                }
                try (PrintWriter pw = new PrintWriter(file)) {
                    // 不要换行
                    pw.print(hash);
                    pw.flush();
                } catch (IOException ex) {
                    // Cannot write file "abc.jar.sha1": java.io.IOException......
                    System.err.printf("Cannot write file \"%s\": %s\n", newFile, ex);
                    System.err.println("Skipped");
                }
            } catch (NoSuchAlgorithmException e) {
                // No Algorithm named SHA-123
                System.err.printf("No Algorithm named \"%s\"\n", args[0]);
                System.exit(-1);
                return;
            } catch (IOException e) {
                // Cannot open file "abc.jar": java.io.FileNotFoundException......
                System.err.printf("Cannot open file \"%s\": %s\n", args[1], e);
                System.err.println("Skipped");
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("OK. Done in " + (end - start) + "ms.");
    }
}
