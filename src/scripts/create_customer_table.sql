CREATE TABLE customer (
                      id varchar(36) not null,
                      customer_name varchar(50) not null,
                      created_date date(6),
                      last_modified_date date(6),
                      version integer,
                      primary key (id)

) engine=innodb