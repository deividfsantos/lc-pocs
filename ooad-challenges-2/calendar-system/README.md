# Calendar System

An object-oriented calendar system built in Java 17 that supports booking meetings, removing meetings, listing meetings, and suggesting the best available time slots for two people.

## Features

| Operation | Description |
|---|---|
| `bookMeeting` | Schedule a meeting for one or more participants; rejects overlapping bookings |
| `removeMeeting` | Cancel a meeting by its UUID |
| `listMeetings` | List all meetings for a person, sorted by start time |
| `suggestTime` | Find free 1-to-N-hour slots within a search window where two people are both available |

## Design

### Package Structure

```
com.dsantos
├── domain/
│   ├── Person.java          # Immutable value object (equality by email)
│   ├── TimeSlot.java        # Immutable value object (start/end LocalDateTime)
│   │                          overlaps(), contains()
│   └── Meeting.java         # Entity (UUID id, title, TimeSlot, participants)
├── repository/
│   ├── MeetingRepository.java          # Interface: save, remove, findById, findAll, findByParticipant
│   └── InMemoryMeetingRepository.java  # HashMap-backed implementation
├── service/
│   ├── MeetingValidator.java      # Checks participant schedule conflicts
│   ├── TimeSuggester.java         # Strategy interface for time suggestions
│   ├── FreeSlotTimeSuggester.java  # Merges busy slots, scans free gaps
│   └── CalendarService.java       # Main facade: orchestrates all operations
└── exception/
    ├── CalendarException.java  # Base unchecked domain exception
    └── ConflictException.java  # Thrown when a scheduling conflict is detected
```

### OOP Principles & Design Patterns

- **Value Object** — `Person`, `TimeSlot`: immutable, equality by value, no identity
- **Entity** — `Meeting`: mutable identity via UUID
- **Repository Pattern** — `MeetingRepository` interface decouples domain logic from storage; swapping to a database requires only a new implementation
- **Strategy Pattern** — `TimeSuggester` interface allows plugging in different suggestion algorithms without changing `CalendarService`
- **Constructor Injection** (Dependency Inversion) — `CalendarService` depends on abstractions, not concretions

## Running

```bash
# Run all tests
./gradlew test

# Run the demo
./gradlew run
```

> **Requirements:** Java 17+, Gradle 9.x (wrapper included)

## Demo Output (example)

```
=== 1. Booking meetings ===
Booked: Daily Standup       TimeSlot{2026-03-13T09:00 -> 2026-03-13T10:00}
Booked: Alice Code Review   TimeSlot{2026-03-13T11:00 -> 2026-03-13T12:00}
Booked: Bob Sprint Planning TimeSlot{2026-03-13T13:00 -> 2026-03-13T15:00}

-- Attempting to double-book Alice at 11-12 (should fail) --
Caught expected conflict: Participant 'Alice' already has meeting 'Alice Code Review' during ...

=== 2. Listing meetings ===
Alice's meetings:
  - Daily Standup      09:00 -> 10:00
  - Alice Code Review  11:00 -> 12:00
Bob's meetings:
  - Daily Standup      09:00 -> 10:00
  - Bob Sprint Planning 13:00 -> 15:00

=== 3. Removing Alice Code Review ===
Removed: true
Alice's meetings after removal:
  - Daily Standup 09:00 -> 10:00

=== 4. Suggesting free 1-hour slots for Alice & Bob (9:00–17:00) ===
First 5 suggestions:
  - 10:00 → 11:00
  - 10:15 → 11:15
  - 10:30 → 11:30
  - 10:45 → 11:45
  - 11:00 → 12:00
```

