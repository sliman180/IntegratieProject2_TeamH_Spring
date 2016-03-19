insert into gebruikers
  (gebruikersnaam, wachtwoord)
values
  ('user', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb'),
  ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'),
  ('moussa', '90be0995aa2c8b9e273ce6b3ce732ba1d325245dd1d4547b843127649c435777');

insert into rollen
  (naam)
values
  ('user'), ('admin');

insert into gebruikers_rollen
  (gebruikers_id, rollen_id)
values
  (1, 1),
  (2, 1),
  (2, 2),
  (3, 1),
  (3, 2);
