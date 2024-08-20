#!/bin/bash

APP_NAME="SearchStudioAPI"
JAR_FILE="./build/libs/searchstudio-0.0.1.jar"
PID_DIR="./pid"

if [ ! -d $PID_DIR ]; then
    mkdir $PID_DIR
fi

PID="./pid/$APP_NAME.pid"

# jvm options
JVM_OPTS="-D$APP_NAME"
JVM_OPTS_FILE=./jvm.options

if [ -f "$JVM_OPTS_FILE" ]; then
    JVM_OPTS+=" $(grep "^-" $JVM_OPTS_FILE)"
fi

# colors
red='\e[0;31m'
green='\e[0;32m'
yellow='\e[0;33m'
reset='\e[0m'

echoRed() { echo -e "${red}$1${reset}"; }
echoGreen() { echo -e "${green}$1${reset}"; }
echoYellow() { echo -e "${yellow}$1${reset}"; }

isRunning() {
  [ -f "$PID" ] && ps -p "$(cat "$PID")" >/dev/null 2>&1
}

start() {
  if isRunning; then
    echoYellow "$APP_NAME application is already running"
    return 0
  fi

  nohup java -jar $JVM_OPTS $JAR_FILE > /dev/null 2>&1 &

  echo $! > "$PID"

  if isRunning; then
    echoGreen "$APP_NAME application started"
    exit 0
  else
    echoRed "$APP_NAME application has not started - check log"
    exit 3
  fi
}

console() {
  if isRunning; then
    echoYellow "$APP_NAME application is already running"
    return 0
  fi

  java -jar $JVM_OPTS $JAR_FILE
}

restart() {
  echo "Restarting $APP_NAME application"
  stop
  start
}

stop() {
  echoYellow "Stopping $APP_NAME application"
  if isRunning; then
    kill "$(cat "$PID")"
    rm "$PID"
  else
    echoRed "$APP_NAME application not running"
  fi
}

status() {
  if isRunning; then
    echoGreen "$APP_NAME application is running: $(cat "$PID")"
  else
    echoRed "$APP_NAME application is either stopped or inaccessible"
  fi
}

case "$1" in
  start)
    start
    ;;
  console)
    console
    ;;
  status)
    status
    ;;
  stop)
    stop
    ;;
  restart)
    restart
    ;;
  *)
    echo "Usage: $0 {status|start|console|stop|restart}"
    exit 1
    ;;
esac

exit 0