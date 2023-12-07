-- Insert data into app_user table
INSERT INTO public.app_user (date_of_birth, last_online, user_id, first_name, last_name, about, email, password)
VALUES
    ('1990-01-01'::date, '2023-01-01 12:00:00'::timestamp, '1f8a6280-12c4-11ec-82a8-0242ac130003'::uuid, 'John', 'Doe', 'Lorem ipsum dolor sit amet.', 'john.doe@example.com', 'password123'),
    ('1985-05-15'::date, '2023-02-01 12:00:00'::timestamp, '6c84fb92-12c4-11ec-82a8-0242ac130003'::uuid, 'Alice', 'Johnson', 'Consectetur adipiscing elit.', 'alice.j@example.com', 'pass456'),
    ('1998-07-20'::date, '2023-03-01 12:00:00'::timestamp, '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid, 'Bob', 'Smith', 'Vivamus et justo nec purus.', 'bob.smith@example.com', 'secure789');

-- Insert data into user_friend table
INSERT INTO public.user_friend (created_at, addressee_id, friend_id, requester_id, status)
VALUES
    ('2023-01-10 12:00:00'::timestamp, '1f8a6280-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fb92-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND'),
    ('2023-02-15 12:00:00'::timestamp, '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid, '1f8a6280-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fb92-12c4-11ec-82a8-0242ac130003'::uuid, 'FRIEND'),
    ('2023-03-20 12:00:00'::timestamp, '6c84fb92-12c4-11ec-82a8-0242ac130003'::uuid, '6c84fb95-12c4-11ec-82a8-0242ac130003'::uuid, '1f8a6280-12c4-11ec-82a8-0242ac130003'::uuid, 'REQUEST');
