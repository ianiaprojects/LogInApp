# Log in application

Implementați un program care oferă posibilitatea de logare, creare și stergere a unui utilizator, printr-o interfață grafică.
In baza de date ar trebui să se rețină username, first name, last name, email, date of creation, last login date, password,
salt.
Parola va fi securizată, folosind sha256 și sare.
Ca și structură a bazei de date, vor fi 2 tabele, unul in care vor fi stocate informațiile de securitate (username, password,

salt), și unul în care vor fi stocate informațiile personale (first name, last name etc.). Cele 2 tabele vor fi conectate printr-
un foreign key pe ID.
