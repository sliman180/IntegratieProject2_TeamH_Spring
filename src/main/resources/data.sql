insert into gebruikers (gebruikersnaam, wachtwoord, email)
values ('user', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb','moussa.elbaroudi@student.kdg.be'), ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','moussa.elbaroudi@student.kdg.be'), ('moussa', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','moussa.elbaroudi@student.kdg.be');

insert into rollen (naam, beschrijving)
values ('user', ''), ('admin', '');

insert into gebruikers_rollen (gebruikers_id, rollen_id)
values (1, 1), (2, 1), (2, 2), (3, 1), (3, 2);
