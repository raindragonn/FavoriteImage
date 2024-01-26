package com.raindragonn.favoriteimage.data.repository

import com.raindragonn.favoriteimage.data.database.dao.ImageDao
import com.raindragonn.favoriteimage.data.database.entity.ImageData
import com.raindragonn.favoriteimage.data.remote.api.SearchApi
import com.raindragonn.favoriteimage.data.remote.response.ResultResponse
import com.raindragonn.favoriteimage.domain.di.IoDispatcher
import com.raindragonn.favoriteimage.domain.entity.Image
import com.raindragonn.favoriteimage.domain.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    @IoDispatcher
    private val _dispatcher: CoroutineDispatcher,
    private val _api: SearchApi,
    private val _imageDao: ImageDao,
) : ImageRepository {

    val test = """{
      "id": "zhFBDjczpl4",
      "slug": "a-couple-of-people-walking-on-a-beach-next-to-a-surfboard-zhFBDjczpl4",
      "created_at": "2023-10-04T19:16:47Z",
      "updated_at": "2024-01-25T06:50:48Z",
      "promoted_at": null,
      "width": 5268,
      "height": 7898,
      "color": "#c0d9f3",
      "blur_hash": "LUJbmzX9nijYyZo#R+RkOtM{a|of",
      "description": null,
      "alt_description": "a couple of people walking on a beach next to a surfboard",
      "breadcrumbs": [],
      "urls": {
        "raw": "https://images.unsplash.com/photo-1696446701657-0db3da722b13?ixid=M3w1NTYzMDF8MXwxfHNlYXJjaHwxfHxzZWF8ZW58MHx8fHwxNzA2MTY3NzkzfDA&ixlib=rb-4.0.3",
        "full": "https://images.unsplash.com/photo-1696446701657-0db3da722b13?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1NTYzMDF8MXwxfHNlYXJjaHwxfHxzZWF8ZW58MHx8fHwxNzA2MTY3NzkzfDA&ixlib=rb-4.0.3&q=85",
        "regular": "https://images.unsplash.com/photo-1696446701657-0db3da722b13?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1NTYzMDF8MXwxfHNlYXJjaHwxfHxzZWF8ZW58MHx8fHwxNzA2MTY3NzkzfDA&ixlib=rb-4.0.3&q=80&w=1080",
        "small": "https://images.unsplash.com/photo-1696446701657-0db3da722b13?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1NTYzMDF8MXwxfHNlYXJjaHwxfHxzZWF8ZW58MHx8fHwxNzA2MTY3NzkzfDA&ixlib=rb-4.0.3&q=80&w=400",
        "thumb": "https://images.unsplash.com/photo-1696446701657-0db3da722b13?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1NTYzMDF8MXwxfHNlYXJjaHwxfHxzZWF8ZW58MHx8fHwxNzA2MTY3NzkzfDA&ixlib=rb-4.0.3&q=80&w=200",
        "small_s3": "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1696446701657-0db3da722b13"
      },
      "links": {
        "self": "https://api.unsplash.com/photos/a-couple-of-people-walking-on-a-beach-next-to-a-surfboard-zhFBDjczpl4",
        "html": "https://unsplash.com/photos/a-couple-of-people-walking-on-a-beach-next-to-a-surfboard-zhFBDjczpl4",
        "download": "https://unsplash.com/photos/zhFBDjczpl4/download?ixid=M3w1NTYzMDF8MXwxfHNlYXJjaHwxfHxzZWF8ZW58MHx8fHwxNzA2MTY3NzkzfDA",
        "download_location": "https://api.unsplash.com/photos/zhFBDjczpl4/download?ixid=M3w1NTYzMDF8MXwxfHNlYXJjaHwxfHxzZWF8ZW58MHx8fHwxNzA2MTY3NzkzfDA"
      },
      "likes": 8,
      "liked_by_user": false,
      "current_user_collections": [],
      "sponsorship": {
        "impression_urls": [],
        "tagline": "Ditch plastic. Choose plant based.",
        "tagline_url": "https://boxedwaterisbetter.com/?utm_source=Unsplash&utm_medium=display&utm_campaign=Brand+Awareness",
        "sponsor": {
          "id": "8Ae1yJe8OoA",
          "updated_at": "2023-12-19T00:49:57Z",
          "username": "boxedwater",
          "name": "Boxed Water Is Better",
          "first_name": "Boxed Water Is Better",
          "last_name": null,
          "twitter_username": "boxedwater",
          "portfolio_url": "https://boxedwaterisbetter.com/?utm_source=brand-referral&utm_medium=unsplash&utm_campaign=stigmatize-plastic",
          "bio": "👋  We're a sustainable alternative to plastic water bottles.\r\n🚫  Ditch plastic bottles  🌲  Plant trees with us using #BetterPlanet Boxed Water is 100% pure, 92% sustainably packaged & 100%  recyclable. Let’s build a #BetterPlanet together. ",
          "location": "Holland, MI",
          "links": {
            "self": "https://api.unsplash.com/users/boxedwater",
            "html": "https://unsplash.com/@boxedwater",
            "photos": "https://api.unsplash.com/users/boxedwater/photos",
            "likes": "https://api.unsplash.com/users/boxedwater/likes",
            "portfolio": "https://api.unsplash.com/users/boxedwater/portfolio",
            "following": "https://api.unsplash.com/users/boxedwater/following",
            "followers": "https://api.unsplash.com/users/boxedwater/followers"
          },
          "profile_image": {
            "small": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
            "medium": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64",
            "large": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128"
          },
          "instagram_username": "boxedwater",
          "total_collections": 2,
          "total_likes": 3,
          "total_photos": 272,
          "total_promoted_photos": 20,
          "accepted_tos": true,
          "for_hire": false,
          "social": {
            "instagram_username": "boxedwater",
            "portfolio_url": "https://boxedwaterisbetter.com/?utm_source=brand-referral&utm_medium=unsplash&utm_campaign=stigmatize-plastic",
            "twitter_username": "boxedwater",
            "paypal_email": null
          }
        }
      },
      "topic_submissions": {},
      "user": {
        "id": "8Ae1yJe8OoA",
        "updated_at": "2023-12-19T00:49:57Z",
        "username": "boxedwater",
        "name": "Boxed Water Is Better",
        "first_name": "Boxed Water Is Better",
        "last_name": null,
        "twitter_username": "boxedwater",
        "portfolio_url": "https://boxedwaterisbetter.com/?utm_source=brand-referral&utm_medium=unsplash&utm_campaign=stigmatize-plastic",
        "bio": "👋  We're a sustainable alternative to plastic water bottles.\r\n🚫  Ditch plastic bottles  🌲  Plant trees with us using #BetterPlanet Boxed Water is 100% pure, 92% sustainably packaged & 100%  recyclable. Let’s build a #BetterPlanet together. ",
        "location": "Holland, MI",
        "links": {
          "self": "https://api.unsplash.com/users/boxedwater",
          "html": "https://unsplash.com/@boxedwater",
          "photos": "https://api.unsplash.com/users/boxedwater/photos",
          "likes": "https://api.unsplash.com/users/boxedwater/likes",
          "portfolio": "https://api.unsplash.com/users/boxedwater/portfolio",
          "following": "https://api.unsplash.com/users/boxedwater/following",
          "followers": "https://api.unsplash.com/users/boxedwater/followers"
        },
        "profile_image": {
          "small": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
          "medium": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64",
          "large": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128"
        },
        "instagram_username": "boxedwater",
        "total_collections": 2,
        "total_likes": 3,
        "total_photos": 272,
        "total_promoted_photos": 20,
        "accepted_tos": true,
        "for_hire": false,
        "social": {
          "instagram_username": "boxedwater",
          "portfolio_url": "https://boxedwaterisbetter.com/?utm_source=brand-referral&utm_medium=unsplash&utm_campaign=stigmatize-plastic",
          "twitter_username": "boxedwater",
          "paypal_email": null
        }
      },
      "tags": [
        {
          "type": "search",
          "title": "sea"
        },
        {
          "type": "landing_page",
          "title": "water",
          "source": {
            "ancestry": {
              "type": {
                "slug": "wallpapers",
                "pretty_slug": "HD Wallpapers"
              },
              "category": {
                "slug": "nature",
                "pretty_slug": "Nature"
              },
              "subcategory": {
                "slug": "water",
                "pretty_slug": "Water"
              }
            },
            "title": "Hd water wallpapers",
            "subtitle": "Download free water wallpapers",
            "description": "Choose from a curated selection of water wallpapers for your mobile and desktop screens. Always free on Unsplash.",
            "meta_title": "Water Wallpapers: Free HD Download [500+ HQ] | Unsplash",
            "meta_description": "Choose from hundreds of free water wallpapers. Download HD wallpapers for free on Unsplash.",
            "cover_photo": {
              "id": "fbbxMwwKqZk",
              "slug": "white-and-black-cardboard-box-fbbxMwwKqZk",
              "created_at": "2019-07-29T16:55:54Z",
              "updated_at": "2024-01-22T01:19:45Z",
              "promoted_at": null,
              "width": 3631,
              "height": 5446,
              "color": "#f3f3f3",
              "blur_hash": "LZMQ^s%hM_%M~qDiMx%MD`$`ofWBt7",
              "description": null,
              "alt_description": "white and black cardboard box",
              "breadcrumbs": [],
              "urls": {
                "raw": "https://images.unsplash.com/photo-1564419320461-6870880221ad?ixlib=rb-4.0.3",
                "full": "https://images.unsplash.com/photo-1564419320461-6870880221ad?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb",
                "regular": "https://images.unsplash.com/photo-1564419320461-6870880221ad?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max",
                "small": "https://images.unsplash.com/photo-1564419320461-6870880221ad?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max",
                "thumb": "https://images.unsplash.com/photo-1564419320461-6870880221ad?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max",
                "small_s3": "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1564419320461-6870880221ad"
              },
              "links": {
                "self": "https://api.unsplash.com/photos/white-and-black-cardboard-box-fbbxMwwKqZk",
                "html": "https://unsplash.com/photos/white-and-black-cardboard-box-fbbxMwwKqZk",
                "download": "https://unsplash.com/photos/fbbxMwwKqZk/download",
                "download_location": "https://api.unsplash.com/photos/fbbxMwwKqZk/download"
              },
              "likes": 591,
              "liked_by_user": false,
              "current_user_collections": [],
              "sponsorship": null,
              "topic_submissions": {},
              "premium": false,
              "plus": false,
              "user": {
                "id": "8Ae1yJe8OoA",
                "updated_at": "2023-12-19T00:49:57Z",
                "username": "boxedwater",
                "name": "Boxed Water Is Better",
                "first_name": "Boxed Water Is Better",
                "last_name": null,
                "twitter_username": "boxedwater",
                "portfolio_url": "https://boxedwaterisbetter.com/?utm_source=brand-referral&utm_medium=unsplash&utm_campaign=stigmatize-plastic",
                "bio": "👋  We're a sustainable alternative to plastic water bottles.\r\n🚫  Ditch plastic bottles  🌲  Plant trees with us using #BetterPlanet Boxed Water is 100% pure, 92% sustainably packaged & 100%  recyclable. Let’s build a #BetterPlanet together. ",
                "location": "Holland, MI",
                "links": {
                  "self": "https://api.unsplash.com/users/boxedwater",
                  "html": "https://unsplash.com/@boxedwater",
                  "photos": "https://api.unsplash.com/users/boxedwater/photos",
                  "likes": "https://api.unsplash.com/users/boxedwater/likes",
                  "portfolio": "https://api.unsplash.com/users/boxedwater/portfolio",
                  "following": "https://api.unsplash.com/users/boxedwater/following",
                  "followers": "https://api.unsplash.com/users/boxedwater/followers"
                },
                "profile_image": {
                  "small": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
                  "medium": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64",
                  "large": "https://images.unsplash.com/profile-1557251674406-effb9d313841?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128"
                },
                "instagram_username": "boxedwater",
                "total_collections": 2,
                "total_likes": 3,
                "total_photos": 272,
                "total_promoted_photos": 20,
                "accepted_tos": true,
                "for_hire": false,
                "social": {
                  "instagram_username": "boxedwater",
                  "portfolio_url": "https://boxedwaterisbetter.com/?utm_source=brand-referral&utm_medium=unsplash&utm_campaign=stigmatize-plastic",
                  "twitter_username": "boxedwater",
                  "paypal_email": null
                }
              }
            }
          }
        },
        {
          "type": "landing_page",
          "title": "nature",
          "source": {
            "ancestry": {
              "type": {
                "slug": "images",
                "pretty_slug": "Images"
              },
              "category": {
                "slug": "nature",
                "pretty_slug": "Nature"
              }
            },
            "title": "Nature images",
            "subtitle": "Download free nature images",
            "description": "Nature produces the most astoundingly beautiful images: the swirling lava of a volcano, palm trees against a blue sky, snow-capped mountains towering above. Unsplash has magnificent , high-quality photos of all the delights that nature has to offer.",
            "meta_title": "100+ Nature Pictures | Download Free Images & Stock Photos on Unsplash",
            "meta_description": "Choose from hundreds of free nature pictures. Download HD nature photos for free on Unsplash.",
            "cover_photo": {
              "id": "VE5FRc7uiC4",
              "slug": "star-in-space-VE5FRc7uiC4",
              "created_at": "2018-10-15T08:58:20Z",
              "updated_at": "2024-01-17T16:17:42Z",
              "promoted_at": "2018-10-15T12:23:00Z",
              "width": 4640,
              "height": 3480,
              "color": "#262640",
              "blur_hash": "L21o;CogI7WARNaxt9j]Mvaxxwof",
              "description": "lost in the sky",
              "alt_description": "star in space",
              "breadcrumbs": [
                {
                  "slug": "images",
                  "title": "1,000,000+ Free Images",
                  "index": 0,
                  "type": "landing_page"
                },
                {
                  "slug": "nature",
                  "title": "Nature Images",
                  "index": 1,
                  "type": "landing_page"
                }
              ],
              "urls": {
                "raw": "https://images.unsplash.com/photo-1539593395743-7da5ee10ff07?ixlib=rb-4.0.3",
                "full": "https://images.unsplash.com/photo-1539593395743-7da5ee10ff07?ixlib=rb-4.0.3&q=85&fm=jpg&crop=entropy&cs=srgb",
                "regular": "https://images.unsplash.com/photo-1539593395743-7da5ee10ff07?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max",
                "small": "https://images.unsplash.com/photo-1539593395743-7da5ee10ff07?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max",
                "thumb": "https://images.unsplash.com/photo-1539593395743-7da5ee10ff07?ixlib=rb-4.0.3&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max",
                "small_s3": "https://s3.us-west-2.amazonaws.com/images.unsplash.com/small/photo-1539593395743-7da5ee10ff07"
              },
              "links": {
                "self": "https://api.unsplash.com/photos/star-in-space-VE5FRc7uiC4",
                "html": "https://unsplash.com/photos/star-in-space-VE5FRc7uiC4",
                "download": "https://unsplash.com/photos/VE5FRc7uiC4/download",
                "download_location": "https://api.unsplash.com/photos/VE5FRc7uiC4/download"
              },
              "likes": 126,
              "liked_by_user": false,
              "current_user_collections": [],
              "sponsorship": null,
              "topic_submissions": {
                "nature": {
                  "status": "approved",
                  "approved_on": "2020-04-06T14:20:12Z"
                }
              },
              "premium": false,
              "plus": false,
              "user": {
                "id": "PvaYY7qgvqU",
                "updated_at": "2024-01-17T01:22:32Z",
                "username": "akin",
                "name": "Akin Cakiner",
                "first_name": "Akin",
                "last_name": "Cakiner",
                "twitter_username": "pausyworld",
                "portfolio_url": "https://akincakiner.com/",
                "bio": "Get your beautiful photos featured on Instagram!\r\nCheck out my page @creatpix   Create the moment #creatpix ",
                "location": "almelo",
                "links": {
                  "self": "https://api.unsplash.com/users/akin",
                  "html": "https://unsplash.com/@akin",
                  "photos": "https://api.unsplash.com/users/akin/photos",
                  "likes": "https://api.unsplash.com/users/akin/likes",
                  "portfolio": "https://api.unsplash.com/users/akin/portfolio",
                  "following": "https://api.unsplash.com/users/akin/following",
                  "followers": "https://api.unsplash.com/users/akin/followers"
                },
                "profile_image": {
                  "small": "https://images.unsplash.com/profile-1655048539859-cd496ee39bd9image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
                  "medium": "https://images.unsplash.com/profile-1655048539859-cd496ee39bd9image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=64&h=64",
                  "large": "https://images.unsplash.com/profile-1655048539859-cd496ee39bd9image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=128&h=128"
                },
                "instagram_username": "akinvisualz",
                "total_collections": 0,
                "total_likes": 32,
                "total_photos": 315,
                "total_promoted_photos": 26,
                "accepted_tos": true,
                "for_hire": true,
                "social": {
                  "instagram_username": "akinvisualz",
                  "portfolio_url": "https://akincakiner.com/",
                  "twitter_username": "pausyworld",
                  "paypal_email": null
                }
              }
            }
          }
        }
      ]
    }"""

    override suspend fun searchImage(query: String, page: Int, perPage: Int): List<Image> =
        withContext(_dispatcher) {
            val json = Json { ignoreUnknownKeys = true }
            val response = json.decodeFromString(ResultResponse.serializer(), test)
            (0..100).map {
                Image(
                    id = response.id + it,
                    thumbnailUrl = response.urls.thumb,
                    originUrl = response.urls.full,
                    author = response.user.username,
                    width = response.width,
                    height = response.height,
                    createdAt = response.createdAt,
                    liked = getByImageId(response.id + it) != null
                )
            }
//            _api.getSongList(query, page, perPage)
//                .results
//                .map { response ->
//                    Image(
//                        id = response.id,
//                        thumbnailUrl = response.urls.thumb,
//                        originUrl = response.urls.full,
//                        author = response.user.username,
//                        width = response.width,
//                        height = response.height,
//                        createdAt = response.createdAt,
//                        liked = getByImageId(response.id) != null
//                    )
//                }
        }

    override suspend fun insertLikedImage(image: Image): Long = withContext(_dispatcher) {
        _imageDao.insert(image.mapToData().copy(liked = true))
    }

    override suspend fun getById(id: Long): Image? = withContext(_dispatcher) {
        _imageDao.getById(id)?.mapToEntity()
    }

    override suspend fun getByImageId(imageId: String): Image? = withContext(_dispatcher) {
        _imageDao.getByImageId(imageId)?.mapToEntity()
    }

    override suspend fun removeByImageId(id: String) = withContext(_dispatcher) {
        _imageDao.deleteByImageId(id)
    }


    override suspend fun getAllLikedImages(): List<Image> = withContext(_dispatcher) {
        _imageDao.getAllLikedImage().map {
            it.mapToEntity()
        }
    }

    override fun getAllLikedImageFlow(): Flow<List<Image>> =
        _imageDao.getAllLikedImageFlow()
            .map { list ->
                list.map {
                    it.mapToEntity()
                }
            }

    private fun Image.mapToData(): ImageData {
        return ImageData(
            imageId = id,
            thumbnailUrl = thumbnailUrl,
            originUrl = originUrl,
            author = author,
            width = width,
            height = height,
            createdAt = createdAt,
            liked = liked,
        )
    }

    private fun ImageData.mapToEntity(): Image {
        return Image(
            id = imageId,
            thumbnailUrl = thumbnailUrl,
            originUrl = originUrl,
            author = author,
            width = width,
            height = height,
            createdAt = createdAt,
            liked = liked,
        )
    }

}