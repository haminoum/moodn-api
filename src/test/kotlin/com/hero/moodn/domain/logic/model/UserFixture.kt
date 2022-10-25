import com.hero.moodn.domain.model.User // ktlint-disable filename
import com.hero.moodn.domain.model.UserId

internal fun User.Companion.fixture() = User(
    id = UserId(),
    username = "baby-ham",
)
