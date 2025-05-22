FROM eclipse-temurin:21-jdk

RUN apt-get update && apt-get install -y \
    curl unzip git vim python3 python3-pip python3-venv \
    docker.io gnupg ca-certificates lsb-release

# Install Gradle manually
ENV GRADLE_VERSION=8.6
RUN curl -LO https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt && \
    ln -s /opt/gradle-${GRADLE_VERSION} /opt/gradle && \
    rm gradle-${GRADLE_VERSION}-bin.zip

ENV GRADLE_HOME=/opt/gradle
ENV PATH=${GRADLE_HOME}/bin:$PATH

# Install Node.js (for gitmoji-cli)
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs \
    && npm install -g gitmoji-cli

# Create and activate Python virtual environment
RUN python3 -m venv /opt/venv
ENV PATH="/opt/venv/bin:$PATH"

# Install Python packages inside virtual environment
RUN pip install --upgrade pip && \
    pip install fastapi uvicorn kafka-python scikit-learn

# Install nginx
RUN apt-get update && apt-get install -y nginx

# Copy custom nginx config
COPY ./nginx.conf /etc/nginx/nginx.conf