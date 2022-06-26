truncate table public.service cascade;

ALTER sequence service_sequence restart with 1;

INSERT INTO public.service(id, name, description)
VALUES (nextval('service_sequence'), 'Cambio de aceite de motor', 'Cambio de aceite y filtro del motor');

INSERT INTO public.service(id, name, description)
VALUES (nextval('service_sequence'), 'Afinamiento de motor', 'Afinamiento general del motor');

INSERT INTO public.service(id, name, description)
VALUES (nextval('service_sequence'), 'Cambio de distribución', 'Cambio de la correa de distribución del vehículo');

