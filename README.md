# 项目
好多系统不支持自动生成哈希文件，手写又太慢了，本程序可以一键生成

# 生成
会以`<文件名>.<哈希算法名>`作为文件名生成在同一目录下，例`abc.jar.sha1`

# 用法
```shell
java -jar <Jar File> <Algorithm> <File1> [File2] [File3]...
# e.g.
java -jar FashHash-1.0-SNAPSHOT SHA-1 abc.jar def.zip
```
或者自定义C程序链接
```shell
sha256 abc.jar def.zip
```
See [sha256.c](https://github.com/flowerinsnowdh/FastHash/blob/1.0-SNAPSHOT/src/main/c/sha256.c)