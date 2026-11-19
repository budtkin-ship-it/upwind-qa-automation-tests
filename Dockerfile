FROM maven:3.9.16-eclipse-temurin-17-noble

ARG CHROME_VERSION=157.0.8103.78

RUN sed -i -E 's@http://(archive|security)\.ubuntu\.com/ubuntu@https://azure.archive.ubuntu.com/ubuntu@g' \
        /etc/apt/sources.list.d/ubuntu.sources \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
        fonts-liberation libasound2t64 libatk-bridge2.0-0t64 libcairo2 \
        libcups2t64 libgbm1 libnss3 libpango-1.0-0 libudev1 \
        libxcomposite1 libxdamage1 libxfixes3 \
        libxkbcommon0 libxrandr2 unzip \
    && rm -rf /var/lib/apt/lists/* \
    && chrome_url="https://storage.googleapis.com/chrome-for-testing-public/${CHROME_VERSION}/linux64" \
    && curl -fsSL --retry 3 "$chrome_url/chrome-linux64.zip" -o /tmp/chrome.zip \
    && curl -fsSL --retry 3 "$chrome_url/chromedriver-linux64.zip" -o /tmp/chromedriver.zip \
    && unzip -q /tmp/chrome.zip -d /opt \
    && unzip -q /tmp/chromedriver.zip -d /opt \
    && rm /tmp/chrome.zip /tmp/chromedriver.zip

WORKDIR /app
COPY pom.xml ./
COPY src ./src

# Resolve the test lifecycle and its Surefire provider without running any scenarios.
RUN mvn -B -ntp -Dgroups=docker-dependency-cache test

CMD ["mvn", "-B", "-ntp", "-Dselenide.headless=true", \
     "-Dselenide.browserBinary=/opt/chrome-linux64/chrome", \
     "-Dwebdriver.chrome.driver=/opt/chromedriver-linux64/chromedriver", \
     "-Dchromeoptions.args=--no-sandbox,--disable-dev-shm-usage", "test"]
