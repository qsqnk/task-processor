create type TaskStatusEnum as enum ('SCHEDULED', 'COMPLETED', 'FAILED');

-- tasks --

create table tasks
(
    id           bigserial      not null primary key,
    name         varchar(256)   not null,
    status       TaskStatusEnum not null,
    scheduled_ts timestamp      not null default (now() at time zone 'UTC'),
    created_ts   timestamp      not null default (now() at time zone 'UTC'),
    updated_ts   timestamp      not null default (now() at time zone 'UTC')
);

create index idx__tasks__status__scheduled_ts
    on tasks (status, scheduled_ts);

-- properties --

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

-- triggers --

create function fn__tasks__update()
    returns trigger as
$$
begin
    new.updated_ts = (now() at time zone 'UTC');
    return new;
end;
$$ language plpgsql;

create trigger tr__executions__update
    before update
    on tasks
    for each row
execute function fn__tasks__update();