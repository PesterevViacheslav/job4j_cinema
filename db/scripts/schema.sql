CREATE SEQUENCE IF NOT EXISTS hall_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS hall (
   id INTEGER DEFAULT nextval('hall_sq'),
   name TEXT,
   CONSTRAINT pk_hall PRIMARY KEY(id)
);
CREATE SEQUENCE IF NOT EXISTS users_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS users (
   id INTEGER DEFAULT nextval('users_sq'),
   name TEXT,
   phone TEXT,
   password TEXT,
   CONSTRAINT pk_users PRIMARY KEY(id)
);
CREATE SEQUENCE IF NOT EXISTS seat_sq MINVALUE 1 START WITH 1;
CREATE TABLE IF NOT EXISTS seat (
   id INTEGER DEFAULT nextval('seat_sq'),
   hall_id INTEGER NOT NULL,
   row INTEGER NOT NULL,
   num INTEGER NOT NULL,
   price NUMERIC NOT NULL,
   user_id INTEGER,
   CONSTRAINT pk_seat PRIMARY KEY(id),
   CONSTRAINT fk_seat_hall FOREIGN KEY(hall_id) REFERENCES hall(id),
   CONSTRAINT fk_seat_users FOREIGN KEY(user_id) REFERENCES users(id)
);
INSERT INTO hall VALUES(default,'Зал 1');
INSERT INTO seat VALUES(default, 1, 1, 1, 100, null),
                       (default, 1, 1, 2, 100, null),
                       (default, 1, 1, 3, 100, null),
                       (default, 1, 2, 1, 200, null),
                       (default, 1, 2, 2, 200, null),
                       (default, 1, 2, 3, 200, null),
                       (default, 1, 3, 1, 150, null),
                       (default, 1, 3, 2, 150, null),
                       (default, 1, 3, 3, 150, null);