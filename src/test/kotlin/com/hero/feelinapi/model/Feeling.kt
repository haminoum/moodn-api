import com.hero.feelin.domain.model.Feeling
import com.hero.feelin.domain.model.FeelingId

internal fun Feeling.Companion.fixture() = Feeling(
    id = FeelingId()
)
