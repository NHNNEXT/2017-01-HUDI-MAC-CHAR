DROP TABLE IF EXISTS User;

CREATE TABLE User (
	id bigint(20) 			NOT NULL AUTO_INCREMENT,
	email varchar(255) 		NOT NULL,
	password varchar(255) 	NOT NULL,
	nickname varchar(255) 	NOT NULL,

	PRIMARY KEY (id)
);