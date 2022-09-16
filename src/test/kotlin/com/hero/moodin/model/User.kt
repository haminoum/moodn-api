import com.hero.moodin.domain.model.User
import com.hero.moodin.domain.model.UserId

internal fun User.Companion.fixture() = User(
    id = UserId(),
    username = "baby-ham",
)
