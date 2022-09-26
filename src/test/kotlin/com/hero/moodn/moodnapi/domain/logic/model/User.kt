import com.hero.moodn.moodnapi.domain.model.User
import com.hero.moodn.moodnapi.domain.model.UserId

internal fun User.Companion.fixture() = User(
    id = UserId(),
    username = "baby-ham",
)
