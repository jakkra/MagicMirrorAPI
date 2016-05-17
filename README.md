# MagicMirrorAPI
=======
An application to display a GUI behind a observation mirror on an Raspberry Pi or similar.

- News are fetched from New York Times.
- Weather from SMHI
- Bus departures from Skånetrafiken
- Reddit posts from Reddit's API

## Configuration
All configuration us done inside the config.properties file.

New York Times API key retrieval can be found here: http://developer.nytimes.com/

    # Weather location in long and latitude
    lat=13.20
    longitude=55.70

    # Skånetrafiken parameters, start and end station name
    hplatsD=Värnhem
    hplatsA=Lund LTH

    # Reddit parameters, subreddit to show posts from
    subreddit=programming
    #hot/new/rising/top
    sort=hot

    # News parameters world/technology/science/sports/trending
    newsCat=technology

    # APIkey for NYTimes received from http://developer.nytimes.com/
    APIkey=key

## Running
Have the jar and the config.properties files in the same folder before running.

    $ java -jar MagicMirrorAPI.jar

## Pics

![Mirror](pics/IMG_20160517_164826.jpg)

![Mirror](pics/IMG_20160517_164955.jpg)
