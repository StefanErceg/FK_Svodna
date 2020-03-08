insert into korisnickinalog values (null, 'Marina', 'Krneta', 'marinak', '97C1B2425112BE6676A52D66A11ECF55DD9FEA89E39EF1B6F936C0B869094AAD', 0, 0);
insert into korisnickinalog values (null, 'Marko', 'Jankovic', 'markoj', 'E3C4A8E68C23890091F9B9531EF3E0F805CE0A9378D6FB4BBCB6EED403C91342', 1, 0);

drop view if exists razlikadatuma;
create view razlikadatuma as
select ljekarskipregled.*, TIMESTAMPDIFF(YEAR, now(), DatumIsteka) as RazlikaGodina, TIMESTAMPDIFF(MONTH, now(), DatumIsteka) as RazlikaMjesec, TIMESTAMPDIFF(DAY, now(), DatumIsteka) as RazlikaDan from ljekarskipregled;

