DO
$$
    DECLARE
        post_id_1         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid;
        post_id_2         uuid      := '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid;
        post_id_3         uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid;
        post_id_4         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130004'::uuid;
        post_id_5         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130005'::uuid;
        post_id_6         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130006'::uuid;
        post_id_7         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130007'::uuid;
        post_id_8         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130008'::uuid;
        post_id_9         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130009'::uuid;
        post_id_10        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130010'::uuid;
        post_id_11        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130011'::uuid;
        post_id_12        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130012'::uuid;
        post_id_13        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130013'::uuid;
        post_id_14        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130014'::uuid;
        post_id_15        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130015'::uuid;

        user_id_1         uuid      := '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_2         uuid      := '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_3         uuid      := '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_4         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_5         uuid      := '6c84fb99-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_6         uuid      := '6c84fb9a-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_7         uuid      := '6c84fb9b-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_8         uuid      := '6c84fb9c-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_9         uuid      := '6c84fb9d-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_10        uuid      := '6c84fb9e-12c4-11ec-82a8-0242ac130003'::uuid;

        timestamp_earlier timestamp := '2023-01-05 00:00:00'::timestamp;
        timestamp_later   timestamp := '2023-12-24 00:00:00'::timestamp;
        
        list_id_1         uuid      := '6c84fbaa-12c4-11ec-82a8-0242ac130003'::uuid;
        list_id_2         uuid      := '6c84fbab-12c4-11ec-82a8-0242ac130003'::uuid;
        list_id_3         uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid;
        list_id_4         uuid      := '6c84fbc0-12c4-11ec-82a8-0242ac130004'::uuid;

        tag_id_1          uuid      := '6c84fbbe-12c4-11ec-82a8-0242ac130003'::uuid;
        tag_id_2          uuid      := '6c84fbbf-12c4-11ec-82a8-0242ac130003'::uuid;
        tag_id_3          uuid      := '6c84fbcd-12c4-11ec-82a8-0242ac130003'::uuid;
        tag_id_4          uuid      := '6c84fbce-12c4-11ec-82a8-0242ac130003'::uuid;
        tag_id_5          uuid      := '6c84fbcf-12c4-11ec-82a8-0242ac130003'::uuid;

    BEGIN
        INSERT INTO post (post_id, user_id, content, created_at, modified_at, is_deleted)
        VALUES (post_id_1, user_id_1, 'Hello, everyone it is my first post on speakapp ^^', timestamp_earlier,
                timestamp_earlier, false),
               (post_id_2, user_id_2, 'Wow, speakapp is great!', timestamp_later, timestamp_later, false),
               (post_id_3, user_id_1, 'It''s my second post here, I love this site!', timestamp_later,
                timestamp_later,
                false),
               (post_id_4, user_id_4, 'Just discovered a new recipe for chocolate chip cookies! Can''t wait to try it out.', '2023-01-10 12:00:00', '2023-01-10 12:00:00', false),
               (post_id_5, user_id_5, 'Excited about the upcoming weekend getaway to the mountains.', '2023-01-11 12:00:00', '2023-01-11 12:00:00', false),
               (post_id_6, user_id_6, 'Found a great book at the local bookstore. Highly recommend "The Adventure of a Lifetime."', '2023-01-12 12:00:00', '2023-01-12 12:00:00', false),
               (post_id_7, user_id_7, 'Trying out a new workout routine this week. Feeling motivated!', '2023-01-13 12:00:00', '2023-01-13 12:00:00', false),
               (post_id_8, user_id_8, 'Just adopted a rescue puppy! Meet the newest member of the family.', '2023-01-14 12:00:00', '2023-01-14 12:00:00', false),
               (post_id_9, user_id_9, 'Enjoying a lazy Sunday afternoon with a cup of hot cocoa.', '2023-01-15 12:00:00', '2023-01-15 12:00:00', false),
               (post_id_10, user_id_10, 'Reflecting on the past year and setting new goals for the months ahead.', '2023-01-16 12:00:00', '2023-01-16 12:00:00', false),
               (post_id_11, user_id_1, 'Attended an inspiring workshop on creative writing. Feeling inspired to write more!', '2023-01-17 12:00:00', '2023-01-17 12:00:00', false),
               (post_id_12, user_id_2, 'Exploring new music genres. Any recommendations?', '2023-01-18 12:00:00', '2023-01-18 12:00:00', false),
               (post_id_13, user_id_3, 'Weekend DIY project: building a small herb garden.', '2023-01-19 12:00:00', '2023-01-19 12:00:00', false),
               (post_id_14, user_id_4, 'Celebrating a personal milestone today. Grateful for the journey!', '2023-01-20 12:00:00', '2023-01-20 12:00:00', false),
               (post_id_15, user_id_5, 'Movie night with friends. Any movie recommendations?', '2023-01-21 12:00:00', '2023-01-21 12:00:00', false);
        
        INSERT INTO comment (comment_id, post_id, user_id, content, created_at, modified_at, is_deleted)
        VALUES
         -- Komentarze dla post_id_1
            ('6c84fbbf-12c4-11ec-82a8-0242ac130004'::uuid, post_id_1, user_id_6, 'Great post!', timestamp_earlier, timestamp_earlier, false),
            ('6c84fbc0-12c4-11ec-82a8-0242ac130004'::uuid, post_id_1, user_id_7, 'I love the content!', timestamp_later, timestamp_later, false),
            ('6c84fbc1-12c4-11ec-82a8-0242ac130004'::uuid, post_id_1, user_id_8, 'Looking forward to more!', timestamp_later, timestamp_later, false),

            -- Komentarze dla post_id_2
            ('6c84fbc2-12c4-11ec-82a8-0242ac130004'::uuid, post_id_2, user_id_9, 'Well done!', timestamp_earlier, timestamp_earlier, false),
            ('6c84fbc3-12c4-11ec-82a8-0242ac130004'::uuid, post_id_2, user_id_10, 'Impressive!', timestamp_later, timestamp_later, false),
            ('6c84fbc4-12c4-11ec-82a8-0242ac130004'::uuid, post_id_2, user_id_1, 'Great content!', timestamp_later, timestamp_later, false),

            -- Komentarze dla post_id_3
            ('6c84fbc5-12c4-11ec-82a8-0242ac130004'::uuid, post_id_3, user_id_2, 'Nice insights!', timestamp_earlier, timestamp_earlier, false),
            ('6c84fbc6-12c4-11ec-82a8-0242ac130004'::uuid, post_id_3, user_id_3, 'I appreciate your thoughts.', timestamp_later, timestamp_later, false),
            ('6c84fbc7-12c4-11ec-82a8-0242ac130004'::uuid, post_id_3, user_id_4, 'Well written!', timestamp_later, timestamp_later, false),

            -- Komentarze dla post_id_4
            ('6c84fbc8-12c4-11ec-82a8-0242ac130004'::uuid, post_id_4, user_id_5, 'Im glad I discovered this post.', timestamp_earlier, timestamp_earlier, false),
            ('6c84fbc9-12c4-11ec-82a8-0242ac130004'::uuid, post_id_4, user_id_6, 'Looking forward to your next one!', timestamp_later, timestamp_later, false),
            ('6c84fbca-12c4-11ec-82a8-0242ac130004'::uuid, post_id_4, user_id_7, 'Your content is amazing!', timestamp_later, timestamp_later, false),
        

        -- Komentarze dla post_id_5
        ('6c84fbcb-12c4-11ec-82a8-0242ac130004'::uuid, post_id_5, user_id_8, 'Interesting post!', timestamp_earlier, timestamp_earlier, false),
        ('6c84fbcc-12c4-11ec-82a8-0242ac130004'::uuid, post_id_5, user_id_9, 'I enjoyed reading this.', timestamp_later, timestamp_later, false),
        ('6c84fbcd-12c4-11ec-82a8-0242ac130004'::uuid, post_id_5, user_id_10, 'Well articulated.', timestamp_later, timestamp_later, false),

        -- Komentarze dla post_id_6
        ('6c84fbce-12c4-11ec-82a8-0242ac130004'::uuid, post_id_6, user_id_1, 'Great insights!', timestamp_earlier, timestamp_earlier, false),
        ('6c84fbcf-12c4-11ec-82a8-0242ac130004'::uuid, post_id_6, user_id_2, 'I appreciate your perspective.', timestamp_later, timestamp_later, false),
        ('6c84fbd0-12c4-11ec-82a8-0242ac130004'::uuid, post_id_6, user_id_3, 'Looking forward to more content.', timestamp_later, timestamp_later, false),

        -- Komentarze dla post_id_7
        ('6c84fbd1-12c4-11ec-82a8-0242ac130004'::uuid, post_id_7, user_id_4, 'Impressive post!', timestamp_earlier, timestamp_earlier, false),
        ('6c84fbd2-12c4-11ec-82a8-0242ac130004'::uuid, post_id_7, user_id_5, 'Well done!', timestamp_later, timestamp_later, false),
        ('6c84fbd3-12c4-11ec-82a8-0242ac130004'::uuid, post_id_7, user_id_6, 'Your insights are valuable.', timestamp_later, timestamp_later, false),

         -- Komentarze dla post_id_8
         ('6c84fbd4-12c4-11ec-82a8-0242ac130004'::uuid, post_id_8, user_id_7, 'Great post!', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbd5-12c4-11ec-82a8-0242ac130004'::uuid, post_id_8, user_id_8, 'I love the content!', timestamp_later, timestamp_later, false),
         ('6c84fbd6-12c4-11ec-82a8-0242ac130004'::uuid, post_id_8, user_id_9, 'Looking forward to more!', timestamp_later, timestamp_later, false),

         -- Komentarze dla post_id_9
         ('6c84fbd7-12c4-11ec-82a8-0242ac130004'::uuid, post_id_9, user_id_10, 'Well done!', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbd8-12c4-11ec-82a8-0242ac130004'::uuid, post_id_9, user_id_1, 'Impressive!', timestamp_later, timestamp_later, false),
         ('6c84fbd9-12c4-11ec-82a8-0242ac130004'::uuid, post_id_9, user_id_2, 'Great content!', timestamp_later, timestamp_later, false),

         -- Komentarze dla post_id_10
         ('6c84fbda-12c4-11ec-82a8-0242ac130004'::uuid, post_id_10, user_id_3, 'Nice insights!', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbdb-12c4-11ec-82a8-0242ac130004'::uuid, post_id_10, user_id_4, 'I appreciate your thoughts.', timestamp_later, timestamp_later, false),
         ('6c84fbdc-12c4-11ec-82a8-0242ac130004'::uuid, post_id_10, user_id_5, 'Well written!', timestamp_later, timestamp_later, false),

         -- Komentarze dla post_id_11
         ('6c84fbdd-12c4-11ec-82a8-0242ac130004'::uuid, post_id_11, user_id_6, 'Im glad I discovered this post.', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbde-12c4-11ec-82a8-0242ac130004'::uuid, post_id_11, user_id_7, 'Looking forward to your next one!', timestamp_later, timestamp_later, false),
         ('6c84fbdf-12c4-11ec-82a8-0242ac130004'::uuid, post_id_11, user_id_8, 'Your content is amazing!', timestamp_later, timestamp_later, false),

         -- Kontynuacja uzupełniania komentarzy dla pozostałych postów...

         -- Komentarze dla post_id_12
         ('6c84fbe0-12c4-11ec-82a8-0242ac130004'::uuid, post_id_12, user_id_9, 'Interesting post!', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbe1-12c4-11ec-82a8-0242ac130004'::uuid, post_id_12, user_id_10, 'I enjoyed reading this.', timestamp_later, timestamp_later, false),
         ('6c84fbe2-12c4-11ec-82a8-0242ac130004'::uuid, post_id_12, user_id_1, 'Well articulated.', timestamp_later, timestamp_later, false),

         -- Komentarze dla post_id_13
         ('6c84fbe3-12c4-11ec-82a8-0242ac130004'::uuid, post_id_13, user_id_2, 'Great insights!', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbe4-12c4-11ec-82a8-0242ac130004'::uuid, post_id_13, user_id_3, 'I appreciate your perspective.', timestamp_later, timestamp_later, false),
         ('6c84fbe5-12c4-11ec-82a8-0242ac130004'::uuid, post_id_13, user_id_4, 'Looking forward to more content.', timestamp_later, timestamp_later, false),

         -- Kontynuacja uzupełniania komentarzy dla pozostałych postów...

         -- Komentarze dla post_id_14
         ('6c84fbe6-12c4-11ec-82a8-0242ac130004'::uuid, post_id_14, user_id_5, 'Impressive post!', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbe7-12c4-11ec-82a8-0242ac130004'::uuid, post_id_14, user_id_6, 'Well done!', timestamp_later, timestamp_later, false),
         ('6c84fbe8-12c4-11ec-82a8-0242ac130004'::uuid, post_id_14, user_id_7, 'Your insights are valuable.', timestamp_later, timestamp_later, false),

         -- Komentarze dla post_id_15
         ('6c84fbe9-12c4-11ec-82a8-0242ac130004'::uuid, post_id_15, user_id_8, 'Great post!', timestamp_earlier, timestamp_earlier, false),
         ('6c84fbea-12c4-11ec-82a8-0242ac130004'::uuid, post_id_15, user_id_9, 'I love the content!', timestamp_later, timestamp_later, false),
         ('6c84fbeb-12c4-11ec-82a8-0242ac130004'::uuid, post_id_15, user_id_10, 'Looking forward to more!', timestamp_later, timestamp_later, false);

        INSERT INTO comment_reaction (reaction_id, comment_id, user_id, type)
        VALUES
           -- Reakcje dla komentarza '6c84fbbf-12c4-11ec-82a8-0242ac130004'
           ('6c84fbd4-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbbf-12c4-11ec-82a8-0242ac130004'::uuid, user_id_1, 'LIKE'),
           ('6c84fbea-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbbf-12c4-11ec-82a8-0242ac130004'::uuid, user_id_2, 'HA_HA'),
           ('6c85fbea-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbbf-12c4-11ec-82a8-0242ac130004'::uuid, user_id_3, 'HA_HA'),

           -- Reakcje dla komentarza '6c84fbc0-12c4-11ec-82a8-0242ac130004'
           ('6c84fbd5-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc0-12c4-11ec-82a8-0242ac130004'::uuid, user_id_3, 'LIKE'),
           ('6c84fbeb-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc0-12c4-11ec-82a8-0242ac130004'::uuid, user_id_4, 'SAD'),

           -- Reakcje dla komentarza '6c84fbc1-12c4-11ec-82a8-0242ac130004'
           ('6c84fbd6-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc1-12c4-11ec-82a8-0242ac130004'::uuid, user_id_5, 'LIKE'),
           ('6c84fbec-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc1-12c4-11ec-82a8-0242ac130004'::uuid, user_id_6, 'WRR'),

           -- Reakcje dla komentarza '6c84fbc2-12c4-11ec-82a8-0242ac130004'
           ('6c84fbd7-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc2-12c4-11ec-82a8-0242ac130004'::uuid, user_id_7, 'LIKE'),
           ('6c84fbed-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc2-12c4-11ec-82a8-0242ac130004'::uuid, user_id_8, 'WOW'),

           -- Reakcje dla komentarza '6c84fbc3-12c4-11ec-82a8-0242ac130004'
           ('6c84fbd8-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc3-12c4-11ec-82a8-0242ac130004'::uuid, user_id_9, 'LIKE'),
           ('6c84fbee-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc3-12c4-11ec-82a8-0242ac130004'::uuid, user_id_10, 'SAD'),

           -- Reakcje dla komentarza '6c84fbc4-12c4-11ec-82a8-0242ac130004'
           ('6c84fbd9-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc4-12c4-11ec-82a8-0242ac130004'::uuid, user_id_1, 'LIKE'),
           ('6c84fbef-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc4-12c4-11ec-82a8-0242ac130004'::uuid, user_id_2, 'LOVE'),

           -- Reakcje dla komentarza '6c84fbc5-12c4-11ec-82a8-0242ac130004'
           ('6c84fbda-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc5-12c4-11ec-82a8-0242ac130004'::uuid, user_id_3, 'LIKE'),
           ('6c84fbf0-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc5-12c4-11ec-82a8-0242ac130004'::uuid, user_id_4, 'WOW'),

           -- Reakcje dla komentarza '6c84fbc6-12c4-11ec-82a8-0242ac130004'
           ('6c84fbdb-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc6-12c4-11ec-82a8-0242ac130004'::uuid, user_id_5, 'LIKE'),
           ('6c84fbf1-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc6-12c4-11ec-82a8-0242ac130004'::uuid, user_id_6, 'SAD'),

           -- Reakcje dla komentarza '6c84fbc7-12c4-11ec-82a8-0242ac130004'
           ('6c84fbdc-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc7-12c4-11ec-82a8-0242ac130004'::uuid, user_id_7, 'LIKE'),
           ('6c84fbf2-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbc7-12c4-11ec-82a8-0242ac130004'::uuid, user_id_8, 'WRR'),

           -- Reakcje dla komentarza '6c84fbd4-12c4-11ec-82a8-0242ac130004'
           ('6c84fbdd-12c4-11ec-82a8-0242ac130004'::uuid, '6c84fbd4-12c4-11ec-82a8-0242ac130004'::uuid, user_id_9, 'LOVE');

        
        INSERT INTO post_reaction (reaction_id, post_id, user_id, type)
        VALUES
        ('6c84fbb9-12c4-11ec-82a8-0242ac130003'::uuid, post_id_2, user_id_2, 'LIKE'),
        ('6c84fbba-12c4-11ec-82a8-0242ac130003'::uuid, post_id_3, user_id_3, 'WOW'),

        -- Reakcje dla post_id_4
        ('6c84fc06-12c4-11ec-82a8-0242ac130003'::uuid, post_id_4, user_id_1, 'LIKE'),
        ('6c84fc12-12c4-11ec-82a8-0242ac130003'::uuid, post_id_4, user_id_2, 'WOW'),
        ('6c85fc12-12c4-11ec-82a8-0242ac130003'::uuid, post_id_4, user_id_2, 'SAD'),

        -- Reakcje dla post_id_5
        ('6c84fc07-12c4-11ec-82a8-0242ac130003'::uuid, post_id_5, user_id_3, 'SAD'),
        ('6c84fc13-12c4-11ec-82a8-0242ac130003'::uuid, post_id_5, user_id_4, 'LIKE'),

        -- Reakcje dla post_id_6
        ('6c84fc08-12c4-11ec-82a8-0242ac130003'::uuid, post_id_6, user_id_5, 'LIKE'),
        ('6c84fc14-12c4-11ec-82a8-0242ac130003'::uuid, post_id_6, user_id_6, 'HA_HA'),

        -- Reakcje dla post_id_7
        ('6c84fc09-12c4-11ec-82a8-0242ac130003'::uuid, post_id_7, user_id_7, 'WOW'),
        ('6c84fc15-12c4-11ec-82a8-0242ac130003'::uuid, post_id_7, user_id_8, 'HA_HA'),

        -- Reakcje dla post_id_8
        ('6c84fc0a-12c4-11ec-82a8-0242ac130003'::uuid, post_id_8, user_id_9, 'SAD'),
        ('6c84fc16-12c4-11ec-82a8-0242ac130003'::uuid, post_id_8, user_id_10, 'LIKE'),

        -- Reakcje dla post_id_9
        ('6c84fc0b-12c4-11ec-82a8-0242ac130003'::uuid, post_id_9, user_id_1, 'LIKE'),
        ('6c84fc17-12c4-11ec-82a8-0242ac130003'::uuid, post_id_9, user_id_2, 'HA_HA'),

        -- Reakcje dla post_id_10
        ('6c84fc0c-12c4-11ec-82a8-0242ac130003'::uuid, post_id_10, user_id_3, 'SAD'),
        ('6c84fc18-12c4-11ec-82a8-0242ac130003'::uuid, post_id_10, user_id_4, 'LIKE'),

        -- Reakcje dla post_id_11
        ('6c84fc0d-12c4-11ec-82a8-0242ac130003'::uuid, post_id_11, user_id_5, 'LIKE'),
        ('6c84fc19-12c4-11ec-82a8-0242ac130003'::uuid, post_id_11, user_id_6, 'WOW'),

        -- Reakcje dla post_id_12
        ('6c84fc0e-12c4-11ec-82a8-0242ac130003'::uuid, post_id_12, user_id_7, 'HA_HA'),
        ('6c84fc1a-12c4-11ec-82a8-0242ac130003'::uuid, post_id_12, user_id_8, 'LOVE'),

        -- Reakcje dla post_id_13
        ('6c84fc0f-12c4-11ec-82a8-0242ac130003'::uuid, post_id_13, user_id_9, 'LIKE'),
        ('6c84fc1b-12c4-11ec-82a8-0242ac130003'::uuid, post_id_13, user_id_10, 'HA_HA'),

        -- Reakcje dla post_id_14
        ('6c84fc10-12c4-11ec-82a8-0242ac130003'::uuid, post_id_14, user_id_1, 'LOVE'),
        ('6c84fc1c-12c4-11ec-82a8-0242ac130003'::uuid, post_id_14, user_id_2, 'LIKE'),

        -- Reakcje dla post_id_15
        ('6c84fc11-12c4-11ec-82a8-0242ac130003'::uuid, post_id_15, user_id_3, 'WOW'),
        ('6c84fc1d-12c4-11ec-82a8-0242ac130003'::uuid, post_id_15, user_id_4, 'HA_HA');


        INSERT INTO favourite_list (list_id, user_id, name, created_at, modified_at)
        VALUES (list_id_1, user_id_1, 'My favourite posts', timestamp_earlier, timestamp_earlier),
               (list_id_2, user_id_2, 'My favourite posts', timestamp_later, timestamp_later),
               (list_id_3, user_id_3, 'My favourite posts', timestamp_later, timestamp_later),
               (list_id_4, user_id_3, 'Top 5 bears of all time', timestamp_later, timestamp_later);

        INSERT INTO favourite_post (fav_post_id, list_id, post_id)
        VALUES ('6c84fbbb-12c4-11ec-82a8-0242ac130003'::uuid, list_id_1, post_id_1),
               ('6c84fbbc-12c4-11ec-82a8-0242ac130003'::uuid, list_id_2, post_id_2),
               ('6c84fbbd-12c4-11ec-82a8-0242ac130003'::uuid, list_id_3, post_id_3),
                ('6c84fbbe-12c4-11ec-82a8-0242ac130003'::uuid, list_id_4, post_id_4),
               ('6c84fbbf-12c4-11ec-82a8-0242ac130003'::uuid, list_id_4, post_id_5),
                ('6c85fbbf-12c4-11ec-82a8-0242ac130003'::uuid, list_id_4, post_id_6);

        INSERT INTO tag (tag_id, tag_name)
        VALUES (tag_id_1, 'Nature'),
               (tag_id_2, 'Movies'),
                (tag_id_3, 'Bears'),
               (tag_id_4, 'Cars'),
                (tag_id_5, 'NotAssignedToAnything');

        INSERT INTO tagging (tagging_id, post_id, tag_id)
        VALUES ('6c84fbc1-12c4-11ec-82a8-0242ac130003'::uuid, post_id_1, tag_id_1),
               ('6c84fbc2-12c4-11ec-82a8-0242ac130003'::uuid, post_id_2, tag_id_2),
                ('6c84fbc3-12c4-11ec-82a8-0242ac130003'::uuid, post_id_2, tag_id_1),
               ('6c84fbc4-12c4-11ec-82a8-0242ac130003'::uuid, post_id_3, tag_id_3),
               ('6c84fbc5-12c4-11ec-82a8-0242ac130003'::uuid, post_id_3, tag_id_1),
               ('6c84fbc6-12c4-11ec-82a8-0242ac130003'::uuid, post_id_4, tag_id_1),
               ('6c84fbc7-12c4-11ec-82a8-0242ac130003'::uuid, post_id_5, tag_id_2),
               ('6c84fbc8-12c4-11ec-82a8-0242ac130003'::uuid, post_id_6, tag_id_4),
               ('6c84fbc9-12c4-11ec-82a8-0242ac130003'::uuid, post_id_7, tag_id_2);
    END
$$;