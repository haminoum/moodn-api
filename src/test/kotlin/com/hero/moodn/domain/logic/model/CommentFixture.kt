import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
import com.hero.moodn.domain.model.UserId
import java.time.Instant

internal fun Comment.Companion.fixture() = Comment(
    id = CommentId(),
    author = UserId(),
    content = "What a lovely day",
    createdAt = Instant.now().minusSeconds(60),
)
