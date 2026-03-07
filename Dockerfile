FROM almalinux:8

# 必要パッケージのインストール
RUN dnf install -y java-17-openjdk-devel rpm-build unzip curl && \
    dnf clean all

# Gradleのインストール
RUN curl -L https://services.gradle.org/distributions/gradle-8.5-bin.zip -o /tmp/gradle.zip && \
    unzip /tmp/gradle.zip -d /opt && \
    ln -s /opt/gradle-8.5/bin/gradle /usr/local/bin/gradle && \
    rm /tmp/gradle.zip

# Gradleキャッシュをコンテナ内に独立させる
ENV GRADLE_USER_HOME=/root/.gradle-container

WORKDIR /workspace

# ソースコードをコピー
COPY . .

# RPMビルド実行
RUN gradle buildRpm --no-daemon
