truncate table public.users cascade;

ALTER sequence users_sequence restart with 1;

INSERT INTO public.users(id, id_card,create_date, email, enabled, first_name, last_name, password, token_expired)
VALUES (nextval('users_sequence'), '12345','2022-04-12 15:30:47.000000', 'fabian.gonzalez.mendez@est.una.ac.cr', true, 'Fabian', 'Gonzalez', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);

INSERT INTO public.users(id, id_card,create_date, email, enabled, first_name, last_name, password, token_expired)
VALUES (nextval('users_sequence'), '54321','2022-04-12 15:30:47.000000', 'jose.rodriguez.berrios@est.una.ac.cr', true, 'Jose', 'Rodriguez', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);

INSERT INTO public.users(id, id_card,create_date, email, enabled, first_name, last_name, password, token_expired)
VALUES (nextval('users_sequence'), '56789','2022-04-12 15:30:47.000000', 'luis.brenes.luna@est.una.ac.cr', true, 'Luis', 'Brenes', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);

INSERT INTO public.users(id, id_card,create_date, email, enabled, first_name, last_name, password, token_expired)
VALUES (nextval('users_sequence'), '98765','2022-04-12 15:30:47.000000', 'andres.zuniga.calderon@est.una.ac.cr', true, 'Andres', 'Zuñiga', '$2a$10$6hg/QTw8Th1EmYtg9/5HhOmRdg320A6J8DumNUqx.4AltXZAjp0VK', false);

