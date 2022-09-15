import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodType
import com.hero.moodin.domain.api.MoodsAPI
import mu.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("moods")
class MoodController(private val moodsAPI: MoodsAPI) {


    @PostMapping("create")
    fun addMood(@RequestParam type: String, @RequestParam username: String) {
        val mood = Mood(type = MoodType.valueOf(type))
        moodsAPI.create(mood, username)
    }
}
