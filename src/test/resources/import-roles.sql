truncate table public.role cascade;

ALTER sequence role_sequence restart with 1;

INSERT INTO public.role(id, name)
VALUES (nextval('role_sequence'), 'mechanic');

INSERT INTO public.role(id, name)
VALUES (nextval('role_sequence'), 'client');

