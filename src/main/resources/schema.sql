DROP DATABASE IF EXISTS ssm;
CREATE DATABASE ssm CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ssm;

CREATE TABLE todo
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `content`   VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE weibo
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `content`   VARCHAR(255) NOT NULL,
    `ct`      INT          NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE comment
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `content`   VARCHAR(255) NOT NULL,
    `weiboid` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE user
(
    `id`      INT          NOT NULL AUTO_INCREMENT,
    `username`   VARCHAR(255) NOT NULL,
    `password`   VARCHAR(255) NOT NULL,
    `salt`   VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);
