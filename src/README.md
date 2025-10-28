Entities:

Author – reprezintă un autor de carte;
BookDetails – descrie detaliile unei carti, legate de autori;
MagazineDetails – reprezinta detaliile unei reviste;
Publication – clasa abstracta de baza pentru toate publicatiile;
BookAuthor – gestioneaza relatia M:N intre carti si autori;


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
