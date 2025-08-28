create table yt_videos
(
    id    bigserial    not null,
    title varchar(255) not null unique,
    url   varchar(500) not null,
    primary key (id)
);

insert into yt_videos(title, url) VALUES
( 'My IntelliJ IDEA SetUp for Java Development', 'https://www.youtube.com/watch?v=HJYybOuPym4'),
( 'Spring Modulith Crash Course : Building Modular Monoliths using Spring Boot', 'https://www.youtube.com/watch?v=FkP2aZiBrhg'),
( 'IntelliJ IDEA Tips for Java Developers - Code Faster', 'https://www.youtube.com/watch?v=Kiry69tEIOE'),
('Spring Boot Microservices Complete Tutorial', 'https://www.youtube.com/watch?v=ZKQWwCUEABY'),
('Spring Boot: Build URL Shortener Application - Complete Course (5 Hours)', 'https://www.youtube.com/watch?v=XEgS8yq-zgw'),
('Bootify - Generate Production Grade Spring Boot Applications in Minutes', 'https://www.youtube.com/watch?v=Lyj4of6FO4w'),
('First Look at JetBrains Junie', 'https://www.youtube.com/watch?v=qqqzIBeYoWY'),
('Spring Boot + Kubernetes Tutorial Series - Part 1 : Introduction', 'https://www.youtube.com/watch?v=pGbBuwzyiV4'),
('Spring Boot REST API Anti-Patterns and Best Practices', 'https://www.youtube.com/watch?v=koxu51eqDiQ'),
('5 Java Libraries You Should Know', 'https://www.youtube.com/watch?v=sHZGv0PklQ0'),
('Intellij IDEA - Live & File Templates : Supercharge your Intellij IDEA', 'https://www.youtube.com/watch?v=pw9DUdk6dIM'),
('Spring Boot Thymeleaf HTMX Tutorial', 'https://www.youtube.com/watch?v=T6dU3GZf6DA'),
('Spring Boot support for Testcontainers Service Connections and Local Development', 'https://www.youtube.com/watch?v=UuLD9gZmiZU'),
('Modern Spring Boot Application Development using Java 17/21 and Testcontainers', 'https://www.youtube.com/watch?v=q2LSz7cnC1g'),
('How do I upskill myself, and what tools & techniques do I use?', 'https://www.youtube.com/watch?v=oOAaGvpQTCo'),
('Getting Started with Generative AI using Java, LangChain4j, OpenAI and Ollama', 'https://www.youtube.com/watch?v=ld0ZU6bd9bM'),
('Spring Boot Docker Compose Support : Local Development Made Simple', 'https://www.youtube.com/watch?v=PZt5EJTLH4o'),
( 'Developing Spring Boot Applications With Joy', 'https://www.youtube.com/watch?v=VvWtoaeHQUQ'),
('Working with Flyway Migrations in a Spring Boot Application', 'https://www.youtube.com/watch?v=AMopB9C2bH8'),
('Spring JdbcClient: A better alternative to JdbcTemplate', 'https://youtu.be/oF7vlaJhySE')
;