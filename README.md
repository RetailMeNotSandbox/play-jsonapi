Publishing locally
----------
For testing locally

    sbt publish-local

Publishing internally
----------
Credentials should be available at ~/.ivy2/.credentials

    realm=Sonatype Nexus Repository Manager
    host=maven.example.com
    user=***
    password=***

With the above credentials stored:

    sbt publish
