insert into gebruikers
  (email, gebruikersnaam, wachtwoord)
values
  ('userone@kandoe.be', 'userone', '1e9d59ad9be1cb302e155d55b61c95b3b3db897da2ed9643b15f8802039ffc8c'),
  ('usertwo@kandoe.be', 'usertwo', 'f64c95e5e1f4537428da7ba9fd1ee87bd263091c38c52d52cefb8e2b408983e8'),
  ('moussa@kandoe.be', 'moussa', '90be0995aa2c8b9e273ce6b3ce732ba1d325245dd1d4547b843127649c435777');

insert into rollen
  (naam)
values
  ('user'), ('guest');

insert into gebruikers_rollen
  (gebruikers_id, rollen_id)
values
  (1, 1), (2, 1), (3, 1);
