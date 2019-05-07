package kristiania.enterprise.exam.backend.services;

import kristiania.enterprise.exam.backend.Category;
import kristiania.enterprise.exam.backend.entity.Item;
import kristiania.enterprise.exam.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * The approach in this file is heavily inspired by:
 * https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/734b4a660d8e1d815e2600c0e84cea5920bc5572/intro/exercise-solutions/quiz-game/part-10/src/main/java/org/tsdes/intro/exercises/quizgame/service/DefaultDataInitializerService.java
 */

@Service
public class DefaultDataInitializer {

    @Autowired
    Environment environment;

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    RankService rankService;
    @Autowired
    CommentService commentService;

    @PostConstruct
    public void initialize() {

        insertCommonData();

        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        boolean test = profiles.contains("test") || profiles.contains("dockertest"); 

        if (test) {

            insertTestData();
        }
    }

    private void insertCommonData() {

        attempt(() ->   attempt(() -> userService.createUser("admin@mail.com", "amdin-given", "admin-family", "admin", "ADMIN")));
    }

    private void insertTestData() {

        System.out.println("ADDING TEST DATA");
        
        // USERS
        attempt(() -> userService.createUser("dev@mail.com", "dev-given", "dev-family", "dev"));
        attempt(() -> userService.createUser("john@williams.com", "John", "Williams", "all_those_wizard_fans"));
        attempt(() -> userService.createUser("hans@zimmer.com", "Hans", "Zimmer", "goodstuff123"));
        attempt(() -> userService.createUser("marcin@przybyłowicz.com", "Marcin", "Przybyłowicz", "silver-and-metal"));
        attempt(() -> userService.createUser("mikołaj@stroiński.com", "Mikołaj", "Stroiński", "tunes-stuck-in-head-help-me"));

        // Items
        Long elephantId = attempt(() -> itemService.createItem("Water elephant", "Swims in the ocean. Cute and fearsome", Category.BLUE));
        Long mermaidId = attempt(() -> itemService.createItem("Mermaid", "Fooling, capturing and devouring seamen since 1633", Category.BLUE));
        Long sailorId = attempt(() -> itemService.createItem("Lone Sailor", "Alone on the ocean, whistling to long forgotten tunes", Category.BLUE));
        Long frogId = attempt(() -> itemService.createItem("Blue Frog", "Poisonous, waiting for the perfect moment to strike", Category.BLUE));

        Long ferretId = attempt(() -> itemService.createItem("Lava Ferret", "Commonly found around volcanoes.", Category.RED));
        Long eagleId = attempt(() -> itemService.createItem("Eagle of Fire", "Wherever this bird flies, forest-fires have a tendency to strike.", Category.RED));
        Long stoneGolemId = attempt(() -> itemService.createItem("Stone Golem", "Commonly found in caves near volcanoes.", Category.RED));

        Long hikerId = attempt(() -> itemService.createItem("Lone hiker", "Walking around in the woods, forever searching..", Category.GREEN));
        Long horseId = attempt(() -> itemService.createItem("Horse of speed", "Runs fater than any other creature in the forest", Category.GREEN));
        Long lizardId = attempt(() -> itemService.createItem("Lizard", "Invisible in grass. Its poison kills instantly", Category.GREEN));
        Long calmGolemId = attempt(() -> itemService.createItem("Calm golem", "Usually found near cliffs. Very shy, spends time watching the scenery", Category.GREEN));


        // getting users back
        UserEntity john = userService.getUser("john@williams.com");
        UserEntity hans = userService.getUser("hans@zimmer.com");
        UserEntity marcin = userService.getUser("marcin@przybyłowicz.com");
        UserEntity mikolaj = userService.getUser("mikołaj@stroiński.com");

        // getting items back
        Item elephant = itemService.getItem(elephantId);
        Item mermaid = itemService.getItem(mermaidId);
        Item sailor = itemService.getItem(sailorId);
        Item frog = itemService.getItem(frogId);

        Item ferret = itemService.getItem(ferretId);
        Item eagle = itemService.getItem(eagleId);
        Item stoneGolem = itemService.getItem(stoneGolemId);

        Item hiker = itemService.getItem(hikerId);
        Item horse = itemService.getItem(horseId);
        Item lizard = itemService.getItem(lizardId);
        Item calmGolem = itemService.getItem(calmGolemId);

        // Ranks
        attempt(() -> rankService.rankItem(elephant, john, 3));
        attempt(() -> rankService.rankItem(elephant, hans, 2));

        attempt(() -> rankService.rankItem(sailor, john, 5));

        attempt(() -> rankService.rankItem(frog, marcin, 4));
        attempt(() -> rankService.rankItem(frog, hans, 3));


        attempt(() -> rankService.rankItem(stoneGolem, marcin, 4));
        attempt(() -> rankService.rankItem(stoneGolem, mikolaj, 2));

        attempt(() -> rankService.rankItem(hiker, mikolaj, 5));
        attempt(() -> rankService.rankItem(hiker, john, 4));
        attempt(() -> rankService.rankItem(hiker, hans, 2));

        attempt(() -> rankService.rankItem(horse, marcin, 4));
        attempt(() -> rankService.rankItem(horse, john, 1));

        attempt(() -> rankService.rankItem(calmGolem, john, 4));
        attempt(() -> rankService.rankItem(calmGolem, marcin, 5));
        attempt(() -> rankService.rankItem(calmGolem, mikolaj, 4));

        // Comments
        commentService.createComment(
                john.getEmail(),
                elephant.getId(),
                "This is a nice card",
                "The image is stunning and the elephant is cute"
        );

        commentService.createComment(
                john.getEmail(),
                sailor.getId(),
                "This is an my favourite card",
                "The lonely sailor is a nice card with beautiful art. It brings back so many memories from the sea."
        );

        commentService.createComment(
                marcin.getEmail(),
                frog.getId(),
                "One of the best",
                "The description is nice and the illustration makes me happy."
        );
        commentService.createComment(
                hans.getEmail(),
                frog.getId(),
                "just ok",
                "While nice, I see nothing special about this card"
        );


        commentService.createComment(
                marcin.getEmail(),
                stoneGolem.getId(),
                "Nice art",
                "Nice art! I do not like the color that much, though.."
        );


        commentService.createComment(
                mikolaj.getEmail(),
                hiker.getId(),
                "Lovely art",
                "This is probably my favourite. I love the art!"
        );
        commentService.createComment(
                john.getEmail(),
                hiker.getId(),
                "Quite nice-looking",
                "The art is nice. I wish the description would elaborate a bit more."
        );
        commentService.createComment(
                hans.getEmail(),
                hiker.getId(),
                "Bad color",
                "The color on the art does not match the theme at all. Howerver, the image is nice on its own"
        );

        commentService.createComment(
                john.getEmail(),
                horse.getId(),
                "Horses!",
                "I do not like horses. I hope other people can have use for this card insted :-)"
        );

        commentService.createComment(
                john.getEmail(),
                calmGolem.getId(),
                "So relaxing",
                "I really enjoy looking at this card. It makes me happy"
        );
        commentService.createComment(
                marcin.getEmail(),
                calmGolem.getId(),
                "Fascinating",
                "I find myself thinking about the portrayed creature all the time. I am fascinated by it"
        );
    }

    private<T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}
