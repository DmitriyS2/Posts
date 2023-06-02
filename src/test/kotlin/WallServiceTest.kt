import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPost() {
        val post1 = Post(comments = Comments(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null)
        val (result) = WallService.add(post1)
        assertEquals(1, result)
    }

    @Test
    fun updateTrueId() {
        val post1 = Post(comments = Comments(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null)
        val post2 = Post(id = 1, comments = Comments(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null)
        WallService.add(post1)
        val result: Boolean = WallService.update(post2)
        assertTrue(result)
    }

    @Test
    fun updateFalseId() {
        val post1 = Post(comments = Comments(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null)
        val post2 = Post(id = 3, comments = Comments(), likes = Likes(0),
            reposts = null, views = null, postSource = null, geo = null)
        WallService.add(post1)
        val result: Boolean = WallService.update(post2)
        assertFalse(result)
    }


}