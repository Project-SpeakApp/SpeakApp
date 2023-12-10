DO
$$
    DECLARE
        post_id_1         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid;
        post_id_2         uuid      := '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid;
        post_id_3         uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_1         uuid      := '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_2         uuid      := '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_3         uuid      := '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid;
        timestamp_earlier timestamp := '2023-01-05 00:00:00'::timestamp;
        timestamp_later   timestamp := '2023-12-24 00:00:00'::timestamp;
        comment_id_1      uuid      := '6c84fbb0-12c4-11ec-82a8-0242ac130003'::uuid;
        comment_id_2      uuid      := '6c84fbb1-12c4-11ec-82a8-0242ac130003'::uuid;
        comment_id_3      uuid      := '6c84fbb2-12c4-11ec-82a8-0242ac130003'::uuid;
        comment_id_4      uuid      := '6c84fbb3-12c4-11ec-82a8-0242ac130003'::uuid;
        list_id_1         uuid      := '6c84fbaa-12c4-11ec-82a8-0242ac130003'::uuid;
        list_id_2         uuid      := '6c84fbab-12c4-11ec-82a8-0242ac130003'::uuid;
        list_id_3         uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid;
        tag_id_1          uuid      := '6c84fbbe-12c4-11ec-82a8-0242ac130003'::uuid;
        tag_id_2          uuid      := '6c84fbbf-12c4-11ec-82a8-0242ac130003'::uuid;
    BEGIN
        INSERT INTO post (post_id, user_id, content, created_at, modified_at, is_deleted)
        VALUES (post_id_1, user_id_1, 'Hello, everyone it is my first post on speakapp ^^', timestamp_earlier,
                timestamp_earlier, false),
               (post_id_2, user_id_2, 'Wow, speakapp is great!', timestamp_later, timestamp_later, false),
               (post_id_3, user_id_1, 'It''s my second post here, I love this site!', timestamp_later,
                timestamp_later,
                false);

        INSERT INTO comment (comment_id, post_id, user_id, content, created_at, modified_at, is_deleted)
        VALUES (comment_id_1, post_id_1, user_id_2, 'Hello, I am glad to see you here!', timestamp_earlier,
                timestamp_earlier, false),
               (comment_id_2, post_id_2, user_id_3, 'Thank you for your feedback <3!', timestamp_later,
                timestamp_later,
                false),
               (comment_id_3, post_id_3, user_id_3, 'We hope it''s not last, thank you for your feedback <3!',
                timestamp_later, timestamp_later, false),
               (comment_id_4, post_id_1, user_id_3, 'Welcome to speakapp!', timestamp_earlier, timestamp_earlier,
                false);

        INSERT INTO comment_reaction (reaction_id, comment_id, user_id, type)
        VALUES ('6c84fbb4-12c4-11ec-82a8-0242ac130003'::uuid, comment_id_1, user_id_1, 'HA_HA'),
               ('6c84fbb5-12c4-11ec-82a8-0242ac130003'::uuid, comment_id_2, user_id_2, 'WOW'),
               ('6c84fbb6-12c4-11ec-82a8-0242ac130003'::uuid, comment_id_3, user_id_1, 'LIKE'),
               ('6c84fbb7-12c4-11ec-82a8-0242ac130003'::uuid, comment_id_4, user_id_2, 'HA_HA');

        INSERT INTO post_reaction (reaction_id, post_id, user_id, type)
        VALUES ('6c84fbb8-12c4-11ec-82a8-0242ac130003'::uuid, post_id_1, user_id_1, 'SAD'),
               ('6c84fbb9-12c4-11ec-82a8-0242ac130003'::uuid, post_id_2, user_id_2, 'LIKE'),
               ('6c84fbba-12c4-11ec-82a8-0242ac130003'::uuid, post_id_3, user_id_3, 'WOW');

        INSERT INTO favourite_list (list_id, user_id, name, created_at, modified_at)
        VALUES (list_id_1, user_id_1, 'My favourite posts', timestamp_earlier, timestamp_earlier),
               (list_id_2, user_id_2, 'My favourite posts', timestamp_later, timestamp_later),
               (list_id_3, user_id_3, 'My favourite posts', timestamp_later, timestamp_later);

        INSERT INTO favourite_post (fav_post_id, list_id, post_id)
        VALUES ('6c84fbbb-12c4-11ec-82a8-0242ac130003'::uuid, list_id_1, post_id_1),
               ('6c84fbbc-12c4-11ec-82a8-0242ac130003'::uuid, list_id_2, post_id_2),
               ('6c84fbbd-12c4-11ec-82a8-0242ac130003'::uuid, list_id_3, post_id_3);

        INSERT INTO tag (tag_id, tag_name)
        VALUES (tag_id_1, 'Nature'),
               (tag_id_2, 'Movies');

        INSERT INTO tagging (tagging_id, post_id, tag_id)
        VALUES ('6c84fbc1-12c4-11ec-82a8-0242ac130003'::uuid, post_id_1, tag_id_1),
               ('6c84fbc2-12c4-11ec-82a8-0242ac130003'::uuid, post_id_2, tag_id_2);
    END
$$;