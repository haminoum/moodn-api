# mood-api

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



## Insight
[Emotion vs Mood](https://www.iihs.edu.lk/pluginfile.php/18502/mod_resource/content/1/emotions.pdf)
- Most experts believe that emotions are more fleeting than moods
- For
  example, if someone is rude to you, you’ll feel angry. That intense feeling of
  anger probably comes and goes fairly quickly, maybe even in a matter of seconds. When you’re in a bad mood, though, you can feel bad for several hours.
- Emotions are reactions to a person (seeing a friend at work may make you feel
  glad) or event (dealing with a rude client may make you feel angry). You show
  your emotions when you’re “happy about something, angry at someone, afraid of
  something.”9 Moods, in contrast, aren’t usually directed at a person or event. But
  emotions can turn into moods when you lose focus on the event or object that
  started the feeling. And, by the same token, good or bad moods can make you
  more emotional in response to an event. So when a colleague criticizes how you
  spoke to a client, you might become angry at him. That is, you show emotion
  (anger) toward a specific object (your colleague). But as the specific emotion dissipates, you might just feel generally dispirited. You can’t attribute this feeling to
  any single event; you’re just not your normal self. You might then overreact to
  other events. This affect state describes a mood.
- of these differences—that emotions are more likely to be caused by a specific
  event, and emotions are more fleeting than moods—we just discussed. Other
  differences are subtler. For example, unlike moods, emotions tend to be more
  clearly revealed with facial expressions (anger, disgust). Also, some researchers
  speculate that emotions may be more action-oriented—they may lead us to
  some immediate action—while moods may be more cognitive, meaning they
  may cause us to think or brood for a while.1
- Finally, the exhibit shows that emotions and moods can mutually influence
  each other. For example, an emotion, if it’s strong and deep enough, can turn
  into a mood: Getting your dream job may generate the emotion of joy, but it
  also can put you in a good mood for several days. Similarly, if you’re in a good
  or bad mood, it might make you experience a more intense positive or negative
  emotion than would otherwise be the case. For example, if you’re in a bad
  mood, you might “blow up” in response to a coworker’s comment when normally it would have just generated a mild reaction. Because emotions and
  moods can mutually influence each other, there will be many point
- Also, the terminology can be confusing. For example, the two main mood
  dimensions are positive affect and negative affect,
- affect A broad range of feelings that
  people experience.
- emotions Intense feelings that are
  directed at someone or something
- moods Feelings that tend to be less
  intense than emotions and that lack a
  contextual stimulus.
