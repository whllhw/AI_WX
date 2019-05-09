drop table if exists data_set;

create table data_set
(
    id        bigint(20)                              not null auto_increment,
    file_name varchar(255) collate utf8mb4_unicode_ci not null,
    label     varchar(255) collate utf8mb4_unicode_ci default null,
    state     int(11)                                 default null,
    task_id   bigint(20)                              not null,
    time      datetime                                default null,
    type      varchar(255) collate utf8mb4_unicode_ci default null,
    user_id   varchar(255) collate utf8mb4_unicode_ci not null,
    primary key (id)
) engine = InnoDB
  auto_increment = 11
  charset = utf8mb4
  collate = utf8mb4_unicode_ci;