# Social Media Photo Sharing App

A POC exploring object-oriented design through a simplified photo sharing platform. Users can publish photos, attach tags to categorize them, leave comments, and browse a timeline of posts. The goal is to practice layered architecture (domain, repository, service) and incremental feature delivery using plain Java — no frameworks, no database.

Built with Java 25 and Gradle.

## Architecture

The project follows a layered architecture:

```
com.dsantos
├── domain/        - Entities and business objects
├── repository/    - Data access interfaces and in-memory implementations
└── service/       - Business logic
```

## Domain Layer

| Class | Responsibilities |
|---|---|
| `User` | Represents a platform user with id, username, email, bio, profilePictureUrl and joinedAt |
| `Photo` | Represents a published photo with imageUrl, caption, author, tags and comments |
| `Tag` | Represents a label attached to photos; normalized to lowercase; equality by name |
| `Comment` | Represents a comment on a photo, with content, author and createdAt |
| `Timeline` | Holds an ordered list of photos for a given user |

## Repository Layer

Each entity has an interface defining the contract and an in-memory implementation backed by a `HashMap`.

| Interface | Implementation |
|---|---|
| `UserRepository` | `InMemoryUserRepository` |
| `PhotoRepository` | `InMemoryPhotoRepository` |
| `TagRepository` | `InMemoryTagRepository` |
| `CommentRepository` | `InMemoryCommentRepository` |

## Service Layer

| Service | Key operations |
|---|---|
| `PhotoService` | Publish photos, tag/untag photos, find by user or tag, delete |
| `CommentService` | Add, edit and delete comments on photos |
| `TagService` | Find or create tags, list all tags, rank most used tags |
| `TimelineService` | Build a user's own timeline or a global feed sorted by date |

## How to Run

```bash
./gradlew run
```

The demo in `Main.java` creates 3 users, publishes 4 photos, adds tags and comments, then prints:
- each user's own timeline
- a global feed sorted by most recent
- photos filtered by a specific tag
- top tags ranked by usage
- comments on a photo

## Known Gaps

- **No UserService** — users are instantiated directly; no registration or lookup service exists yet.
- **No follow system** — `getUserTimeline` shows only the user's own photos. A real feed should pull from followed users.
- **No like system** — photos have no like counter or interaction model.
- **No persistence** — all data lives in memory and is lost on restart.
- **No tests** — there are no unit tests covering any layer.

