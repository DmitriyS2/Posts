data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId:Int = 0,
    val createdBy:Int = 0,
    val date:Int =0,
    val text: String = "text",
    val replyOwnerId:Int = 0,
    val replyPostId:Int =0,
    val friendsOnly:Boolean = true,
    val comments: Comments,
    val copyright: String = "",
    val likes:Likes,
    val reposts:Reposts?,
    val views:Views?,
    val postType: String = "postType",
    val postSource:PostSource?,
    val geo:Geo?,
    val signerId: Int = 0,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val isPinned:Boolean = true,
    val markedAsAds:Boolean = true,
    val isFavorite:Boolean = true,
    val postponedId:Int = 0
)

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Likes(
    val count: Int,
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)
data class Reposts(
    val count:Int=0,
    val userReposted:Boolean = true
)
data class Views(
    val count:Int?
)
data class PostSource(
    val id:Int?
)
data class Geo(
    val type:String = "type",
    val coordinates:String = "coordinates",
    val place:String = "place"
)

private var uniqueId: Int = 1

object WallService {
    var posts = emptyArray<Post>()

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
}

fun main(args: Array<String>) {
    var p1 = PostSource(null)
    var post1 = Post(comments = Comments(), likes = Likes(0),
        reposts = null, views = null, postSource = null, geo = null)
    println(post1)
    post1 = WallService.add(post1)
    println(post1)
    println(WallService.posts[0])
    println("\n")
    var post2 = Post(comments = Comments(), likes = Likes(5),
        reposts = null, views = null, postSource = null, geo = null)
    println(post2)
    post2 = WallService.add(post2)
    println(post2)
    println(WallService.posts[1])
}