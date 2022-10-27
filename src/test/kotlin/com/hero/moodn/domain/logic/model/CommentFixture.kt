import com.hero.moodn.domain.model.Comment
import com.hero.moodn.domain.model.CommentId
import com.hero.moodn.domain.model.MoodId
import java.time.Instant

internal fun Comment.Companion.fixture() = Comment(
    id = CommentId(),
    mood = MoodId(),
    content = "What a lovely day",
    createdAt = Instant.now().minusSeconds(60),
)
