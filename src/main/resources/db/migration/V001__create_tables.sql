create table subject(
    "id"   serial      not null UNIQUE,
    "name" varchar(255) NOT NULL default '',
    "purpose" varchar(255) NOT NULL default '',
    "description" varchar(255) NOT NULL default '',
    "result" varchar(255) NOT NULL default '',
    "authors" varchar(255) NOT NULL default '',
     CONSTRAINT subject_pkey PRIMARY KEY (id),
     CONSTRAINT subject_uk unique ("name")
);

create table tutor (
    "id"   serial      not null UNIQUE,
    "username" varchar(255) NOT NULL default '',
    "password" varchar(255) NOT NULL default '',
    "firstname" varchar(255) NOT NULL default '',
    "lastname" varchar(255) NOT NULL default '',
    "institution" varchar(255) NOT NULL default '',
    "department" varchar(255) NOT NULL default '',
    "city" varchar(255) NOT NULL default '',
    "country" varchar(255) NOT NULL default '',
    "maildisplay" boolean NOT NULL default FALSE,
    "email" varchar(255) NOT NULL default '',
    CONSTRAINT tutor_pkey PRIMARY KEY (id),
    CONSTRAINT tutor_uk unique (username, firstname, lastname)
);

create table course (
    "id"   serial      not null UNIQUE,
    "name" varchar(255) NOT NULL default '',
    "code" varchar(255) NOT NULL default '',
    "description" varchar(255) NOT NULL default '',
    "start_date" timestamp      not null default CURRENT_TIMESTAMP,
    "end_date" timestamp      not null default CURRENT_TIMESTAMP,
    "pass_criteria_description" varchar(255) NOT NULL default '',
    "subject_id" bigint NOT NULL,
    CONSTRAINT course_pkey PRIMARY KEY (id),
    CONSTRAINT course_uk unique (code),
    CONSTRAINT subject_fk foreign key (subject_id) references subject(id)
);

create table "module" (
    "id"   serial      not null UNIQUE,
    "code" varchar(255) NOT NULL default '',
    "start_date" timestamp      not null default CURRENT_TIMESTAMP,
    "end_date" timestamp      not null default CURRENT_TIMESTAMP,
    "course_id" bigint not null,
    CONSTRAINT module_pkey PRIMARY KEY (id),
    CONSTRAINT module_uk unique (code),
    CONSTRAINT course_fk foreign key (course_id) references course(id)
);
create table topic(
    "id"   serial      not null UNIQUE,
    "description" varchar(255) NOT NULL default '',
    "module_id"  bigint NOT NULL,
    CONSTRAINT topic_pkey PRIMARY KEY (id),
    CONSTRAINT topic_uk unique (description, module_id),
    CONSTRAINT module_fk foreign key (module_id) references "module"(id)
);

create table topic_file(
    "id"   serial      not null UNIQUE,
    "content" TEXT,
    "media_type" varchar(255),
    "link" varchar(255),
    "name" varchar(255),
    "topic_id" bigint not null,
    CONSTRAINT topic_file_pkey PRIMARY KEY (id),
    CONSTRAINT topic_file_fk foreign key (topic_id) references topic(id)
);

create table course_tutor(
    "course_id" bigint not null,
    "tutor_id" bigint not null,
    CONSTRAINT course_tutor_pkey PRIMARY KEY (course_id, tutor_id),
    CONSTRAINT course_fk foreign key (course_id) references course(id),
    CONSTRAINT tutor_fk foreign key (tutor_id) references tutor(id)
);

create table module_tutor(
    "module_id" bigint not null,
    "tutor_id" bigint not null,
    CONSTRAINT module_tutor_pkey PRIMARY KEY (module_id, tutor_id),
    CONSTRAINT module_fk foreign key (module_id) references "module"(id),
    CONSTRAINT tutor_fk foreign key (tutor_id) references tutor(id)
);