import com.hero.moodn.domain.model.Mood // ktlint-disable filename
import com.hero.moodn.domain.model.MoodId
import com.hero.moodn.domain.model.MoodType
import com.hero.moodn.domain.model.UserId

internal fun Mood.Companion.fixture() = Mood(
    id = MoodId(),
    type = MoodType.HAPPY,
    user = UserId(),
)
