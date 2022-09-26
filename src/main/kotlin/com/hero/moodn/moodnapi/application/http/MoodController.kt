
import com.hero.moodn.moodnapi.domain.api.Moods
import com.hero.moodn.moodnapi.domain.model.Mood
import com.hero.moodn.moodnapi.domain.model.MoodType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("moods")
class MoodController(private val moods: Moods) {

    @PostMapping("create")
    fun addMood(@RequestParam type: String, @RequestParam username: String) {
        val mood = Mood(type = MoodType.valueOf(type))
        moods.create(mood, username)
    }
}
