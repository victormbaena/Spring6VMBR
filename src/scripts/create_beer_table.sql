CREATE TABLE beer (
    id varchar(36) not null,
    beer_name varchar(50) not null,
    beer_style smallint not null,
    created_date date(6),
    price decimal(38,2) not null,
    quantity_on_hand integer,
    upc varchar(255) not null,
    update_date date(6),
    version integer,
    primary key (id)

) engine=innodb