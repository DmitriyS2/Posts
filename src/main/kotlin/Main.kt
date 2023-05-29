data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val text: String = "text",
    val comments: Comments,
    val postType: String = "postType",
    val signerId: Int = 0,
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val copyright: Copyright
)

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Copyright(
    val id: Int,
    val link: String = "link",
    val name: String = "name",
    val type: String = "type"
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
//    var post1 = Post(comments = Comments(), copyright = Copyright(1))
//    println(post1)
//    post1 = WallService.add(post1)
//    println(post1)
//    println(WallService.posts[0])
//    println("\n")
//    var post2 = Post(comments = Comments(), copyright = Copyright(2))
//    println(post2)
//    post2 = WallService.add(post2)
//    println(post2)
//    println(WallService.posts[1])
}