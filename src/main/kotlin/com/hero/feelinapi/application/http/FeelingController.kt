import com.hero.feelin.domain.model.Feeling
import com.hero.feelinapi.domain.api.FeelingsAPI
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("feelins")
class FeelingController(private val feelingsAPI: FeelingsAPI) {

    @PostMapping("create")
    fun addMood(@RequestParam type: String) {
        val feeling = Feeling()
        feelingsAPI.createFeeling(feeling)
    }
}
