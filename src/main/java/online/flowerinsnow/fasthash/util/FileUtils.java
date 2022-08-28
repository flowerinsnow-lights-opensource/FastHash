package online.flowerinsnow.fasthash.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;

public final class FileUtils {
    public static void forEachAllFiles(File root, Consumer<File> action) {
        //noinspection ConstantConditions
        for (File file : root.listFiles()) {
            if (file.isFile()) {
                action.accept(file);
            } else if (file.isDirectory()) {
                forEachAllFiles(file, action);
            }
        }
    }

    public static boolean hashAndWrite(Path path, String algorithm) {
        String[] algs = algorithm.split(":");
        for (String alg : algs) {
            try {
                String hash = HashUtils.getFileHash(alg, path); // 解析的哈希值
                // SHA-256 of file abc.jar is xxxxxxxx
                System.out.printf("%s of file \"%s\" is %s\n", alg, path, hash);
                Path parent = path.getParent();
                if (parent == null) {
                    parent = Path.of("");
                }
                Path newFile = Path.of(parent.toString(), path.getFileName() + "." + alg.replace("-", "").toLowerCase()); // 创建的文件
                try {
                    Files.createFile(newFile);
                } catch (FileAlreadyExistsException ignored) {
                } catch (IOException ex) {
                    System.err.printf("Cannot create file \"%s\"\n", newFile);
                    System.err.println("Skipped");
                    return false;
                }

                try (PrintWriter pw = new PrintWriter(Files.newOutputStream(newFile))) {
                    // 不要换行
                    pw.print(hash);
                    pw.flush();
                } catch (IOException ex) {
                    // Cannot write file "abc.jar.sha1": java.io.IOException......
                    System.err.printf("Cannot write file \"%s\": %s\n", newFile, ex);
                    System.err.println("Skipped");
                    return false;
                }
            } catch (NoSuchAlgorithmException e) {
                // No Algorithm named SHA-123
                System.err.printf("No Algorithm named \"%s\"\n", alg);
                System.exit(-1);
                return false;
            } catch (IOException e) {
                // Cannot open file "abc.jar": java.io.FileNotFoundException......
                System.err.printf("Cannot open file \"%s\": %s\n", path, e);
                System.err.println("Skipped");
                return false;
            }
        }
        return true;
    }

    private FileUtils() {
    }
}
