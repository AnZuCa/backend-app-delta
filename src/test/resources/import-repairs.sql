truncate table public.repair cascade ;

ALTER sequence repair_sequence restart with 1;

INSERT INTO public.repair(id, departure_date, description, registration_date, client_id, mechanic_id, status_id, vehicle_id)
VALUES (nextval('repair_sequence'), '2022-04-24 14:00:47.000000', 'Descripcion de la reparacion 1', '2022-04-12 12:22:51.000000', 1, 4, 1, 1);

INSERT INTO public.repair(id, departure_date, description, registration_date, client_id, mechanic_id, status_id, vehicle_id)
VALUES (nextval('repair_sequence'), '2022-04-24 14:00:47.000000', 'Descripcion de la reparacion 2', '2022-04-12 12:22:51.000000', 2, 4, 2, 2);

INSERT INTO public.repair(id, departure_date, description, registration_date, client_id, mechanic_id, status_id, vehicle_id)
VALUES (nextval('repair_sequence'), '2022-04-24 14:00:47.000000', 'Descripcion de la reparacion 3', '2022-04-12 12:22:51.000000', 3, 4, 3, 3);

