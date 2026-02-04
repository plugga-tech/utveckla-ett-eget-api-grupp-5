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

-- Dessa kan inte kollas mot någonting som kräver lösenords-check, då deras lösenord inte är krypterade
-- och de metoder som använder sig av någon typ av lösenordskoll förväntar sig krypterade lösenord att matcha mot
insert into users (id, username, password, api_key, role) values (111, 'asdf', 'asdf', '123456', 'user');
insert into users (id, username, password, api_key, role) values (222, 'user', 'user', '123456123', 'user');