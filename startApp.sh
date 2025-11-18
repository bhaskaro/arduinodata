#!/bin/bash

APP_HOME=/scratch/voggu/iot/arduinodata
JAVA_HOME=/scratch/voggu/binaries/jdk-17.0.6
PROFILE=prod
JAR_NAME=arduinodata-0.0.1-SNAPSHOT.jar
PID_FILE="$APP_HOME/arduinodata.pid"
LOG_DIR="$APP_HOME/logs"
LOG_FILE="$LOG_DIR/arduinodata.out"

export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"
export SPRING_PROFILES_ACTIVE="$PROFILE"

cd "$APP_HOME" || { echo "Failed to cd to $APP_HOME"; exit 1; }

mkdir -p "$LOG_DIR"

is_running() {
  if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")
    if ps -p "$PID" > /dev/null 2>&1; then
      return 0
    else
      return 1
    fi
  else
    return 1
  fi
}

start_app() {
  if is_running; then
    echo "arduinodata is already running with PID $(cat "$PID_FILE")."
    return 0
  fi

  echo "Pulling latest code from Git..."
  git pull

  echo "Building application (skipping tests)..."
  chmod +x mvnw
  ./mvnw clean package -DskipTests
  if [ $? -ne 0 ]; then
    echo "Build failed. Not starting application."
    exit 1
  fi

  echo "Starting arduinodata with profile '$PROFILE' in background..."
  nohup java -jar "$APP_HOME/target/$JAR_NAME" \
    --spring.profiles.active="$PROFILE" \
    >> "$LOG_FILE" 2>&1 &

  echo $! > "$PID_FILE"
  echo "arduinodata started with PID $(cat "$PID_FILE"). Logs: $LOG_FILE"
}

stop_app() {
  if ! is_running; then
    echo "arduinodata is not running."
    [ -f "$PID_FILE" ] && rm -f "$PID_FILE"
    return 0
  fi

  PID=$(cat "$PID_FILE")
  echo "Stopping arduinodata (PID $PID)..."
  kill "$PID"

  # Wait a bit for graceful shutdown
  sleep 5

  if ps -p "$PID" > /dev/null 2>&1; then
    echo "Process still running, forcing kill..."
    kill -9 "$PID"
  fi

  rm -f "$PID_FILE"
  echo "arduinodata stopped."
}

status_app() {
  if is_running; then
    echo "arduinodata is running with PID $(cat "$PID_FILE")."
  else
    echo "arduinodata is not running."
  fi
}

restart_app() {
  echo "Restarting arduinodata..."
  stop_app
  start_app
}

usage() {
  echo "Usage: $0 {start|stop|restart|status}"
  exit 1
}

case "$1" in
  start)
    start_app
    ;;
  stop)
    stop_app
    ;;
  restart)
    restart_app
    ;;
  status)
    status_app
    ;;
  *)
    usage
    ;;
esac

