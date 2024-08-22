# Use a imagem base do Maven para compilar o projeto
FROM maven:3.8.4-openjdk-17 AS builder

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Baixa as dependências do Maven (apenas as dependências)
RUN mvn -B dependency:go-offline

# Copia todos os arquivos do projeto para o diretório de trabalho
COPY src src

# Compila o projeto usando o Maven
RUN mvn -B package

# Use a imagem base do OpenJDK 17 para implantar o aplicativo
FROM eclipse-temurin:17.0.10_7-jdk-jammy AS final

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o artefato JAR do estágio de compilação para o diretório de trabalho
COPY --from=builder /app/target/gx2-teste-1.0.jar .

# Define o comando de inicialização do contêiner
CMD ["java", "-jar", "gx2-teste-1.0.jar"]

# Expose a porta que o contêiner expõe (opcional)
EXPOSE 8080
