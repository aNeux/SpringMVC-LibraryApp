create table person (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    second_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    birth_year int NOT NULL CHECK (birth_year > 1920),
    UNIQUE(first_name, second_name, last_name)
);

insert into person(first_name, second_name, last_name, birth_year) values ('Павел', 'Александрович', 'Тропинов', 1997);
insert into person(first_name, second_name, last_name, birth_year) values ('Дарья', 'Николаевна', 'Львова', 2000);
insert into person(first_name, second_name, last_name, birth_year) values ('Евгений', 'Владимирович', 'Волобуев', 1997);
insert into person(first_name, second_name, last_name, birth_year) values ('Дмитрий', 'Алеексеевич', 'Балдин', 1998);
insert into person(first_name, second_name, last_name, birth_year) values ('Юрий', 'Алексеевич', 'Николаев', 1997);
insert into person(first_name, second_name, last_name, birth_year) values ('Евгений', 'Кириллович', 'Васильев', 1990);

create table book (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    attached_person int REFERENCES person(id) ON DELETE SET NULL,
    title varchar(100) NOT NULL,
    author varchar(100) NOT NULL,
    publishing_year int NOT NULL CHECK (publishing_year > 0),
    UNIQUE(title, author, publishing_year)
);

insert into book(title, author, publishing_year) values ('1984', 'Джордж Оруэлл', 1949);
insert into book(title, author, publishing_year) values ('Лолита', 'Владимир Набоков', 1955);
insert into book(title, author, publishing_year) values ('Ревизор', 'Николай Гоголь', 1836);
insert into book(title, author, publishing_year) values ('Мастер и Маргарита', 'Михаил Булгаков', 1940);
insert into book(title, author, publishing_year) values ('Война и мир', 'Лев Толстой', 1869);