create table document
(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name,
    path,
    description,
    requisite,
    date
);

create table task
(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name,
    period,
    deadline,
    description,
    type,
    complete
);

create table tag
(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name,
    description
);

create table department
(
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    name,
    fullname
);

create table person
(
    id         INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    firstName,
    lastName,
    patronymic,
    birthDay,
    department INTEGER,
    position,
    rank,
    description,
    FOREIGN KEY (department) REFERENCES department (id) ON DELETE CASCADE
);

create table document_person
(
    document_id,
    person_id,
    FOREIGN KEY (document_id) REFERENCES document (id) ON DELETE CASCADE,
    FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE
);

create table document_task
(
    document_id INTEGER,
    task_id     INTEGER,
    FOREIGN KEY (document_id) REFERENCES document (id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE
);

create table document_tag
(
    tag_id      INTEGER,
    document_id INTEGER,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE,
    FOREIGN KEY (document_id) REFERENCES document (id) ON DELETE CASCADE
);

create table task_tag
(
    tag_id  INTEGER,
    task_id INTEGER,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE,
    FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE
);

CREATE TABLE task_person
(
    task_id   INTEGER,
    person_id INTEGER,
    FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE,
    FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE
);
