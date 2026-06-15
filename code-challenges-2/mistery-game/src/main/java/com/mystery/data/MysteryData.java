package com.mystery.data;

import com.mystery.model.Clue;
import com.mystery.model.Suspect;

import java.util.List;

public class MysteryData {

    public static List<Suspect> suspects() {
        return List.of(
            new Suspect("Diana Crane", "Victor's business partner, sharply dressed and composed.", "Claims she was at the bar all evening", true),
            new Suspect("Marcus Webb", "The hotel butler, nervous and evasive.", "Says he was delivering room service on another floor", false),
            new Suspect("Scarlett Voss", "Victor's ex-wife, arrived unexpectedly.", "Claims she was in the lobby talking to staff", false),
            new Suspect("Henry Kale", "Victor's nephew, looking to inherit.", "Insists he was in his room sleeping", false),
            new Suspect("Iris Cho", "The hotel bartender, calm under pressure.", "Was behind the bar all night, multiple witnesses confirm", false)
        );
    }

    public static List<Clue> clues() {
        return List.of(
            new Clue("registry", "Hotel registry shows room 412 was booked under the name 'D. Crawford'.", "reception", false),
            new Clue("footage", "Security footage shows a woman in a red dress near suite 501 at 11:14 PM.", "security_room", false),
            new Clue("glass", "A whiskey glass with traces of an unknown substance found on the bedside table.", "suite", false),
            new Clue("phone", "Victor's phone contains a threatening message: 'You'll regret cutting me out. -D'", "suite", false),
            new Clue("vial", "An empty vial of potassium cyanide found in the trash bin near room 412.", "hallway", false),
            new Clue("receipt", "Bar receipt shows 'D. Crawford' ordered two whiskeys at 10:45 PM.", "bar", false),
            new Clue("dress", "A red dress with a monogram 'DC' found in a laundry bag from room 412.", "laundry", false)
        );
    }
}
