DO
$$
    DECLARE
        user_id_1         uuid      := '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_2         uuid      := '6c84fb96-12c4-11ec-82a8-0242ac130003'::uuid;
        user_id_3         uuid      := '6c84fb97-12c4-11ec-82a8-0242ac130003'::uuid;
    BEGIN
        INSERT INTO app_user (user_id, first_name, last_name, email, date_of_birth, last_online, about)
        VALUES (user_id_1, 'John', 'Doe', 'johndoe@gmail.com', '1990-01-01'::date, '2023-01-01 12:00:00'::timestamp, 'Lorem ipsum dolor sit amet.'),
                (user_id_2, 'Alice', 'Johnson', 'alicejohnson@gmail.com', '1985-05-15'::date, '2023-02-01 12:00:00'::timestamp, 'Consectetur adipiscing elit.'),
                (user_id_3, 'Christopher', 'Bear', 'christopherbear@gmail.com', '1998-07-20'::date, '2023-03-01 12:00:00'::timestamp, 'Vivamus et justo nec purus.');

        INSERT INTO user_friend (requester_id, addressee_id, friend_id, status, created_at)
        VALUES (user_id_1, user_id_2, '6c84fb98-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-01-10 12:00:00'::timestamp),
               (user_id_2, user_id_1, '6c84fb99-12c4-11ec-82a8-0242ac130003', 'FRIEND', '2023-02-15 12:00:00'::timestamp),
               (user_id_3, user_id_1, '6c84fc91-12c4-11ec-82a8-0242ac130003', 'REQUEST', '2023-03-20 12:00:00'::timestamp);
    END
$$;