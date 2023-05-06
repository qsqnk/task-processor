create type TaskStatus as enum ('SCHEDULED', 'COMPLETED', 'FAILED');

create table tasks
(
    id           bigserial    not null primary key,
    name         varchar(256) not null,
    status       TaskStatus   not null,
    scheduled_ts timestamp    not null,
    created_ts   timestamp    not null,
    updated_ts   timestamp    not null
);

create index idx__tasks__status__scheduled_ts
    on tasks (status, scheduled_ts);

create table tasks_properties
(
    id         bigserial    not null primary key,
    task_id    bigint       not null,
    field_name varchar(256) not null,
    value      jsonb        not null,

    constraint fk__tasks_properties__task_id
        foreign key (task_id)
            references tasks (id)
);

create index idx__tasks_properties__task_id__field_name
    on tasks_properties (task_id, field_name);