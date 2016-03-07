insert into gebruikers (gebruikersnaam, wachtwoord)
values ('user', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb'), ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

insert into rollen (naam, beschrijving)
values ('user', ''), ('admin', '');

insert into gebruikers_rollen (gebruikers_id, rollen_id)
values (1, 1), (2, 1), (2, 2);
