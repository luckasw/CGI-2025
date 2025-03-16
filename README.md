# CGI proovitöö
## Käivitamine
Lisa .env failid backendi ja frontendi kaustadesse

Näidis backendi env fail
```
HOSTNAME=localhost
DB_PORT=5432
DB_NAME=flights
DB_USERNAME=postgres
DB_PASSWORD=
```

Näidis frontendi env fail
```
VITE_API_BASE_URL=http://localhost:8080
```

Lisa backendi muutujad pathi
``` bash
export $(cat .env | xargs)
```
Või anna muutujad kaasa käivitamisel
``` bash
HOSTNAME=localhost DB_PORT=5432 DB_NAME=flights DB_USERNAME=postgres ./gradlew bootrun
```

Seadista java 21 jdk ja loo postgres andmebaas vastavalt .env failile

Käivita backend
``` bash
./gradlew bootrun
```
Käivita frontend
``` bash
npm install
npm run dev
```
Ava http://localhost:5173/
