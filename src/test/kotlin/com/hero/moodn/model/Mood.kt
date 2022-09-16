import com.hero.feelin.domain.model.Mood // ktlint-disable filename
import com.hero.feelin.domain.model.MoodId
import com.hero.feelin.domain.model.MoodType

internal fun Mood.Companion.fixture() = Mood(
    id = MoodId(),
    type = MoodType.HAPPY,
)

