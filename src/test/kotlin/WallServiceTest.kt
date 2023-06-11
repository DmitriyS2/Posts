import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.lang.RuntimeException
import kotlin.test.assertFailsWith

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPost() {
        val post1 = Post(
            comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        val (result) = WallService.add(post1)
        assertEquals(1, result)
    }

    @Test
    fun updateTrueId() {
        val post1 = Post(
            comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        val post2 = Post(
            id = 1, comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        WallService.add(post1)
        val result: Boolean = WallService.update(post2)
        assertTrue(result)
    }

    @Test
    fun updateFalseId() {
        val post1 = Post(
            comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        val post2 = Post(
            id = 3, comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        WallService.add(post1)
        val result: Boolean = WallService.update(post2)
        assertFalse(result)
    }

    @Test
    fun createCommentTrue() {
        val post = Post(
            comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        WallService.add(post)
        val postId = 1
        val comment = Comment()
        //val commentOther = Comment(1,1,1,"noText",1)
        val result: Comment = WallService.createComment(postId, comment)
        assertEquals(comment, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val postId = 1
        val comment = Comment()
        WallService.createComment(postId, comment)
    }

    @Test
    fun checkNegativeCommentsTrue() {
        val post = Post(
            comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        WallService.add(post)
        val postId: Int = 1
        val reason: Int = 2
        val result: Boolean = WallService.checkNegativeComments(postId, reason)
        assertTrue(result)
    }

    @Test(expected = PostNotFoundException::class)
    fun checkNegativeCommentsNotPost() {
        val postId = 1
        val reason = 2
        WallService.checkNegativeComments(postId, reason)
    }

    @Test
    fun checkNegativeCommentsInvalidReason() {
        val post = Post(
            comment = Comment(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null
        )
        WallService.add(post)
        val postId: Int = 1
        val reason: Int = 8
        WallService.checkNegativeComments(postId, reason)
    }


}