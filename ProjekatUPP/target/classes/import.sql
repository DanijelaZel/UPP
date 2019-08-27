
insert into casopis(ime, issn_br, open_access) values ('CasopisIme', '1111',1)
insert into casopis(ime, issn_br, open_access) values ('CasopisIme2', '2222',0)
/*Glavni urednici*/
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni,casopis_casopis_id) values ('Zvornik', 'RS','danijela.zelenovic@gmail.com', 'Danijela','daca', 'Danijela',2, 0,1,2)
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni,casopis_casopis_id) values ('Lopare', 'RS','sreten.95@hotmail.com', 'Sreten','pas', 'Petrovic',2, 0,1,1)
/*korisnici*/
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni) values ('NS', 'Srbija','korisnik@korisnik.com', 'korisnik','korisnik', 'KORISNIK',0, 0,0)
/*urednici*/
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni) values ('NS', 'Srbija','urednik@urednik.com', 'urednik','urednik', 'UREDNIK',2, 0,0)
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni) values ('NS', 'Srbija','urednik1@urednik1.com', 'urednik1','urednik1', 'UREDNIK1',2, 0,0)
/*recezenti*/
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni) values ('NS', 'Srbija','recezent@recezent.com', 'recezent','recezent', 'RECEZENT',1, 0,0)
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni) values ('NS', 'Srbija','recezent1@recezent1.com', 'recezent1','recezent1', 'RECEZENT',1, 0,0)
insert into user(city,country,email,name,password,surname,user_role,user_status,glavni) values ('NS', 'Srbija','recezent2@recezent2.com', 'recezent2','recezent2', 'RECEZENT',1, 0,0)


insert into casopis_urednici(casopis_casopis_id,urednici_user_id) values (1,2)
insert into casopis_urednici(casopis_casopis_id,urednici_user_id) values (2,1)
insert into casopis_urednici(casopis_casopis_id,urednici_user_id) values (2,4)
insert into casopis_urednici(casopis_casopis_id,urednici_user_id) values (2,5)


insert into naucna_oblast(naziv)  value ('prirodne')
insert into naucna_oblast(naziv)  value ('bioloske')
insert into naucna_oblast(naziv)  value ('drustvene')
insert into naucna_oblast(naziv)  value ('poljoprivredne')


/*insert into rad(apstrakt,kljucni_pojmovi,naslov,pdf,rad_status,naucna_oblast_naucna_oblast_id,casopis_rad_casopis_id,autor_user_id) values ('apstrakt','kljucnipojmovi','naslov','pdf',0,0,2,3)*/

