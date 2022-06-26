truncate table public.status cascade;

ALTER sequence status_sequence restart with 1;

INSERT INTO public.status(id, name)
VALUES (nextval('status_sequence'), 'Pendiente');

INSERT INTO public.status(id, name)
VALUES (nextval('status_sequence'), 'En proceso');

INSERT INTO public.status(id, name)
VALUES (nextval('status_sequence'), 'Completado');

