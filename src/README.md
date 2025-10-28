Entities:

Author – reprezintă un autor de carte;
BookDetails – descrie detaliile unei carti, legate de autori;
MagazineDetails – reprezinta detaliile unei reviste;
Publication – clasa abstracta de baza pentru toate publicatiile;
BookAuthor – gestioneaza relatia M:N intre carti si autori;
Library - reprezintă o bibliotecă ce conține membri și publicații;
Member - aparține unei biblioteci (libraryId) și are împrumuturi + rezervări;
Reservation - reprezintă o rezervare efectuată de un membru pentru un exemplar disponibil;
ReadableItem - reprezintă o copie fizică a unei publicații (carte sau revistă) aflată într-o bibliotecă;
Loan - reprezintă un împrumut efectuat de un membru pentru unul sau mai multe exemplare;

Relaţiile:
• Library are Members (1 → N) și ReadableItems (1 → N)
• Member are Loans (1 → N) și Reservations (1 → N)
• Loan și Reservation se referă la ReadableItems (N → 1 pentru fiecare legătură directă)
• ReadableItem aparține unei Publicații (BookDetails / MagazineDetails) (N → 1)
• BookDetails și Author sunt legați prin BookAuthor (N ↔ M)

Overview: 
Arhitectura urmează modelul MVC (Model–View–Controller):

Model – clasele din pachetul model (entitățile de date).
Repository – gestionează persistarea în memorie.
Service – conține logica de business.
Controller – leagă cererile HTTP de operațiile din service.

Repository:

Implementarea este in-memory, bazata pe un Baserepo<T> generic;
Functionlitati CRUD/entitate;


Service:

Functionlitati CRUD/obiecte;
Fiecare entitate are propriul serviciu derivat dintr-un BaseService;
 - pentru a centraliza operațiile CRUD comune;
 - pentru a evita repetitia codului si pentru a pastra o structura uniforma;
AuthorService gestioneaza relatiile autor–carte (stergerea unui autor actualizeaza automat cartile asociate);
BookService se ocupa de gestionarea detaliilor cartilor;
MagazineService aplica aceleasi principii pentru reviste.


Controller:

Fiecare entitate are propriul controller, care apelează metodele din service;
Fiecare controller gestioneaza doar cererile legate de o singura entitate;
