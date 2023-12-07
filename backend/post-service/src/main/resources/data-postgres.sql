-- Continue inserting data into favourite_list table
INSERT INTO public.favourite_list (list_id, created_at, modified_at, name, user_id)
VALUES
    ('6c84fbaa-12c4-11ec-82a8-0242ac130003'::uuid, '2023-01-05 00:00:00'::timestamp, '2023-01-05 00:00:00'::timestamp, 'Holiday Picks', '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbab-12c4-11ec-82a8-0242ac130003'::uuid, '2023-01-06 00:00:00'::timestamp, '2023-01-06 00:00:00'::timestamp, 'Reading List', '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid, '2023-01-07 00:00:00'::timestamp, '2023-01-07 00:00:00'::timestamp, 'Recipes to Try', '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid);

-- Continue inserting data into post table
INSERT INTO public.post (post_id, created_at, modified_at, content, is_deleted, user_id)
VALUES
    ('6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid, '2023-02-05 12:00:00'::timestamp, '2023-02-05 12:00:00'::timestamp, 'Post content 5', false, '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid, '2023-02-06 12:00:00'::timestamp, '2023-02-06 12:00:00'::timestamp, 'Post content 6', false, '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbaf-12c4-11ec-82a8-0242ac130003'::uuid, '2023-02-07 12:00:00'::timestamp, '2023-02-07 12:00:00'::timestamp, 'Post content 7', false, '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid);

-- Continue inserting data into comment table
INSERT INTO public.comment (comment_id, created_at, modified_at, content, is_deleted, user_id, post_id)
VALUES
    ('6c84fbb0-12c4-11ec-82a8-0242ac130003'::uuid, '2023-03-05 12:00:00'::timestamp, '2023-03-05 12:00:00'::timestamp, 'Comment 5', false, '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbb1-12c4-11ec-82a8-0242ac130003'::uuid, '2023-03-06 12:00:00'::timestamp, '2023-03-06 12:00:00'::timestamp, 'Comment 6', false, '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbb2-12c4-11ec-82a8-0242ac130003'::uuid, '2023-03-07 12:00:00'::timestamp, '2023-03-07 12:00:00'::timestamp, 'Comment 7', false, '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbaf-12c4-11ec-82a8-0242ac130003'::uuid);

-- Continue inserting data into comment_reaction table
INSERT INTO public.comment_reaction (comment_reaction_id, type, user_id, comment_id)
VALUES
    ('6c84fbb3-12c4-11ec-82a8-0242ac130003'::uuid, 'HA_HA', '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbb0-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbb4-12c4-11ec-82a8-0242ac130003'::uuid, 'WOW', '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbb1-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbb5-12c4-11ec-82a8-0242ac130003'::uuid, 'LIKE', '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbb2-12c4-11ec-82a8-0242ac130003'::uuid);

-- Continue inserting data into favourite_post table
INSERT INTO public.favourite_post (fav_post_id, list_id, post_id)
VALUES
    ('6c84fbb6-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbaa-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbb7-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbab-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbb8-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbaf-12c4-11ec-82a8-0242ac130003'::uuid);

-- Continue inserting data into post_reaction table
INSERT INTO public.post_reaction (reaction_id, type, user_id, post_id)
VALUES
    ('6c84fbb9-12c4-11ec-82a8-0242ac130003'::uuid, 'SAD', '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbba-12c4-11ec-82a8-0242ac130003'::uuid, 'LIKE', '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbbb-12c4-11ec-82a8-0242ac130003'::uuid, 'WOW', '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbaf-12c4-11ec-82a8-0242ac130003'::uuid);

-- Continue inserting data into tag table
INSERT INTO public.tag (tag_id, tag_name)
VALUES
    ('6c84fbbc-12c4-11ec-82a8-0242ac130003'::uuid, 'Nature'),
    ('6c84fbbd-12c4-11ec-82a8-0242ac130003'::uuid, 'Movies'),
    ('6c84fbbe-12c4-11ec-82a8-0242ac130003'::uuid, 'Books');

-- Continue inserting data into tagging table
INSERT INTO public.tagging (tagging_id, post_id, tagging)
VALUES
    ('6c84fbbf-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbbc-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbc0-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbbd-12c4-11ec-82a8-0242ac130003'::uuid),
    ('6c84fbc1-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbaf-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fbbe-12c4-11ec-82a8-0242ac130003'::uuid);
