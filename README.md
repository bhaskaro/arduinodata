## Build from Source

### Prerequisites

- Git installed
- JDK 17 available at:

```bash
/scratch/voggu/binaries/jdk-17.0.6
```

---

### 1. Clone the repository

```bash
mkdir -p /scratch/voggu/iot
cd /scratch/voggu/iot

git clone https://github.com/bhaskaro/weatherapp.git
cd weatherapp
```

> Application path after clone: `/scratch/voggu/iot/weatherapp`

---

### 2. Set `JAVA_HOME` and update `PATH`

```bash
export JAVA_HOME=/scratch/voggu/binaries/jdk-17.0.6
export PATH="$JAVA_HOME/bin:$PATH"
```

You can add these lines to your shell profile (`~/.bashrc`, `~/.bash_profile`, etc.) to persist them.

---

### 3. Build the application using Maven Wrapper

```bash
cd /scratch/voggu/iot/weatherapp

# Ensure mvnw is executable (first time only)
chmod +x mvnw

# Build the project
./mvnw clean package
```

The packaged JAR will be available under the `target/` directory after a successful build.

```
```
