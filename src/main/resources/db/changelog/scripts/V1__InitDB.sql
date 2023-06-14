CREATE TABLE users(
    id                  bigserial        PRIMARY KEY ,
    username            text             NOT NULL ,
    name                text             NOT NULL ,
    surname             text             NOT NULL ,
    birth_date          date             NOT NULL ,
    email               text             NOT NULL ,
    password            text             NOT NULL,
    CONSTRAINT uniq_username UNIQUE (username)
);

CREATE TABLE roles(
	id                  bigserial        PRIMARY KEY ,
	name                text             NOT NULL
);

CREATE TABLE users_to_roles(
    users_id            bigint           REFERENCES users(id) ON DELETE CASCADE ,
    roles_id            bigint           REFERENCES roles(id) ON DELETE CASCADE ,
    PRIMARY KEY (users_id, roles_id)
);

CREATE TABLE shops(
	id                  bigserial        PRIMARY KEY,
	name                text             NOT NULL
);

CREATE TABLE categories(
	id                  bigserial         PRIMARY KEY,
	name                text              NOT NULL
);

CREATE TABLE brands(
	id                  bigserial         PRIMARY KEY,
	name                text              NOT NULL,
	created_date        timestamp         DEFAULT LOCALTIMESTAMP,
	updated_date        timestamp         DEFAULT LOCALTIMESTAMP
);

CREATE TABLE products(
	id                  bigserial         PRIMARY KEY,
	name                text              NOT NULL,
	created_date        timestamp         DEFAULT LOCALTIMESTAMP,
	updated_date        timestamp         DEFAULT LOCALTIMESTAMP,
	brands_id           bigint            REFERENCES brands(id) ,
	categories_id       bigint            REFERENCES categories(id),
	CONSTRAINT uniq_product UNIQUE (name, brands_id)
);

CREATE TABLE prices(
	id                  bigserial         PRIMARY KEY,
	value               numeric(9, 2)     NOT NULL CHECK ( value > 0 ),
	created_date        timestamp         DEFAULT LOCALTIMESTAMP,
	shops_id            bigint            REFERENCES shops(id),
	products_id         bigint            REFERENCES products(id),
	CONSTRAINT uniq_price UNIQUE (value, created_date, shops_id, products_id)
);


CREATE TABLE shops_to_products(
	shops_id            bigint           REFERENCES shops(id)    ON DELETE CASCADE,
	products_id         bigint           REFERENCES products(id) ON DELETE CASCADE,
	PRIMARY KEY (shops_id, products_id)
);




