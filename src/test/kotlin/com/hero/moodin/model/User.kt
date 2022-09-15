
import com.hero.feelin.domain.model.User
import com.hero.feelin.domain.model.UserId

internal fun User.Companion.fixture() = User(
    id = UserId(),
    username = "baby-ham",
)
