CREATE TABLE Library_User (
                              id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                              username varchar(100) NOT NULL UNIQUE,
                              year_of_birth int check(year_of_birth > 1800),
                              role varchar default 'ROLE_USER',
                              password varchar(100)
);

CREATE TABLE Book (
                      id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                      title varchar(100) NOT NULL,
                      author varchar(100) NOT NULL,
                      year int check(year > 1500),
                      taken_at date,
                      user_id bigint REFERENCES Library_User(id) ON DELETE SET NULL
);