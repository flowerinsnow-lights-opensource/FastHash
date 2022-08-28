#include <stdlib.h>
#include <string.h>

int main(int argc, char** argv) {
    char command[4096] = {0};

    // Replace $JAVA_EXE to your java executable file.
    // Replace $JAR_FILE to your lib jar file.
    strcat(command, "$JAVA_EXE -jar $JAR_FILE SHA-256");

    for (int i = 1; i < argc; i++) {
        strcat(command, " \"");
        strcat(command, argv[i]);
        strcat(command, "\"");
    }

    return system(command);
}