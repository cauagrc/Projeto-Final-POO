# Projeto de Programação Orientada a Objetos

## 📺 Vídeo de instalação

Caso prefira, você pode acompanhar todo o processo de instalação através do vídeo abaixo:

**YouTube:** https://youtu.be/AlDXS4Qw-UY

---

## Configuração do Ambiente

Antes de executar o projeto, é necessário instalar algumas dependências e compilar a biblioteca gráfica utilizada.

---

## 1. Clonar os repositórios

Clone os dois repositórios necessários:

```bash
git clone https://github.com/Tatmiki/progton-lib
git clone https://github.com/cauagrc/Projeto-Final-POO
```

---

## 2. Pré-requisitos

Certifique-se de possuir instalados:

- JDK **21.0.10**
- Maven (qualquer versão)
- GLM (qualquer versão)

### Dependências do sistema (Ubuntu/Debian)

Instale as dependências abaixo:

```bash
sudo apt install libglfw3
sudo apt install libglfw3-dev
sudo apt install cmake
sudo apt install default-jdk
```

---

## 3. Instalação do GLM

Extraia o arquivo do GLM e entre na pasta criada.

Em seguida, execute os comandos abaixo, um de cada vez:

```bash
cmake \
    -DGLM_BUILD_TESTS=OFF \
    -DBUILD_SHARED_LIBS=OFF \
    -B build .
```

```bash
cmake --build build --all
```

```bash
sudo cmake --build build --install
```

---

## 4. Compilando a biblioteca gráfica

Entre no repositório **progton-lib**:

```bash
cd progton-lib
```

Altere para a branch correta:

```bash
git checkout feat/domain
```

Crie a pasta de build:

```bash
mkdir build
```

Configure o projeto:

```bash
cmake -B build -S .
```

Compile a biblioteca:

```bash
cmake --build build
```

---

## 5. Executando o projeto

Entre no repositório **Projeto-Final-POO**:

```bash
cd ../Projeto-Final-POO
```

Execute a aplicação:

```bash
mvn javafx:run
```

---

## Resumo

1. Clone os dois repositórios.
2. Instale o JDK, Maven, CMake, GLFW e GLM.
3. Compile e instale o GLM.
4. Compile a biblioteca `progton-lib`.
5. Execute o projeto com:

```bash
mvn javafx:run
```
