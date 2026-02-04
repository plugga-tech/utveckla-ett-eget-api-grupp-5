-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into hero (name, heroClass, race, focusedFire, steadyFrame, strongArms, jackOfAllTrades) values ('Jayce' , 'Fighter'   , 'HUMAN' , false , false , false , true );
insert into hero (name, heroClass, race, focusedFire, steadyFrame, strongArms, jackOfAllTrades) values ('Sammy' , 'Ranger'    , 'ELF'   , true  , false , false , false);
insert into hero (name, heroClass, race, focusedFire, steadyFrame, strongArms, jackOfAllTrades) values ('Gronk' , 'Vanguard'  , 'DWARF' , false , true  , false , false);
insert into hero (name, heroClass, race, focusedFire, steadyFrame, strongArms, jackOfAllTrades) values ('Nark'  , 'Barbarian' , 'ORC'   , false , false , true  , false);

