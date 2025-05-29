#!/bin/bash
set -e  # 오류 발생 시 중단

echo ">>> Setting up environment..."

# gradlew 실행 여부 확인
if [ -f "./gradlew" ]; then
  ./gradlew clean build --no-daemon
else
  echo "⚠️ gradlew not found in $(pwd), skipping build step."
fi

# Gitmoji 초기화 (git repo인 경우만)
if git rev-parse --is-inside-work-tree > /dev/null 2>&1; then
  echo "✅ Git repository detected. Initializing gitmoji..."
  gitmoji -i
else
  echo "⚠️ Not a Git repository, skipping gitmoji init."
fi

# GitHub SSH 호스트 등록
mkdir -p ~/.ssh
ssh-keyscan github.com >> ~/.ssh/known_hosts

nginx