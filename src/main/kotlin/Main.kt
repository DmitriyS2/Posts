import java.lang.RuntimeException

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val date: Int = 0,
    val text: String = "text",
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = true,
    val comment: Comment,
    val copyright: String = "",
    val likes: Likes,
    val reposts: Reposts?,
    val views: Views?,
    val postType: String = "postType",
    val postSource: PostSource?,
    val geo: Geo?,
    val signerId: Int = 0,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val isPinned: Boolean = true,
    val markedAsAds: Boolean = true,
    val isFavorite: Boolean = true,
    val postponedId: Int = 0,
    var attachments: Array<Attachment> = emptyArray()
)


data class Comment(
    val count: Int = 0,
    val fromId: Int = 0,
    val date: Int = 0,
    val text: String = "text",
    val replyToUser: Int = 0
)

data class Likes(
    val count: Int,
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = true
)

data class Views(
    val count: Int?
)

data class PostSource(
    val id: Int?
)

data class Geo(
    val type: String = "type",
    val coordinates: String = "coordinates",
    val place: String = "place"
)

sealed class Attachment(val type: String)
class PhotoAttachment(val photo: Photo) : Attachment("photo")
class GraffitiAttachment(val graffiti: Graffiti) : Attachment("graffiti")
class AppAttachment(val app: App) : Attachment("app")
class PageAttachment(val page: Page) : Attachment("page")
class EventAttachment(val event: Event) : Attachment("event")

data class Photo(
    val id: Int = 0,
    val idOwner: Int = 0,
    val photo130: String? = null,
    val photo604: String? = null
)

data class Graffiti(
    val id: Int = 0,
    val idOwner: Int = 0,
    val photo130: String? = null,
    val photo604: String? = null
)

data class App(
    val id: Int = 0,
    val idOwner: Int = 0,
    val photo130: String? = null,
    val photo604: String? = null
)

data class Page(
    val id: Int = 0,
    val groupId: Int = 0,
    val title: String = "title"
)

data class Event(
    val id: Int = 0,
    val time: Int = 0,
    val memberStatus: Int = 0,
    val text: String = "text"
)

data class NegativeComments(
    val postId: Int = 0,
    val reason: String = "text"
)

private var uniqueId: Int = 1
val reasons: Array<String> = arrayOf(
    "спам", "детская порнография", "экстремизм", "насилие",
    "пропаганда наркотиков", "материал для взрослых", "оскорбление", "призывы к суициду"
)
var reportsNegativeComments: Array<NegativeComments> = emptyArray()

class PostNotFoundException(message: String) : RuntimeException(message)

object WallService {
    var posts = emptyArray<Post>()
    var comments = emptyArray<Comment>()

    fun add(post: Post): Post {
        val postUniqueId = post.copy(id = uniqueId)
        posts += postUniqueId
        uniqueId += 1
        return postUniqueId
    }

    fun update(post: Post): Boolean {
        for ((index, postForEach: Post) in posts.withIndex()) {
            if (postForEach.id == post.id) {
                posts[index] = post.copy()
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        uniqueId = 1
    }

    fun addAttachment(post: Post, attachment: Attachment): Post {
        post.attachments += attachment
        update(post)
        return post
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        if (checkId(postId)) {
            comments += comment
            return comment
        } else throw PostNotFoundException("Нет поста с Id = $postId")
    }

    fun checkId(postId: Int): Boolean {
        for (post: Post in posts) {
            if (post.id == postId) {
                return true
            }
        }
        return false
    }

    fun checkNegativeComments(postId: Int, reason: Int): Boolean {
        try {
            if (checkId(postId)) {
                reportsNegativeComments += NegativeComments(postId, reasons[reason])
                return true
            } else throw PostNotFoundException("Нет поста с Id = $postId")
        } catch (e: ArrayIndexOutOfBoundsException) {
            println("Вы ввели неверный номер причины ($reason - не входит в интервал от 0 до 7 включительно)")
        }
        return false
    }
}

fun main(args: Array<String>) {
    val pS1 = PostSource(null)
    var post1 = Post(
        comment = Comment(), likes = Likes(0),
        reposts = null, views = null, postSource = pS1, geo = null
    )
    println(post1)
    post1 = WallService.add(post1)
    println(post1)
    println(WallService.posts[0])
    val attachmentPhoto: Attachment = PhotoAttachment(photo = Photo(photo130 = "photo130", photo604 = "photo604"))
    WallService.addAttachment(post1, attachmentPhoto)
    println(post1)
    println("\n")

    var post2 = Post(
        comment = Comment(), likes = Likes(5),
        reposts = null, views = null, postSource = null, geo = null
    )
    println(post2)
    post2 = WallService.add(post2)
    println(post2)
    println(WallService.posts[1])

    println("\n" + WallService.createComment(1, comment = Comment()))
    //println(WallService.createComment(5, comment = Comment()))

    if (WallService.checkNegativeComments(1, 5)) println(reportsNegativeComments[0])

}