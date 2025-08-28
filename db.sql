create sequence user_id_seq start with 100 increment by 50;

create table users
(
    id         bigint       not null default nextval('user_id_seq'),
    email      varchar(255) not null,
    password   varchar(255) not null,
    name       varchar(255) not null,
    role       varchar(20)  not null,
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (id),
    constraint user_email_unique unique (email)
);

create sequence post_id_seq start with 100 increment by 50;

create table posts
(
    id         bigint       not null default nextval('post_id_seq'),
    title      varchar(250) not null,
    slug       varchar(300) not null,
    content    text         not null,
    created_by bigint       not null references users (id),
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (id),
    constraint posts_slug_unique unique (slug)
);

create sequence comment_id_seq start with 100 increment by 50;

create table comments
(
    id         bigint       not null default nextval('comment_id_seq'),
    post_id    bigint       not null references posts (id),
    name       varchar(150) not null,
    email      varchar(150),
    content    text         not null,
    created_at timestamp    not null,
    updated_at timestamp,
    primary key (id)
);

insert into users(id, email, password, name, role, created_at) values
(1,'admin@gmail.com','$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS','Administrator', 'ROLE_ADMIN', CURRENT_TIMESTAMP),
(2,'siva@gmail.com','$2a$10$UFEPYW7Rx1qZqdHajzOnB.VBR3rvm7OI7uSix4RadfQiNhkZOi2fi','Siva Prasad', 'ROLE_USER', CURRENT_TIMESTAMP);

insert into posts(id, title, slug, content, created_by, created_at, updated_at) values
(1,'Installing LinuxMint','installing-linuxmint','Installing LinuxMint 17', 1, '2014-01-20', now()),
(2,'SpringBoot: Introducing SpringBoot', 'introducing-springboot','SpringBoot is a new spring portfolio project which takes opinionated view of building production-ready Spring applications by drastically reducing the amount of configuration required.', 2, '2014-06-20', null),
(3,'Exporting Spring Data JPA Repositories as REST Services using Spring Data REST','exporting-spring-data-jpa-repositories-as-rest-services-using-spring-data-rest','Spring Data modules provides various modules to work with various types of datasources like RDBMS, NOSQL stores etc in unified way.',2, '2014-06-25', null),
(4,'SpringMVC4 + Spring Data JPA + SpringSecurity configuration using JavaConfig','springmvc4-springdatajpa-springsecurity-configuration-using-javaconfig','In this article we will see how to configure and integrate SpringMVC4, Spring Data JPA with Hibernate and SpringSecurity using JavaConfig.',2, '2014-04-20', now()),
(5,'Book Review: Enterprise Application Development with Ext JS and Spring','book-review-enterprise-application-development-with-extjs-and-spring','I was asked to review "Enterprise Application Development with Ext JS and Spring" book by Packtpub guys and here is my review on the book.',2, '2014-05-20', null),
(6,'Clean Code: Dont mix different levels of abstractions','cleancode-dont-mix-different-levels-of-abstractions','We spend more time on reading code than writing. So if the code is more readable then obviously it will increase the developer productivity. Many people associate readability of code with coding conventions like following standard naming conventions, closing file, DB resources etc etc.',2, '2014-03-20', null),
(7,'PrimeFaces Beginners Guide book published','primefaces-beginners-guide-book-published','I am glad to announce that my second book PrimeFaces Beginners Guide is published. As many of us know PrimeFaces is leading JSF component library for JSF based web applications.',2, '2014-03-20', now()),
(8,'Packt Publishing "Java Persistence With MyBatis3" published','packt-publishing-java-persistence-with-mybatis3-published','Hurray...My first book "Java Persistence with MyBatis3" is published. I would like to thank Packt Publishers for giving me this opportunity to write on my favorite framework MyBatis.',2, '2014-02-20', null),
(9,'Deploying BroadleafCommerce 2.0 on JBoss AS 7','deploying-broadleafcommerce-20-on-jbossas7','First 2 steps are not really related to Broadleaf specific, but mentioned to make it easy to follow(copy/paste) the steps.',2, '2014-01-20', now())
;

insert into comments(id, post_id, email, name, content, created_at, updated_at) values
(1, 1, 'guest@gmail.com', 'Guest','sample comment 1', now(), null),
(2, 2, 'test@gmail.com', 'Test','sample comment 2', now(), null),
(3, 2, 'test@gmail.com', 'Test','sample comment 3', now(), now())
;