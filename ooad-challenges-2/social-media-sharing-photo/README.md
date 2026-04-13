# Social Media Photo Sharing App

A POC exploring object-oriented design through a simplified photo sharing platform. Users can register, follow each other, publish photos, attach tags, leave comments, like posts, and browse a personalised timeline. The goal is to practice layered architecture (domain, repository, service) and incremental feature delivery using plain Java — no frameworks, no database.

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
| `User` | Platform user with id, username, email, bio, profilePictureUrl and joinedAt |
| `Photo` | Published photo with imageUrl, caption, author, tags and comments |
| `Tag` | Label attached to photos; normalised to lowercase; equality by name |
| `Comment` | Comment on a photo with content, author and createdAt |
| `Follow` | Directed relationship between two users (follower → following) |
| `Like` | A user liking a photo, with likedAt timestamp |
| `Timeline` | Ordered list of photos for a given user |

## Repository Layer

Each entity has an interface and an in-memory implementation backed by a `HashMap`.

| Interface | Implementation |
|---|---|
| `UserRepository` | `InMemoryUserRepository` |
| `PhotoRepository` | `InMemoryPhotoRepository` |
| `TagRepository` | `InMemoryTagRepository` |
| `CommentRepository` | `InMemoryCommentRepository` |
| `FollowRepository` | `InMemoryFollowRepository` |
| `LikeRepository` | `InMemoryLikeRepository` |

## Service Layer

| Service | Key operations |
|---|---|
| `UserService` | Register users, find by id or username, update bio and profile picture |
| `PhotoService` | Publish photos, tag/untag, find by user or tag |
| `CommentService` | Add, edit and delete comments |
| `TagService` | Find or create tags, rank most used tags |
| `FollowService` | Follow/unfollow users, list followers and following |
| `LikeService` | Like/unlike photos, count likes, list photos liked by a user |
| `TimelineService` | User feed (own + followed users' photos) and global feed, sorted by date |

## How to Run

```bash
./gradlew run
```

The demo in `Main.java` registers 3 users, sets up follows, publishes photos with tags, adds comments and likes, then prints the personalised timeline, global feed, like activity, top tags and comments.

## Known Gaps

- **No persistence** — all data lives in memory and is lost on restart.
- **No tests** — there are no unit tests covering any layer.
