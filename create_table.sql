create table person(
                       id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                       firstName TEXT,
                       lastName TEXT,
                       patronymic TEXT,
                       birthDay TEXT,
                       department TEXT,
                       position TEXT,
                       rank TEXT
);

create table task(
                     id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                     name,
                     period,
                     deadline,
                     description,
                     type,
                     complete
);

CREATE TABLE task_executor(
                       task_id INTEGER,
                       person_id INTEGER,
                       FOREIGN KEY(task_id) REFERENCES task(id) ON DELETE CASCADE,
                       FOREIGN KEY(person_id) REFERENCES person(id) ON DELETE CASCADE
);