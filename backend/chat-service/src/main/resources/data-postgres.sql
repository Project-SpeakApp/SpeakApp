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
        message_id_9        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130010'::uuid;

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

        group_member_id_1         uuid      := '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_2         uuid      := '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_3         uuid      := '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_4         uuid      := '6c84fb98-12c4-11ec-82a8-0242ac130003'::uuid;
        group_member_id_5         uuid      := '6c84fb99-12c4-11ec-82a8-0242ac130003'::uuid;

        conversation_id_1        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130011'::uuid;
        conversation_id_2        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130012'::uuid;
        conversation_id_3        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130013'::uuid;
        conversation_id_4        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130014'::uuid;
        conversation_id_5        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130015'::uuid;
        conversation_id_6        uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid;
        conversation_id_7        uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130004'::uuid;
        conversation_id_8        uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130005'::uuid;
        conversation_id_9        uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130006'::uuid;
        conversation_id_10       uuid      := '6c84fbac-12c4-11ec-82a8-0242ac130007'::uuid;

        message_reaction_id_1        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130011'::uuid;
        message_reaction_id_2        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130012'::uuid;
        message_reaction_id_3        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130013'::uuid;
        message_reaction_id_4        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130014'::uuid;
        message_reaction_id_5        uuid      := '6c84fbad-12c4-11ec-82a8-0242ac130015'::uuid;

        timestamp_earlier timestamp := '2024-03-28 00:00:00'::timestamp;
        timestamp_later   timestamp := '2024-06-18 00:00:00'::timestamp;

        message_timestamp_1 timestamp :='2024-03-28 12:00:00'::timestamp;
        message_timestamp_2 timestamp :='2024-03-28 13:00:00'::timestamp;
        message_timestamp_3 timestamp :='2024-03-28 14:00:00'::timestamp;
        message_timestamp_4 timestamp :='2024-03-28 15:00:00'::timestamp;
        message_timestamp_5 timestamp :='2024-03-28 16:00:00'::timestamp;
        message_timestamp_6 timestamp :='2024-03-28 17:00:00'::timestamp;

        reaction_timestamp_1 timestamp :='2024-03-28 12:10:00'::timestamp;
        reaction_timestamp_2 timestamp :='2024-03-28 13:10:00'::timestamp;
        reaction_timestamp_3 timestamp :='2024-03-28 14:10:00'::timestamp;
        reaction_timestamp_4 timestamp :='2024-03-28 15:10:00'::timestamp;
        reaction_timestamp_5 timestamp :='2024-03-28 16:10:00'::timestamp;

BEGIN
INSERT INTO conversation (conversation_id, conversation_name, is_group_conversation)
VALUES (conversation_id_1, 'Bears fans', true),
       (conversation_id_2, 'Private conversation 1', false);

INSERT INTO group_member (group_member_id, user_id, conversation_id, joined_datetime, left_datetime)
VALUES (group_member_id_1, user_id_1, conversation_id_1 , timestamp_earlier, timestamp_later),
       (group_member_id_2, user_id_2, conversation_id_1 , timestamp_earlier, timestamp_later),
       (group_member_id_3, user_id_3, conversation_id_1 , timestamp_earlier, timestamp_later);

INSERT INTO message (message_id, from_user, content, type, conversation_id, response_to_message_id, sent_at, delivered_at)
VALUES (message_id_1, user_id_1, 'Bears are the best animals imo!', 'TEXT', conversation_id_1, null, message_timestamp_1, message_timestamp_1),
       (message_id_2, user_id_2, 'It is true!', 'TEXT', conversation_id_1, message_id_1, message_timestamp_2, message_timestamp_2),
       (message_id_3, user_id_3, 'I think cats are better than bears', 'TEXT', conversation_id_1, null, message_timestamp_3, message_timestamp_3),
       (message_id_4, user_id_4, 'Hi, whats up?', 'TEXT', conversation_id_2, null, message_timestamp_4, message_timestamp_4),
       (message_id_5, user_id_5, 'Im going to the gym in 5 minutes', 'TEXT', conversation_id_2, null, message_timestamp_5, message_timestamp_5);

INSERT INTO message_reaction (message_reaction_id, message_id, user_id, date, type, number_of_reactions)
VALUES (message_reaction_id_1, message_id_1, user_id_2, reaction_timestamp_1, 'LOVE', 1),
       (message_reaction_id_2, message_id_3, user_id_2, reaction_timestamp_3, 'WRR', 1),
       (message_reaction_id_3, message_id_5, user_id_4, reaction_timestamp_5, 'LIKE', 1);
END
$$;