import com.hero.moodn.moodnapi.domain.model.Mood
import com.hero.moodn.moodnapi.domain.model.MoodId
import com.hero.moodn.moodnapi.domain.model.MoodType

internal fun Mood.Companion.fixture() = Mood(
    id = MoodId(),
    type = MoodType.HAPPY,
)
