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


