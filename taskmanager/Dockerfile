FROM ubuntu:latest
LABEL authors="joao"

ENTRYPOINT ["top", "-b"]

# Use uma imagem base com o JDK 21 instalado
FROM eclipse-temurin:21-jdk-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o código-fonte para o contêiner
COPY --from=builder /app/target/TaskmanagerApplication.jar .

# Instala as dependências e compila o projeto
RUN ./mvnw clean install -DskipTests -T 1C

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/TaskmanagerApplication.jar"]
