# Redis Clone

A lightweight Redis-like client/server built in Java, supporting **string** and **hash** data structures over a raw TCP connection using a simplified [RESP](https://redis.io/docs/reference/protocol-spec/) protocol.

---

## Architecture

```
src/main/java/com/dsantos/
├── Main.java
├── server/
│   ├── Server.java           # TCP server, spawns a thread per connection
│   └── ClientHandler.java    # Reads lines, parses commands, writes responses
├── protocol/
│   ├── RawCommand.java       # Record: command name + argument list
│   ├── CommandParser.java    # Parses a raw text line into a RawCommand
│   └── Response.java         # RESP response factory (ok, error, bulk, integer, array, nil)
├── command/
│   ├── CommandHandler.java         # Interface: supportedCommands + handle
│   ├── CommandDispatcher.java      # Routes commands to the correct handler
│   ├── StringCommandHandler.java   # Handles SET, GET, DEL, APPEND
│   └── HashCommandHandler.java     # Handles HSET, HGET, HKEYS, HVALS
└── store/
    ├── StringStore.java      # Thread-safe key/value string store
    └── HashStore.java        # Thread-safe nested hash store
```

### Design decisions

- **Extensible dispatcher** — registering a new command type only requires implementing `CommandHandler` and calling `dispatcher.register(...)`.
- **Isolated stores** — `StringStore` and `HashStore` have no coupling between each other.
- **Thread-safe** — both stores use `ConcurrentHashMap`; `append` uses the atomic `merge()` operation.
- **RESP protocol** — responses follow the Redis Serialization Protocol, making the server compatible with standard Redis clients such as `redis-cli`.

---

## Running

```bash
./gradlew run
```

The server listens on port **6379** by default.

---

## Supported Commands

### Strings

| Command | Syntax | Description |
|---|---|---|
| `SET` | `SET key value` | Store a string value |
| `GET` | `GET key` | Retrieve a string value (`$-1` if absent) |
| `DEL` | `DEL key` | Delete a key, returns `1` if removed, `0` otherwise |
| `APPEND` | `APPEND key value` | Append to a string; returns the new length |

### Hashes (Maps)

| Command | Syntax | Description |
|---|---|---|
| `HSET` | `HSET key field value` | Set a field inside a hash |
| `HGET` | `HGET key field` | Retrieve a field value (`$-1` if absent) |
| `HKEYS` | `HKEYS key` | List all fields of a hash |
| `HVALS` | `HVALS key` | List all values of a hash |

---

## Example Session

```
$ redis-cli -p 6379

SET name redis
+OK

APPEND name -clone
:11

GET name
"redis-clone"

DEL name
:1

GET name
(nil)

HSET user name Alice
+OK

HSET user age 30
+OK

HGET user name
"Alice"

HKEYS user
1) "name"
2) "age"

HVALS user
1) "Alice"
2) "30"
```

