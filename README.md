# film-ratings

[![CircleCI](https://circleci.com/gh/chrishowejones/film-ratings.svg?style=svg)](https://circleci.com/gh/chrishowejones/film-ratings)

Simple web application to enter films and their ratings.
Uses Duct micro-framework developed by Weavejester (James Greaves).

## Developing

### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests through Leiningen.

```sh
lein test
```
## Running in production

First you need to build the uberjar for the application that will be
included in the docker container(s).

``` sh
lein do clean, uberjar
```

You will need to set the `DB_PASSWORD` environment variable to a
password for the postgresql database. You can then bring up the
postgresql, migrations and web application docker instances using
`docker-compose`.

``` sh
docker-compose up -d
```

This will use the `DB_PASSWORD` to create a postgresql database. This
database will use a volume mounted for the data in a directory
`./postgresdata`.

The `docker-compose` file starts three services:

    1. Postgres database - with a start up script to create an empty
    database if not already present.
    2. Migrations - starts the application passing the
    `:duct/migrator` key to run database migrations to create the film
    table.
    3. FilmApp - starts the web application listening on port 3000

## Legal

Copyright © 2018 Chris Howe-Jones
