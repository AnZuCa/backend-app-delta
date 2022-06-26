truncate table public.vehicle cascade;

ALTER sequence vehicle_sequence restart with 1;


INSERT INTO public.vehicle(id, engine_number, engine_type, plate_number, vehicle_brand, vehicle_type, vin_number)
VALUES (nextval('vehicle_sequence'), 'AFB6701', 'diesel', '25785', 'Honda', 'Sedan', '12AU5HHL');

INSERT INTO public.vehicle(id, engine_number, engine_type, plate_number, vehicle_brand, vehicle_type, vin_number)
VALUES (nextval('vehicle_sequence'), '17YTVV', 'gas', '12FHJ8', 'Chevrolet', 'SUV', '89546AFIQW');

INSERT INTO public.vehicle(id, engine_number, engine_type, plate_number, vehicle_brand, vehicle_type, vin_number)
VALUES (nextval('vehicle_sequence'), 'ROV419', 'electric', '784269', 'Toyota', 'Sedan', '1AFRS723');

