# OVERWATCH HEROES HUB

### Progetto Companion Web App per videogioco Overwatch

L'idea è quella di realizzare una companion app dove si trovano le statistiche degli eroi del videogioco Overwatch di Blizzard con le build per i personaggi da usare in 5v5.
L'idea nasce dalla mia passione per gli sparatutto, recentemente navigando per il web cercavo un sito che mostrasse le build per i perk per il 5v5 tradizionale purtroppo però non sono riuscito a trovarne, incappando solo in siti che suggerivano build per la modalità Stadium.

L'obiettivo è quello di realizzare un progetto che mi possa spingere a dare il meglio e che possa eventualmente dare un di più alla comunità ed eventualmente espanderlo con diverse sezioni in futuro.

### Teconlogie utilizzate:

Front-End: JavaScript + React + Redux + Bootstrap

Back-End: Java & Spring e i suoi moduli con DB PostgreSQL

All'interno della Repository troverete lo schema del DB PostgreSQL

### Note avvio progetto:
Assicurati di avere installato Java 21+

#### Nella cartella del progetto crea un file env.properties che abbia come attributi:

PORTA=3001 (altrimenti le chiamate al Back-End da parte del front-end non funzioneranno)

DB_NAME=nome_a_tua_scelta

PG_USERNAME=tuo_username_database

PG_PASSWORD=tua_password_database

JWT_SECRET=tuo_JWT

CLOUDINARY_NAME=

CLOUDINARY_API_KEY=

CLOUDINARY_SECRET=

MG_API_KEY=

MG_DOMAIN=

MG_FROM=

BASE_URL=

ADMIN_USERNAME=tuo_username

ADMIN_EMAIL=tua_email

ADMIN_PSW=tua_password

Questi ultimi 3 campi ADMIN_ sono l'username, email e password con il quale verrai automaticamente registrato come amministratore per poter gestire gli eroi ed eventualmente eliminare le build degli utenti che si registreranno.

#### N.B.

Il Front-End è impostato con porta 5173 per cambiare questo comportamente accedi al file SecurityConfig nella cartella security

nel @Bean CorsConfigurationSource nel config.setAllowedOrigins(List.of("http://localhost:5173"));
cambia la porta 5173 con la tua porta eventualmente.

Link alla repo per il Front-End: https://github.com/RickSciascia/OW_5v5_BUILDS_CAPSTONE_PROJECT_FRONT_END
