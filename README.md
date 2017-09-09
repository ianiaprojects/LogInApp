# Log in application

Program care oferă posibilitatea de logare, creare și stergere a unui utilizator, printr-o interfață grafică.
In baza de date este reținut un username, first name, last name, email, date of creation, last login date, password,
salt.

Parola este securizată, folosind sha256 și sare.

Ca și structură a bazei de date, sunt 2 tabele, unul in care vor fi stocate informațiile de securitate (username, password,
salt), și unul în care vor fi stocate informațiile personale (first name, last name etc.). Cele 2 tabele sunt conectate printr-
un foreign key pe ID.
