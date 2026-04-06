# Social Media Photo Sharing App

An object-oriented Java application simulating the core features of a photo sharing social media platform. Built with Java 25 and Gradle.

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

## How to Build

```bash
./gradlew build
```

## Known Gaps

- **No UserService** — users must be instantiated manually; no registration or lookup logic exists yet.
- **No follow system** — `TimelineService.getGlobalTimeline()` returns all photos from all users. A real social feed should show only photos from followed users.
- **No like system** — photos have no like counter or interaction model.
- **No persistence** — all data lives in memory and is lost when the app stops.
- **No main demo** — `Main.java` only prints a startup message; nothing is wired together end to end.
- **No tests** — there are no unit tests covering any layer.

