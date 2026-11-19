FROM maven:3.9.16-eclipse-temurin-17-noble

ARG CHROME_VERSION=157.0.8103.78

RUN sed -i -E 's@http://(archive|security)\.ubuntu\.com/ubuntu@https://azure.archive.ubuntu.com/ubuntu@g' \
        /etc/apt/sources.list.d/ubuntu.sources \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
        fonts-liberation libasound2t64 libatk-bridge2.0-0t64 libatk1.0-0t64 \
        libcups2t64 libgbm1 libglib2.0-0t64 libgtk-3-0t64 libnspr4 libnss3 \
        libx11-6 libxcb1 libxcomposite1 libxdamage1 libxext6 libxfixes3 \
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
RUN mvn -B -ntp dependency:go-offline
COPY src ./src

CMD ["mvn", "-B", "-ntp", "-Dselenide.headless=true", \
     "-Dselenide.browserBinary=/opt/chrome-linux64/chrome", \
     "-Dwebdriver.chrome.driver=/opt/chromedriver-linux64/chromedriver", \
     "-Dchromeoptions.args=--no-sandbox,--disable-dev-shm-usage", "test"]
