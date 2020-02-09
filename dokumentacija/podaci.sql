insert into korisnickinalog values (null, 'Marina', 'Krneta', 'marinak', '97C1B2425112BE6676A52D66A11ECF55DD9FEA89E39EF1B6F936C0B869094AAD', 0, 0);
insert into korisnickinalog values (null, 'Marko', 'Jankovic', 'markoj', 'E3C4A8E68C23890091F9B9531EF3E0F805CE0A9378D6FB4BBCB6EED403C91342', 1, 0);

insert into rukovodilac values (null, 'Jovan', 'Aksic', '065/725-111', 'jovana@gmail.com', 'direktor', 0);
insert into rukovodilac values (null, 'Tamara', 'Jovanovic', '066/125-287', 'tamaratasa@gmail.com', 'sekretar', 0);

insert into sponzor values (null, 'Aura osiguranje', '', null, '051/490-490', 'pravno lice', '1234567891235', 0);
insert into sponzor values (null, 'Transporter d.o.o', 'Poljavnice 427b Novi Grad', null, '052/752-542', 'pravno lice', '9876543212034', 0);
insert into sponzor values (null, 'Ligno Progres d.o.o', 'Blagaj Zurin bb Novi Grad', null, '052/720-990', 'pravno lice', '9517536482300', 0);
insert into sponzor values (null, 'Milan Balaban', 'Svodna 124 Novi Grad', 'milanb@fujifilm.com', '066/712-999', 'fizicko lice', '2005981120301', 0);
insert into sponzor values (null, 'Slaven Vujasin', 'Svodna 98 Novi Grad', 'slavenvujasin2@gmail.com', '065/311-201', 'fizicko lice', '1501978111204', 0);

insert into uplata values (null, 1, 250.0, '2020-01-04 13:17:20', null);
insert into uplata values (null, 2, 100.0, '2020-01-07 10:20:20', null);
insert into uplata values (null, 3, 55.0, '2020-01-04 18:02:10', null);
insert into uplata values (null, 4, 200.0, '2020-01-05 09:09:20', null);
insert into uplata values (null, 5, 50.0, '2020-01-10 08:00:10', null);

insert into kontaktosoba values(null, 'Milan', 'Jovanovic', '066/212-720', 0);
insert into kontaktosoba values(null, 'Marko', 'Bojanic', '065/412-300', 0);
insert into kontaktosoba values(null, 'Bojan', 'Milic', '065/741-113', 0);
insert into kontaktosoba values(null, 'Bojan', 'Milic', '065/741-113', 0);

insert into sponzorkontaktosoba values(1, 3);
insert into sponzorkontaktosoba values(2, 1);
insert into sponzorkontaktosoba values(3, 2);
insert into sponzorkontaktosoba values(4, 4);
insert into sponzorkontaktosoba values(5, 1);

insert into tim values (null, 'Tim A', 0);
insert into tim values (null, 'Tim B', 0);

insert into osoba values (null, 'Nikola', 'Medic', '065/473-053', '1302985102011', 'nikolam@fujifilm.com', 'Trgoviste bb Novi Grad', 'A148215', 0);
insert into osoba values (null, 'Srdjan', 'Batez', '066/926-729', '2003996100031', 'srdjanba@gmail.com', 'Prijedor bb', 'A712204', 0);
insert into osoba values (null, 'Nemanja', 'Sarengaca', '065/261-475', '0201995111024', 'nemanjaneco@fujifilm.com', 'Svodna bb', 'A722001', 0);

insert into osobatim values (1, 1, '2019-05-17 16:30:02', null, 'igrac', 'golman');
insert into osobatim values (2, 1, '2019-02-18 10:30:02', null, 'igrac', 'trener');
insert into osobatim values (3, 2, '2019-05-17 12:15:15', null, 'igrac', 'lijevo krilo');
insert into osobatim values (2, 2, '2019-06-25 14:23:02', null, 'igrac', 'golman');

insert into ljekarskipregled values (null, '2019-11-08 13:11:03', '2020-05-08 13:11:03', 1);
insert into ljekarskipregled values (null, '2019-10-15 09:20:03', '2020-04-15 09:20:03', 2);
insert into ljekarskipregled values (null, '2019-08-08 10:15:08', '2020-02-08 10:15:08', 3);

insert into oprema values (null, 1, 'dres', '1', 'A52871');
insert into oprema values (null, 2, 'dres', '12', 'A74120');
insert into oprema values (null, 3, 'kopacke', '43', 'C74136');

insert into kazna values (null, '2019-06-12 18:00:06', 55.0, 4, 'prekrsaj na terenu', 3, 2); 

insert into utakmica values (null, '2020-01-16 12:00:00', 'FK Borac Banja Luka', '1:1');
insert into utakmica values (null, '2020-01-24 18:00:00', 'OFK Laus Banja Luka', '2:1');

insert into osobatimutakmica values(1, 1, 1);
insert into osobatimutakmica values(3, 2, 2);

insert into zaduzenje values (null, 'pice', 1, 1, 0);
insert into zaduzenje values (null, 'kupljenje smeca', 1, 1, 0);
insert into zaduzenje values (null, 'ciscenje tribina', 1, 1, 0);
insert into zaduzenje values (null, 'rostilj + hljeb', 1, 1, 0);
insert into zaduzenje values (null, 'plin za rostilj', 1, 1, 0);
insert into zaduzenje values (null, 'oprema za igrace', 1, 1, 0);
insert into zaduzenje values (null, 'aparat za kokice', 1, 1, 0);
insert into zaduzenje values (null, 'grickalice', 1, 1, 0);
insert into zaduzenje values (null, 'postaviti suncobrane', 1, 1, 0);
insert into zaduzenje values (null, 'ozvucenje', 1, 1, 0);
insert into zaduzenje values (null, 'sitne pare za rad', 1, 1, 0);
insert into zaduzenje values (null, 'ispomoc za organizaciju', 1, 1, 0);
insert into zaduzenje values (null, 'voda za igrace', 1, 1, 0);
insert into zaduzenje values (null, 'provjera terena', 1, 1, 0);

drop view if exists razlikadatuma;
create view razlikadatuma as
select ljekarskipregled.*, TIMESTAMPDIFF(DAY, DatumPregleda, DatumIsteka) as Razlika from ljekarskipregled;



