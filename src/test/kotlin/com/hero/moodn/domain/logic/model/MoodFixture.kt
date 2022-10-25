import com.hero.moodn.domain.model.Mood // ktlint-disable filename
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType

internal fun Mood.Companion.fixture() = Mood(
    id = MoodId(),
    type = MoodType.HAPPY,
)
