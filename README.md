# Concert Database project

*Autorzy: Jakub Zając, Szymon Borusiewicz*

## Model bazy danych

Projekt bazy danych został zrealizowany jako model relacyjny, którego głównymi encjami są:

- `Ticket` - informacje o rezerwacji biletu
- `Event` - informacje o konkretnym wydarzeniu
- `Organizer` - organizator, który może organizować wydarzenia
- `Participant` - uczestnik, który może zarezerwować bilet na wydarzenie
- `Seat` - pojedyncze miejsce na wydarzeniu, jest przypisane do konkretnej lokalizacji
- `Venue` - lokalizacje, w których możliwe jest zorganizowanie wydarzenia
- `Category` - kategoria wydarzenia
- `City` - miasto, w którym znajduje się dana lokalizacja
- `Country` - kraj (każde miasto ma swój kraj, a także uczestnik ma swoje obywatelstwo)

Relacje między encjami zostały zrealizowane przy użyciu adnotacji JPA (`@ManyToOne`, `@OneToMany`).
Dla każdej encji została utworzona klasa encyjna (`@Entity`) oraz interfejs repozytorium (`@JpaRepository`). Dzięki temu aplikacja obsługuje podstawowe operacje CRUD. Serwisy (`@Service`) realizują logikę biznesową, np. tworzenie rezerwacji z kontrolą dostępności miejsc.

---

## Schemat bazy danych

![Schemat](/doc/Schemat.png)

Poniżej znajduje się opis tabel wykorzystanych w projekcie:

### Tickets
Tabela przechowuje dane o rezerwacjach użytkowników
- **TicketID** - id rezerwacji, klucz główny
- **EventID** - id wydarzenia, którego dotyczy rezerwacja
- **ParticipantID** - id uczestnika, który zarezerwował dany bilet
- **SeatID** - id miejsca, które zostało zarezerwowane
- **Type** - typ biletu (REGULAR, STUDENT, VIP)
- **Price** - cena biletu

### Events
Tabela przechowuje dane o wydarzeniach
- **EventID** - id wydarzenia, klucz główny
- **Title** - nazwa wydarzenia
- **Description** - opis wydarzenia
- **VenueID** - id lokalizacji wydarzenia
- **CategoryID** - id kategorii wydarzenia
- **OrganizerID** - id organizatora wydarzenia
- **StartDate** - data początku wydarzenia
- **EndDate** - data końca wydarzenia
- **RegularPrice** - cena biletu REGULAR na wydarzenie
- **StudentPrice** - cena biletu STUDENT na wydarzenie
- **VipPrice** - cena biletu VIP na wydarzenie

### Organizers
Tabela przechowuje dane o organizatorach wydarzeń
- **OrganizerID** - id organizatora, klucz główny
- **Name** - nazwa organizatora
- **Email** - adres e-mail organizatora
- **Phone** - numer telefonu organizatora

### Participants
Tabela przechowuje dane o uczestnikach wydarzeń
- **ParticipantID** - id uczestnika, klucz główny
- **CountryID** - id kraju z którego pochodzi uczestnik
- **FirstName** - imię uczestnika
- **LastName** - nazwisko uczestnika
- **Email** - adres e-mail uczestnika

### Seats
Tabela przechowuje pojedyncze miejsca w danej lokalizacji
- **SeatID** - id miejsca, klucz główny
- **VenueID** - id lokalizacji, w której znajduje się miejsce
- **SeatNumber** - numer miejsca
- **Section** - sektor na którym znajduje się siedzenie
- **Row** - rząd, w którym znajduje się siedzenie

### Venues
Tabela przechowuje lokalizacje, w których można zorganizować wydarzenia
- **VenueID** - id lokalizacji, klucz główny
- **CityID** - id miasta, w którym znajduje się lokalizacja
- **Name** - nazwa lokalizacji
- **Description** - opis lokalizacji
- **Capacity** - pojemność lokalizacji

### Categories
Tabela przechowuje kategorie wydarzeń
- **CategoryID** - id kategorii, klucz główny
- **Name** - nazwa kategorii

### Cities
Tabela przechowuje miasta
- **CityID** - id miasta, klucz główny
- **CountryID** - id kraju, w którym znajduje się miasto
- **Name** - nazwa miasta

### Countries
Tabela przechowuje kraje
- **CountryID** - id kraju, klucz główny
- **Name** - nazwa kraju

---

## Technologie

### Backend 
- `Spring Boot 3.5.0` - szkielet aplikacji backendowej
- `Spring Data JPA` - warstwa dostępu do bazy danych, automatyzuje operacje CRUD i mapowanie obiektowo-relacyjne
- `Hibernate` - mapuje encje Javy na tabele w relacyjnej bazie danych
- `PostgreSQL` - relacyjna baza danych
- `Lombok` - biblioteka ułatwiająca pisanie kodu Javy, automatyzuje generowanie getterów, setterów itp.
- `Spring Boot Test` - narzędzie do testowania komponentów Springa

### Frontend

- `React.js` - biblioteka do budowy UI aplikacji webowej
- `Fetch API` - użyty do komunikacji frontend-backend
- `CSS` - stylowanie komponentów frontendowych

W projekcie zdecydowaliśmy się na stos technologiczny Spring Boot + React, gdyż umożliwia on tworzenie nowoczesnych aplikacji webowych w architekturze client-server.

Spring Boot oferuje szerokie wsparcie dla REST API, dostępu do danych i testowania. Dzięki Spring Data JPA można było wygodnie zaimplementować operacje na bazie danych bez pisania własnych zapytań SQL.

PostgreSQL to stabilna, dobrze wspierana relacyjna baza danych, z której łatwo korzystać w środowiskach deweloperskich, więc stąd wybór padł na nią.

Po stronie frontendowej wybraliśmy React, ze względu na komponentową strukturę i elastyczność w budowaniu dynamicznych interfejsów. React w połączeniu z prostym CSSem oraz `fetch()` do komunikacji z backendem był wystarczający do realizacji założeń projektu.

---

## Funkcje aplikacji

- **Widok listy wydarzeń**  
Wyświetla listę wszystkich wydarzeń wraz z informacjami o nich
- **Widok listy organizatorów**  
Wyświetla listę wszystkich organizatorów wraz z informacjami o nich
- **Widok listy uczestników**  
Wyświetla listę wszystkich uczestników wraz z informacjami o nich
- **Widok listy zarezerwowanych biletów**  
Wyświetla listę wszystkich zarezerwowanych biletów wraz z informacjami o rezerwacji
- **Widok listy miejsc**  
Wyświetla listę wszystkich lokalizacji, w których można zorganizować wydarzenie
- **Raport popularności organizatorów**  
Wyświetla zestawienie organizatorów z sumą sprzedanych biletów na ich wydarzenia
- **Raport uczestników według kraju**  
Wyświetla zestawienie krajów z liczbą uczestników z danego kraju, którzy kiedykolwiek kupowali bilet na jakieś wydarzenie
- **Raport zapełnienia wydarzeń**  
Wyświetla zestawienie wydarzeń wraz z liczbą sprzedanych biletów na nie i procentowym zapełnieniem miejsca
- **Formularz dodawania nowego wydarzenia**  
Pozwala dodać nowe wydarzenie
- **Formularz dodawania nowego organizatora**  
Pozwala dodać nowego organizatora
- **Formularz dodawania nowego miejsca**  
Pozwala dodać nową lokalizację, w której będzie można organizować wydarzenie
- **Formularz rezerwacji biletu**  
Umożliwia rezerwację biletu.  
Operacja rezerwacji biletu jest operacją transakcyjną, nie jest możliwe zarezerwowanie miejsca, które zostało już zarezerwowane na dane wydarzenie. Przy wpisywaniu danych użytkownika, jeśli dany użytkownik już istnieje w bazie, wówczas system korzysta z jego ID, w przeciwnym razie tworzy się nowy użytkownik. Podobnie sytuacja wygląda z tworzeniem nowego miasta i kraju. Jeśli dane miasto już istnieje w bazie, wówczas pobiera jego ID. Tak samo wygląda to w przypadku dodawania nowego kraju, dzięki czemu kraje, miasta i uczestnicy nie duplikują się w bazie.