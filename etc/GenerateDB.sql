CREATE DATABASE IF NOT EXISTS HouseControl;
USE HouseControl;
CREATE TABLE IF NOT EXISTS House(
    ID int auto_increment,
    HOUSENAME varchar(255),
    NAME varchar(255) NOT NULL,
    LOCATION varchar(255),
    OWNERS varchar(255) NOT NULL,
    PRIMARY KEY (ID,HOUSENAME)
);
CREATE TABLE IF NOT EXISTS Players(
    ID int auto_increment,
    USERNAME varchar(255),
    NAME varchar(255) NOT NULL,
    PRIMARY KEY (ID,USERNAME)
);
INSERT INTO Players (ID,USERNAME,NAME) VALUES (0,"admin","Administrator");
INSERT INTO House (ID,HOUSENAME,NAME,LOCATION,OWNERS) VALUES (0,"adminHouse","Administrator's house","5,5,5","0");