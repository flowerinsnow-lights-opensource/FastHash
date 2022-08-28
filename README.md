# 项目
好多系统不支持自动生成哈希文件，手写又太慢了，本程序可以一键生成

# 生成
会以`<文件名>.<哈希算法名>`作为文件名生成在同一目录下，例`abc.jar.sha1`

# 通配符
感谢[Issues中的建议](https://github.com/flowerinsnowdh/FastHash/issues/1)  
通配符`?`代表任意一个字符 `*`代表任意任意个字符[0,∞)  
<strong>因为我不会写了，所以仅当当前目录作为主目录时，才启用通配符  
并且好像性能很低的样子，取决于硬盘读写速度，所以能不用就不用吧</strong>

# 用法
```shell
$ java -jar <Jar File> <Algorithm> <File1> [File2] [File3]...
# e.g.
$ java -jar FashHash-1.0-SNAPSHOT SHA-1 abc.jar def.zip
```

## 这里不建议
<del>
或者自定义C程序链接
```shell
sha256 abc.jar def.zip
```
See [sha256.c](https://github.com/flowerinsnowdh/FastHash/blob/1.0-SNAPSHOT/src/main/c/sha256.c)
</del>

## Gradle application
gradle的`application`插件可以一键生成启动脚本
```shell
# Windows环境下将./gradlew改为gradlew.bat
# 生成zip压缩包
$ ./gradlew distZip
# 生成tar压缩包
$ ./gradlew distTar
```
将会在`build\distributions`下生成

# 2.0更新
允许使用通配符  
允许同时使用多个哈希算法，以冒号分割  
例如
```shell
$ java -jar MD5:SHA-1:SHA-256:SHA-512 *.zip
```
即可一键生成所有当前目录下，所有zip文件的上述四个算法的哈希值