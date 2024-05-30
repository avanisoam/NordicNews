# NordicNews

### Table of Contents
- Project Overview
- Features
- Technologies Used
- Installation
- Usage
- Architecture
- Screenshots
- Contributing

<p float = "left">
<img src="./Requirement/Home pageHome_Page.png" width="100">
<img src="Requirement/Detail pageDetail_Page.png" width="100">
<img src="Requirement/FeedbackFeedback_page.png" width="100">
<img src="Requirement/ShareShare_page.png" width="100">
</p>

## APIs description

### API 1: Search for news articles that mention a specific topic or keyword
 ```
/everything
```

#### Examples:

All News from specific source, like ("bbc-news","the-verge","ars-technica"):
```
getNewsBySource(sources : String = "bbc-news", page : Int = 1) 

https://newsapi.org/v2/everything?sources=bbc-news&page=1&apiKey=API_KEY

```

All articles mentioning Apple from yesterday, sorted by popular publishers first:
```
searchNewsV1(keyword:String = "apple", from: LocalDate, to:LocalDate? = LocalDate.now())

https://newsapi.org/v2/everything?q=apple&from=2024-05-24&to=2024-05-24&sortBy=popularity&apiKey=API_KEY

```

All articles about Tesla from the last month, sorted by recent first:
```
searchNewsV2(keyword: String = "tesla", from: LocalDate? = LocalDate.now().minusMonths(1))

https://newsapi.org/v2/everything?q=tesla&from=2024-04-25&sortBy=publishedAt&apiKey=API_KEY
```
All articles published by the Wall Street Journal in the last 6 months, sorted by recent first:
```
SearchNewsByDomain(keyword: String = "wsj.com")

https://newsapi.org/v2/everything?domains=wsj.com&apiKey=API_KEY
```


### API 2: Get the current top headlines for a country or category
```
/top-headlines
```

#### Examples:

Top business headlines in the US right now:
```
getHeadlinesByCountryAndCategory(country : String = "us",category : String = "business")

https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=API_KEY
```

Top headlines from TechCrunch right now:

```
getHeadlinesBySource(source : String ="techcrunch")

https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=API_KEY
```
Nordic News - README
====================

Table of Contents
-----------------

1.  [Introduction](#introduction)
2.  [Features](#features)
3.  [Technologies Used](#technologies-used)
4.  [Installation](#installation)
5.  [Usage](#usage)
6.  [Screenshots](#screenshots)
7.  [Contributing](#contributing)
8.  [License](#license)
9.  [Contact](#contact)

Introduction
------------

**Nordic News** is an Android application designed to provide users with the latest news from the Nordic region. It aggregates news articles from various sources and presents them in a user-friendly format. The app is built with a focus on performance, usability, and accessibility.

Features
--------

*   **News Feed**: Display a list of the latest news articles.
*   **Article Details**: View detailed information about a selected article.
*   **Categories**: Browse news by different categories such as politics, sports, technology, etc.
*   **Search**: Search for news articles using keywords.
*   **Bookmarks**: Save articles to read later.
*   **Notifications**: Receive notifications for breaking news.
*   **Offline Mode**: Access previously loaded articles offline.

Technologies Used
-----------------

*   **Kotlin**: Primary programming language for Android development.
*   **Android Jetpack**: Suite of libraries to help with best practices and easier development.
    *   **LiveData**
    *   **ViewModel**
    *   **Room**
    *   **Navigation Component**
*   **Retrofit**: For network requests.
*   **Glide**: For image loading and caching.
*   **Firebase**: For push notifications and analytics.
*   **Coroutines**: For asynchronous programming.
*   **MVVM Architecture**: Model-View-ViewModel for a clean and maintainable codebase.

Installation
------------

### Prerequisites

*   Android Studio installed on your development machine.
*   A device or emulator running Android 5.0 (Lollipop) or higher.

### Steps

1.  Clone the repository:
    
    bash
    
    Copy code
    
    `git clone https://github.com/your-username/nordic-news.git`
    
2.  Open the project in Android Studio.
3.  Build the project to install dependencies.
4.  Run the application on your device or emulator.

Usage
-----

1.  Open the app to view the latest news articles.
2.  Tap on an article to view more details.
3.  Use the search bar to find specific articles.
4.  Navigate through different categories using the menu.
5.  Bookmark articles for later reading.
6.  Enable notifications to stay updated with breaking news.

Screenshots
-----------

Contributing
------------

Contributions are welcome! Please follow these steps to contribute:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature-branch`).
3.  Make your changes.
4.  Commit your changes (`git commit -m 'Add some feature'`).
5.  Push to the branch (`git push origin feature-branch`).
6.  Open a pull request.

License
-------

This project is licensed under the MIT License - see the LICENSE file for details.

Contact
-------

**Your Name**  
Email: your.email@example.com  
GitHub: [your-username](https://github.com/your-username)  
Twitter: [@your-twitter-handle](https://twitter.com/your-twitter-handle)

* * *

Feel free to reach out if you have any questions or need further assistance!

