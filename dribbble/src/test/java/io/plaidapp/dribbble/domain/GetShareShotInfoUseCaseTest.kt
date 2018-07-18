/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.plaidapp.dribbble.domain

import android.net.Uri
import io.plaidapp.core.dribbble.data.api.model.Images
import io.plaidapp.core.dribbble.data.api.model.Shot
import io.plaidapp.dribbble.any
import io.plaidapp.dribbble.shot
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Tests for [GetShareShotInfoUseCase], mocking out its dependencies.
 */
class GetShareShotInfoUseCaseTest {

    private val imageUriProvider = mock(DribbbleImageUriProvider::class.java)
    private val uri = mock(Uri::class.java)
    private val getShareShotInfoUseCase = GetShareShotInfoUseCase(imageUriProvider)

    @Test
    fun getShareInfo_Png() {
        // Given a shot with a png image
        val shot = withUrl("https://cdn.dribbble.com/users/6295/screenshots/2344334/plaid_dribbble.png")

        // When invoking the use case
        val shareInfo = getShareShotInfoUseCase(shot)

        // Then the expected share info is returned
        assertNotNull(shareInfo)
        assertEquals(shot.title, shareInfo.title)
        assertFalse(shareInfo.shareText.isNullOrBlank())
        assertTrue(shareInfo.mimeType.contains("png"))
    }

    @Test
    fun getShareInfo_Gif() {
        // Given a shot with a gif image
        val shot = withUrl("https://cdn.dribbble.com/users/213811/screenshots/2916762/password_visibility_toggle.gif")

        // When invoking the use case
        val shareInfo = getShareShotInfoUseCase(shot)

        // Then the expected share info is returned
        assertNotNull(shareInfo)
        assertEquals(shot.title, shareInfo.title)
        assertFalse(shareInfo.shareText.isNullOrBlank())
        assertTrue(shareInfo.mimeType.contains("gif"))
    }

    @Test
    fun getShareInfo_Jpeg() {
        // Given a shot with a jpg image
        val shot = withUrl("https://cdn.dribbble.com/users/3557/screenshots/1550672/full2.jpg")

        // When invoking the use case
        val shareInfo = getShareShotInfoUseCase(shot)

        // Then the expected share info is returned
        assertNotNull(shareInfo)
        assertEquals(shot.title, shareInfo.title)
        assertFalse(shareInfo.shareText.isNullOrBlank())
        assertTrue(shareInfo.mimeType.contains("jpeg"))
    }

    private fun withUrl(url: String): Shot {
        val shot = shot.copy(images = Images(hidpi = url))
        Mockito.`when`(imageUriProvider.invoke(any(), any())).thenReturn(uri)
        return shot
    }
}
