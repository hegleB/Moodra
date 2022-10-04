
<p align="center">
<img width="150" alt="2021-12-13_22-09-52" src="https://user-images.githubusercontent.com/39490416/163661942-18f79cde-a3fb-4b4f-81bd-ae41063c7587.png">
  </p>

<p align="center">
<img src="https://img.shields.io/badge/Kotlin-1.6.21-005AF0&style= flat?color=blue">
<img src="https://img.shields.io/badge/AGP-4.1.3-005AF0&style= flat?color=blue">
<img src="https://img.shields.io/badge/Gradle-6.5-005AF0&style= flat&?color=blue">
</p>
<p align="center">
<img src="https://img.shields.io/badge/minSdkVersion-24-005AF0&style= flat&?color=DDE072">
<img src="https://img.shields.io/badge/compileSdkVersion-32-005AF0&style= flat&?color=FF7328">
<p>

<br></br>

## Moodra



#### TV프로그램과 영화에 대한 정보를 얻을 수 있는 앱이다.

<a href="https://play.google.com/store/apps/details?id=com.quere.moodra" target="_blank">
  <img src="https://user-images.githubusercontent.com/39490416/163449923-06d4b787-9fc9-438c-a9c9-dc035b9cec78.png" width="250">
</a>

<br></br>

# ScreenShots

<p align="center">
<br>
<img width="200" alt="2021-12-13_22-09-52" src="https://user-images.githubusercontent.com/39490416/146237995-2b963f6f-a3ad-4364-87d7-7e73461e7bd0.png">
<img width="200" alt="2021-12-13_22-16-56" src="https://user-images.githubusercontent.com/39490416/146238011-27fe9945-4e85-4ded-958e-7a5a8114f2bd.png">
<img width="200" alt="2021-12-13_22-26-10" src="https://user-images.githubusercontent.com/39490416/146238015-ec24badc-8215-4bd2-bfdf-1a567743915d.png">
<img width="200" alt="2021-12-13_22-30-43" src="https://user-images.githubusercontent.com/39490416/146238024-96db716d-84cc-41e4-8688-1c832ad4fb89.png">
  
</br>

<br>
<img width="200" alt="2021-12-13_22-40-25" src="https://user-images.githubusercontent.com/39490416/146238026-97b196ce-298c-4edc-87b1-dc468070f880.png">
<img width="200" alt="2021-12-13_22-41-53" src="https://user-images.githubusercontent.com/39490416/146238029-151e59b8-e865-43f2-af85-d9ac99361a4b.png">
<img width="200" alt="2021-12-13_22-44-08" src="https://user-images.githubusercontent.com/39490416/146238030-f1e558d7-c9c0-4953-9982-62dd5688e810.png">
<img width="200" alt="2021-12-13_22-45-05" src="https://user-images.githubusercontent.com/39490416/146238032-228bff73-92ea-4b9f-92a9-8cda05259986.png">
</br>
</p>

<br></br>


# Project Architecture
<img width="500" alt="clean_architecture" src="https://user-images.githubusercontent.com/39490416/193833099-4a7dd588-13fe-43a8-bf47-82e04b482b0f.png">

### Data Layer

```bash

├── base
│   └── BasePagingSource.kt
├── database
│   ├── BookmarkDao.kt
│   └── BookmarkDatabase.kt
├── paging
│   ├── common
│   │   ├── CreditPagingSource.kt
│   │   ├── GenreAllPagingSource.kt
│   │   ├── GenrePagingSource.kt
│   │   ├── GenreViewPagerPagingSource.kt
│   │   ├── RecommendPagingSource.kt
│   │   └── SimilarPagingSource.kt
│   ├── movie
│   │   ├── MoviePagingSource.kt
│   │   ├── MovieSearchAllPagingSource.kt
│   │   └── MovieSearchPagingSource.kt
│   └── tv
│       ├── TVPagingSource.kt
│       ├── TVSearchAllPagingSource.kt
│       └── TVSearchPagingSource.kt
├── repository
│   ├── local
│   │   └── BookmarkRepositoryImpl.kt
│   └── remote
│       ├── common
│       │   └── CommonRepositoryImpl.kt
│       ├── movie
│       │   └── MovieRepositoryImpl.kt
│       ├── search
│       │   └── SearchRepositoryImpl.kt
│       └── tv
│           └── TVshowRepositoryImpl.kt
└── service
    ├── commonservice
    │   └── CommonService.kt
    ├── model
    │   └── MovieResponse.kt
    ├── movieservice
    │   └── MovieService.kt
    └── tvservice
        └── TVService.kt
```

### Domain Layer

``` bash

├── model
│   ├── common
│   │   ├── Bookmark.kt
│   │   ├── Credit.kt
│   │   ├── Detail.kt
│   │   ├── Genre.kt
│   │   ├── OtherContent.kt
│   │   └── Trailer.kt
│   ├── movie
│   │   └── Movie.kt
│   └── tv
│       └── TVshow.kt
├── repository
│   ├── bookmark
│   │   └── BookmarkRepository.kt
│   ├── common
│   │   └── CommonRepository.kt
│   ├── movie
│   │   └── MovieRepository.kt
│   ├── search
│   │   └── SearchRepository.kt
│   └── tv
│       └── TVshowRepository.kt
└── usecase
    ├── CheckBookmarkUseCase.kt
    ├── DeleteAllBookarmkUseCase.kt
    ├── DeleteBookmarkUseCase.kt
    ├── GetBookmarkRepoUseCase.kt
    ├── GetCreditRepoUseCase.kt
    ├── GetGenreAllRepoUseCase.kt
    ├── GetGenreRepoUseCase.kt
    ├── GetMovieDetailRepoUseCase.kt
    ├── GetMoviePopularRepoUseCase.kt
    ├── GetMovieRepoUseCase.kt
    ├── GetMovieSearchAllRepoUseCase.kt
    ├── GetMovieSearchRepoUseCase.kt
    ├── GetRecommendRepoUseCase.kt
    ├── GetSimilarRepoUseCase.kt
    ├── GetTVDetailRepoUseCase.kt
    ├── GetTVSearchAllRepoUseCase.kt
    ├── GetTVSearchRepoUseCase.kt
    ├── GetTVshowRepoUseCase.kt
    ├── GetTrailerRepoUseCase.kt
    ├── GetTvPopularRepoUseCase.kt
    └── SaveBookmarkUseCase.kt

```

### Presenation

``` bash
├── AppConstants.kt
├── Event.kt
├── base
│   ├── BaseActivity.kt
│   ├── BaseAdapter.kt
│   ├── BaseFragment.kt
│   ├── BasePagingAdapter.kt
│   └── BaseViewHolder.kt
├── di
│   ├── App.kt
│   ├── LocalDataSourceModule.kt
│   ├── NetworkModule.kt
│   └── RepositoryModule.kt
├── utils
│   └── KeepStateNavigator.kt
├── view
│   ├── MainActivity.kt
│   ├── SplashActivity.kt
│   ├── adapter
│   │   ├── BindingAdapter
│   │   │   ├── ImageBindingAdapter.kt
│   │   │   └── TextBindingAdatper.kt
│   │   ├── BookmarkAdapter.kt
│   │   ├── ContentsAdapter.kt
│   │   ├── CreditAdapter.kt
│   │   ├── GenreAdapter.kt
│   │   ├── GenreAllAdapter.kt
│   │   ├── HorizontalItemDecorator.kt
│   │   ├── MoviePagingAdapter.kt
│   │   ├── MovieSearchPagingAdapter.kt
│   │   ├── MovieViewPagerAdapter.kt
│   │   ├── SearchDetailLoadStateAdapter.kt
│   │   ├── TVPagingAdapter.kt
│   │   ├── TVSearchPagingAdapter.kt
│   │   ├── TVViewPagerAdapter.kt
│   │   └── TrailerAdapter.kt
│   ├── bookmark
│   │   ├── BookmarkContainerFragment.kt
│   │   └── BookmarkFragment.kt
│   ├── detail
│   │   ├── DetailFragment.kt
│   │   └── VideoFragment.kt
│   ├── genre
│   │   ├── GenreAllFragment.kt
│   │   ├── movie
│   │   │   └── MovieGenreFragment.kt
│   │   └── tv
│   │       └── TVGenreFragment.kt
│   ├── home
│   │   ├── HomeContainerFragment.kt
│   │   └── HomeFragment.kt
│   └── search
│       ├── SearchAllFragment.kt
│       ├── SearchContainerFragment.kt
│       └── SearchFragment.kt
└── viewmodel
    ├── BookmarkViewModel.kt
    ├── DetailViewModel.kt
    ├── GenreViewModel.kt
    ├── HomeViewModel.kt
    └── SearchViewModel.kt
```


# Specifications used
|||
|---|---|
|아키텍쳐(Architecture), 디자인 패턴(Design Pattern)|Clean Architecture, MVVM, Repository Pattern|
|네트워크(Network)|Retrofit2|
|비동기처리(Async)|Coroutine, Flow|
|의존성 주입(Dependency Injection)|Dagger Hilt|
|내부 DB(Internal DB)|Room|
|이미지 처리(Image Load & Download)|Glide|
|JetPack|ViewModel, Data Binding, Lifecycles, LiveData, Navigation|
|Other|ViewPager2, CardView, RecyclerView|


<br></br>
# TMDB License
<img width="200" alt="2021-12-13_22-09-52" src="https://user-images.githubusercontent.com/39490416/163661827-22eb220f-295b-4c2c-b187-78d48cd5a04b.png">
TMDb respects the rights of copyright holders and publishers and requires all users to confirm they own the copyright or have permission from the copyright holder to upload content. We comply with the Digital Millennium Copyright Act (DMCA) and expeditiously remove content when properly notified, unless it reasonably appears to us that the content does not infringe upon copyright. Please note, however, that under Section 512(f) any person who knowingly materially misrepresents that material or activity is a copyright infringement may be subject to liability for damages. You should educate yourself as to whether content does, in fact infringe upon your copyright, or whether, for instance "fair use" under 17 U.S.C. §107 applies. If you are unsure whether the content you are reporting is infringing your legal rights, you may wish to seek legal guidance. Keep in mind that submitting intentionally misleading reports of infringement may be punishable under the Digital Millennium Copyright Act (DMCA) in the United States or similar laws in other countries.

For claims of copyright infringement, please contact our designated agent under the Digital Millennium Copyright Act:

Copyright Agent at: 489 S. El Camino Real, San Mateo, CA, 94402.

[API Terms of Use](https://www.themoviedb.org/documentation/api/terms-of-use?language=ko-KR)

