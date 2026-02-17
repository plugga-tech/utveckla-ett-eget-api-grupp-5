-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into hero (name, heroClass, race, weapon, focusedFire, steadyFrame, strongArms, jackOfAllTrades, ownerApiKey) values ('Jayce' , 'WARRIOR' , 'HUMAN' , 'SWORD', false , false , false , true , 123456);
insert into hero (name, heroClass, race, weapon, focusedFire, steadyFrame, strongArms, jackOfAllTrades, ownerApiKey) values ('Sammy' , 'ROGUE'   , 'ELF'   , 'DAGGER', true  , false , false , false, 123456);
insert into hero (name, heroClass, race, weapon, focusedFire, steadyFrame, strongArms, jackOfAllTrades, ownerApiKey) values ('Gronk' , 'PALADIN' , 'DWARF' , 'MACE', false , true  , false , false, 123456);
insert into hero (name, heroClass, race, weapon, focusedFire, steadyFrame, strongArms, jackOfAllTrades, ownerApiKey) values ('Nark'  , 'MAGE'    , 'ORC'   , 'STAFF', false , false , true  , false, 123456);
insert into hero (name, heroClass, race, weapon, focusedFire, steadyFrame, strongArms, jackOfAllTrades, ownerApiKey) values ('Sad'  , 'MAGE'    , 'ELF'   , 'STAFF', false , false , true  , false, 'wrongId');


-- Lägger till en enda test-user. Vi behöver inte testa lösenord och sådant längre, så det är irrelevant
-- om det är okrypterat eller ej. Det enda som spelar någon roll är att den har en key som vi kan använda
-- för att testa filtret.
-- För att skicka en request i postman:
-- välj 'Auth'
-- välj 'Auth type: API Key
-- ställ in:
-- Key: X-API-KEY
-- Value: <nyckeln> (i det här fallet 123456)
insert into users (id, username, password, api_key) values (111, 'asdf', 'asdf', '123456');
