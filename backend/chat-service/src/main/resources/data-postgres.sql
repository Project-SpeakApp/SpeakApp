DO
$$
    DECLARE
message_id_1         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130003'::uuid;
        message_id_2         uuid      := '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid;
        message_id_3         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130004'::uuid;
        message_id_4         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130005'::uuid;
        message_id_5         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130006'::uuid;
        message_id_6         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130007'::uuid;
        message_id_7         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130008'::uuid;
        message_id_8         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130009'::uuid;
        message_id_9         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130010'::uuid;
        message_id_10         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130011'::uuid;
        message_id_11         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130012'::uuid;
        message_id_12         uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130013'::uuid;

        user_id_1         uuid      := '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_2         uuid      := '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_3         uuid      := '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_4         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_5         uuid      := '6c84fb99-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_6         uuid      := '6c84fb9a-12c4-11ec-82a8-0242ac130003'::uuid;

        group_member_id_1         uuid      := '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_2         uuid      := '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_3         uuid      := '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_4         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_5         uuid      := '6c84fb99-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_6         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130004'::uuid;
        group_member_id_7         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130005'::uuid;
        group_member_id_8         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130006'::uuid;
        group_member_id_9         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130007'::uuid;
        group_member_id_10         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130008'::uuid;

        conversation_id_1        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130011'::uuid;
        conversation_id_2        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130012'::uuid;
        conversation_id_3        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130013'::uuid;
        conversation_id_4        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130014'::uuid;

        message_reaction_id_1        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130011'::uuid;
        message_reaction_id_2        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130012'::uuid;
        message_reaction_id_3        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130013'::uuid;
        message_reaction_id_4        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130014'::uuid;
        message_reaction_id_5        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130015'::uuid;
        message_reaction_id_6        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130016'::uuid;

        timestamp_earlier timestamp := '2024-03-28 00:00:00'::timestamp;
        timestamp_later   timestamp := '2024-06-18 00:00:00'::timestamp;

        message_timestamp_1 timestamp :='2024-03-28 12:00:00'::timestamp;
        message_timestamp_2 timestamp :='2024-03-28 13:00:00'::timestamp;
        message_timestamp_3 timestamp :='2024-03-28 14:00:00'::timestamp;
        message_timestamp_4 timestamp :='2024-03-28 15:00:00'::timestamp;
        message_timestamp_5 timestamp :='2024-03-28 16:00:00'::timestamp;
        message_timestamp_6 timestamp :='2024-03-28 17:00:00'::timestamp;
        message_timestamp_7 timestamp :='2024-03-28 18:00:00'::timestamp;

        reaction_timestamp_1 timestamp :='2024-03-28 12:10:00'::timestamp;
        reaction_timestamp_2 timestamp :='2024-03-28 13:10:00'::timestamp;
        reaction_timestamp_3 timestamp :='2024-03-28 14:10:00'::timestamp;
        reaction_timestamp_4 timestamp :='2024-03-28 15:10:00'::timestamp;
        reaction_timestamp_5 timestamp :='2024-03-28 16:10:00'::timestamp;
        reaction_timestamp_6 timestamp :='2024-03-28 17:10:00'::timestamp;

BEGIN
INSERT INTO conversation (conversation_id, conversation_name, is_group_conversation)
VALUES (conversation_id_1, 'Bears fans', true),
       (conversation_id_2, 'Private conversation 1', false),
       (conversation_id_3, 'Private conversation 2', false),
       (conversation_id_4, 'Private conversation 3', false);

INSERT INTO group_member (group_member_id, user_id, conversation_id, joined_datetime, left_datetime)
VALUES (group_member_id_1, user_id_1, conversation_id_1 , timestamp_earlier, timestamp_later),
       (group_member_id_2, user_id_2, conversation_id_1 , timestamp_earlier, timestamp_later),
       (group_member_id_3, user_id_3, conversation_id_1 , timestamp_earlier, timestamp_later),
       (group_member_id_4, user_id_6, conversation_id_1 , timestamp_earlier, timestamp_later),

       (group_member_id_5, user_id_4, conversation_id_2 , timestamp_earlier, timestamp_later),
       (group_member_id_6, user_id_5, conversation_id_2 , timestamp_earlier, timestamp_later),

       (group_member_id_7, user_id_1, conversation_id_3 , timestamp_earlier, timestamp_later),
       (group_member_id_8, user_id_2, conversation_id_3 , timestamp_earlier, timestamp_later),

       (group_member_id_9, user_id_6, conversation_id_4 , timestamp_earlier, timestamp_later),
       (group_member_id_10, user_id_3, conversation_id_4 , timestamp_earlier, timestamp_later);

INSERT INTO message (message_id, from_user, content, type, conversation_id, response_to_message_id, sent_at, delivered_at, is_deleted)
VALUES (message_id_1, user_id_1, 'Bears are the best animals imo!', 'TEXT', conversation_id_1, null, message_timestamp_1, message_timestamp_1, false),
       (message_id_2, user_id_2, 'It is true!', 'TEXT', conversation_id_1, message_id_1, message_timestamp_2, message_timestamp_2, false),
       (message_id_3, user_id_3, 'I think cats are better than bears', 'TEXT', conversation_id_1, null, message_timestamp_3, message_timestamp_3, false),
       (message_id_6, user_id_6, 'Are you crazy dude?', 'TEXT', conversation_id_1, message_id_3, message_timestamp_6, message_timestamp_6, false),

       (message_id_4, user_id_4, 'Hi, whats up?', 'TEXT', conversation_id_2, null, message_timestamp_4, message_timestamp_4, false),
       (message_id_5, user_id_5, 'Im going to the gym in 5 minutes', 'TEXT', conversation_id_2, null, message_timestamp_5, message_timestamp_5, false),
       (message_id_7, user_id_4, 'Ok, have a nice workout', 'TEXT', conversation_id_2, null, message_timestamp_7, message_timestamp_7, false),

       (message_id_8, user_id_1, 'Where are you?', 'TEXT', conversation_id_3, null, message_timestamp_1, message_timestamp_1, false),
       (message_id_9, user_id_2, 'Give me 5 minutes, I had to feed my bear...', 'TEXT', conversation_id_3, null, message_timestamp_2, message_timestamp_2, false),
       (message_id_10, user_id_1, 'Okay, I will get some beers for us then', 'TEXT', conversation_id_3, null, message_timestamp_3, message_timestamp_3, false),

       (message_id_11, user_id_6, 'Hey, do you want to go out sometimes?', 'TEXT', conversation_id_4, null, message_timestamp_3, message_timestamp_3, false),
       (message_id_12, user_id_3, 'No, btw I use arch', 'TEXT', conversation_id_4, null, message_timestamp_4, message_timestamp_4, false);

INSERT INTO message_reaction (message_reaction_id, message_id, user_id, date, type)
VALUES (message_reaction_id_1, message_id_1, user_id_2, reaction_timestamp_1, 'LOVE'),
       (message_reaction_id_2, message_id_3, user_id_2, reaction_timestamp_3, 'WRR'),
       (message_reaction_id_3, message_id_5, user_id_4, reaction_timestamp_5, 'LIKE'),
       (message_reaction_id_4, message_id_6, user_id_3, reaction_timestamp_6, 'WRR'),
       (message_reaction_id_5, message_id_9, user_id_1, reaction_timestamp_2, 'HA_HA'),
       (message_reaction_id_6, message_id_12, user_id_6, reaction_timestamp_4, 'WOW');
END
$$;