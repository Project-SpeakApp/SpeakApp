DO
$$
    DECLARE
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
    BEGIN
        INSERT INTO app_user (user_id, first_name, last_name, email, date_of_birth, last_online, about, profile_photo_url, created_at)
        VALUES
            (user_id_1, 'John', 'Doe', 'johndoe@gmail.com', '1990-01-01'::date, '2023-01-01 12:00:00'::timestamp, 'I like bears', '', '2023-12-01'::date),
            (user_id_2, 'Alice', 'Johnson', 'alicejohnson@gmail.com', '1985-05-15'::date, '2023-02-01 12:00:00'::timestamp, 'I love Taylor Swift', '', '2023-12-11'::date),
            (user_id_3, 'Christopher', 'Bear', 'christopherbear@gmail.com', '1998-07-20'::date, '2023-03-01 12:00:00'::timestamp, 'Polska gurom ;)', '', '2023-12-09'::date),
            (user_id_4, 'Emily', 'Smith', 'emilysmith@gmail.com', '1992-04-18'::date, '2023-04-01 12:00:00'::timestamp, 'WITAM SERDECZNIE!', '', '2023-12-05'::date),
            (user_id_5, 'Michael', 'Williams', 'michaelwilliams@gmail.com', '1987-09-30'::date, '2023-05-01 12:00:00'::timestamp, 'Lubie placki', '', '2023-12-18'::date),
            (user_id_6, 'Sophia', 'Brown', 'sophiabrown@gmail.com', '1995-12-12'::date, '2023-06-01 12:00:00'::timestamp, '..........', '', '2023-12-22'::date),
            (user_id_7, 'William', 'Jones', 'williamjones@gmail.com', '1990-08-25'::date, '2023-07-01 12:00:00'::timestamp, 'Aenean fermentum ligula ac suscipit fringilla.', '', '2023-12-02'::date),
            (user_id_8, 'Olivia', 'Davis', 'oliviadavis@gmail.com', '1983-03-08'::date, '2023-08-01 12:00:00'::timestamp, 'Curabitur non lectus id nulla auctor sagittis.', '', '2023-12-14'::date),
            (user_id_9, 'Ethan', 'Miller', 'ethanmiller@gmail.com', '1997-06-22'::date, '2023-09-01 12:00:00'::timestamp, 'Vestibulum hendrerit ligula eu lorem facilisis, ut lacinia mauris gravida.', '', '2023-12-10'::date),
            (user_id_10, 'Ava', 'Wilson', 'avawilson@gmail.com', '1989-11-05'::date, '2023-10-01 12:00:00'::timestamp, 'Integer in quam sit amet felis bibendum fermentum.', '', '2023-12-08'::date);

        INSERT INTO user_friend (requester_id, addressee_id, id, status, created_at)
        VALUES
            (user_id_1, user_id_6, '6c84fba4-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-06-10 12:00:00'::timestamp),
            (user_id_2, user_id_10, '6c84fbb6-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-02-25 12:00:00'::timestamp),
            (user_id_3, user_id_1, '6c84fc91-12c4-11ec-82a8-0242ac130003'::uuid, 'REQUEST', '2023-03-20 12:00:00'::timestamp),
            (user_id_3, user_id_2, '6c84fbae-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-03-25 12:00:00'::timestamp),
            (user_id_5, user_id_1, '6c84fba3-12c4-11ec-82a8-0242ac130003'::uuid, 'REQUEST', '2023-05-15 12:00:00'::timestamp),
            (user_id_5, user_id_4, '6c84fbb0-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-05-20 12:00:00'::timestamp),
            (user_id_7, user_id_1, '6c84fba6-12c4-11ec-82a8-0242ac130003'::uuid, 'REQUEST', '2023-07-10 12:00:00'::timestamp),
            (user_id_7, user_id_6, '6c84fbb2-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-07-20 12:00:00'::timestamp),
            (user_id_8, user_id_1, '6c84fba8-12c4-11ec-82a8-0242ac130003'::uuid, 'REQUEST', '2023-08-15 12:00:00'::timestamp),
            (user_id_9, user_id_1, '6c84fbaa-12c4-11ec-82a8-0242ac130003'::uuid, 'REQUEST', '2023-09-15 12:00:00'::timestamp),
            (user_id_9, user_id_8, '6c84fbb4-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-09-20 12:00:00'::timestamp),
            (user_id_10, user_id_1, '6c84fbac-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND', '2023-10-15 12:00:00'::timestamp);










    END
$$;