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

To run in production you will need to set the `DB_PASSWORD` environment
variable to a password for the postgresql database. You can then bring
up the postgresql docker instance using `docker-compose`.

``` sh
docker-compose up
```

This will use the `DB_PASSWORD` to create a postgresql database. This
database will use a volume mounted for the data in a directory
`./postgresdata`.
If this is the first time you've brought up the `docker-compose`
process you will need to run migrations to populate the database
before using it.

You run the migrations like so (make sure the `DB_PASSWORD` env
variable is set first):

``` sh
lein run :duct/migrator
```

Once the table(s) are created you can run the web app itself (make
sure the `DB_PASSWORD` env variable is set first):

``` sh
lein run
```



## Legal

Copyright © 2018 Chris Howe-Jones
