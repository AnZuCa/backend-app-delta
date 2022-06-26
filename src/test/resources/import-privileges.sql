truncate table public.privilege cascade;

ALTER sequence privilege_sequence restart with 1;

INSERT INTO public.privilege(id, name)
VALUES (nextval('privilege_sequence') , 'Privilege 1');

INSERT INTO public.privilege(id, name)
VALUES (nextval('privilege_sequence') , 'Privilege 2');

INSERT INTO public.privilege(id, name)
VALUES (nextval('privilege_sequence') , 'Privilege 3');

