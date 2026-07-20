package ec.edu.espe.lab6ci.service;

public interface EquipmentPolicyClient {
    boolean isBlocked(String borrowerEmail);
}




















/*
* Paso 2: Integrar con Git
bash
git init                    # a. Inicializa el repositorio local
git add .                   # b. Añade TODO el proyecto (el punto = todo)
git commit -m "Initial commit"   # c. Guarda los cambios en un commit

Si es la primera vez que usas git en esa máquina, quizá te pida configurarte:

bash
git config --global user.name "Tu Nombre"
git config --global user.email "jjguerra5@espe.edu.ec"
Paso 3: Ejecutar pruebas y build
bash
./gradlew test     # a. Corre las pruebas unitarias
./gradlew build    # b. Compila y empaqueta (genera el .jar)
Paso 4: Subir a repositorio remoto
bash
git remote add origin https://github.com/TU_USUARIO/nombreRepositorio.git   # a. Enlaza tu repo local con GitHub
git push -u origin main    # b. Sube la rama main (el -u la deja "rastreada")

Si tu rama se llama master en vez de main, primero renómbrala con git branch -M main.

Paso 5: Gestionar una nueva rama
bash
git checkout -b feature/withdraw    # a. Crea y se cambia a la rama feature en un solo comando

Equivale a git branch feature/withdraw + git checkout feature/withdraw. En git nuevo también sirve git switch -c feature/withdraw.

Aquí es donde implementas el método withdraw en WalletService (que ya lo tienes: valida amount > 0, que el wallet exista, saldo suficiente, actualiza y guarda) y creas las pruebas (withdraw exitoso y fondos insuficientes — las que ya revisamos). Luego:

bash
./gradlew test     # d. Corre tests en la feature
./gradlew build    # d. Build en la feature

git add .                                   # e. Añade los cambios de la feature
git commit -m "feat: add withdraw method and tests"   # e. Commit de la feature
Paso 6: Subir la rama al remoto
bash
git push -u origin feature/withdraw    # a. Sube la rama feature a GitHub
git push -u origin main                # b. (asegura que main también esté subida)

Ojo con el nombre: la guía a veces dice feature y a veces feature/withdraw. Usa el mismo nombre que le pusiste al crearla. Si la creaste como feature/withdraw, entonces git push -u origin feature/withdraw.

Paso 7: Merge controlado a main
bash
git checkout main                  # Cambia a la rama main
git merge feature/withdraw         # Fusiona la feature dentro de main

./gradlew test     # Vuelve a correr pruebas ya con todo integrado
./gradlew build    # Vuelve a construir

Después del merge, sube main actualizada:

bash
git push origin main
Paso 8: Generar CI con GitHub Actions
bash
# a. Crear la carpeta y el archivo
mkdir -p .github/workflows

Luego creas el archivo .github/workflows/ci.yml con el contenido (que es justo el que te expliqué antes: name, on, jobs). Y lo subes:

bash
git add .github/workflows/ci.yml
git commit -m "ci: add GitHub Actions workflow"
git push origin main

Apenas hagas ese push, el workflow se ejecuta solo y verás si tus pruebas pasan en la pestaña Actions.

Chuleta de comandos "por si acaso"

Estos no están en la guía pero salvan en el examen si algo se ve raro:

bash
git status              # ¿Qué archivos cambiaron / están por commitear?
git log --oneline       # Historial de commits resumido
git branch              # Lista tus ramas y marca en cuál estás
git diff                # Ver los cambios que aún no añades
git remote -v           # Ver a qué remoto estás conectado*/