import com.hero.feelin.domain.model.Mood
import com.hero.feelin.domain.model.MoodType
import com.hero.moodin.domain.api.FeelingsAPI
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("feelings")
class FeelingController(private val feelingsAPI: FeelingsAPI) {

    @PostMapping("create")
    fun addMood(@RequestParam type: String) {
        val mood = Mood(type = MoodType.valueOf(type))
        feelingsAPI.create(mood)
    }
}
