package online.flowerinsnow.fasthash;

import online.flowerinsnow.fasthash.util.FileUtils;
import online.flowerinsnow.fasthash.util.RegexUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.regex.Pattern;

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
            // args[0]: 哈希算法
            // args[i]: 文件名
            // 生成文件：拿SHA-1举例，去除其横线，并转换为小写
            final boolean[] anyone = {false};
            String algorithm = args[0];
            if (args[1].contains("?") || args[1].contains("*")) { // 包含通配符
                // 仅在当前目录下查找
                Pattern pattern = RegexUtils.parseWildcard(args[i]);
                FileUtils.forEachAllFiles(new File(""), f -> {
                    if (pattern.matcher(f.getPath()).matches()) {
                        if (FileUtils.hashAndWrite(f.toPath(), algorithm)) {
                            anyone[0] = true;
                        }
                    }
                });
            } else {
                if (FileUtils.hashAndWrite(Path.of(args[i]), algorithm)) {
                    anyone[0] = true;
                }
            }

            if (!anyone[0]) {
                System.err.println("Cannot hash file \"" + args[i] + "\"");
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("OK. Done in " + (end - start) + "ms.");
    }
}
