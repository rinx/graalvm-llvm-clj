FROM oracle/graalvm-ce:latest as graalvm

RUN curl -o /usr/bin/lein https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein \
    && chmod a+x /usr/bin/lein

RUN mkdir -p /llvm-clj
WORKDIR /llvm-clj

COPY hello.c .
RUN clang -c -O1 -emit-llvm hello.c

COPY project.clj project.clj
COPY src src

RUN lein uberjar

RUN gu install native-image

RUN native-image \
    -jar target/uberjar/graalvm-llvm-clj-0.1.0-SNAPSHOT-standalone.jar \
    -H:Name=graalvm-llvm-clj \
    -H:+ReportExceptionStackTraces \
    -J-Dclojure.spec.skip-macros=true \
    -J-Dclojure.compiler.direct-linking=true \
    -H:Log=registerResource: \
    --verbose \
    --no-fallback \
    --no-server \
    --report-unsupported-elements-at-runtime \
    --initialize-at-build-time \
    --static \
    -J-Xms2g \
    -J-Xmx4g

FROM scratch

COPY --from=graalvm /llvm-clj/graalvm-llvm-clj /graalvm-llvm-clj

ENTRYPOINT ["/graalvm-llvm-clj"]
