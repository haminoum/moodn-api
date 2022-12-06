# mood-api

[![CI](https://github.com/haminoum/moodn-api/actions/workflows/gradle.yml/badge.svg)](https://github.com/haminoum/moodn-api/actions/workflows/gradle.yml)

A service that helps with the creation of moods.

## First time setup

1. Install the pre-commit hooks automate the boring stuff

```bash
brew install pre-commit
pre-commit install
```

2. Install grpcurl to interact with APIs locally

```bash
brew install grpcurl
```

3. [Install Docker](https://docs.docker.com/desktop/mac/install/)

## Containerize

Docker Credential Helpers

- AWS [docker-credential-ecr-login] (https://github.com/awslabs/amazon-ecr-credential-helper)
    - [Details] (https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin#authentication-methods)

````bash
# builds and pushes a container image to container registry
./gradlew jib --image=moodn/moodn-api
````

## Running locally

mood-api runs on port `55555`

Spin up a postgres database locally

```bash
docker run --name postgres-local -e POSTGRES_PASSWORD=postgres -e TS_TUNE_MAX_CONNS=100 -d -p 5432:5432 timescale/timescaledb:latest-pg11
PGPASSWORD=postgres createdb -h ${DOCKER_HOST_IP:-localhost} -U postgres moodn-api-db
```

Accessing local db

```bash
docker exec -it <container-id> /bin/sh
psql -U postgres
```

```bash
./mvnw org.springframework.boot:spring-boot-maven-plugin:run
```

### Accessing API

```bash
# Create mood
curl "http://localhost:{$PORT}/moods/create?type=${MOOD}&username=${USER}" -XPOST
```
## Connecting to AWS Database
````bash
psql \
   --host=<DB instance endpoint> \
   --port=<port> \
   --username=<master username> \
   --password \
   --dbname=<database name>
````


