/* Delete all the tables */
DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public IS 'standard public schema';

/* Delete all types */
drop type if exists anime_user_infos_status;
drop type if exists tags_importance;
drop type if exists threads_status;
drop type if exists posts_status;

/* Start creating the database elements */

    /* Create tables connected to the user */
create table users (
    id uuid not null unique primary key,
    username varchar(45) not null unique,
    watch_time int not null default 0,
    achievement_points int not null default 0
);
comment on table users is 'Table containing basic information on a user. The id is assigned by the keycloak server and copied here.';

create table achievements (
    id smallserial not null unique primary key check ( id >= 0 ),
    name varchar(45) not null unique check ( trim(name) != '' ),
    description varchar(100) default 'No description given',
    icon_path varchar(100) not null unique,
    points smallint not null default 10 check ( points >= 0 )
);
comment on table achievements is 'Table containing information on achievements that the users can earn. Their conditions are determined in the main backend module (achievement_listeners)';

create table user_achievements (
    achievement_id smallint not null check ( achievement_id >= 0 ),
    constraint fk_userachievements_achievement foreign key (achievement_id) references achievements(id),

    user_id uuid not null,
    constraint fk_userachievements_user foreign key (user_id) references users(id),

    primary key (achievement_id, user_id)
);
comment on table user_achievements is 'Table creating a many-to-many relationship between the users and achievements tables. Represents the achievements a user earned';

    /* Create tables connected to Anime */
create table anime (
    id int not null unique primary key check ( id >= 0 ),
    averageScore numeric(2, 1) not null default 0 check ( averageScore >= 0.0 AND averageScore <= 10.0 ),
    nr_scores int not null default 0 check ( nr_scores >= 0 ),
    nr_favourites int not null default 0 check ( nr_favourites >= 0 ),
    nr_reviews int not null default 0 check ( nr_reviews >= 0 ),
    average_episode_length smallint default 20 check ( average_episode_length >= 0 )
);
comment on table anime is 'Table containing basic information on an Anime. Some data, like id or episode length, is copied from anilist';

create table reviews (
    id serial not null unique primary key check ( id >= 0 ),
    title varchar(100) not null check ( trim(title) != '' ),
    text varchar(300) default 'No text given',
    nr_helpful int not null default 0 check ( nr_helpful >= 0 ),
    nr_plus int not null default 0 check ( nr_plus >= 0 ),
    nr_minus int not null default 0 check ( nr_minus >= 0 )
);
comment on table reviews is 'Table containing a review on an Anime written by a user';

create type anime_user_infos_status as enum ('NO_STATUS', 'WATCHING', 'COMPLETED', 'DROPPED', 'PLAN_TO_WATCH');
create table anime_user_infos (
    user_id uuid not null,
    constraint fk_animeuserinfos_user foreign key (user_id) references users(id),

    anime_id int not null check ( anime_id >= 0 ),
    constraint fk_animeuserinfos_anime foreign key (user_id) references users(id),

    primary key (user_id, anime_id),

    status anime_user_infos_status not null,
    watch_start date,
    watch_end date,
    episodes_seen smallint not null default 0 check ( episodes_seen >= 0 ),
    favourite boolean not null default false,
    grade smallint not null check ( grade > 0 AND grade <= 10 ),
    modification timestamp not null default current_timestamp check ( modification <= current_timestamp ),
    review int unique check ( review >= 0 ),
    constraint fk_animeuserinfos_review foreign key (review) references reviews(id)
);
comment on table anime_user_infos is 'Table used to represent the many-to-many relationship between users and anime. For each combinations holds the opinion of the user on an Anime';

    /* Create tables connected to Forum */
create table forum_categories (
    id smallserial not null unique primary key check ( id >= 0 ),
    name varchar(45) not null unique,
    description varchar(150) not null
);
comment on table forum_categories is 'Table containing information on a forum category. Each thread can have one category';

create type tags_importance as enum ('LOW', 'MEDIUM', 'HIGH', 'ADMIN');
create table tags (
    id serial not null unique primary key check ( id >= 0 ),
    name varchar(45) not null check ( trim(name) != '' ),
    importance tags_importance not null default 'LOW',
    color varchar(18) default 'rgb(166, 166, 166)'
);
comment on table tags is 'Table containing information on tags a thread can have';

create type threads_status as enum ('OPEN', 'CLOSED');
create table threads (
    id serial not null unique primary key check ( id >= 0 ),
    title varchar(80) not null check ( trim(title) != '' ),
    text varchar(600) default 'No description given',
    status threads_status not null default 'OPEN',
    nr_posts smallint default 0 check ( nr_posts >= 0 ),
    creation timestamp not null default now() check ( creation <= now() ),
    modification timestamp not null default now() check ( creation <= now() ),
    category smallint not null,
    constraint fk_threads_category foreign key (category) references forum_categories(id),
    creator uuid not null,
    constraint fk_threads_users foreign key (creator) references users(id)
);
comment on table threads is 'Table containing all information on a given thread';

create table thread_user_status (
    user_id uuid not null,
    constraint fk_threaduserstatus_user foreign key (user_id) references users(id),

    thread_id int not null check ( thread_id >= 0 ),
    constraint fk_threaduserstatus_thread foreign key (thread_id) references threads(id),

    primary key (user_id, thread_id),

    watched boolean not null default false,
    blocked boolean not null default false
);
comment on table thread_user_status is 'Table creating many-to-many relationship between the user and thread tables. Represents a user and his/her relation to a thread';

create table thread_tags (
    thread_id int not null check ( thread_id >= 0 ),
    constraint fk_threadtags_thread foreign key (thread_id) references threads(id),

    tag_id int not null check ( tag_id >= 0 ),
    constraint fk_threadtags_tag foreign key (tag_id) references tags(id),

    primary key (thread_id, tag_id)
);
comment on table thread_tags is 'Table creating many-to-many relationship between the threads and tags tables. Represents a thread and its tags';

create type posts_status as enum ('NO_PROBLEM', 'PENDING', 'DELETED');
create table posts (
    id serial not null unique primary key check ( id >= 0 ),
    title varchar(80) not null check ( trim(title) != '' ),
    text varchar(600) default 'No description given',
    blocked boolean not null default false,
    status posts_status not null default 'NO_PROBLEM',
    nr_plus int not null default 0 check ( nr_plus >= 0 ),
    nr_minus int not null default 0 check ( nr_minus >= 0 ),
    nr_reports int not null default 0 check ( nr_reports >= 0 ),
    creation timestamp not null default now() check ( creation <= now() ),
    modification timestamp not null default now() check ( creation <= now() ),
    creator uuid not null,
    constraint fk_threaduserstatus_user foreign key (creator) references users(id),
    thread int not null check ( thread >= 0 ),
    constraint fk_threadtags_thread foreign key (thread) references threads(id)
);
comment on table thread_tags is 'Table containing all information on a post';

create table post_responses (
    post_id int not null check ( post_id >= 0 ),
    constraint fk_postresponses_post foreign key (post_id) references posts(id),

    response_id int not null check ( response_id >= 0 AND response_id != post_id ),
    constraint fk_postresponses_response foreign key (response_id) references posts(id),

    primary key (post_id, response_id)
);
comment on table post_responses is 'Table connecting a post with its replies';

create table post_user_status (
    user_id uuid not null,
    constraint fk_postuserstatus_user foreign key (user_id) references users(id),

    post_id int not null check ( post_id >= 0 ),
    constraint fk_postuserstatus_post foreign key (post_id) references posts(id),

    primary key (user_id, post_id),

    liked boolean not null default false,
    disliked boolean not null default false,
    reported boolean not null default false
);